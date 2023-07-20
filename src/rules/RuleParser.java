package rules;

import java.util.ArrayList;

public class RuleParser
{

    private ArrayList<String> tagList, tags;
    private ArrayList<ArrayList<String>> works;

    public RuleParser()
    {
        tags = new ArrayList<>();
        works = new ArrayList<>();
        fillTags();
    }

    public void analyze(ArrayList<String> process)
    {
        tags.clear();
        works.clear();

        int loc = 0;

        while (loc < process.size())
        {
            String candidate = null;
            if (process.get(loc).contains(":")) candidate = process.get(loc).split(":")[0].trim();

            if (candidate != null && tagList.contains(candidate))
            {
                ArrayList<String> work = new ArrayList<>();

                tags.add(candidate);
                work.add(process.get(loc).substring(process.get(loc).indexOf(":") + 1).trim());

                loc++;

                while (loc < process.size())
                {
                    if (process.get(loc).contains(":")
                    && tagList.contains(process.get(loc).split(":")[0].trim())) break;
                    else work.add(process.get(loc));

                    loc++;
                }

                works.add(work);
            }
            else loc++;
        }
    }

    public ArrayList<String> getTags() {return tags;}
    public ArrayList<String> getWork(int index) {return works.get(index);}

    private void fillTags()
    {
        tagList = new ArrayList<>();

        tagList.add("cmd");
        tagList.add("exec");
    }
}
