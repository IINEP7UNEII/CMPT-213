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

public class JsonLoader 
{
    private JSONArray dataJSON;
    private ArrayList<Unit> units;

    public JsonLoader()
    { 
        dataJSON = new JSONArray();
        units = new ArrayList<Unit>();
    }

    public ArrayList<Unit> loadJson(Scanner scan, String filePath)
    {
        JSONParser parser = new JSONParser();

        try (FileReader reader = new FileReader(filePath)) 
        {
            Object obj = parser.parse(reader);
            dataJSON = (JSONArray) obj;
            listThroughJSONUnits();
        } 
        catch (IOException | ParseException exception) 
        {
            exception.printStackTrace();
        }
        return units;
    }

    private ArrayList<Unit> listThroughJSONUnits()
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