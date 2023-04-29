package fileReading;

import java.util.ArrayList;

public class DataReading
{
    private char separator;
    private char neutralizer;
    private ArrayList<String> headers;
    private ArrayList<ArrayList<String>> columns;
    public DataReading()
    {
        setDefault();

        headers = new ArrayList<>();
        columns = new ArrayList<>();
    }

    public void setNeutralizer(char neutralizer) {this.neutralizer = neutralizer;}
    public void setSeparator(char separator) {this.separator = separator;}
    public void setDefault(){separator = ','; neutralizer = '\"';}

    public void scan(String path)
    {
        ArrayList<String> rows = TextReading.read(path);

        for (int i = 0; i < rows.get(0).length(); i++)
        {
            String header = null;
            boolean isNeutral = false;

            while (i < rows.get(0).length() && (rows.get(0).charAt(i) != separator || isNeutral))
            {
                if (rows.get(0).charAt(i) == neutralizer)
                {
                    if (i - 1 > 0 && rows.get(0).charAt(i-1) == '\\')
                    {
                        if (header == null) header = Character.toString(rows.get(0).charAt(i));
                        else header += Character.toString(rows.get(0).charAt(i));
                    }
                    else
                    {
                        if (isNeutral) isNeutral = false;
                        else isNeutral = true;
                    }
                }
                else if (rows.get(0).charAt(i) == '\\')
                {
                    if (i - 1 > 0 && rows.get(0).charAt(i-1) == '\\')
                    {
                        if (header == null) header = Character.toString(rows.get(0).charAt(i));
                        else header += Character.toString(rows.get(0).charAt(i));
                    }
                }
                else
                {
                    if (header == null) header = Character.toString(rows.get(0).charAt(i));
                    else header += Character.toString(rows.get(0).charAt(i));
                }

                i++;
            }

            headers.add(header);
        }

        for (int i = 0; i < headers.size(); i++)
        {
            ArrayList<String> temp = new ArrayList<>();
            columns.add(temp);
        }

        for (int i = 1; i < rows.size(); i++)
        {
            ArrayList<String> temp = new ArrayList<>();
            int loc = 0;

            while (loc < rows.get(i).length())
            {
                String item = null;
                boolean isNeutral = false;

                while (loc < rows.get(i).length() && (rows.get(i).charAt(loc) != separator || isNeutral))
                {
                    if (rows.get(i).charAt(loc) == neutralizer)
                    {
                        if (loc - 1 > 0 && rows.get(i).charAt(loc-1) == '\\')
                        {
                            if (item == null) item = Character.toString(rows.get(i).charAt(loc));
                            else item += Character.toString(rows.get(i).charAt(loc));
                        }
                        else
                        {
                            if (isNeutral) isNeutral = false;
                            else isNeutral = true;
                        }
                    }
                    else if (rows.get(i).charAt(loc) == '\\')
                    {
                        if (loc - 1 > 0 && rows.get(i).charAt(loc-1) == '\\')
                        {
                            if (item == null) item = Character.toString(rows.get(i).charAt(loc));
                            else item += Character.toString(rows.get(i).charAt(loc));
                        }
                    }
                    else
                    {
                        if (item == null) item = Character.toString(rows.get(i).charAt(loc));
                        else item += Character.toString(rows.get(i).charAt(loc));
                    }

                    loc++;
                }

                temp.add(item);
                loc++;
            }

            for (int j = 0; j < headers.size(); j++) {columns.get(j).add(temp.get(j));}
        }
    }

    public ArrayList<String> getHeaders() {return headers;}

    public ArrayList<String> getColumn(String header) {return columns.get(headers.indexOf(header));}
}