package ca.cmpt213.as4.UI;

public class TextWrapper 
{
    private String text;
    private int horSpace;
    private int vertSpace;

    public TextWrapper(String text, int horSpace, int vertSpace)
    {
        this.text = text;
        this.horSpace = horSpace;
        this.vertSpace = vertSpace;
    }

    public String[] lineBuilder() 
    {
        String[] words = text.split(" ");
        String[] lines = new String[vertSpace];
        int lineCount = 0;
        StringBuilder lineBuild = new StringBuilder();
    
        for (int wordIndex = 0; wordIndex < words.length && lineCount < vertSpace; ++wordIndex) 
        {
            if (words[wordIndex].length() > horSpace) 
            {
                lineCount = buildLongWordLines(words[wordIndex], lineBuild, lines, horSpace, vertSpace, lineCount);
            } 
            else 
            {
                lineCount = buildShortWordLines(words[wordIndex], lineBuild, lines, horSpace, vertSpace, lineCount);
            }
        }
    
        if (lineBuild.length() > 0 && lineCount < vertSpace) 
        {
            lines[lineCount] = lineBuild.toString().trim();
            ++lineCount;
        }
    
        fillEmptyLines(lines, lineCount, vertSpace);
        return lines;
    }
    
    private int buildLongWordLines(String word, StringBuilder lineBuild, String[] lines, int horSpace, int vertSpace, int lineCount) 
    {
        String remainingWord = word;

        while (remainingWord.length() > 0 && lineCount < vertSpace) 
        {
            if (remainingWord.length() < (lineBuild.length() + horSpace)) 
            {
                if (remainingWord.length() > horSpace) 
                {
                    lines[lineCount] = lineBuild.toString().trim();
                    lineBuild.setLength(0);
                    ++lineCount;
                }
                lineBuild.append(remainingWord);
                lines[lineCount] = lineBuild.toString().trim();
                remainingWord = "";
            } 
            else 
            {
                String subWord = remainingWord.substring(0, horSpace - lineBuild.length());
                remainingWord = remainingWord.substring(horSpace - lineBuild.length());
                lineBuild.append(subWord);
                lines[lineCount] = lineBuild.toString().trim();
            }
            ++lineCount;
            lineBuild.setLength(0);
        }
        return lineCount;
    }
    
    private int buildShortWordLines(String word, StringBuilder lineBuild, String[] lines, int horSpace, int vertSpace, int lineCount) 
    {
        if (lineBuild.length() + word.length() <= horSpace) 
        {
            lineBuild.append(word);
            lineBuild.append(" ");
        } 
        else 
        {
            lines[lineCount] = lineBuild.toString().trim();
            lineBuild.setLength(0);
            lineBuild.append(word);
            lineBuild.append(" ");
            ++lineCount;
        }
        return lineCount;
    }
    
    private void fillEmptyLines(String[] lines, int lineCount, int vertSpace) 
    {
        for (; lineCount < vertSpace; ++lineCount) 
        {
            lines[lineCount] = "";
        }
    }
}
