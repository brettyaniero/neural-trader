package NeuralNetwork;

import NeuralNetwork.ActivationFunctions.ActivationFunction;
import NeuralNetwork.ActivationFunctions.SigmoidActivationFunction;
import NeuralNetwork.SumFunctions.InputSumFunction;
import NeuralNetwork.SumFunctions.WeightedInputSumFunction;

import java.util.ArrayList;

public class Neuron
{
    /* Connections between previous neurons */
    protected ArrayList<Connection> inputConnections;

    /* Connections between outgoing neurons */
    protected ArrayList<Connection> outputConnections;

    /* The sum function to be used for the inputs */
    protected InputSumFunction sumFunction;

    /* The activation function to be used on the summed inputs */
    protected ActivationFunction activationFunction;

    /* The error in weight to be used during gradient descent */
    protected double deltaError = 0;

    /* Start value if this neuron is part of the input layer */
    protected double startValue = 0;

    /* Starting bias of the neuron */
    protected double bias = 0.5;


    public Neuron()
    {
        this.inputConnections = new ArrayList<>();
        this.outputConnections = new ArrayList<>();
        sumFunction = new WeightedInputSumFunction();
        activationFunction = new SigmoidActivationFunction();
    }

    /* This constructor should be used if this neuron is part of the input layer */
    public Neuron(double startValue)
    {
        this.inputConnections = new ArrayList<>();
        this.outputConnections = new ArrayList<>();
        sumFunction = new WeightedInputSumFunction();
        activationFunction = new SigmoidActivationFunction();
        this.startValue = startValue;
    }

    public double getOutput()
    {
        if (inputConnections.size() > 0)
        {
            double input = sumFunction.inputSumFunction(inputConnections);
            input += this.bias;

            return activationFunction.normalize(input);
        }
        else
        {
            return startValue;
        }
    }
}
