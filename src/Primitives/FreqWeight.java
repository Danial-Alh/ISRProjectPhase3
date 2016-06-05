package Primitives;

/**
 * Created by danial on 5/28/16.
 */
public class FreqWeight
{
    private TermVector termVectorParent;
    private long freq;
    private double weight;
    private double unit; //unit=weight/(length of Vector)
    private boolean isUnitCalculated = false;
    public void SetUnitCalculated(boolean b)
    {
        isUnitCalculated=b;
    }
    public FreqWeight (TermVector parent)
    {
        this.termVectorParent=parent;

    }

    public void setUnit(double unit)
    {
        this.unit=unit;
    }

    public double getUnit()
    {
        if (!isUnitCalculated)
        {
            unit=weight/(termVectorParent.getLength());
            isUnitCalculated=true;
        }
        return this.unit;
    }
    public void setValue(double weight)
    {
        this.weight=weight;
    }
    public double getValue()
    {
        return weight;
    }
    public void setFreq(long freq)
    {
        this.freq=freq;
    }

}
