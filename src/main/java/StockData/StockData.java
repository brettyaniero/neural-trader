package StockData;

import java.util.ArrayList;

public interface StockData
{
    ArrayList<StockMovement> getStockData(String api_key, String tickerSymbol, int targetHour);
}
