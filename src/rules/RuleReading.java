package rules;

import fileReading.TextReading;

import java.util.ArrayList;

public class RuleReading
{
    private String path;
    private ArrayList<String> lines, funs;
    private ArrayList<ArrayList<String>> processes;

    public RuleReading(String path)
    {
        this.path = path;
        funs = new ArrayList<>();
        processes = new ArrayList<>();
    }

    public void read()
    {
        lines = TextReading.read(path);
        analyze();
    }

    public ArrayList<String> getFuns() {return funs;}
    public ArrayList<String> getProcess(int index) {return processes.get(index);}

    private void analyze()
    {
        int loc = 0;

        while (loc < lines.size())
        {
            if (lines.get(loc).trim().startsWith("_") && !lines.get(loc).trim().contains(" "))
            {
                funs.add(lines.get(loc).substring(1));
                loc++;

                ArrayList<String> process = new ArrayList<>();

                while (loc < lines.size() && !lines.get(loc).trim().startsWith("_"))
                {
                    if (lines.get(loc).length() > 0) process.add(lines.get(loc));
                    loc++;
                }

                processes.add(process);

            }

            else loc++;
        }
    }

}
