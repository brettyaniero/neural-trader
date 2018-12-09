package StockData.TechnicalIndicators;

import StockData.StockData;
import StockData.StockMovement;
import org.patriques.AlphaVantageConnector;
import org.patriques.TechnicalIndicators;
import org.patriques.TimeSeries;
import org.patriques.input.technicalindicators.Interval;
import org.patriques.input.technicalindicators.SeriesType;
import org.patriques.input.technicalindicators.TimePeriod;
import org.patriques.input.timeseries.OutputSize;
import org.patriques.output.AlphaVantageException;
import org.patriques.output.technicalindicators.TRIX;
import org.patriques.output.technicalindicators.data.IndicatorData;
import org.patriques.output.timeseries.Daily;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/*
 * This object retrieves the triple exponential average (TRIX) value of
 * any given stock.
 */

public class TRIXStockData implements StockData
{
    @Override
    public ArrayList<StockMovement> getStockData(String api_key, String tickerSymbol)
    {
        ArrayList<StockMovement> stockMovements = new ArrayList<StockMovement>();

        int timeout = 3000;
        AlphaVantageConnector apiConnector = new AlphaVantageConnector(api_key, timeout);
        TimeSeries stockTimeSeries = new TimeSeries(apiConnector);
        TechnicalIndicators technicalIndicators = new TechnicalIndicators(apiConnector);

        try
        {
            /* Get the daily stock price movements */
            Daily timeSeriesResponse = stockTimeSeries.daily(tickerSymbol, OutputSize.FULL);
            List<org.patriques.output.timeseries.data.StockData> stockData =
                    timeSeriesResponse.getStockData();

            stockData.forEach(stock -> {
                StockMovement dailyStockMovement = new StockMovement("ADX", stock.getDateTime());
                dailyStockMovement.setStockMovementPct((stock.getClose() - stock.getOpen()) / stock.getOpen());
                stockMovements.add(dailyStockMovement);
            });

            /* Get the CMO of the stock */
            TRIX techIndicatorsResponse = technicalIndicators.trix(tickerSymbol, Interval.DAILY, TimePeriod.of(10),
                    SeriesType.OPEN);
            List<IndicatorData> trixData = techIndicatorsResponse.getData();

            trixData.forEach(data -> {
                for (int i = 0; i < stockMovements.size(); i++)
                {
                    LocalDateTime date = stockMovements.get(i).getLocalDateTime();

                    if (date.getDayOfYear() == data.getDateTime().getDayOfYear()
                            && date.getYear() == data.getDateTime().getYear())
                    {
                        StockMovement dailyTechIndicator = stockMovements.get(i);
                        dailyTechIndicator.setTechIndicatorVal(data.getData());
                    }
                }
            });
        } catch (AlphaVantageException exception)
        {
            System.out.println("Something went wrong when querying API.");
        }

        return stockMovements;
    }
}
