package UserInterface;

import WaterPurificationInventorySystem.*;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;
import java.util.ArrayList;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

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
    private JSONArray dataJSON;

    public JsonLoader()
    { 
        dataJSON = new JSONArray();
    }

    public ArrayList<Unit> loadJson(Scanner scan, String filePath)
    {
        ArrayList<Unit> units = new ArrayList<Unit>();
        JSONParser parser = new JSONParser();

        try (FileReader reader = new FileReader(filePath)) 
        {
            Object obj = parser.parse(reader);
            dataJSON = (JSONArray) obj;
            units = listThroughJSONUnits(units);
        } 
        catch (IOException | ParseException exception) 
        {
            System.out.println(">File not found; please verify that the file location is a full path to the file<");
        }
        return units;
    }

    private ArrayList<Unit> listThroughJSONUnits(ArrayList<Unit> units)
    {
        for (Object jsonObject : dataJSON)
        {
            JSONObject unitObject = (JSONObject) jsonObject;
            Unit newUnit = new Unit();
            
            newUnit.setModel((String) unitObject.get("model"));
            newUnit.setSerialNumber((String) unitObject.get("serialNumber"));
            newUnit.setDateShipped((String) unitObject.get("dateShipped"));

            if (newUnit.getDateShipped() == null)
            {
                newUnit.setDateShipped("-");
            }

            newUnit.setTests(listThroughUnitTests(unitObject, newUnit));
            units.add(newUnit);
        }
        return units;
    }

    private ArrayList<Test> listThroughUnitTests(JSONObject unitObject, Unit unit)
    {
        JSONArray testArray = (JSONArray) unitObject.get("tests");
        ArrayList<Test> testsForUnit = new ArrayList<Test>();

        for (Object test : testArray)
        {
            JSONObject testObject = (JSONObject) test;
            Test newTest = new Test();

            newTest.setDate((String) testObject.get("date"));
            if ((Boolean) testObject.get("isTestPassed"))
            {
                newTest.setStatus("Passed");
            }
            else
            {
                newTest.setStatus("FAILED");
            }
            newTest.setComment((String) testObject.get("testResultComment"));

            testsForUnit.add(newTest);
        }
        return testsForUnit;
    }
}