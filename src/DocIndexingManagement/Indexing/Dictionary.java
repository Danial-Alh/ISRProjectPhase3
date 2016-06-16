package DocIndexingManagement.Indexing;

import DataStructures.Tree.RamFileBtree;
import DataStructures.Vector.FileVector;
import Primitives.TermAbstractDetail;
import Primitives.TermDocDetail;

public class Dictionary
{
    private static Dictionary instance = null;
    RamFileBtree<TermAbstractDetail> tree;
    FileVector<TermDocDetail> fileVector;

    private Dictionary()
    {
        String temp = "ممممممممممممممممممممممممممممممممممممممم";
        TermAbstractDetail termAbstractDetail = new TermAbstractDetail(null, null);
        tree = new RamFileBtree<>(temp.length(), termAbstractDetail.sizeof(), 17, TermAbstractDetail.class);
        fileVector = new FileVector<>(TermDocDetail.class);
    }

    public static Dictionary getIntance()
    {
        if(instance == null)
            instance = new Dictionary();
        return instance;
    }

    public void insert(String term, int docID)
    {
        TermAbstractDetail searchRes = tree.search(term);
        if(searchRes == null)
        {
            Long indexPtr = fileVector.writeElementAt(null, docID, new TermDocDetail(1));
            try
            {
                tree.insert(term, new TermAbstractDetail(1, indexPtr));
            } catch (Exception e)
            {
                e.printStackTrace();
            }

        }
        else
        {
            TermDocDetail termDocDetail = fileVector.elementAt(searchRes.getFilePtr(), docID);
            if(termDocDetail == null)
                termDocDetail = new TermDocDetail(0);
            searchRes.incrementOccurences();
            termDocDetail.incrementOccurences();
            fileVector.writeElementAt(searchRes.getFilePtr(), docID, termDocDetail);
            tree.update(term, searchRes);
        }
    }
}
