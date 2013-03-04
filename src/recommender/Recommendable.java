package recommender;

import  com.fridge.classes.Tag;
import java.util.ArrayList;
import recommender.util.OrderedPair;

public interface Recommendable extends Comparable
{
    public double getNumeric();
    public OrderedPair getOrderedPair();
    public ArrayList<Tag> getTagSet();
}