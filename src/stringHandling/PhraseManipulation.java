package stringHandling;

import java.util.ArrayList;

public class PhraseManipulation
{

    public static Integer where(String phrase, String requested)
    {
        Integer loc = null;

        for (int i = 0; i < phrase.length(); i++)
        {
            if (phrase.charAt(i) == requested.charAt(0))
            {
                boolean thereIs = true;

                for (int j = 0; j < requested.length(); j++)
                {
                    if (requested.charAt(j) != phrase.charAt(i+j))
                    {
                        thereIs = false;
                        break;
                    }
                }

                if (thereIs)
                {
                    loc = i;
                    break;
                }
            }
        }

        return loc;
    }

    public static int howMany(String phrase, String requested)
    {
        int amount = 0;

        for (int i = 0; i < phrase.length(); i++)
        {
            if (phrase.charAt(i) == requested.charAt(0))
            {
                boolean thereIs = true;

                for (int j = 0; j < requested.length(); j++)
                {
                    if (requested.charAt(j) != phrase.charAt(i+j))
                    {
                        thereIs = false;
                        break;
                    }
                }

                if (thereIs)
                {
                    amount++;
                    i = i + requested.length() - 1;
                }
            }
        }

        return amount;
    }

    public static ArrayList<String> change(ArrayList<String> lines, String oldPart, String newPart)
    {
        ArrayList<String> changed = lines;

        for (int k = 0; k < changed.size(); k++)
        {
            for (int i = 0; i < changed.get(k).length(); i++)
            {
                if (changed.get(k).charAt(i) == oldPart.charAt(0))
                {
                    boolean isThere = true;

                    for (int j = 0; j < oldPart.length(); j++)
                    {
                        if (oldPart.charAt(j) != changed.get(k).charAt(i+j))
                        {
                            isThere = false;
                            break;
                        }
                    }

                    if (isThere)
                    {
                        changed.set(k, changed.get(k).substring(0, i) + newPart + changed.get(k).substring(i+oldPart.length()));
                        i = i + oldPart.length() - 1;
                    }
                }
            }
        }


        return changed;
    }

}
