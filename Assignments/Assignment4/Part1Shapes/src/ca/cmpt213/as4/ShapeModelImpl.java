package ca.cmpt213.as4;

import ca.cmpt213.as4.UI.*;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonArray;

import java.io.File;
import java.io.IOException;
import java.util.Iterator;
import java.io.BufferedReader;
import java.io.FileReader;

/**
 * It must:
 * - Implement ShapeModel so that the UI can be passed a reference to the model at runtime (DI)
 *     - populateFromJSON(): load all the boxes from the JSON file
 *            Create and store DrawableShape objects.
 **
 *     - redact(): change all existing objects to be "redacted" (see assignment doc).
 *
 *     - iterator(): return an iterator to the DrawableShapes (your boxes) which you have created.
 * This class just puts some text in the middle of the screen, but it shows *how* you can do this.
 */
public class ShapeModelImpl implements ShapeModel
{
    private ShapeList shapeList;

    public ShapeModelImpl()
    {
        shapeList = new ShapeList();
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
