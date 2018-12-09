import NeuralNetwork.NeuralNetwork;
import NeuralNetwork.ActivationFunctions.ActivationFunction;
import NeuralNetwork.ActivationFunctions.SigmoidActivationFunction;
import StockData.ConfidenceNormalizer.BinaryConfidenceNormalizer;
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

        // ArrayList<StockMovement> adxMovements = adxData.getStockData(API_KEY, "MSFT");
        // ArrayList<StockMovement> rsiMovements = rsiData.getStockData(API_KEY, "S");
        // ArrayList<StockMovement> trixMovements = trixData.getStockData(API_KEY, "MSFT");
        // ArrayList<StockMovement> cmoMovements = cmoData.getStockData(API_KEY, "S");
        // ArrayList<StockMovement> cciMovements = cciData.getStockData(API_KEY, "S");

        /* Initialize the neural network */
        NeuralNetwork neuralNetwork = new NeuralNetwork(1, 3, 1, 0.3);
        ConfidenceNormalizer normalizer = new StepwiseConfidenceNormalizer();

        /* Error sets */
        int errorSet0 = 0;
        int errorSet1 = 0;
        int errorSet2 = 0;
        int errorSet3 = 0;
        int errorSet4 = 0;

        /* Train the neural network */
        for (int i = 1; i < 5268; i++)
        {
            ArrayList<Double> technicals = new ArrayList<>();
            // technicals.add(cmoMovements.get(i).getTechIndicatorVal());
            // technicals.add(rsiMovements.get(i).getTechIndicatorVal());
            // technicals.add(trixMovements.get(i).getTechIndicatorVal());
            // technicals.add(adxMovements.get(i).getTechIndicatorVal());
            // technicals.add(cciMovements.get(i).getTechIndicatorVal());
            //double actualOutput = neuralNetwork.trainNetwork(technicals, normalizer.normalize(trixMovements.get(i).getStockMovementPct()));
            //double error = Math.abs(normalizer.normalize(trixMovements.get(i).getStockMovementPct()) - actualOutput);
            technicals.add(normalizer.normalize(i));
            double actualOutput = neuralNetwork.trainNetwork(technicals, normalizer.normalize((i)));
            double error = Math.abs(normalizer.normalize(i) - actualOutput);

            if (error < 0.1)
                errorSet0++;
            else if (error >= 0.1 && error < 0.2)
                errorSet1++;
            else if (error >= 0.2 && error < 0.3)
                errorSet2++;
            else if (error >= 0.3 && error < 0.4)
                errorSet3++;
            else
                errorSet4++;
        }
        System.out.println();
    }
}
