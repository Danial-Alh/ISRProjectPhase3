package Primitives;

import java.util.Vector;

public class TermPosting
{
    private String term;
    private Vector<TermDocDetail> data;
    private double idf;

    public TermPosting(String term, Vector<TermDocDetail> data, double idf)
    {
        this.term = term;
        this.data = data;
        this.idf = idf;
    }

    public String getTerm()
    {
        return term;
    }

    public void setTerm(String term)
    {
        this.term = term;
    }

    public Vector<TermDocDetail> getData()
    {
        return data;
    }

    public void setData(Vector<TermDocDetail> data)
    {
        this.data = data;
    }

    public double getIdf()
    {
        return idf;
    }

    public void setIdf(double idf)
    {
        this.idf = idf;
    }
}
