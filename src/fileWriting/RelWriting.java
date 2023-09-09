package fileWriting;

import fileReading.TextReading;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class RelWriting
{

    private final String path;
    private ArrayList<String> valueLines;
    private Map<String, String> relationLines;
    private Map<String, ArrayList<String>> sequentialRelationLines;

    public RelWriting(String path)
    {
        this.path = path;
        valueLines = new ArrayList<>();
        relationLines = new HashMap<>();
        sequentialRelationLines = new HashMap<>();
    }

    public void addValue(String var, String type, String val)
    {
        if (validTypes().contains(type.toLowerCase())) valueLines.add("@" + var + " as " + type + " = " + val);
        else
        {
            System.err.println("Invalid datatype usage");
            System.exit(1);
        }
    }

    public void addValue(String[] var, String[] type, String[] val)
    {
        if (var.length == type.length && var.length == val.length)
            for (int i = 0; i < var.length; i++) addValue(var[i], type[i], val[i]);
        else
        {
            System.err.println("The parameters don't have same size");
            System.exit(1);
        }
    }

    public void addValue(ArrayList<String> var, ArrayList<String> type, ArrayList<String> val)
    {
        if (var.size() == type.size() && var.size() == val.size())
            for (int i = 0; i < var.size(); i++) addValue(var.get(i), type.get(i), val.get(i));
        else
        {
            System.err.println("The parameters don't have same size");
            System.exit(1);
        }
    }

    public void addRelation(String name, String relation)
    {
        relationLines.put(name, relation);
    }

    public void addRelation(String[] name, String[] relation)
    {
        if (name.length == relation.length)
            for (int i = 0; i < name.length; i++) addRelation(name[i], relation[i]);
        else
        {
            System.err.println("The parameters don't have same size");
            System.exit(1);
        }
    }

    public void addRelation(ArrayList<String> name, ArrayList<String> relation)
    {
        if (name.size() == relation.size())
            for (int i = 0; i < name.size(); i++) addRelation(name.get(i), relation.get(i));
        else
        {
            System.err.println("The parameters don't have same size");
            System.exit(1);
        }
    }

    public void addSequence(String name, ArrayList<String> relation)
    {
        sequentialRelationLines.put(name, relation);
    }

    public void addSequence(String name, String[] relation)
    {
        ArrayList<String> temp = new ArrayList<>();
        for (String s: relation) temp.add(s);

        for (int i = 0; i < temp.size(); i++) sequentialRelationLines.put(name, temp);
    }

    public void addSequence(String[] name, ArrayList<ArrayList<String>> relation)
    {
        ArrayList<String> nameTemp = new ArrayList<>();
        ArrayList<ArrayList<String>> relationTemp = new ArrayList<>();

        for (int i = 0; i < name.length; i++)
        {
            if (!sequentialRelationLines.containsKey(name[i]))
            {
                nameTemp.add(name[i]);
                relationTemp.add(relation.get(i));
            }
        }

        for (int i = 0; i < nameTemp.size(); i++)
            sequentialRelationLines.put(nameTemp.get(i), relationTemp.get(i));
    }

    public void addSequence(ArrayList<String> name, ArrayList<ArrayList<String>> relation)
    {
        ArrayList<String> nameTemp = new ArrayList<>();
        ArrayList<ArrayList<String>> relationTemp = new ArrayList<>();

        for (int i = 0; i < name.size(); i++)
        {
            if (!sequentialRelationLines.containsKey(name.get(i)))
            {
                nameTemp.add(name.get(i));
                relationTemp.add(relation.get(i));
            }
        }

        for (int i = 0; i < nameTemp.size(); i++)
            sequentialRelationLines.put(nameTemp.get(i), relationTemp.get(i));
    }

    public void create()
    {
        ArrayList<String> lines = new ArrayList<>();
        lines.add("DATA");
        for (String l: valueLines) lines.add(l);
        lines.add("END\n\nRELATIONS");

        Set<String> temp = relationLines.keySet();
        for (String k: temp) lines.add(k + " as SINGLE: " + relationLines.get(k));

        temp.clear();

        temp = sequentialRelationLines.keySet();
        for (String k: temp)
        {
            lines.add(k + " as SEQUENCE: ");

            for (String d: sequentialRelationLines.get(k))
                lines.add("\t" + d);

            lines.add("DONE\n");
        }
        lines.add("END");

        TextWriting.write(path, lines);
    }

    public void save()
    {
        ArrayList<String> valuesSection = new ArrayList<>();
        ArrayList<String> relationsSection = new ArrayList<>();

        ArrayList<String> allLines = TextReading.read(path);

        int loc = 0;

        while (loc < allLines.size())
        {
            if (allLines.get(loc).trim().equals("DATA"))
            {
                loc++;
                break;
            }
            else loc++;
        }

        while (loc < allLines.size())
        {
            if (!allLines.get(loc).trim().equals("END"))
            {
                if (!allLines.get(loc).isBlank() && !allLines.get(loc).isEmpty())
                    valuesSection.add(allLines.get(loc));

                loc++;
            }
            else break;
        }

        while (loc < allLines.size())
        {
            if (allLines.get(loc).trim().equals("RELATIONS"))
            {
                loc++;
                break;
            }
            else loc++;
        }

        while (loc < allLines.size())
        {
            if (!allLines.get(loc).trim().equals("END"))
            {
                if (!allLines.get(loc).isBlank() && !allLines.get(loc).isEmpty())
                    relationsSection.add(allLines.get(loc));

                loc++;
            }
            else break;
        }

        //recreating
        ArrayList<String> lines = new ArrayList<>();
        lines.add("DATA");
        for (String l: valuesSection) lines.add(l);
        for (String l: valueLines) lines.add(l);
        lines.add("END\n\nRELATIONS");

        for (String l: relationsSection) lines.add(l);
        Set<String> temp = relationLines.keySet();
        for (String k: temp) lines.add(k + " as SINGLE: " + relationLines.get(k));

        temp.clear();

        temp = sequentialRelationLines.keySet();
        for (String k: temp)
        {
            lines.add(k + " as SEQUENCE: ");

            for (String d: sequentialRelationLines.get(k))
                lines.add("\t" + d);

            lines.add("DONE\n");
        }
        lines.add("END");

        TextWriting.write(path, lines);
    }

    private ArrayList<String> validTypes()
    {
        ArrayList<String> types = new ArrayList<>();

        types.add("int");       types.add("integer");
        types.add("double");    types.add("float");
        types.add("long");

        return types;
    }
}
