package ca.cmpt213.as4.UI;

public class Border 
{
    private char lineChar;
    private int width;
    private int height;
    private String type;

    public Border(char lineChar, int width, int height)
    {
        this.lineChar = lineChar;
        this.width = width;
        this.height = height;
    }

    public void setType(String type)
    {
        this.type = type;
    }

    public char getChar(int currRowInCell, int currColInCell)
    {
        char cellChar = '0';

        if (type.equals("ascii line"))
        {
            cellChar = asciiLine(currRowInCell, currColInCell);
        }
        else if (type.equals("sequence"))
        {
            cellChar = sequence(currRowInCell, currColInCell);
        }
        return cellChar;
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

    private Boolean isSmall()
    {
        if (width == 1 || height == 1)
        {
            return true;
        }
        return false;
    }
}
