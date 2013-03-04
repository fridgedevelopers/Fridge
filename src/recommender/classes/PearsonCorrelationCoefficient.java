package recommender.classes;

import recommender.util.OrderedPair;

public final class PearsonCorrelationCoefficient
{
    private PearsonCorrelationCoefficient() throws Exception
    {
        throw new Exception("Uninstantiable class.");
    }
    
    private static double summation(double[] var)
    {
        double summation = 0;
        
        for(int i = 0; i < var.length; i++)
            summation += var[i];
        
        return summation;
    }
    
    private static double summation(double[] xVar, double[] yVar)
    {
        double summation = 0;
        
        for(int i = 0; i < xVar.length; i++)
            summation += xVar[i] * yVar[i];
        
        return summation;
    }
    
    private static double squareRoot(double value)
    {
        return Math.sqrt(value);
    }
    
    private static double square(double value)
    {
        return value * value;
    }
    
    private static double summationOfSquares(double[] var)
    {
        double summation = 0;
        
        for(int i = 0; i < var.length; i++)
            summation += square(var[i]);
        
        return summation;
    }
    
    public static double getCorrelation(OrderedPair[] orderedPairs) throws Exception
    {
        if(orderedPairs.length <= 1)
            throw new Exception("Number of elements must be more than 1.");
        
        int size = orderedPairs.length;
        double[] xVar = new double[size];
        double[] yVar = new double[size];
        
        for(int i = 0; i < size; i++)
        {
            xVar[i] = orderedPairs[i].getX();
            yVar[i] = orderedPairs[i].getY();
        }
        
        double numerator = (size * summation(xVar, yVar)) - (summation(xVar) * summation(yVar));
        double denominator = (size * summationOfSquares(xVar) - square(summation(xVar))) * (size * summationOfSquares(yVar) - square(summation(yVar)));
        return numerator / (squareRoot(denominator));
    }
    
    public static double getCorrelation(double[] xVar, double[] yVar) throws Exception
    {
        if(xVar.length <= 1 && yVar.length <= 1) 
            throw new Exception("Number of elements must be more than 1.");
        
        if(xVar.length != yVar.length)
            throw new Exception("List sizes are not the same.");
        
        int size = xVar.length;
        double numerator = (size * summation(xVar, yVar)) - (summation(xVar) * summation(yVar));
        double denominator = (size * summationOfSquares(xVar) - square(summation(xVar))) * (size * summationOfSquares(yVar) - square(summation(yVar)));
        return numerator / (squareRoot(denominator));
    }
}
