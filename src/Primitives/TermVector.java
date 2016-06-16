package Primitives;

public class TermVector
{
    private TermFreqVector termFreqVector;
    public int getLength()
    {
        return termFreqVector.size();
    }

    public TermFreqVector getFreqWeight()
    {
        return this.termFreqVector;
    }
}
