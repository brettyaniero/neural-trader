package StockData;

import StockData.StockData;
import org.patriques.AlphaVantageConnector;
import org.patriques.TechnicalIndicators;
import org.patriques.TimeSeries;
import org.patriques.input.technicalindicators.Interval;
import org.patriques.input.technicalindicators.SeriesType;
import org.patriques.input.technicalindicators.TimePeriod;
import org.patriques.input.timeseries.OutputSize;
import org.patriques.output.AlphaVantageException;
import org.patriques.output.technicalindicators.RSI;
import org.patriques.output.technicalindicators.SMA;
import org.patriques.output.technicalindicators.data.IndicatorData;
import org.patriques.output.timeseries.Daily;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/*
 * This object retrieves the relative strength index (RSI) history of
 * any given stock.
 */

public class RSIStockData implements StockData
{
    @Override
    public ArrayList<StockMovement> getStockData(String api_key, String tickerSymbol, int targetHour)
    {
        ArrayList<StockMovement> stockMovements = new ArrayList<StockMovement>();

        int timeout = 3000;
        AlphaVantageConnector apiConnector = new AlphaVantageConnector(api_key, timeout);
        TimeSeries stockTimeSeries = new TimeSeries(apiConnector);
        TechnicalIndicators technicalIndicators = new TechnicalIndicators(apiConnector);

        try
        {
            /* Get the daily stock price movements */
            Daily timeSeriesResponse = stockTimeSeries.daily(tickerSymbol, OutputSize.COMPACT);
            List<org.patriques.output.timeseries.data.StockData> stockData =
                    timeSeriesResponse.getStockData();

            stockData.forEach(stock -> {
                StockMovement dailyStockMovement = new StockMovement("RSI", stock.getDateTime());
                dailyStockMovement.setStockMovementPct((stock.getClose() - stock.getOpen()) / stock.getOpen());
                stockMovements.add(dailyStockMovement);
            });

            /* Get the RSI of the stock */
            RSI techIndicatorsResponse = technicalIndicators.rsi(tickerSymbol, Interval.SIXTY_MIN, TimePeriod.of(10),
                    SeriesType.OPEN);
            List<IndicatorData> rsiData = techIndicatorsResponse.getData();

            rsiData.forEach(data -> {
                if (data.getDateTime().getHour() == targetHour)
                {
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
                }
            });
        } catch (AlphaVantageException exception)
        {
            System.out.println("Something went wrong when querying API.");
        }

        return stockMovements;
    }
}
