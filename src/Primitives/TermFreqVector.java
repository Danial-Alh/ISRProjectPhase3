package Primitives;

import DataStructures.Vector.FileVector;
import FileManagement.RandomAccessFileManager;

public class TermFreqVector
{
    private FileVector<FreqWeight> fileVector;
    private final int fileID;
    private Long indexPtr;
    private TermVector termVectorParent;

    public TermFreqVector(TermVector termVectorParent)
    {
        this.termVectorParent = termVectorParent;
        fileID = RandomAccessFileManager.createNewInstance("termVector.txt");
        fileVector = new FileVector<>(FreqWeight.class, fileID);
        indexPtr = null;
    }

    public void addNewFreqWeight(FreqWeight freqWeight, int location)
    {
        if(indexPtr == null)
            indexPtr = fileVector.writeElementAt(null, location, freqWeight);
        else
            fileVector.writeElementAt(indexPtr, location, freqWeight);
    }

    public FreqWeight elementAt(int offset)
    {
        FreqWeight freqWeight = fileVector.elementAt(indexPtr, offset);
        freqWeight.setTermVectorParent(termVectorParent);
        return freqWeight;
    }

    public void setElementAt(FreqWeight freqWeight, int location)
    {
        addNewFreqWeight(freqWeight, location);
    }

    public int size()
    {
        return fileVector.size();
    }
}
