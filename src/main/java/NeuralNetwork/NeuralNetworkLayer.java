package NeuralNetwork;

import java.util.ArrayList;

public class NeuralNetworkLayer
{
    protected ArrayList<Neuron> neurons;

    public NeuralNetworkLayer()
    {
        neurons = new ArrayList<>();
    }

    public NeuralNetworkLayer(ArrayList<Neuron> neurons)
    {
       this.neurons = neurons;
    }

    public void setNeurons(ArrayList<Neuron> neurons)
    {
        this.neurons = neurons;
    }
}
