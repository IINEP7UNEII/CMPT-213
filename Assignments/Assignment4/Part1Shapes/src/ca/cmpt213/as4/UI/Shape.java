package ca.cmpt213.as4.UI;

import java.awt.Color;
import java.util.HashMap;
import java.util.Map;
import java.util.ArrayList;

public class Shape implements DrawableShape
{
    private int top;
    private int left;
    private int width;
    private int height;
    private String background;
    private Color backgroundColor;
    private String line;
    private char lineChar;
    private String fill;
    private String fillText;
    private Boolean isFirstShape;

    private static final Map<String, Color> COLOR_MAP = new HashMap<>();
    static 
    {
        COLOR_MAP.put("white", Color.WHITE);
        COLOR_MAP.put("light gray", Color.LIGHT_GRAY);
        COLOR_MAP.put("gray", Color.GRAY);
        COLOR_MAP.put("dark gray", Color.DARK_GRAY);
        COLOR_MAP.put("black", Color.BLACK);
        COLOR_MAP.put("red", Color.RED);
        COLOR_MAP.put("pink", Color.PINK);
        COLOR_MAP.put("orange", Color.ORANGE);
        COLOR_MAP.put("yellow", Color.YELLOW);
        COLOR_MAP.put("green", Color.GREEN);
        COLOR_MAP.put("magenta", Color.MAGENTA);
        COLOR_MAP.put("cyan", Color.CYAN);
        COLOR_MAP.put("blue", Color.BLUE);
    }

    public Shape()
    {
        top = 0;
        left = 0;
        width = 0;
        height = 0;
        background = "";
        backgroundColor = Color.WHITE;
        line = "";
        lineChar = '0';
        fill = "";
        fillText = "";
        isFirstShape = false;
    }

    public int getTop() 
    {
        return top;
    }

    public void setTop(int top) 
    {
        this.top = top;
    }

    public int getLeft() 
    {
        return left;
    }

    public void setLeft(int left) 
    {
        this.left = left;
    }

    public int getWidth() 
    {
        return width;
    }

    public void setWidth(int width) 
    {
        this.width = width;
    }

    public int getHeight() 
    {
        return height;
    }

    public void setHeight(int height) 
    {
        this.height = height;
    }

    public String getBackground() 
    {
        return background;
    }

    public void setBackground(String background) 
    {
        this.background = background;
    }

    public Color getBackgroundColor() 
    {
        return backgroundColor;
    }

    public void setBackgroundColor(String backgroundColor) 
    {
        this.backgroundColor = getColor(backgroundColor);
    }

    public String getLine() 
    {
        return line;
    }

    public void setLine(String line) 
    {
        this.line = line;
    }

    public char getLineChar() 
    {
        return lineChar;
    }

    public void setLineChar(char lineChar) 
    {
        this.lineChar = lineChar;
    }

    public String getFill() 
    {
        return fill;
    }

    public void setFill(String fill) 
    {
        this.fill = fill;
    }

    public String getFillText() 
    {
        return fillText;
    }

    public void setFillText(String fillText) 
    {
        this.fillText = fillText;
    }

    public void setFirst() 
    {
        isFirstShape = true;
    }

    public Boolean isFirst() 
    {
        return isFirstShape;
    }

    // This object should draw itself onto the canvas
	public void draw(Canvas canvas)
	{
        if (isFirstShape)
        {
            canvas.clear();
        }

        for (int rowCount = top; rowCount < (top + height); ++rowCount)
        {
            for (int colCount = left; colCount < (left + width); ++colCount)
            {
                canvas.setCellColor(colCount, rowCount, getCurrCellBackground(rowCount, colCount));

                if (isBorder(rowCount - top, colCount - left))
                {
                    canvas.setCellText(colCount, rowCount, getCurrCellLine(rowCount, colCount));
                }
                else
                {
                    canvas.setCellText(colCount, rowCount, getCurrCellFill(rowCount - top - 1, colCount - left - 1));
                }
            }
        }
	}

    private static Color getColor(String colorName) 
    {
        return COLOR_MAP.getOrDefault(colorName.toLowerCase(), Color.white);
    }

    private Color getCurrCellBackground(int currRow, int currCol) 
    {
        Color cellColor = backgroundColor;
        switch (background)
        {
            case "solid":
                break;

            case "checker":
                if ((currRow - top + currCol - left) % 2 == 0)
                {
                    cellColor = backgroundColor;
                }
                else
                {
                    cellColor = Color.WHITE;
                }
                break;

            case "triangle":
                if ((currRow - top) <= (currCol - left))
                {
                    cellColor = backgroundColor;
                }
                else
                {
                    cellColor = Color.WHITE;
                }
                break;
        }
        return cellColor;
    }

    private char getCurrCellLine(int currRow, int currCol) 
    {
        char currCellChar = lineChar;

        switch (line)
        {
            case "char":
                break;

            case "ascii line":
                currCellChar = asciiLine(currRow - top, currCol - left);
                break;

            case "sequence":
                currCellChar = sequence(currRow - top, currCol - left);
                break;
        }

        return currCellChar;
    }

    private char getCurrCellFill(int textRow, int textCol) 
    {
        char currCellChar = fillText.charAt(0);

        switch (fill)
        {
            case "solid":
                break;

            case "wrapper":
                currCellChar = displayChar(fillText, textRow, textCol);
                break;
        }

        return currCellChar;
    }


    private char asciiLine(int currRowInCell, int currColInCell)
    {
        char cellChar = lineChar;

        if (isSmall())
        {
            cellChar = '■';
        }
        else if (currRowInCell == 0 && currColInCell == 0)
        {
            cellChar = '╔';
        }
        else if (currRowInCell == 0 && currColInCell == (width - 1))
        {
            cellChar = '╗';
        }
        else if (currRowInCell == (height - 1) && currColInCell == 0)
        {
            cellChar = '╚';
        }
        else if (currRowInCell == (height - 1) && currColInCell == (width - 1))
        {
            cellChar = '╝';
        }
        else if (currRowInCell == 0 || currRowInCell == (height - 1))
        {
            cellChar = '═';
        }
        else if (currColInCell == 0 || currColInCell == (width - 1))
        {
            cellChar = '║';
        }
        return cellChar;
    }

    private Boolean isSmall()
    {
        if (width == 1 || height == 1)
        {
            return true;
        }
        return false;
    }

    private char sequence(int currRowInShape, int currColInShape)
    {
        char cellChar = lineChar;
        int cellInt = 0;

        int lastIntTop = width % 5;
        int lastIntRight = (width + height - 1) % 5;
        int lastIntBottom = ((width * 2) + height - 2) % 5;

        if (currRowInShape == 0) //if at top
        {
            cellInt = (currColInShape % 5) + 1;
        }
        else if (currColInShape == (width - 1)) //if at right
        {
            cellInt = ((lastIntTop + (currRowInShape - 1)) % 5) + 1;
        }
        else if (currRowInShape == (height - 1)) //if at bottom
        {
            cellInt = ((lastIntRight + (width - currColInShape - 2)) % 5) + 1;
        }
        else if (currColInShape == 0) //if at left
        {
            cellInt = ((lastIntBottom + (height - currRowInShape - 2)) % 5) + 1;
        }
        
        cellChar = (char) (cellInt + 48);
        return cellChar;
    }

    private Boolean isBorder(int currRowInShape, int currColInShape)
    {
        if (currRowInShape == 0 || currColInShape == 0 
        || currRowInShape == (height - 1) || currColInShape == (width - 1))
        {
            return true;
        }
        return false;
    }

    private char displayChar(String str, int textRow, int textCol)
    {
        ArrayList<String> lines = getLines(str);
        char cellChar = ' ';

        cellChar = lines.get(textRow).charAt(textCol);

        return cellChar;
    }

    private ArrayList<String> getLines(String str)
    {
        int horSpace = width - 2;
        int vertSpace = height - 2;
        String[] lines = lineBuilder(str, horSpace, vertSpace);
        ArrayList<String> formatted = new ArrayList<String>();

        for (String line : lines) 
        {
            int numSpaces = horSpace - line.length();
            int numSpacesRight = numSpaces / 2;
            int numSpacesLeft = numSpaces - numSpacesRight;

            String formattedLine = String.format("%" + (numSpacesLeft + line.length()) + "s", line);
            formattedLine = String.format("%-" + (numSpacesRight + formattedLine.length()) + "s", formattedLine);
            formatted.add(formattedLine);
        }
        return formatted;
    }

    private String[] lineBuilder(String str, int horSpace, int vertSpace)
    {
        String[] words = str.split(" ");
        String[] lines = new String[vertSpace];
        int lineCount = 0;
        StringBuilder lineBuild = new StringBuilder();

        for (int wordIndex = 0; wordIndex < words.length && lineCount < vertSpace; ++wordIndex) 
        {
            if (words[wordIndex].length() > horSpace) 
            {
                String remainingWord = words[wordIndex];
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
            }
            else if (lineBuild.length() + words[wordIndex].length() <= horSpace) 
            {
                lineBuild.append(words[wordIndex]);
                lineBuild.append(" ");
            }
            else 
            {
                lines[lineCount] = lineBuild.toString().trim();
                lineBuild.setLength(0);
                lineBuild.append(words[wordIndex]);
                lineBuild.append(" ");
                ++lineCount;
            }
        }

        if (lineBuild.length() > 0 && lineCount < vertSpace) 
        {
            lines[lineCount] = lineBuild.toString().trim();
            ++lineCount;
        }

        for (; lineCount < vertSpace; lineCount++)
        {
            lines[lineCount] = "";
        }
        return lines;
    }
}
