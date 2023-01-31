package WaterPurificationInventorySystem;

public class Unit 
{
    private String serialNumber;
    private String model;
    private boolean shipped;

    public Unit()
    {
        serialNumber = "";
        model = "";
        shipped = false;
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
}