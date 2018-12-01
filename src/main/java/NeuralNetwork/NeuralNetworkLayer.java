package NeuralNetwork;

import java.util.ArrayList;

public class NeuralNetworkLayer
{
    private ArrayList<Neuron> neurons;

    public NeuralNetworkLayer()
    {

    }

    public NeuralNetworkLayer(ArrayList<Neuron> neurons)
    {
       this.neurons = neurons;
    }
}
