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

    public void addTest(Test newTest)
    {
        tests.add(newTest);
    }

    public boolean isValidModelString(String model)
    { 
        if (model.length() > 10)
        {
            return false;
        }
        return true;
    }

    public boolean isValidSerialString(String serial, int length)
    {
        if ((length < 3 && length > 15)
        || !onlyDigits(serial, length)
        || !checksumSerialNumber(serial, length))
        {
            return false;
        }
        return true;
    }

    private boolean checksumSerialNumber(String serial, int length)
    {
        int serialInt = Integer.parseInt(serial);
        int lastTwo = serialInt % 100;

        int sumOfPrevious = 0;

        for (int count = 0; count < length - 2; ++count) 
        {
            sumOfPrevious += (serial.charAt(count) - 48); //-48 for ascii char to int conversion
        }

        if (sumOfPrevious == (lastTwo))
        {
            return true;
        }
        return false;
    }

    private boolean onlyDigits(String serial, int length)
    {
        for (int count = 0; count < length; ++count) 
        {
            if (serial.charAt(count) < '0' || serial.charAt(count) > '9') 
            {
                return false;
            }
        }
        return true;
    }
}