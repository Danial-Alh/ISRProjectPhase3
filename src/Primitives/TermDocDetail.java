package Primitives;

import Primitives.Interfaces.Parsable;
import Primitives.Interfaces.Sizeofable;
import Utilities.ByteUtils;

public class TermDocDetail implements Sizeofable, Parsable
{
    private Integer occurences;

    public TermDocDetail() {
        this.occurences = 0;
    }

    public TermDocDetail(Integer occurences)
    {
        this.occurences = occurences;
    }

    @Override
    public byte[] toByteArray()
    {
        return ByteUtils.intToBytes(occurences);
    }

    @Override
    public void parseFromByteArray(byte[] input)
    {
        occurences = ByteUtils.bytesToInt(input);
    }

    @Override
    public int sizeof()
    {
        return Integer.BYTES;
    }

    public Integer getOccurences()
    {
        return occurences;
    }

    public void setOccurences(Integer occurences)
    {
        this.occurences = occurences;
    }

    public void incrementOccurences()
    {
        occurences++;
    }
}
