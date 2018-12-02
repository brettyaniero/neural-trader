package NeuralNetwork;

public class SigmoidActivationFunction implements ActivationFunction
{
    @Override
    public double normalize(double input)
    {
        /* Sigmoid function */
        return 1 / (1 + Math.exp(-input));
    }
}
