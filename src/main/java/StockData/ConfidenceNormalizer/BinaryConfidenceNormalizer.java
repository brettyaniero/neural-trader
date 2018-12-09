package StockData.ConfidenceNormalizer;

public class BinaryConfidenceNormalizer implements ConfidenceNormalizer
{
    public double normalize(double input)
    {
        if (input <= 0)
            return 0;
        else
            return 1;
    }
}
