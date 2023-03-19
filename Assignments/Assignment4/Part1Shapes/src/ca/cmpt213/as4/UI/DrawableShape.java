package ca.cmpt213.as4.UI;

import java.awt.Color;

/**
 * Define a graphical shape interface
 */
public interface DrawableShape 
{
	public int getTop();

    public void setTop(int top);

    public int getLeft();

    public void setLeft(int left);

    public int getWidth();

    public void setWidth(int width);

    public int getHeight();

    public void setHeight(int height);

    public String getBackground();

    public void setBackground(String background);

    public Color getBackgroundColor();

    public void setBackgroundColor(String backgroundColor);

    public String getLine();

    public void setLine(String line);

    public char getLineChar();

    public void setLineChar(char lineChar);

    public String getFill();

    public void setFill(String fill);

    public String getFillText();

    public void setFillText(String fillText);

	// This object should draw itself onto the canvas
	void draw(Canvas canvas);
}
