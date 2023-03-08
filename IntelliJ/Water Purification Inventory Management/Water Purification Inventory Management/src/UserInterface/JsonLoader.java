package UserInterface;

import WaterPurificationInventorySystem.*;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;
import java.util.ArrayList;

import com.google.gson.*;

/**
* Description: This JsonLoader class handles the loading of water purification unit data
* from a JSON file into the Water Purification Inventory System database. The correct
* formating of the objects are handled within this class for easier use in the UI class.
*
* @author Daniel Tolsky
* @version 1.0
*/

public class JsonLoader 
{
    private JsonArray dataJSON;

    public JsonLoader()
    {
        dataJSON = new JsonArray();
    }

    public ArrayList<Unit> loadJson(Scanner scan, String filePath)
    {
        ArrayList<Unit> units = new ArrayList<Unit>();
        JsonParser parser = new JsonParser();

        try (FileReader reader = new FileReader(filePath)) 
        {
            Object obj = parser.parse(reader);
            dataJSON = (JsonArray) obj;
            units = listThroughJSONUnits(units);
        } 
        catch (IOException exception)
        {
            System.out.println(">File not found; please verify that the file location is a full path to the file<");
        }
        return units;
    }

    private ArrayList<Unit> listThroughJSONUnits(ArrayList<Unit> units)
    {
        for (Object jsonObject : dataJSON)
        {
            JsonObject unitObject = (JsonObject) jsonObject;
            Unit newUnit = new Unit();

            newUnit.setModel(unitObject.get("model").getAsString());
            newUnit.setSerialNumber(unitObject.get("serialNumber").getAsString());
            if (unitObject.get("dateShipped") == null)
            {
                newUnit.setDateShipped("-");
            }
            else
            {
                newUnit.setDateShipped(unitObject.get("dateShipped").getAsString());
            }

            newUnit.setTests(listThroughUnitTests(unitObject, newUnit));
            units.add(newUnit);
        }
        return units;
    }

    private ArrayList<Test> listThroughUnitTests(JsonObject unitObject, Unit unit)
    {
        JsonArray testArray = (JsonArray) unitObject.get("tests");
        ArrayList<Test> testsForUnit = new ArrayList<Test>();

        for (Object test : testArray)
        {
            JsonObject testObject = (JsonObject) test;
            Test newTest = new Test();

            newTest.setDate((String) testObject.get("date").getAsString());

            if (testObject.get("isTestPassed").getAsBoolean())
            {
                newTest.setStatus("Passed");
            }
            else
            {
                newTest.setStatus("FAILED");
            }
            newTest.setComment(testObject.get("testResultComment").getAsString());

            testsForUnit.add(newTest);
        }
        return testsForUnit;
    }
}