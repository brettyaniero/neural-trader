package NeuralNetwork;

public class SigmoidActivationFunction implements ActivationFunction
{
    @Override
    public double activationFunction(double input)
    {
        /* Sigmoid function */
        return 1 / (1 + Math.exp(-input));
    }
}
