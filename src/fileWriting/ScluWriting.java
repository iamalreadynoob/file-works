package fileWriting;

import java.util.ArrayList;

public class ScluWriting
{
    public static void write(String path, String nameA, String nameB, ArrayList<String> setA, ArrayList<String> setB)
    {
        ArrayList<String> lines = new ArrayList<>();

        ArrayList<String> common = intersection(setA, setB);
        ArrayList<String> total = combination(setA, setB);
        ArrayList<String> onlyA = aDiffB(setA, setB);
        ArrayList<String> onlyB = bDiffA(setA, setB);
        ArrayList<String> unique = xor(setA, setB);

        lines.add("NAME=" + nameA + "," + nameB);
        lines.add(nameA + " AS A");
        lines.add(nameB + " AS B");
        lines.add("@A");
        for (String o: setA) lines.add(o);
        lines.add("ENDSET");
        lines.add("@B");
        for (String o: setB) lines.add(o);
        lines.add("ENDSET");
        lines.add("@A^B");
        for (String o: common) lines.add(o);
        lines.add("ENDSET");
        lines.add("@AvB");
        for (String o: total) lines.add(o);
        lines.add("ENDSET");
        lines.add("@A-B");
        for (String o: onlyA) lines.add(o);
        lines.add("ENDSET");
        lines.add("@B-A");
        for (String o: onlyB) lines.add(o);
        lines.add("ENDSET");
        lines.add("@A_xor_B");
        for (String o: unique) lines.add(o);
        lines.add("ENDSET");

        TextWriting.write(path, lines);
    }

    private static ArrayList<String> intersection(ArrayList<String> setA, ArrayList<String> setB)
    {
        ArrayList<String> common = new ArrayList<>();

        ArrayList<String> temp, other;
        if (setA.size() < setB.size())
        {
            temp = setA;
            other = setB;
        }
        else
        {
            temp = setB;
            other = setA;
        }

        for (String o: temp) if (other.contains(o)) common.add(o);

        return common;
    }

    private static ArrayList<String> combination(ArrayList<String> setA, ArrayList<String> setB)
    {
        ArrayList<String> total = new ArrayList<>();

        for (String o: setA) total.add(o);
        for (String o: setB) if (!total.contains(o)) total.add(o);

        return total;
    }

    private static ArrayList<String> aDiffB(ArrayList<String> setA, ArrayList<String> setB)
    {
        ArrayList<String> difference = new ArrayList<>();

        for (String o: setA) if (!setB.contains(o)) difference.add(o);

        return difference;
    }

    private static ArrayList<String> bDiffA(ArrayList<String> setA, ArrayList<String> setB)
    {
        ArrayList<String> difference = new ArrayList<>();

        for (String o: setB) if (!setA.contains(o)) difference.add(o);

        return difference;
    }

    private static ArrayList<String> xor(ArrayList<String> setA, ArrayList<String> setB)
    {
        ArrayList<String> unique = new ArrayList<>();

        for (String o: setA) if (!setB.contains(o)) unique.add(o);
        for (String o: setB) if (!setA.contains(o)) unique.add(o);

        return unique;
    }
}
