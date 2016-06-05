package Primitives;

/**
 * Created by danial on 5/28/16.
 */
public class TermVector
{
    private FreqWeight[] termFreqVector;
    private double length;//length of vector
    public double getLength()
    {
        return this.length;
    }

    public FreqWeight[] getFreqWeight()
    {
        return this.termFreqVector;
    }
}
