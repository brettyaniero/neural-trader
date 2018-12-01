package NeuralNetwork;

public class Connection
{
    /* The starting and terminating neurons */
    private Neuron fromNeuron;
    private Neuron toNeuron;

    /* The weight of the current connection between the two nodes */
    private double edgeWeight;

    public Connection(Neuron fromNeuron, Neuron toNeuron)
    {
        this.fromNeuron = fromNeuron;
        this.toNeuron = toNeuron;
    }

    public Connection(Neuron fromNeuron, Neuron toNeuron, double edgeWeight)
    {
        this.fromNeuron = fromNeuron;
        this.toNeuron = toNeuron;
        this.edgeWeight = edgeWeight;
    }

    public double getEdgeWeight()
    {
        return this.edgeWeight;
    }

    public void setEdgeWeight(double edgeWeight)
    {
        this.edgeWeight = edgeWeight;
    }

    public double getInput()
    {
        return fromNeuron.getOutput();
    }

    public double getWeightedInput()
    {
        return fromNeuron.getOutput() * edgeWeight;
    }

    public Neuron getFromNeuron()
    {
        return this.fromNeuron;
    }

    public Neuron getToNeuron()
    {
        return this.toNeuron;
    }
}
