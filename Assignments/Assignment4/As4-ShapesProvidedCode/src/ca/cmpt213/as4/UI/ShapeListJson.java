package ca.cmpt213.as4.UI;

import java.util.ArrayList;

public class ShapeListJson 
{
    private ArrayList<Shape> shapeListJson;

    public ShapeListJson() 
    {
        shapeListJson = null;
    }

    public ArrayList<Shape> getShapes() 
    {
        return shapeListJson;
    }

    public void setShapes(ArrayList<Shape> shapes) 
    {
        this.shapeListJson = shapes;
    }
}

