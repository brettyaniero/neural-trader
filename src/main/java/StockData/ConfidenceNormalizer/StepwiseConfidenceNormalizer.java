package StockData.ConfidenceNormalizer;

public class StepwiseConfidenceNormalizer implements ConfidenceNormalizer
{
    public double normalize(double stockPctChg)
    {
        if (stockPctChg <= -0.01)           return 0;
        else if (stockPctChg < -0.009)      return 0.05;
        else if (stockPctChg < -0.008)       return 0.1;
        else if (stockPctChg < -0.007)      return 0.15;
        else if (stockPctChg < -0.006)       return 0.2;
        else if (stockPctChg < -0.005)      return 0.25;
        else if (stockPctChg < -0.004)       return 0.3;
        else if (stockPctChg < -0.003)      return 0.35;
        else if (stockPctChg < -0.002)      return 0.4;
        else if (stockPctChg < -0.001)      return 0.45;
        else if (stockPctChg < 0)           return 0.5;
        else if (stockPctChg < 0.001)       return 0.55;
        else if (stockPctChg < 0.002)        return 0.6;
        else if (stockPctChg < 0.003)       return 0.65;
        else if (stockPctChg < 0.004)        return 0.70;
        else if (stockPctChg < 0.005)       return 0.75;
        else if (stockPctChg < 0.006)        return 0.80;
        else if (stockPctChg < 0.007)       return 0.85;
        else if (stockPctChg < 0.008)        return 0.90;
        else if (stockPctChg < 0.009)       return 0.95;
        else                                return 1;
    }
}
