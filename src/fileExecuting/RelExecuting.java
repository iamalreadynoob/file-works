package fileExecuting;

import fileReading.RelReading;
import fileReading.TextReading;
import fileWriting.TextWriting;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class RelExecuting
{
    private final String path;
    private Map<String, ArrayList<String>> relations;
    private Map<String, String> names;

    public RelExecuting(String path)
    {
        this.path = path;

        relations = new HashMap<>();
        names = new HashMap<>();

        reload();
        load();
    }
    public void setValue(String name, String newValue)
    {
        if (names.containsKey(name))
        {
            ArrayList<String> allLines = TextReading.read(path);
            ArrayList<String> beforeLines = new ArrayList<>();
            ArrayList<String> changedLines = new ArrayList<>();
            ArrayList<String> afterLines = new ArrayList<>();

            int startLn = getIndex("DATA");
            int finLn = getIndex("END");

            for (int i = 0; i <= startLn; i++) beforeLines.add(allLines.get(i));
            for (int i = startLn + 1; i < finLn; i++) changedLines.add(allLines.get(i));
            for (int i = finLn; i < allLines.size(); i++) afterLines.add(allLines.get(i));

            for (int i = 0; i < changedLines.size(); i++)
            {
                String tempLine = changedLines.get(i);
                String varName = tempLine.split("=")[0].split(" as ")[0].trim();

                if (varName.equals(name))
                {
                    changedLines.set(i, tempLine.split("=")[0] + " " + newValue);
                    break;
                }
            }

            allLines.clear();
            for (String s: beforeLines) allLines.add(s);
            for (String s: changedLines) allLines.add(s);
            for (String s: afterLines) allLines.add(s);

            TextWriting.write(path, allLines);
        }
        else
        {
            System.err.println("Invalid variable name");
            System.exit(1);
        }
    }

    public void exec(String name)
    {
        ArrayList<String> execution = relations.get(name);

        if (execution.size() == 1)
        {
            String command = execution.get(0);

            if (command.startsWith("IF"))
            {
                command = command.substring(2);
                String[] pieces = command.split("THEN");
                if (isProvided(pieces[0]))
                {
                    //TODO: execute the relation
                }
            }
        }
        else if (execution.size() > 1)
        {

        }
    }

    public void refresh()
    {
        relations.clear();

        reload();
        load();
    }

    private void reload()
    {
        names.clear();
        names = new RelReading(path).getValues();
    }

    private void load() {relations = new RelReading(path).getRelations();}

    private int getIndex(String req)
    {
        ArrayList<String> lines = TextReading.read(path);

        for (int i = 0; i < lines.size(); i++)
            if (lines.get(i).trim().equals(req))
                return i;

        return Integer.MIN_VALUE;
    }

    private boolean isProvided(String condition)
    {
        boolean provided = false;

        String operator = null;

        if (condition.contains(">")) operator = ">";
        else if (condition.contains("GREATER")) operator = ">";
        else if (condition.contains("<")) operator = "<";
        else if (condition.contains("SMALLER")) operator = "<";
        else if (condition.contains(">=")) operator = ">=";
        else if (condition.contains("EQORGRE")) operator = ">=";
        else if (condition.contains("<=")) operator = "<=";
        else if (condition.contains("EQORSMA")) operator = "<=";
        else if (condition.contains("==")) operator = "==";
        else if (condition.contains("EQUALS")) operator = "==";

        if (operator != null)
        {
            String[] pieces = condition.split(operator);

            char datatype = 'i';
            if (pieces[0].contains(".") || pieces[1].contains(".")) datatype = 'd';

            if (isCalculation(pieces[0])) pieces[0] = calculate(pieces[0], datatype);
            if (isCalculation(pieces[1])) pieces[1] = calculate(pieces[1], datatype);

            if (datatype == 'i') provided = relation(Integer.parseInt(pieces[0]), Integer.parseInt(pieces[1]), operator);
            else provided = relation(Double.parseDouble(pieces[0]), Double.parseDouble(pieces[1]), operator);
        }

        return provided;
    }

    private boolean isCalculation(String phrase)
    {
        boolean calculation = false;

        if (phrase.contains("+") || phrase.contains("-") || phrase.contains("*") || phrase.contains("/") ||
                phrase.contains("(") || phrase.contains(")") || phrase.contains("%") || phrase.contains("^") ||
                phrase.contains("#") || phrase.contains("ADD") || phrase.contains("MINUS") ||
                phrase.contains("MULTIPLY") || phrase.contains("DIVIDE") || phrase.contains("SUB") ||
                phrase.contains("MODE") || phrase.contains("TIMES") || phrase.contains("ROOT"))
            calculation = true;

        return calculation;
    }

    private String calculate(String calculation, char datatype)
    {
        String result = null;

        calculation = calculation.replaceAll("[+]", " + ")
                .replaceAll("-", " - ").replaceAll("[*]", " * ")
                .replaceAll("/", " / ").replaceAll("[(]", " ( ")
                .replaceAll("[)]", " ) ").replaceAll("%", " % ")
                .replaceAll("\\^", " ^ ").replaceAll("#", " # ")
                .replaceAll("ADD", " + ").replaceAll("MINUS", " - ")
                .replaceAll("MULTIPLY", " * ").replaceAll("DIVIDE", " / ")
                .replaceAll("SUB", " SUB ").replaceAll("MODE", " % ")
                .replaceAll("TIMES", " ^ ").replaceAll("ROOT", " # ");

        String[] parsed = calculation.split(" ");
        ArrayList<String> pieces = new ArrayList<>();
        for (int i = 0; i < parsed.length; i++)
            if (!parsed[i].isEmpty() && !parsed[i].isBlank())
                pieces.add(parsed[i].trim());

        if (pieces.contains("SUB"))
        {
            int subAmount = 0;
            ArrayList<Integer> indexes = new ArrayList<>();

            for (int i = 0; i < pieces.size(); i++)
                if (pieces.get(i).equals("SUB"))
                {
                    indexes.add(i);
                    subAmount++;
                }

            if (subAmount % 2 == 0)
            {
                for (int i = 0; i < subAmount / 2; i++)
                    pieces.set(indexes.get(i), "(");

                for (int i = subAmount / 2; i < indexes.size(); i++)
                    pieces.set(indexes.get(i), ")");
            }
        }

        while (true)
        {
            if (pieces.contains("("))
            {
                int lastOpen = Integer.MIN_VALUE;
                int lastClose = Integer.MAX_VALUE;

                for (int i = 0; i < pieces.size(); i++)
                {
                    if (pieces.get(i).equals("(")) lastOpen = i;
                    else if (pieces.get(i).equals(")")) lastClose = i;
                }

                ArrayList<String> equation = new ArrayList<>();
                for (int i = lastOpen+1; i < lastClose; i++) equation.add(pieces.get(i));

                String number = arithmetic(equation, datatype);

                pieces.set(lastOpen, number);
                for (int i = 0; i < lastClose - lastOpen; i++) pieces.remove(lastOpen + 1);

            }
            else break;
        }

        return result;
    }

    private boolean relation(Integer first, Integer second, String operator)
    {
        boolean flag = false;

        if (operator.equals(">") && first > second) flag = true;
        else if (operator.equals("<") && first < second) flag = true;
        else if (operator.equals(">=") && first >= second) flag = true;
        else if (operator.equals("<=") && first <= second) flag = true;
        else if (operator.equals("==") && first == second) flag = true;

        return flag;
    }

    private boolean relation(Double first, Double second, String operator)
    {
        boolean flag = false;

        if (operator.equals(">") && first > second) flag = true;
        else if (operator.equals("<") && first < second) flag = true;
        else if (operator.equals(">=") && first >= second) flag = true;
        else if (operator.equals("<=") && first <= second) flag = true;
        else if (operator.equals("==") && first == second) flag = true;

        return flag;
    }

    private String arithmetic(ArrayList<String> equation, char datatype)
    {
        String result = null;

        if (equation.size() == 1) result = equation.get(0);
        else
        {

        }

        return result;
    }

}
