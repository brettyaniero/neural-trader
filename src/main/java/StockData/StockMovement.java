package StockData;

import java.time.LocalDateTime;

public class StockMovement
{
    private String techIndicator;
    private double techIndicatorVal;
    private double stockMovementPct;
    private LocalDateTime date;

    public StockMovement(String techIndicator, LocalDateTime date)
    {
        this.techIndicator = techIndicator;
        this.techIndicatorVal = 0;
        this.stockMovementPct = 0;
        this.date = date;
    }

    public void setTechIndicatorVal(double value)
    {
        this.techIndicatorVal = value;
    }

    public void setStockMovementPct(double value)
    {
        this.stockMovementPct = value;
    }

    public double getTechIndicatorVal()
    {
        return this.techIndicatorVal;
    }

    public double getStockMovementPct()
    {
        return this.stockMovementPct;
    }

    public LocalDateTime getLocalDateTime()
    {
        return this.date;
    }
}
