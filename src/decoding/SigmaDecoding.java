package decoding;

import decoder.Decoder;
import java.util.ArrayList;

public class SigmaDecoding
{
    private Decoder decoder;
    public SigmaDecoding()
    {
        decoder = new Decoder();
    }

    //without extension
    public void scan(String fileName) {decoder.convert(fileName);}
    public ArrayList<String> getLines(){return decoder.getList();}
    public String getString(){return decoder.getString();}
    public void getTxt(){decoder.getTxt();}

}
