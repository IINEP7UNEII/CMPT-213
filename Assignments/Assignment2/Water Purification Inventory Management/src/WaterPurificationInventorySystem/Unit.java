package WaterPurificationInventorySystem;

import java.util.ArrayList;

public class Unit 
{
    private String serialNumber;
    private String model;
    private String dateShipped;
    private ArrayList<Test> tests;

    public Unit()
    {
        serialNumber = "";
        model = "";
        dateShipped = "-";
        tests = new ArrayList<Test>();
    }

    public String getSerialNumber()
    {
        return serialNumber;
    }

    public void setSerialNumber(String num)
    {
        serialNumber = num;
    }

    public String getModel()
    {
        return model;
    }

    public void setModel(String name)
    {
        model = name;
    }

    public String getDateShipped()
    {
        return dateShipped;
    }

    public void setDateShipped(String shipped)
    {
        dateShipped = shipped;
    }

    public ArrayList<Test> getTests()
    {
        return tests;
    }

    public void setTests(ArrayList<Test> newTests)
    {
        tests = newTests;
    }
}