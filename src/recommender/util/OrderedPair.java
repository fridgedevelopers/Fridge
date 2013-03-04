package recommender.util;

public class OrderedPair
{
    private double x;
    private double y;
    
    public OrderedPair(double x, double y)
    {
        this.x = x;
        this.y = y;
    }
    
    public OrderedPair(OrderedPair orderedPair)
    {
        x = orderedPair.getX();
        y = orderedPair.getY();
    }

    public double getX()
    {
        return x;
    }

    public void setX(double x)
    {
        this.x = x;
    }

    public double getY()
    {
        return y;
    }

    public void setY(double y)
    {
        this.y = y;
    }
}
