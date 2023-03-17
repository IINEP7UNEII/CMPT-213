package ca.cmpt213.as4.UI;

public class Shape implements DrawableShape
{
    private int top;
    private int left;
    private int width;
    private int height;
    private String background;
    private String backgroundColor;
    private String line;
    private char lineChar;
    private String fill;
    private String fillText;

    public Shape()
    {
        top = 0;
        left = 0;
        width = 0;
        height = 0;
        background = "";
        backgroundColor = "";
        line = "";
        lineChar = '0';
        fill = "";
        fillText = "";
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

    public String getBackgroundColor() 
    {
        return backgroundColor;
    }

    public void setBackgroundColor(String backgroundColor) 
    {
        this.backgroundColor = backgroundColor;
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

    // This object should draw itself onto the canvas
	public void draw(Canvas canvas)
	{

	}
}
