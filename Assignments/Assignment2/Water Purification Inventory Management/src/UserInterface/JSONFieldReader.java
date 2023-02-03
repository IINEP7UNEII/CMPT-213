package UserInterface;

import org.json.simple.JSONObject;

public class JSONFieldReader 
{
    public static void main(String[] args) 
    {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("name", "John Doe");
        jsonObject.put("age", 30);
        jsonObject.put("isMarried", true);
    
        // Read the "name" field
        String name = (String) jsonObject.get("name");
        System.out.println("Name: " + name);
    
        // Read the "age" field
        int age = (int) jsonObject.get("age");
        System.out.println("Age: " + age);
    
        // Read the "isMarried" field
        boolean isMarried = (Boolean) jsonObject.get("isMarried");
        System.out.println("Is Married: " + isMarried);
    }
}
