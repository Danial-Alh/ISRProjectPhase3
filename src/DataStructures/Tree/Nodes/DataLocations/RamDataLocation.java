package DataStructures.Tree.Nodes.DataLocations;

import DataStructures.Tree.Nodes.RamFileNode;
import Primitives.Interfaces.Parsable;
import Primitives.Interfaces.Sizeofable;

public class RamDataLocation<Value extends Sizeofable & Parsable> extends DataLocation<RamFileNode<Value>>
{
    public RamDataLocation(RamFileNode<Value> node, int offset)
    {
        super(node, offset);
    }
}