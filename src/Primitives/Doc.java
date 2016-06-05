package Primitives;

import java.util.Vector;

/**
 * Created by danial on 5/28/16.
 */
public class Doc
{
    private int docId;
    private TermVector termVector;
    private double distanceFromLeader;
    public double getDistanceFromLeader()
    {
        return distanceFromLeader;
    }
    public void setDistanceFromLeader(double newdis)
    {
        distanceFromLeader=newdis;
    }
    public TermVector getTermVector()
    {
        return  this.termVector;
    }

}
