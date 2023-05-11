package fileReading;

import stringHandling.PhraseManipulation;

import java.util.ArrayList;

public class ScluReading
{
    private ArrayList<String> setA, setB, lines;
    String nameA, nameB;
    public ScluReading()
    {
        setA = new ArrayList<>();
        setB = new ArrayList<>();
    }

    public void scan(String path)
    {
        lines = TextReading.read(path);
        nameA = lines.get(1).substring(0, PhraseManipulation.where(lines.get(1), " AS A"));
        nameB = lines.get(2).substring(0, PhraseManipulation.where(lines.get(2), " AS B"));

        int loc = 4;

        while (!lines.get(loc).equals("ENDSET"))
        {
            setA.add(lines.get(loc));
            loc++;
        }

        loc += 2;

        while (!lines.get(loc).equals("ENDSET"))
        {
            setB.add(lines.get(loc));
            loc++;
        }
    }

    public ArrayList<String> getSetA() {return setA;}
    public ArrayList<String> getSetB() {return setB;}
    public String getNameA() {return nameA;}
    public String getNameB() {return nameB;}

    public ArrayList<String> intersection() {return commonReading("@A^B");}
    public ArrayList<String> combination() {return commonReading("@AvB");}
    public ArrayList<String> aDiffB() {return commonReading("@A-B");}
    public ArrayList<String> bDiffA() {return commonReading("@B-A");}
    public ArrayList<String> xor() {return commonReading("@A_xor_B");}

    private ArrayList<String> commonReading(String set)
    {
        ArrayList<String> wantedSet = new ArrayList<>();

        int initial = 0;


        while (!set.equals(lines.get(initial))) {initial++;}

        initial++;

        while (!lines.get(initial).equals("ENDSET"))
        {
            wantedSet.add(lines.get(initial));
            initial++;
        }

        return wantedSet;
    }
}
