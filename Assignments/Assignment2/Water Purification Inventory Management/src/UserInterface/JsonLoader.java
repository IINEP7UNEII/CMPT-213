package UserInterface;

import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;
import org.json.simple.JSONArray;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class JsonLoader 
{
    private JSONArray data;

    public JsonLoader()
    { 
        data = null;
    }

    public JSONArray loadJson(Scanner scan, String filePath) 
    {
        JSONParser parser = new JSONParser();

        try (FileReader reader = new FileReader(filePath)) 
        {
            Object obj = parser.parse(reader);
            data = (JSONArray) obj;
        } 
        catch (IOException | ParseException exception) 
        {
            exception.printStackTrace();
        }
        return data;
    }
}