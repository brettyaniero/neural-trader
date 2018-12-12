import NeuralNetwork.NeuralNetwork;
import StockData.ConfidenceNormalizer.ConfidenceNormalizer;
import StockData.ConfidenceNormalizer.StepwiseConfidenceNormalizer;
import StockData.StockData;
import StockData.TechnicalIndicators.*;
import StockData.StockMovement;

import java.util.ArrayList;

public class App
{
    public static final String API_KEY = "O9KRO0R0C0BTVR9Z";

    public static void main(String[] args)
    {
        /* Initialize the technical indicators to use */
        StockData adxData, cciData, cmoData, rsiData, trixData;
        adxData = new ADXStockData();
        cciData = new CCIStockData();
        cmoData = new CMOStockData();
        rsiData = new RSIStockData();
        trixData = new TRIXStockData();

        ArrayList<StockMovement> adxMovements = adxData.getStockData(API_KEY, "MU");
        ArrayList<StockMovement> rsiMovements = rsiData.getStockData(API_KEY, "MU");
        ArrayList<StockMovement> trixMovements = trixData.getStockData(API_KEY, "MU");
        ArrayList<StockMovement> cmoMovements = cmoData.getStockData(API_KEY, "MU");
        ArrayList<StockMovement> cciMovements = cciData.getStockData(API_KEY, "MU");

        /* Initialize the neural network */
        NeuralNetwork neuralNetwork = new NeuralNetwork(1, 8, 5, 9);
        ConfidenceNormalizer normalizer = new StepwiseConfidenceNormalizer();

        /* Error sets */
        int errorSet1 = 0;
        int errorSet2 = 0;
        int errorSet3 = 0;
        int errorSet4 = 0;
        int errorSet5 = 0;
        int errorSet6 = 0;
        int errorSet7 = 0;
        int errorSet8 = 0;
        int errorSet9 = 0;
        int errorSet10 = 0;

        /* Train the neural network */
        for (int i = 0; i < adxMovements.size(); i++)
        {
            /* Add the technical indicators to the neural network input */
            ArrayList<Double> technicals = new ArrayList<>();
            technicals.add(cmoMovements.get(i).getTechIndicatorVal());
            technicals.add(rsiMovements.get(i).getTechIndicatorVal());
            technicals.add(trixMovements.get(i).getTechIndicatorVal());
            technicals.add(adxMovements.get(i).getTechIndicatorVal());
            technicals.add(cciMovements.get(i).getTechIndicatorVal());

            /* Run the training sample */
            double actualOutput = neuralNetwork.trainNetwork(technicals, normalizer.normalize(adxMovements.get(i).getStockMovementPct()));
            double error = Math.abs(normalizer.normalize(adxMovements.get(i).getStockMovementPct()) - actualOutput);

            if (error < 0.1)
                errorSet1++;
            else if (error >= 0.1 && error < 0.2)
                errorSet2++;
            else if (error >= 0.2 && error < 0.3)
                errorSet3++;
            else if (error >= 0.3 && error < 0.4)
                errorSet4++;
            else if (error >= 0.4 && error < 0.5)
                errorSet5++;
            else if (error >= 0.5 && error < 0.6)
                errorSet6++;
            else if (error >= 0.6 && error < 0.7)
                errorSet7++;
            else if (error >= 0.7 && error < 0.8)
                errorSet8++;
            else if (error >= 0.8 && error < 0.9)
                errorSet9++;
            else
                errorSet10++;
        }
    }
}
