package Primitives;

import Primitives.Interfaces.Parsable;
import Primitives.Interfaces.Sizeofable;
import Utilities.ByteUtils;

public class FreqWeight implements Sizeofable, Parsable
{
    private TermVector termVectorParent;
    private long freq;
    private double weight;
    private double unit; //unit=weight/(length of Vector)
    private boolean isUnitCalculated = false;

    public TermVector getTermVectorParent()
    {
        return termVectorParent;
    }

    public void setTermVectorParent(TermVector termVectorParent)
    {
        this.termVectorParent = termVectorParent;
    }

    public FreqWeight()
    {
    }

    public FreqWeight(TermVector parent)
    {
        this.termVectorParent = parent;
    }

    public void SetUnitCalculated(boolean b)
    {
        isUnitCalculated = b;
    }

    public double getUnit()
    {
        if (!isUnitCalculated)
        {
            unit = weight / (termVectorParent.getLength());
            isUnitCalculated = true;
        }
        return this.unit;
    }

    public void setUnit(double unit)
    {
        this.unit = unit;
    }

    public double getValue()
    {
        return weight;
    }

    public void setValue(double weight)
    {
        this.weight = weight;
    }

    public void setFreq(long freq)
    {
        this.freq = freq;
    }

    @Override
    public byte[] toByteArray()
    {
        byte[] bytes = new byte[sizeof()];
        byte[] freqByte = ByteUtils.longToBytes(freq);
        byte[] weightByte = ByteUtils.doubleToBytes(weight);
        System.arraycopy(ByteUtils.intToBytes(freqByte.length), 0, bytes, 0, 1);
        System.arraycopy(freqByte, 0, bytes, 1, freqByte.length);
        System.arraycopy(weightByte, 0, bytes, freqByte.length+1, weightByte.length);
        return bytes;
    }

    @Override
    public void parseFromByteArray(byte[] input)
    {
        int size = ByteUtils.bytesToInt(new byte[]{input[0]});
        byte[] freqByte = new byte[size];
        byte[] weightByte = new byte[input.length-size-1];
        System.arraycopy(input, 1, freqByte, 0, freqByte.length);
        System.arraycopy(input, freqByte.length+1, weightByte, 0, weightByte.length);
        freq = ByteUtils.bytesToLong(freqByte);
        weight = ByteUtils.bytesToDouble(weightByte);
    }

    @Override
    public int sizeof()
    {
        return Long.BYTES + Double.BYTES + 1; // store size
    }
}
