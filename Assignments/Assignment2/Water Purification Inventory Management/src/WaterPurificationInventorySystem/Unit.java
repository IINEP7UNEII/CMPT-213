package WaterPurificationInventorySystem;

import java.util.Vector;

public class Unit 
{
    private String serialNumber;
    private String model;
    private boolean shipped;
    private Vector<Test> tests;

    public Unit()
    {
        serialNumber = "";
        model = "";
        shipped = false;
        tests = new Vector<Test>();
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

    public boolean getShippedStatus()
    {
        return shipped;
    }

    public void setShippedStatus(boolean status)
    {
        shipped = status;
    }

    public Vector<Test> getTests()
    {
        return tests;
    }
}