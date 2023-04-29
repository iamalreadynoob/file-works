package fileReading;

import exceptions.FileExceptions;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class TextReading
{

    public static ArrayList<String> read(String path)
    {
        ArrayList<String> lines = new ArrayList<>();

        File file = new File(path);
        try
        {
            FileReader reader = new FileReader(file);
            BufferedReader bufferedReader = new BufferedReader(reader);

            String line;
            while ((line = bufferedReader.readLine()) != null) lines.add(line);
        }
        catch (IOException e){e.printStackTrace();}

        return lines;
    }

    public static ArrayList<String> onlyRead(int from, int to, String path)
    {
        ArrayList<String> lines = new ArrayList<>();
        ArrayList<String> reqLines = new ArrayList<>();

        if (from > to)
        {
            System.err.println(new FileExceptions().getExceptionList().get(0));
            System.exit(1);
        }
        else if (from < 0)
        {
            System.err.println(new FileExceptions().getExceptionList().get(1));
            System.exit(1);
        }

        else
        {
            lines = read(path);

            for (int i = from; i <= to; i++)
            {
                reqLines.add(lines.get(i));
            }
        }

        return reqLines;
    }

    public static Integer getLength(String path)
    {
        Integer length = null;

        ArrayList<String> lines = read(path);

        for (String line: lines) length += line.length();

        return length;
    }

    public static Integer getSize(String path, int bytesPerChar) {return getLength(path) * bytesPerChar;}

    public static Integer getLineAmount(String path) {return read(path).size();}

}
