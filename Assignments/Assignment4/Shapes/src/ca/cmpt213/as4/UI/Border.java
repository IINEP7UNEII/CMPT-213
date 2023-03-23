package ca.cmpt213.as4.UI;

/**
 * Description: This Border class is responsible for the creating of a border around the given shape object
 * with given information such as the dimsensions and style (type) of border. To create a new border, we simply create
 * pass the border character, width, and height as argumetns to the Border() constructor and get our specific
 * border character using the getChar() method which provides a single character as border for a specific
 * location on the border of the shape.
 *
 * @author Daniel Tolsky
 * @version 1.0
 */

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
