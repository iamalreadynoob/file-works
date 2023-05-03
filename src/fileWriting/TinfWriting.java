package fileWriting;

import fileReading.TinfReading;

import java.util.ArrayList;

public class TinfWriting
{
    private String finisher;

    public TinfWriting()
    {
        setDefault();
    }

    public void setFinisher(String finisher) {this.finisher = finisher;}
    public void setDefault(){finisher = "END-TEXT";}

    public void write(String path, ArrayList<String> titles, ArrayList<String> texts)
    {
        ArrayList<String> lines = new ArrayList<>();

        for (int i = 0; i < titles.size(); i++)
        {
            lines.add("@" + titles.get(i));
            lines.add(texts.get(i));
            lines.add(finisher);
        }

        TextWriting.write(path, lines);
    }

    public void append(String path, ArrayList<String> titles, ArrayList<String> texts)
    {
        ArrayList<String> lines = new ArrayList<>();

        for (int i = 0; i < titles.size(); i++)
        {
            lines.add("@" + titles.get(i));
            lines.add(texts.get(i));
            lines.add(finisher);
        }

        TextWriting.append(path, lines);
    }

    public void delete(String path, int index)
    {
        TinfReading reading = new TinfReading();
        reading.scan(path);

        ArrayList<String> titles = reading.getTitles();
        ArrayList<String> texts = reading.getTexts();

        titles.remove(index);
        texts.remove(index);

        write(path, titles, texts);
    }

    public void changeTitle(String path, int index, String newTitle)
    {
        TinfReading reading = new TinfReading();
        reading.scan(path);

        ArrayList<String> titles = reading.getTitles();
        ArrayList<String> texts = reading.getTexts();

        titles.set(index, newTitle);

        write(path, titles, texts);
    }

    public void changeText(String path, int index, String newText)
    {
        TinfReading reading = new TinfReading();
        reading.scan(path);

        ArrayList<String> titles = reading.getTitles();
        ArrayList<String> texts = reading.getTexts();

        texts.set(index, newText);

        write(path, titles, texts);
    }
}
