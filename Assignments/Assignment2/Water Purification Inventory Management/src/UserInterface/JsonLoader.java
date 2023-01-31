package UserInterface;

import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class JsonLoader 
{
    public static void main(String[] args) 
    {
        Scanner scan = new Scanner(System.in);
        System.out.println("Enter the path of the JSON file: ");
        String filePath = scan.nextLine();
        JSONParser parser = new JSONParser();
        
        try (FileReader reader = new FileReader(filePath)) 
        {
            Object obj = parser.parse(reader);
            JSONArray data = (JSONArray) obj;

            for (Object o : data) 
            {
                JSONObject jsonObject = (JSONObject) o;
                // Access the data in the JSONObject and replace any data currently managed by the system
                
                System.out.println(jsonObject);
            }
        } 
        catch (IOException | ParseException e)
        {
            e.printStackTrace();
        }
        scan.close();
    }
}