package fileReading;

import java.util.ArrayList;

public class VitdReading
{
    private String finisher;
    private ArrayList<String> headers;
    private ArrayList<ArrayList<String>> texts;

    public VitdReading()
    {
        setDefault();
        headers = new ArrayList<>();
        texts = new ArrayList<>();
    }

    public void setFinisher(String finisher){this.finisher = finisher;}
    public void setDefault(){finisher = "END-TEXT";}

    public void scan(String path)
    {
        TinfReading tinf = new TinfReading();
        tinf.setFinisher(finisher);
        tinf.scan(path);

        texts = tinf.getRawTexts();
        headers = tinf.getTexts();
    }

    public ArrayList<String> getHeaders() {return headers;}
    public ArrayList<ArrayList<String>> getTexts() {return texts;}
}
