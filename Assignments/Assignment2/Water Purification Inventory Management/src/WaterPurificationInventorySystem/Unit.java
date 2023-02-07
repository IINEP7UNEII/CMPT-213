package WaterPurificationInventorySystem;

import java.util.ArrayList;

/**
* Description: This Unit class holds the base object which the Water Purification Inventory System
* handles. It contains the specific data to a single Water Purification Unit and has some mehods
* do handle and check this data.
*
* @author Daniel Tolsky
* @version 1.0
*/

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
        long serialLong = Long.parseLong(serial);
        int lastTwo = (int) (serialLong % 100);
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
        try
        {
            Long.parseLong(serial);
        }
        catch (NumberFormatException exception)
        {
            return false;
        }
        return true;
    }
}