package NeuralNetwork;

public class Connection
{
    /* The starting and terminating neurons */
    protected Neuron fromNeuron;
    protected Neuron toNeuron;

    /* The weight of the current connection between the two nodes */
    protected double edgeWeight;

    public Connection(Neuron fromNeuron, Neuron toNeuron, double edgeWeight)
    {
        this.fromNeuron = fromNeuron;
        this.toNeuron = toNeuron;
        this.edgeWeight = edgeWeight;
    }

    public double getWeightedInput()
    {
        return fromNeuron.getOutput() * edgeWeight;
    }
}
