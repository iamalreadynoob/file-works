package fileWriting;

import fileReading.DataReading;
import fileReading.TextReading;

import java.util.ArrayList;

public class DataWriting
{
    private char separator;
    private char neutralizer;

    public DataWriting()
    {
        setDefault();
    }

    public void setNeutralizer(char neutralizer) {this.neutralizer = neutralizer;}
    public void setSeparator(char separator) {this.separator = separator;}
    public void setDefault(){neutralizer = '\"'; separator = ',';}

    public void write(String path, ArrayList<ArrayList<String>> columns)
    {
        ArrayList<String> lines = new ArrayList<>();

        String header = null;
        for (int i = 0; i < columns.get(0).size(); i++)
        {
            if (header == null) header = columns.get(0).get(i);
            else header += columns.get(0).get(i);

            if (i != columns.get(0).size() - 1) header += separator;
        }
        lines.add(header);

        for (int i = 0; i < columns.get(1).size(); i++)
        {
            String line = null;

            for (int j = 1; j < columns.size(); j++)
            {
                if (line == null) line = columns.get(j).get(i);
                else line += columns.get(j).get(i);

                if (j != columns.size() - 1) line += separator;
            }


            lines.add(line);
        }

        TextWriting.write(path, lines);
    }

    public void append(String path, ArrayList<String> lines)
    {
        TextWriting.append(path, lines);
    }

    public void change(String path, String column, int index, String newData)
    {
        DataReading reading = new DataReading();
        reading.scan(path);

        ArrayList<String> changedColumn = reading.getColumn(column);
        changedColumn.set(index, newData);

        ArrayList<ArrayList<String>> editedColumns = new ArrayList<>();
        editedColumns.add(reading.getHeaders());
        for (String c: editedColumns.get(0))
        {
            if (c.equals(column)) editedColumns.add(changedColumn);
            else editedColumns.add(reading.getColumn(c));
        }

        write(path, editedColumns);
    }

    public void delete(String path, int index)
    {
        ArrayList<String> lines = TextReading.read(path);
        lines.remove(index+1);
        TextWriting.write(path, lines);
    }

    public void addColumn(String path, String header, ArrayList<String> column)
    {
        DataReading reading = new DataReading();
        reading.scan(path);

        ArrayList<String> headers = reading.getHeaders();
        headers.add(header);

        ArrayList<ArrayList<String>> allData = new ArrayList<>();
        allData.add(headers);
        for (int i = 0; i < headers.size() - 1; i++) allData.add(reading.getColumn(headers.get(i)));
        allData.add(column);

        write(path, allData);
    }

    public void deleteColumn(String path, String header)
    {
        DataReading reading = new DataReading();
        reading.scan(path);

        ArrayList<String> headers = reading.getHeaders();
        headers.remove(headers.indexOf(header));

        ArrayList<ArrayList<String>> allData = new ArrayList<>();
        allData.add(headers);
        for (String h: headers) allData.add(reading.getColumn(h));

        write(path, allData);
    }

}
