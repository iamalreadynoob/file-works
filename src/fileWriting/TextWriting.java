package fileWriting;

import exceptions.FileExceptions;
import fileReading.TextReading;

import java.io.*;
import java.util.ArrayList;

public class TextWriting
{

    public static void write(String path, ArrayList<String> lines)
    {
        File file = new File(path);

        try
        {
            FileWriter writer = new FileWriter(file);

            for (int i = 0; i < lines.size(); i++)
            {
                writer.write(lines.get(i));

                if (i != lines.size() - 1) writer.write("\n");
            }

            writer.close();
        }
        catch (IOException e){e.printStackTrace();}
    }

    public static void write(String path, String[] lines)
    {
        ArrayList<String> temp = new ArrayList<>();

        for (String line: lines) temp.add(line);

        write(path, temp);
    }

    public static void append(String path, ArrayList<String> lines)
    {
        File file = new File(path);

        try
        {
            ArrayList<String> exist = TextReading.read(path);

            FileWriter writer = new FileWriter(file);

            for (String exl: exist) writer.write(exl + "\n");

            for (int i = 0; i < lines.size(); i++)
            {
                writer.write(lines.get(i));

                if (i != lines.size() - 1) writer.write("\n");
            }

            writer.close();
        }
        catch (IOException e){e.printStackTrace();}
    }

    public static void append(String path, String[] lines)
    {
        ArrayList<String> temp = new ArrayList<>();

        for (String line: lines) temp.add(line);

        append(path, temp);
    }

    public static void appendTo(String path, ArrayList<String> appendLines, int before)
    {
        if (before < 0) System.err.println(new FileExceptions().getExceptionList().get(2));
        else
        {
            ArrayList<String> existed = TextReading.read(path);

            File file = new File(path);

            try
            {
                FileWriter writer = new FileWriter(file);

                for (int i = 0; i < before; i++) writer.write(existed.get(i) + "\n");
                for (String line: appendLines) writer.write(line + "\n");
                for (int i = before; i < existed.size(); i++)
                {
                    writer.write(existed.get(i));
                    if (i != existed.size() - 1) writer.write("\n");
                }

                writer.close();
            }
            catch (IOException e){e.printStackTrace();}
        }
    }

    public static void appendTo(String path, String[] appendLines, int before)
    {
        ArrayList<String> temp = new ArrayList<>();
        for (String line: appendLines) temp.add(line);

        appendTo(path, temp, before);
    }

    public static void change(String path, String newLine, int line)
    {
        ArrayList<String> lines = TextReading.read(path);
        lines.set(line, newLine);
        write(path, lines);
    }

    public static void changeAt(String path, String changed, int line, int from, int to)
    {
        ArrayList<String> lines = TextReading.read(path);
        lines.set(line, lines.get(line).substring(0, from) + changed + lines.get(line).substring(to+1));
        write(path, lines);
    }

    public static void appendAt(String path, String appended, int line, int after, boolean leftSpace, boolean rightSpace)
    {
        ArrayList<String> lines = TextReading.read(path);
        String edited = lines.get(line);

        edited = edited.substring(0, after+1);

        if (leftSpace) edited += " " + appended;
        else edited += appended;

        if (rightSpace) edited += " " + lines.get(line).substring(after+1);
        else edited += lines.get(line).substring(after+1);

        lines.set(line, edited);
        write(path, lines);
    }

    public static void delete(String path, int line)
    {
        ArrayList<String> lines = TextReading.read(path);
        lines.remove(line);
        TextWriting.write(path, lines);
    }
}
