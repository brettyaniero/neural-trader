package NeuralNetwork;

import java.util.ArrayList;

public class NeuralNetwork
{
    /* Each neuron in the input layer will contain the technical indicators to
       be fed through the neural network. */
    protected NeuralNetworkLayer inputLayer;
    protected NeuralNetworkLayer outputLayer;
    protected ArrayList<NeuralNetworkLayer> hiddenLayers;
    protected double learningRate;

    private double actualOutput;
    private double targetOutput;

    private double error = 0;

    public NeuralNetwork(int numLayers, int numNeurons)
    {
        /* Initialize input and output layers */
        inputLayer = new NeuralNetworkLayer();
        ArrayList<Neuron> inputNeurons = new ArrayList<>();
        inputNeurons.add(new Neuron(0.99));
        inputNeurons.add(new Neuron(0.67));
        inputLayer.neurons = inputNeurons;
        outputLayer = new NeuralNetworkLayer();
        ArrayList<Neuron> outputNeurons = new ArrayList<>();
        outputNeurons.add(new Neuron());
        outputNeurons.add(new Neuron());
        outputLayer.neurons = outputNeurons;

        /* Create hidden layers */
        hiddenLayers = new ArrayList<>();
        createHiddenLayers(numLayers, numNeurons);

        /* Create connections between neurons */
        createConnections();

        actualOutput = outputLayer.neurons.get(0).getOutput();
        targetOutput = 2;
        learningRate = 0.4;
        System.out.println("Output 1: " + actualOutput);
        this.gradientDescent();
        this.updateEdgeWeights();
    }

    private void createHiddenLayers(int numLayers, int numNeurons)
    {
        for (int i = 0; i < numLayers; i++)
        {
            NeuralNetworkLayer layer = new NeuralNetworkLayer();
            ArrayList<Neuron> neurons = new ArrayList<Neuron>();

            for (int j = 0; j < numNeurons; j++)
            {
                Neuron neuron = new Neuron();
                neurons.add(neuron);
            }

            layer.neurons = neurons;
            hiddenLayers.add(layer);
        }
    }

    private void createInputConnections()
    {
        /* Create connections between input layer and the first hidden layer */
        for (int hiddenLayerNeuron = 0; hiddenLayerNeuron < hiddenLayers.get(0).neurons.size(); hiddenLayerNeuron++)
        {
            for (int inputLayerNeuron = 0; inputLayerNeuron < inputLayer.neurons.size(); inputLayerNeuron++)
            {
                Connection connection = new Connection(
                        inputLayer.neurons.get(inputLayerNeuron),
                        hiddenLayers.get(0).neurons.get(hiddenLayerNeuron),
                        Math.random()
                );

                inputLayer.neurons.get(inputLayerNeuron).outputConnections.add(connection);
                hiddenLayers.get(0).neurons.get(hiddenLayerNeuron).inputConnections.add(connection);
            }
        }
    }

    private void createHiddenLayerConnections()
    {
        /* Create connections between subsequent hidden layers */
        if (hiddenLayers.size() > 1)
        {
            /*
             * If there are 3 hidden layers, we want this loop to run twice:
             *      1. Connections between hidden layer 1 & 2
             *      2. Connections between hidden layer 2 & 3
             * Thus, we will use hiddenLayers.size() - 1 as the upper bound
             */
            for (int hiddenLayer = 0; hiddenLayer < hiddenLayers.size() - 1; hiddenLayer++)
            {
                /* Loop through the neurons in the first of the two layers we're currently handling */
                for (int firstLayerNeuron = 0; firstLayerNeuron < hiddenLayers.get(hiddenLayer).neurons.size();
                     firstLayerNeuron++)
                {
                    /* Loop through the neurons in the second of the two layers we're currently handling */
                    for (int secondLayerNeuron = 0; secondLayerNeuron
                            < hiddenLayers.get(hiddenLayer + 1).neurons.size(); secondLayerNeuron++)
                    {
                        /* Create the connection between each pair of neurons between the two layers */
                        Connection connection = new Connection(
                                hiddenLayers.get(hiddenLayer).neurons.get(firstLayerNeuron),
                                hiddenLayers.get(hiddenLayer + 1).neurons.get(secondLayerNeuron),
                                Math.random()
                        );

                        hiddenLayers.get(hiddenLayer).neurons.get(firstLayerNeuron).outputConnections.add(connection);
                        hiddenLayers.get(hiddenLayer + 1).neurons.get(secondLayerNeuron).inputConnections
                                .add(connection);
                    }
                }
            }
        }
    }

    private void createOutputConnections()
    {
        /* Create connections between the last hidden layer and the output layer */
        for (int hiddenLayerNeuron = 0; hiddenLayerNeuron < hiddenLayers.get(hiddenLayers.size() - 1).neurons.size();
             hiddenLayerNeuron++)
        {
            for (int outputLayerNeuron = 0; outputLayerNeuron < outputLayer.neurons.size(); outputLayerNeuron++)
            {
                Connection connection = new Connection(
                        hiddenLayers.get(hiddenLayers.size() - 1).neurons.get(hiddenLayerNeuron),
                        outputLayer.neurons.get(outputLayerNeuron),
                        Math.random()
                );

                hiddenLayers.get(hiddenLayers.size() - 1).neurons.get(hiddenLayerNeuron).outputConnections
                        .add(connection);
                outputLayer.neurons.get(outputLayerNeuron).inputConnections.add(connection);
            }
        }
    }

    private void createConnections()
    {
        createInputConnections();
        createHiddenLayerConnections();
        createOutputConnections();
    }

    private void gradientDescent()
    {
        /* Start with the output layer and calculate error */
        double derivedError = this.actualOutput - this.targetOutput;
        int reluDerivativeValue = this.actualOutput > 0 ? 1 : 0;
        this.outputLayer.neurons.get(0).deltaError = derivedError * reluDerivativeValue;

        /* Move backwards through the hidden layers, calculating delta error value as we go */
        for (int layer = this.hiddenLayers.size() - 1; layer >= 0; layer--)
        {
            for (int neuron = 0; neuron < this.hiddenLayers.get(layer).neurons.size(); neuron++)
            {
                double deltaSum = 0;

                for (int connection = 0; connection < this.hiddenLayers.get(layer).neurons.get(neuron)
                        .outputConnections.size(); connection++)
                {
                    double edgeWeight = this.hiddenLayers.get(layer).neurons.get(neuron).outputConnections
                            .get(connection).edgeWeight;
                    double deltaError = this.hiddenLayers.get(layer).neurons.get(neuron).outputConnections
                            .get(connection).toNeuron.deltaError;
                    deltaSum += edgeWeight * deltaError;
                }

                this.hiddenLayers.get(layer).neurons.get(neuron).deltaError = deltaSum;
            }
        }

        /* Last step is to calculate the delta error for the input layer connections */
        for (int neuron = 0; neuron < this.inputLayer.neurons.size(); neuron++)
        {
            double deltaSum = 0;

            for (int connection = 0; connection < this.inputLayer.neurons.get(neuron)
                    .outputConnections.size(); connection++)
            {
                double edgeWeight = this.inputLayer.neurons.get(neuron).outputConnections
                        .get(connection).edgeWeight;
                double deltaError = this.inputLayer.neurons.get(neuron).outputConnections
                        .get(connection).toNeuron.deltaError;
                deltaSum += edgeWeight * deltaError;
            }

            this.inputLayer.neurons.get(neuron).deltaError = deltaSum;
        }
    }

    private void updateEdgeWeights()
    {
        /* Update edge weights for the hidden layers */
        for (int layer = 1; layer < this.hiddenLayers.size(); layer++)
        {
            for (int neuron = 0; neuron < this.hiddenLayers.get(layer).neurons.size(); neuron++)
            {
                for (int connection = 0; connection < this.hiddenLayers.get(layer).neurons.get(neuron)
                        .inputConnections.size(); connection++)
                {
                    double deltaWeight = (-1) * this.learningRate
                            * this.hiddenLayers.get(layer).neurons.get(neuron).deltaError
                            * this.hiddenLayers.get(layer).neurons.get(neuron).getOutput() > 0 ? 1 : 0
                            * this.hiddenLayers.get(layer).neurons.get(neuron).inputConnections.get(connection)
                                .fromNeuron.getOutput();
                    this.hiddenLayers.get(layer).neurons.get(neuron).inputConnections.get(connection).edgeWeight
                            += deltaWeight;
                }
            }
        }

        /* Update edge weights for the output layer */
        for (int connection = 0; connection < this.outputLayer.neurons.get(0).inputConnections.size(); connection++)
        {
            double deltaWeight = (-1) * this.learningRate
                    * this.outputLayer.neurons.get(0).deltaError
                    * this.outputLayer.neurons.get(0).getOutput() > 0 ? 1 : 0
                    * this.outputLayer.neurons.get(0).inputConnections.get(connection)
                        .fromNeuron.getOutput();
            this.outputLayer.neurons.get(0).inputConnections.get(connection).edgeWeight
                    += deltaWeight;
        }
    }
}
