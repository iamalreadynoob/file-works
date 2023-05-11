package stringHandling;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class ShortedProcesses
{

    public static void console(String message) {System.out.println(message);}
    public static void console(int message){System.out.println(message);}
    public static void console(double message){System.out.println(message);}
    public static void console(float message){System.out.println(message);}
    public static void console(boolean message){System.out.println(message);}
    public static void console(char message){System.out.println(message);}
    public static void console(Integer message){System.out.println(message);}
    public static void console(Double message){System.out.println(message);}
    public static void console(Float message){System.out.println(message);}
    public static void console(Boolean message){System.out.println(message);}
    public static void console(Character message){System.out.println(message);}
    public static void console(ArrayList<String> message){System.out.println(message);}
    public static void console(List<String> message){System.out.println(message);}
    public static void console(Map<String, String> message){System.out.println(message);}

    public static String input()
    {
        return new Scanner(System.in).nextLine();
    }

    public static String input(String prompt)
    {
        System.out.println(prompt);
        return new Scanner(System.in).nextLine();
    }

}
