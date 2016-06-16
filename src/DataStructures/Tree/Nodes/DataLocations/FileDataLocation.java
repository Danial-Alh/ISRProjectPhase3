package DataStructures.Tree.Nodes.DataLocations;

import DataStructures.Tree.Nodes.FileNode;
import Primitives.Interfaces.Parsable;
import Primitives.Interfaces.Sizeofable;

public class FileDataLocation<Value extends Sizeofable & Parsable> extends DataLocation<FileNode<Value>>
{
    public FileDataLocation(FileNode<Value> node, int offset)
    {
        super(node, offset);
    }
}