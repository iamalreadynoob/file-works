package fileReading;

import java.util.ArrayList;

public class TinfReading
{
    private ArrayList<String> texts, titles;
    private String finisher;
    public TinfReading()
    {
        titles = new ArrayList<>();
        texts = new ArrayList<>();

        setDefault();
    }

    public void setFinisher(String finisher){this.finisher = finisher;}
    public void setDefault(){finisher = "END-TEXT";}
    public void scan(String path)
    {
        ArrayList<String> lines = TextReading.read(path);

        int loc = 0;

        while (loc < lines.size())
        {
            if (lines.get(loc).charAt(0) == '@')
            {
                titles.add(lines.get(loc).substring(1));
                loc++;

                ArrayList<String> temp = new ArrayList<>();

                while (loc < lines.size() && !lines.get(loc).equals(finisher))
                {
                    temp.add(lines.get(loc));
                    loc++;
                }

                String text = null;

                for (int i = 0; i < temp.size(); i++)
                {
                    if (text == null) text = temp.get(i);
                    else text += temp.get(i);

                    if (i != temp.size() - 1) text += "\n";
                }

                texts.add(text);
            }

            loc++;
        }
    }

    public ArrayList<String> getTitles() {return titles;}
    public ArrayList<String> getTexts() {return texts;}

    public Integer getIndex(String title)
    {
        Integer index = null;

        for (int i = 0; i < titles.size(); i++)
        {
            if (title.equals(titles.get(i).substring(0)))
            {
                index = i;
                break;
            }
        }

        return index;
    }
}
