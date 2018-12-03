package NeuralNetwork;

public class SigmoidActivationFunction implements ActivationFunction
{
    @Override
    public double normalize(double input)
    {
        /* Sigmoid function */
        System.out.println("Input: " + input);
        System.out.println("Output: " + 1 / (1 + Math.exp(-input)));
        return 1 / (1 + Math.exp(-input));
    }
}
