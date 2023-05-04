package fileWriting;

import fileReading.TextReading;

import java.util.ArrayList;

public class SavfWriting
{

    public static void write(String path, ArrayList<String> params, ArrayList<String> values)
    {
        ArrayList<String> lines = new ArrayList<>();
        for (int i = 0; i < params.size(); i++) {lines.add("@" + params.get(i) + "=" + values.get(i));}
        TextWriting.write(path, lines);
    }

    public static void change(String path, String param, String newValue)
    {
        ArrayList<String> lines = TextReading.read(path);

        for (int i = 0; i < lines.size(); i++)
        {
            if (lines.get(i).equals("@" + param))
            {
                lines.set(i, newValue);
            }
        }

        TextWriting.write(path, lines);
    }

    public static void add(String path, String param, String value)
    {
        String newLine = "@" + param + "=" + value;
        ArrayList<String> temp = new ArrayList<>();
        temp.add(newLine);
        TextWriting.append(path, temp);
    }

    public static void delete(String path, String param)
    {
        ArrayList<String> lines = TextReading.read(path);

        for (int i = 0; i < lines.size(); i++)
        {
            if (lines.contains("@" + param))
            {
                lines.remove(i);
                break;
            }
        }

        TextWriting.write(path, lines);
    }

}
