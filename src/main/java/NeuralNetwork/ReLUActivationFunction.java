package NeuralNetwork;

public class ReLUActivationFunction implements ActivationFunction
{
    public double normalize(double input)
    {
         if (input < 0)
         {
             return 0;
         }
         else
         {
             return input;
         }
    }
}
