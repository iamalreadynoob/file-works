package exceptions;

import java.util.ArrayList;

public class FileExceptions
{
    private ArrayList<String> exceptionList;
    public FileExceptions()
    {
        exceptionList = new ArrayList<>();
        createExceptionList();
    }

    public ArrayList<String> getExceptionList() {return exceptionList;}

    private void createExceptionList()
    {
        exceptionList.add("initial point is greater than final point");
        exceptionList.add("interval is out of predefined area");
        exceptionList.add("this line is not exist");
    }
}
