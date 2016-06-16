package FileManagement;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.RandomAccessFile;

public class RandomAccessFileManager
{
    private static String path = "index.txt";
    private static boolean instanceCreated = false;
    private static RandomAccessFile instance = null;

    protected RandomAccessFileManager()
    {

    }

    public static RandomAccessFile getInstance()
    {
        if(!instanceCreated)
        {
            System.out.println("instance creating");
            instanceCreated = true;
            try
            {
                File file = new File(path);
                if (file.exists())
                {
                    file.delete();
                    System.out.println("deleting last index");
                }
                instance = new RandomAccessFile(path, "rw");
            } catch (FileNotFoundException e)
            {
                e.printStackTrace();
            }
        }
        return instance;
    }

    @Override
    protected void finalize() throws Throwable
    {
        if(instance != null)
            instance.close();
        super.finalize();
    }
}
