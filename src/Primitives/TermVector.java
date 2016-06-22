package Primitives;

public class TermVector
{
    private TermFreqVector termFreqVector;


    public TermVector(String term)
    {
        termFreqVector = new TermFreqVector(this, term);
    }

    public int getLength()
    {
        return termFreqVector.size();
    }

    public TermFreqVector getFreqWeight()
    {
        return this.termFreqVector;
    }
}
