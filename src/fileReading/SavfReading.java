package fileReading;

import java.util.ArrayList;

public class SavfReading
{
    private ArrayList<String> values, params;
    public SavfReading()
    {
        values = new ArrayList<>();
        params = new ArrayList<>();
    }

    public void scan(String path)
    {
        ArrayList<String> lines = TextReading.read(path);
        ArrayList<Integer> equalIndex = new ArrayList<>();
        for (String l: lines)
        {
            for (int i = 0; i < l.length(); i++)
            {
                if (l.charAt(i) == '=' && l.charAt(i-1) != '\\')
                {
                    if (!(i-2 > 0 && l.charAt(i-2) == '\\'))
                    {
                        equalIndex.add(i);
                        break;
                    }
                }
            }
        }

        for (int i = 0; i < equalIndex.size(); i++)
        {
            String param = lines.get(i).substring(1, equalIndex.get(i));
            params.add(param.replace("\\=", "="));
            values.add(lines.get(i).substring(equalIndex.get(i) + 1));
        }
    }

    public ArrayList<String> getValues() {return values;}
    public ArrayList<String> getParams(){return params;}
    public String getValue(String param) {return values.get(params.indexOf(param));}
}
