package ca.cmpt213.as4;

import ca.cmpt213.as4.UI.*;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonArray;

import java.io.File;
import java.io.IOException;
import java.util.Iterator;
import java.util.ArrayList;
import java.io.BufferedReader;
import java.io.FileReader;

/**
 * Description: This ShapeModelImpl class implements the ShapeModel interface specifically the populateFromJSON(), redact(), and iterator() methods.
 * It handles the deserialization of the shape from the JSON file, and allows the user to use the redact button to change the appearence of the shapes
 * for easier marking :). It also allows the use of an iterator over the ArrayList of Shape objects.
 *
 * @author Daniel Tolsky
 * @version 1.0
 */

public class ShapeModelImpl implements ShapeModel
{
    private ArrayList<Shape> shapeList;

    public ShapeModelImpl()
    {
        shapeList = new ArrayList<Shape>();
    }

    // Load objects from file
    @Override
    public void populateFromJSON(File jsonFile)
    {
        try 
        {
            Gson gson = new Gson();
            BufferedReader reader = new BufferedReader(new FileReader(jsonFile));
            JsonObject jsonObject = gson.fromJson(reader, JsonObject.class);
            JsonArray jsonArray = jsonObject.getAsJsonArray("shapes");

            for (int count = 0; count < jsonArray.size(); ++count) 
            {
                shapeList.add(shapeJsonSetter(jsonArray.get(count).getAsJsonObject(), count));
            }
            reader.close();
        } 
        catch (IOException exeption) 
        {
            exeption.printStackTrace();
        }
    }

    // Redact all our objects (UI updates after calling this)
    @Override
    public void redact()
    {
        for (Shape shape : shapeList)
        {
            shape.setLine("char");
            shape.setLineChar('+');
            shape.setFill("solid");
            shape.setFillText("X");
            shape.setBackground("solid");
            shape.setBackgroundColor("light gray");
        }
    }

    @Override
    public Iterator<Shape> iterator() 
    {
        return shapeList.iterator();
    }

    private Shape shapeJsonSetter(JsonObject currObject, int index)
    {
        Shape shape = new Shape();
        shape.setTop(currObject.get("top").getAsInt());
        shape.setLeft(currObject.get("left").getAsInt());
        shape.setWidth(currObject.get("width").getAsInt());
        shape.setHeight(currObject.get("height").getAsInt());
        shape.setBackground(currObject.get("background").getAsString());
        shape.setBackgroundColor(currObject.get("backgroundColor").getAsString());
        shape.setLine(currObject.get("line").getAsString());
        if (currObject.get("lineChar") != null)
        {
            shape.setLineChar(currObject.get("lineChar").getAsString().charAt(0));
        }
        shape.setFill(currObject.get("fill").getAsString());
        shape.setFillText(currObject.get("fillText").getAsString());

        if (index == 0)
        {
            shape.setFirst();
        }
        return shape;
    }
}
