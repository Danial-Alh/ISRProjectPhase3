package DataStructures.Tree.Nodes.DataLocations;

import DataStructures.Tree.Nodes.FileNode;
import Primitives.Parsable;
import Primitives.Sizeofable;

public class FileDataLocation<Value extends Sizeofable & Parsable> extends DataLocation<FileNode<Value>>
{
    public FileDataLocation(FileNode<Value> node, int offset)
    {
        super(node, offset);
    }
}