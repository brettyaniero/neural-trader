package StockData.ConfidenceNormalizer;

public class StepwiseConfidenceNormalizer implements ConfidenceNormalizer
{
    public double normalize(double stockPctChg)
    {
        if (stockPctChg <= -0.05)           return -1;
        else if (stockPctChg < -0.045)      return -0.9;
        else if (stockPctChg < -0.04)       return -0.8;
        else if (stockPctChg < -0.035)      return -0.7;
        else if (stockPctChg < -0.03)       return -0.6;
        else if (stockPctChg < -0.025)      return -0.5;
        else if (stockPctChg < -0.02)       return -0.4;
        else if (stockPctChg < -0.015)      return -0.3;
        else if (stockPctChg < -0.010)      return -0.2;
        else if (stockPctChg < -0.005)      return -0.1;
        else if (stockPctChg < 0)           return 0;
        else if (stockPctChg < 0.005)       return 0.1;
        else if (stockPctChg < 0.01)        return 0.2;
        else if (stockPctChg < 0.015)       return 0.3;
        else if (stockPctChg < 0.02)        return 0.4;
        else if (stockPctChg < 0.025)       return 0.5;
        else if (stockPctChg < 0.03)        return 0.6;
        else if (stockPctChg < 0.035)       return 0.7;
        else if (stockPctChg < 0.04)        return 0.8;
        else if (stockPctChg < 0.045)       return 0.9;
        else                                return 1;
    }
}
