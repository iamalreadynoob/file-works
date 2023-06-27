package stringHandling;

import java.util.ArrayList;

public class Parsing
{

    public static ArrayList<String> parse(String line)
    {
        ArrayList<String> pieces = new ArrayList<>();

        int loc = 0;

        while (loc < line.length())
        {
            if (line.charAt(loc) != ' ')
            {
                String piece = null;

                while (loc < line.length() && line.charAt(loc) != ' ')
                {
                    if (piece == null) piece = Character.toString(line.charAt(loc));
                    else piece += Character.toString(line.charAt(loc));

                    loc++;
                }

                pieces.add(piece);
            }

            loc++;
        }

        return pieces;
    }

    public static ArrayList<ArrayList<String>> parse(ArrayList<String> lines)
    {
        ArrayList<ArrayList<String>> pieceLines = new ArrayList<>();

        for (int i = 0; i < lines.size(); i++) pieceLines.add(parse(lines.get(i)));

        return pieceLines;
    }

    public static ArrayList<String> parse(ArrayList<Character> signs, String line)
    {
        ArrayList<String> pieces = new ArrayList<>();

        int loc = 0;

        while (loc < line.length())
        {
            if (signs.contains(line.charAt(loc))) {pieces.add(Character.toString(line.charAt(loc)));}

            else if (line.charAt(loc) != ' ')
            {
                String piece = null;

                while (loc < line.length() && line.charAt(loc) != ' ' && !signs.contains(line.charAt(loc)))
                {
                    if (piece == null) piece = Character.toString(line.charAt(loc));
                    else piece += Character.toString(line.charAt(loc));

                    loc++;
                }

                pieces.add(piece);

                if (loc < line.length() && signs.contains(line.charAt(loc))) {pieces.add(Character.toString(line.charAt(loc)));}
            }

            loc++;
        }

        return pieces;
    }

    public static boolean isString(String line, int from, int to)
    {
        boolean is = false;

        String before = "";
        if (from != 0) before = line.substring(0, from);

        String after = "";
        if (to != line.length()) after = line.substring(to);

        int quoteAmountInBefore = 0;
        int quoteAmountInAfter = 0;

        for (int i = 0; i < before.length(); i++)
        {
            if (i == 0) {if (before.charAt(i) == '\"') quoteAmountInBefore++;}
            else if (before.charAt(i) == '\"' && isQuote(before, i-1, 0)) quoteAmountInBefore++;
        }

        for (int i = 0; i < after.length(); i++)
        {
            if (i == 0) {if (after.charAt(i) == '\"') quoteAmountInAfter++;}
            else if (after.charAt(i) == '\"' && isQuote(after, i-1, to)) quoteAmountInAfter++;
        }

        if (quoteAmountInBefore % 2 == 1 && quoteAmountInAfter % 2 == 1) is = true;

        return is;
    }

    public static String getString(String line)
    {
        String text = null;

        int loc = 0;
        while (loc < line.length())
        {
            if (line.charAt(loc) == '\"' && isQuote(line, loc - 1, 0))
            {
                loc++;

                while (loc < line.length())
                {
                    if (line.charAt(loc) == '\"')
                    {
                        if (isQuote(line, loc - 1, 0)) break;
                        else
                        {
                            if (text == null) text = Character.toString(line.charAt(loc));
                            else text += Character.toString(line.charAt(loc));
                        }
                    }
                    else
                    {
                        if (text == null) text = Character.toString(line.charAt(loc));
                        else text += Character.toString(line.charAt(loc));
                    }
                    loc++;
                }
            }
            loc++;
        }

        return text;
    }

    private static boolean isQuote(String line, int start, int finish)
    {
        boolean is = false;

        int backSlashStreak = 0;

        int subLoc = start;
        while (subLoc >= finish && line.charAt(subLoc) == '\\')
        {
            backSlashStreak++;
            subLoc--;
        }

        if (backSlashStreak % 2 == 0) is = true;

        return is;
    }
}
