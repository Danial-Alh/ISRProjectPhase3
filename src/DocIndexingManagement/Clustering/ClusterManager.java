package DocIndexingManagement.Clustering;

import Primitives.Doc;
import Primitives.FreqWeight;

import java.util.Vector;

/**
 * Created by ali on 6/5/16.
 */


public class ClusterManager
{
    Vector<Doc> allDocs;
    int docsNumber;
    int vectorLength;
    Vector<Cluster> clusters;
    public ClusterManager ()
    {
        this.vectorLength=Support.getVectorLength();
        this.allDocs=Support.getAllDoc();
        docsNumber=allDocs.size();
        generateClusters();
        addDocsToClusters();
        updateClusters();


    }
    private void generateClusters()
    {
       int n = (int) Math.sqrt((double)docsNumber);
       for (int i=0; i<n; i++)
       {
           FreqWeight[] termFreqVectorSample= new FreqWeight[vectorLength];
           for (int j=0; j<vectorLength; j++)
           {
               termFreqVectorSample[j].setValue(Math.random());//random between 0 and 1
           }
           Cluster tmp = new Cluster(termFreqVectorSample);
           clusters.add(tmp);//add new cluster
       }
    }
    private void addDocsToClusters()
    {
        for (Doc doc: allDocs)
        {
            double bestDistance=Double.MAX_VALUE;
            Cluster condidateCl=null;
            for (Cluster cl: clusters)
            {
                double tmp = Arithmatics.getDistance(cl.getTermFreqVectorSample(), doc.getTermVector().getFreqWeight());
                if (tmp<bestDistance)
                {
                    condidateCl=cl;
                    bestDistance=tmp;
                }
            }
            condidateCl.addToFollowers(doc);
        }
    }
    private void updateClusters()
    {
        for (Cluster cl: clusters)
        {
            cl.normalizeSample();

        }
        for (Cluster cl: clusters)
        {
            cl.calculateDistanceForAllFollowers();
        }
        for (int i=0; i<clusters.size(); i++)
        {
            for (int k=0; k<clusters.elementAt(i).followers.size(); k++)
          //  for (Doc doc: clusters.elementAt(i).followers)
            {
                Doc doc = clusters.elementAt(i).followers.elementAt(k);
                double bestDistance=doc.getDistanceFromLeader();
                int bestIndex=i;
                for (int j=0; j<clusters.size(); j++)
                {
                    if (i!=j)
                    {
                       double tmp = Arithmatics.getDistance(doc.getTermVector().getFreqWeight(), clusters.elementAt(j).getTermFreqVectorSample());
                       if (tmp < bestDistance)
                       {
                           bestDistance = tmp;
                           bestIndex = j;
                       }
                    }
                }
                if (bestIndex != i) {
                    //We should change the Leader of doc from i to bestIndex
                    //TODO: we can't remove element k, if we want to use method removeElementAt(k), we should do "k--", we can use this method if we want more speed
                    clusters.elementAt(i).followers.remove(doc);
                    clusters.elementAt(bestIndex).followers.add(doc);
                }
            }

        }

    }


}
