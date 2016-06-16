package DocIndexingManagement.Clustering;

import Primitives.Doc;
import Primitives.FreqWeight;

import java.util.Vector;

/**
 * Created by ali on 6/4/16.
 */
public class Cluster
{
    private FreqWeight[] termFreqVectorSample;
    Vector <Doc> followers;
    Cluster (FreqWeight[] termFreqVectorSample)
    {
        this.termFreqVectorSample=termFreqVectorSample;
    }
    public FreqWeight[] getTermFreqVectorSample()
    {
        return  this.termFreqVectorSample;
    }
    public void normalizeSample ()
    {

        int n = followers.size();
        int m = termFreqVectorSample.length;
        FreqWeight [] res = new FreqWeight[m];
        double [] tmpVal = new double[m];
        FreqWeight [] tmpFW;

        for (int i=0; i<n ;i++)
        {
            tmpFW = followers.elementAt(i).getTermVector().getFreqWeight();
            for (int j=0; j<m; j++)
            {
                tmpVal[j]+=tmpFW[j].getValue();
            }
        }
        for (int j=0; j<m; j++)
        {
            tmpVal[j]/=n;
            termFreqVectorSample[j].setValue(tmpVal[j]);
        }



    }
    public void calculateDistanceForAllFollowers()
    {
        //TODO:
        for (Doc doc: followers)
        {
            double tmp = Arithmatics.getDistance(doc.getTermVector().getFreqWeight(), termFreqVectorSample);
            doc.setDistanceFromLeader(tmp);
        }
    }
    public void addToFollowers(Doc doc)
    {
        followers.add(doc);
    }
}
