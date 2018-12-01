package NeuralNetwork;

import java.util.ArrayList;

public class Neuron
{
    /* Connections between previous neurons */
    private ArrayList<Connection> inputConnections;

    /* Connections between outgoing neurons */
    private ArrayList<Connection> outputConnections;

    /* The sum function to be used for the inputs */
    private InputSumFunction sumFunction;

    /* The activation function to be used on the summed inputs */
    private ActivationFunction activationFunction;

    public Neuron()
    {
        this.inputConnections = new ArrayList<>();
        this.outputConnections = new ArrayList<>();
    }

    public double getOutput()
    {
        double input = sumFunction.inputSumFunction(inputConnections);

        return activationFunction.activationFunction(input);
    }
}
