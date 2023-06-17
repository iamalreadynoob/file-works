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

}
