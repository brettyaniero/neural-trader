package NeuralNetwork.SumFunctions;

import NeuralNetwork.Connection;

import java.util.ArrayList;

public class WeightedInputSumFunction implements InputSumFunction
{
    @Override
    public double inputSumFunction(ArrayList<Connection> inputConnections)
    {
        double sum = 0;

        for (int i = 0; i < inputConnections.size(); i++)
        {
            sum += inputConnections.get(i).getWeightedInput();
        }

        return sum;
    }
}
