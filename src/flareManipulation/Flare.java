package flareManipulation;

import fileReading.TextReading;
import fileWriting.TextWriting;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class Flare
{

    private Dimension dimension;
    private final String path;
    private Map<String, String> bridges;

    public Flare(Dimension dimension, String path)
    {
        this.dimension = dimension;
        this.path = path;

        bridges = new HashMap<>();
    }

    public Dimension getDimension() {return dimension;}

    public void launch()
    {
        if (new File(path).exists())
        {
            ArrayList<String> lines = TextReading.read(path);

            for (String l: lines)
            {
                int last = l.indexOf("]");
                String pos = l.substring(0, last+1);
                String val = l.substring(last+1);

                bridges.put(pos, val);
            }
        }

        else TextWriting.write(path, new ArrayList<>());
    }

    public String getBrick(ArrayList<Integer> location)
    {
        String brick;
        String loc = null;
        for (Integer p: location)
        {
            if (loc == null) loc = "[" + p;
            else loc += "," + p;
        }
        loc += "]";

        brick = bridges.get(loc);
        return brick;
    }

    public String getBrick(String location)
    {
        String brick;

        if (location.startsWith("[") && location.endsWith("]")) brick = bridges.get(location);
        else brick = bridges.get("[" + location + "]");

        return brick;
    }

    public void setBrick(ArrayList<Integer> location, String value)
    {
        String loc = null;
        for (Integer p: location)
        {
            if (loc == null) loc = "[" + p;
            else loc += "," + p;
        }
        loc += "]";

        bridges.put(loc, value);
    }

    public void setBrick(String location, String value)
    {
        String loc;

        if (location.startsWith("[") && location.endsWith("]")) loc = location;
        else loc = "[" + location + "]";

        bridges.put(loc, value);
    }

    public void save()
    {
        Set<String> keys = bridges.keySet();
        ArrayList<String> lines = new ArrayList<>();
        for (String k: keys) lines.add(k  + bridges.get(k));
        TextWriting.write(path, lines);
    }

    public ArrayList<String> getColumn(String pattern)
    {
        ArrayList<String> column = new ArrayList<>();

        ArrayList<String> indexes = getIndexes(pattern);
        Integer loc = null;
        for (int i = 0; i < indexes.size(); i++)
            if (!indexes.get(i).equals("x"))
            {
                loc = i;
                break;
            }

        Set<String> keys = bridges.keySet();
        for (String k: keys)
        {
            String clean = k.substring(1, k.length() - 1);
            ArrayList<String> cleanIndexes = getIndexes(clean);

            if (indexes.get(loc).equals(cleanIndexes.get(loc)))
                column.add(bridges.get(k));
        }

        return column;
    }

    public Map<String, String> getColumnWithPos(String pattern)
    {
        Map<String, String> column = new HashMap<>();

        ArrayList<String> indexes = getIndexes(pattern);
        Integer loc = null;
        for (int i = 0; i < indexes.size(); i++)
            if (!indexes.get(i).equals("x"))
            {
                loc = i;
                break;
            }

        Set<String> keys = bridges.keySet();
        for (String k: keys)
        {
            String clean = k.substring(1, k.length() - 1);
            ArrayList<String> cleanIndexes = getIndexes(clean);

            if (indexes.get(loc).equals(cleanIndexes.get(loc)))
                column.put(k, bridges.get(k));
        }

        return column;
    }

    public ArrayList<String> filter(String pattern)
    {
        ArrayList<String> items = new ArrayList<>();

        ArrayList<String> indexes = new ArrayList<>();
        ArrayList<Integer> layers = new ArrayList<>();

        ArrayList<String> patternIndexes = getIndexes(pattern);
        for (int i = 0; i < patternIndexes.size(); i++)
            if (!patternIndexes.get(i).equals("x"))
            {
                indexes.add(patternIndexes.get(i));
                layers.add(i);
            }

        Set<String> keys = bridges.keySet();
        for (String k: keys)
        {
            boolean isIt = true;
            ArrayList<String> keyIndexes = getIndexes(k.substring(1, k.length() - 1));

            for (int i = 0; i < indexes.size(); i++)
            {
                if (!keyIndexes.get(layers.get(i)).equals(indexes.get(i)))
                {
                    isIt = false;
                    break;
                }
            }

            if (isIt) items.add(bridges.get(k));
        }

        return items;
    }

    private ArrayList<String> getIndexes(String pattern)
    {
        ArrayList<String> indexes = new ArrayList<>();
        int loc = 0;
        while (loc < pattern.length())
        {
            if (pattern.charAt(loc) != ',')
            {
                String index = null;

                while (loc < pattern.length() && pattern.charAt(loc) != ',')
                {
                    if (index == null) index = Character.toString(pattern.charAt(loc));
                    else index += Character.toString(pattern.charAt(loc));

                    loc++;
                }

                indexes.add(index);
            }
            loc++;
        }

        return indexes;
    }
}
