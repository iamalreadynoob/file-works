package fileReading;

import fileWriting.TextWriting;

import java.util.*;

public class RelReading
{
    private ArrayList<String> lines;
    private final String path;

    public RelReading(String path)
    {
        lines = new ArrayList<>();
        lines = TextReading.read(path);
        this.path = path;
    }

    public Set<String> getValNames()
    {
        Set<String> names = new HashSet<>();

        ArrayList<String> interval = getInterval("DATA");

        for (String l: interval)
        {
            l = l.trim();

            String[] pieces = l.split("=");
            String temp = pieces[0].split(" as ")[0];
            temp = temp.replace("@", "").trim();
            names.add(temp);
        }

        return names;
    }

    public String getVal(String name)
    {
        String value = null;

        ArrayList<String> interval = getInterval("DATA");

        for (String l: interval)
        {
            l = l.trim();

            String[] pieces = l.split("=");
            String temp = pieces[0].split(" as ")[0];
            temp = temp.replace("@", "").trim();

            if (name.equals(temp))
            {
                value = pieces[1].trim();
                break;
            }
        }

        return value;
    }

    public Map<String, String> getValues()
    {
        Map<String, String> values = new HashMap<>();

        ArrayList<String> interval = getInterval("DATA");

        for (String l: interval)
        {
            l = l.trim();

            String[] pieces = l.split("=");
            String temp = pieces[0].split(" as ")[0];

            temp = temp.replace("@", "").trim();
            values.put(temp, pieces[1].trim());
        }

        return values;
    }

    public String getValType(String name)
    {
        String type = null;

        ArrayList<String> interval = getInterval("DATA");

        for (String l: interval)
        {
            l = l.trim();

            String[] pieces = l.split("=");
            String temp = pieces[0].split(" as ")[0];
            temp = temp.replace("@", "").trim();

            if (name.equals(temp))
            {
                type = pieces[0].split(" as ")[1].trim();
                break;
            }
        }

        return type;
    }

    public Map<String, String> getValTypes()
    {
        Map<String, String> types = new HashMap<>();

        ArrayList<String> interval = getInterval("DATA");

        for (String l: interval)
        {
            l = l.trim();

            String[] pieces = l.split("=");
            String temp = pieces[0].split(" as ")[0];

            temp = temp.replace("@", "").trim();
            String type = pieces[0].split(" as ")[1].trim();
            types.put(temp, type);
        }

        return types;
    }

    public Set<String> getRelationNames()
    {
        Set<String> names = new HashSet<>();

        ArrayList<String> interval = getInterval("RELATIONS");

        for (String l: interval)
        {
            if (l.contains(":"))
            {
                String temp = l.split(":")[0];
                temp = temp.split(" as ")[0].trim();
                names.add(temp);
            }
        }

        return names;
    }

    public ArrayList<String> getRelation(String name)
    {
        ArrayList<String> relation = new ArrayList<>();

        ArrayList<String> interval = getInterval("RELATIONS");

        int index = 0;
        for (String l: interval)
        {
            if (l.contains(":"))
            {
                String raw = l.split(":")[0];
                String relName = raw.split(" as ")[0].trim();
                String type = raw.split(" as ")[1].trim();

                if (relName.equals(name))
                {
                    if (type.equals("SINGLE"))
                    {
                        relation.add(l.split(":")[1].trim());
                        break;
                    }
                    else if (type.equals("SEQUENCE"))
                    {
                        index++;

                        while (index < interval.size())
                        {
                            if (!interval.get(index).trim().equals("DONE"))
                            {
                                relation.add(interval.get(index));
                                index++;
                            }
                            else break;
                        }

                        break;
                    }
                }
            }

            index++;
        }

        return relation;
    }

    public Map<String, ArrayList<String>> getRelations()
    {
        Map<String, ArrayList<String>> relations = new HashMap<>();

        ArrayList<String> interval = getInterval("RELATIONS");

        int loc = 0;

        while (loc < interval.size())
        {
            if (interval.get(loc).contains(":"))
            {
                String name = interval.get(loc).split(":")[0].split(" as ")[0].trim();
                String type = interval.get(loc).split(":")[0].split(" as ")[1].trim();

                ArrayList<String> relation = new ArrayList<>();

                if (type.equals("SINGLE"))
                {
                    relation.add(interval.get(loc).split(":")[1].trim());
                    break;
                }
                else if (type.equals("SEQUENCE"))
                {
                    loc++;

                    while (loc < interval.size())
                    {
                        if (!interval.get(loc).trim().equals("DONE"))
                        {
                            relation.add(interval.get(loc));
                            loc++;
                        }
                        else break;
                    }
                }

                relations.put(name, relation);
            }

            loc++;
        }

        return relations;
    }

    public String getRelationType(String name)
    {
        String type = null;

        ArrayList<String> interval = getInterval("RELATIONS");

        for (String l: interval)
        {
            if (l.contains(":"))
            {
                String temp = l.split(":")[0];
                temp = temp.split(" as ")[1].trim();

                if (l.split(":")[0].split(" as ")[0].trim().equals(name))
                    type = temp;
            }
        }

        return type;
    }

    public Map<String, String> getRelationTypes()
    {
        Map<String, String> types = new HashMap<>();

        ArrayList<String> interval = getInterval("RELATIONS");

        for (String l: interval)
        {
            if (l.contains(":"))
            {
                String raw = l.split(":")[0];
                String name = raw.split(" as ")[0].trim();
                String type = raw.split(" as ")[1].trim();
                types.put(name, type);
            }
        }

        return types;
    }

    private ArrayList<String> getInterval(String starter)
    {
        ArrayList<String> interval = new ArrayList<>();

        lines.clear();
        lines = TextReading.read(path);

        int loc = 0;
        while (loc < lines.size())
        {
            if (lines.get(loc).trim().equals(starter))
            {
                loc++;

                while (loc < lines.size())
                {
                    if (!lines.get(loc).trim().equals("END"))
                    {
                        interval.add(lines.get(loc));
                        loc++;
                    }
                    else break;
                }
            }
            else loc++;
        }

        return interval;
    }

}
