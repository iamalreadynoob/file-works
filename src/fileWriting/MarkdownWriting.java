package fileWriting;

import fileReading.DataReading;

import java.util.ArrayList;

public class MarkdownWriting
{
    private String path, content;
    private char mode;
    private ArrayList<String> footnoteDetails;
    private int footnoteOrder;

    public MarkdownWriting()
    {
        content = null;
        footnoteDetails = new ArrayList<>();
        footnoteOrder = 1;
    }

    public void open(String path, char mode)
    {
        this.mode = mode;
        this.path = path;
    }

    public void close()
    {

        if (footnoteDetails.size() > 0)
        {
            content += "\n\n***\n\n";
            for (String l: footnoteDetails) content += l + "\n\n";
        }

        switch (mode)
        {
            case 'w': TextWriting.write(path, content); break;
            case 'a': TextWriting.append(path, content); break;
            case 't': TextWriting.appendTo(path, content, 0); break;
        }
    }

    public void write(Brick brick, String text)
    {
        String prepared = null;

        switch (brick)
        {
            case H1: prepared = "# " + text; break;
            case H2: prepared = "## " + text; break;
            case H3: prepared = "### " + text; break;
            case H4: prepared = "#### " + text; break;
            case H5: prepared = "##### " + text; break;
            case H6: prepared = "###### " + text; break;
            case BOLD: prepared = "**" + text + "**"; break;
            case ITALIC: prepared = "_" + text + "_"; break;
            case QUOTE: prepared = "> " + text; break;
            case PLAIN: prepared = text; break;
            case STRIKETHROUGH: prepared = "~~" + text + "~~"; break;
            case HIGHLIGHT: prepared = "==" + text + "=="; break;
            case EMOJI: prepared = ":" + text + ":"; break;
        }

        if (content == null) content = prepared;
        else content += prepared;
    }

    public void writeln(Brick brick, String text)
    {
        write(brick, text);
        content += "\n";
    }

    public void addQueue(Queue queue, ArrayList<String>... inputs)
    {
        String prepared = null;

        switch (queue)
        {
            case ORDERED_LIST:
                for (int i = 0; i < inputs[0].size(); i++)
                {
                    if (prepared == null) prepared = Integer.toString(i+1) + ". " + inputs[0].get(i);
                    else prepared += "\n" + Integer.toString(i+1) + ". " + inputs[0].get(i);
                }
                break;
            case UNORDERED_LIST:
                for (String line: inputs[0])
                {
                    if (prepared == null) prepared = "* " + line;
                    else prepared += "\n" + line;
                }
                break;
            case TASKLIST:
                prepared = tasklistHandle(inputs[0], inputs[1]);
                break;
            case TABLE: prepared = basicTableHandle(inputs); break;
            case TABLE_WITH_HEADERS_FIRST: prepared = headerFirstTableHandling(inputs); break;
        }

        if (content == null) content = prepared + "\n";
        else content += prepared + "\n";
    }

    public void addQueue(Queue queue, ArrayList<ArrayList<String>> inputs)
    {
        String prepared = null;

        if (queue == Queue.TABLE)
        {
            for (int i = 0; i < inputs.get(0).size(); i++)
            {
                String line = "| ";

                for (int j = 0; j < inputs.size(); j++) line += inputs.get(j).get(i) + " | ";

                if (prepared == null) prepared = line;
                else prepared += "\n" + line;

                if (i == 0)
                {
                    prepared += "\n|";
                    for (int j = 0; j < inputs.size(); j++) prepared += " ----------- |";
                }
            }
        }
        else if (queue == Queue.TABLE_WITH_HEADERS_FIRST)
        {
            prepared = "| ";

            for (String i: inputs.get(0)) prepared += i + " | ";
            prepared += "\n|";
            for (int i = 0; i < inputs.get(0).size(); i++) prepared += " ----------- |";

            for (int i = 0; i < inputs.get(0).size(); i++)
            {
                String line = "| ";

                for (int j = 1; j < inputs.size(); j++) line += inputs.get(j).get(i) + " | ";

                prepared += "\n" + line;
            }
        }

        if (prepared != null)
        {
            if (content == null) content = prepared + "\n";
            else content += prepared + "\n";
        }
    }

    public void addQueue(Queue queue, String path)
    {
        if (queue == Queue.TABLE_WITH_CSV)
        {
            ArrayList<ArrayList<String>> inputs =  new ArrayList<>();

            DataReading dataReading = new DataReading();
            dataReading.scan(path);

            inputs.add(dataReading.getHeaders());
            for (String h: dataReading.getHeaders()) inputs.add(dataReading.getColumn(h));

            addQueue(Queue.TABLE_WITH_HEADERS_FIRST, inputs);
        }
    }

    public void addLinkage(Linkage linkage, String prompt, String link)
    {
        String prepared = null;

        switch (linkage)
        {
            case LINK: prepared = "[" + prompt + "](" + link + ")"; break;
            case IMAGE: prepared = "![" + prompt + "](" + link + ")"; break;
            case FOOTNOTE:
                prepared = prompt + "[^" + footnoteOrder + "]";
                footnoteDetails.add("[^" + footnoteOrder + "]: " + link);
                footnoteOrder++;
                break;
        }

        if (content == null) content = prepared;
        else  content += prepared;
    }

    public void drawLine()
    {
        if (content == null) content = "***\n";
        else content += "\n\n***\n\n";
    }

    public void addCode(String lang, boolean isFenced, String code)
    {
        if (isFenced)
        {
            if (lang == null)
            {
                if (content == null) content = "```\n" + code + "\n```\n";
                else content += "```\n" + code + "\n```\n";
            }
            else
            {
                if (content == null) content = "```" + lang + "\n" + code + "\n```\n";
                else content += "```" + lang + "\n" + code + "\n```\n";
            }
        }
        else
        {
            if (content == null) content = "`" + code + "`";
            else content += "`" + code + "`";
        }
    }

    public void addCode(String lang, boolean isFenced ,ArrayList<String> codeLines)
    {
        String code = null;

        for (String line: codeLines)
        {
            if (code == null) code = line;
            else code += "\n" + line;
        }

        addCode(lang, isFenced, code);
    }



    private String basicTableHandle(ArrayList<String>... inputs)
    {
        String prepared = null;

        for (int i = 0; i < inputs[0].size(); i++)
        {

            String line = "| ";

            for (int j = 0; j < inputs.length; j++) line += inputs[j].get(i) + " | ";

            if (prepared == null) prepared = line;
            else prepared += "\n" + line;

            if (i == 0)
            {
                prepared += "\n|";
                for (int j = 0; j < inputs.length; j++) prepared += " ----------- |";
            }
        }

        return prepared;
    }

    private String headerFirstTableHandling(ArrayList<String>... inputs)
    {
        String prepared = "| ";

        for (String i: inputs[0]) prepared += i + " | ";
        prepared += "\n|";
        for (int i = 0; i < inputs[0].size(); i++) prepared += " ----------- |";

        for (int i = 0; i < inputs[0].size(); i++)
        {
            String line = "| ";

            for (int j = 1; j < inputs.length; j++) line += inputs[j].get(i) + " | ";

            prepared += "\n" + line;
        }

        return prepared;
    }

    private String tasklistHandle(ArrayList<String> taskSet, ArrayList<String> situSet)
    {
        String prepared = null;

        ArrayList<Boolean> stabledSitu = new ArrayList<>();
        for (String situ: situSet)
        {
            if (situ.equals("1") || situ.toLowerCase().equals("true") || situ.toLowerCase().equals("t") ||
                    situ.toLowerCase().equals("yes") || situ.toLowerCase().equals("y") ||
                    situ.toLowerCase().equals("done") || situ.toLowerCase().equals("x"))
                stabledSitu.add(true);

            else if (situ.toLowerCase().equals("0") || situ.toLowerCase().equals("false") ||
                    situ.toLowerCase().equals("f") || situ.toLowerCase().equals("no") ||
                    situ.toLowerCase().equals("n") || situ.toLowerCase().equals("not") ||
                    situ.toLowerCase().equals("o"))
                stabledSitu.add(false);
            else
            {
                System.err.println("EXCEPTION: Undefined situation info");
                System.exit(1);
            }
        }

        for (int i = 0; i < taskSet.size(); i++)
        {
            if (stabledSitu.get(i))
            {
                if (prepared == null) prepared = "- [x] " + taskSet.get(i);
                else prepared += "\n- [x] " + taskSet.get(i);
            }
            else
            {
                if (prepared == null) prepared = "- [ ] " + taskSet.get(i);
                else prepared += "\n- [ ] " + taskSet.get(i);
            }
        }

        return prepared;
    }


    public enum Brick
    {
        H1, H2, H3, H4, H5, H6, BOLD, ITALIC, QUOTE, PLAIN, STRIKETHROUGH, HIGHLIGHT, EMOJI
    }

    public enum Queue
    {
        ORDERED_LIST, UNORDERED_LIST, TABLE, TASKLIST, TABLE_WITH_HEADERS_FIRST, TABLE_WITH_CSV
    }

    public enum Linkage
    {
        LINK, IMAGE, FOOTNOTE
    }

}
