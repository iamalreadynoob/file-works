package stringHandling;

import java.util.Scanner;

public class ShortedProcesses
{

    public static void console(String message)
    {
        System.out.println(message);
    }

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
