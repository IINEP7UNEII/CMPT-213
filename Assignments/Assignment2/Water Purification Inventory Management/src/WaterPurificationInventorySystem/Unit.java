package WaterPurificationInventorySystem;

import java.text.SimpleDateFormat;
import java.util.Date;

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

    private void test()
    {
        SimpleDateFormat dateFormat = new SimpleDateFormat("YYYY-MM-DD");
        String date = dateFormat.format(new Date());

        System.out.println("Date: " + date);
        System.out.println("Serial number test: ");
        if (testSerialNumberString())
        {
            System.out.print("Passed! All checks completed!");
        }
        else
        {
            System.out.print("Failed!\nPlease check that the serial number only contains"
            + "\ndigits, is 3 to 15 digits long, and passes the checksum test!");
        }

        System.out.println("\nModel name test: ");
        if (testModelString())
        {
            System.out.print("Passed! All checks completed!");
        }
        else
        {
            System.out.print("Failed!\nPlease check that the model name is up to 10 characters long!");
        }
    }

    private boolean testSerialNumberString()
    {
        int lengthOfSerialNumber = serialNumber.length();

        if ((lengthOfSerialNumber < 3 || lengthOfSerialNumber > 15)
        || !onlyDigits(lengthOfSerialNumber)
        || !checksumSerialNumber(lengthOfSerialNumber))
        {
            return false;
        }
        return true;
    }

    private boolean testModelString()
    { 
        if (model.length() > 10)
        {
            return false;
        }
        return true;
    }

    private boolean checksumSerialNumber(int length)
    {
        int last = serialNumber.charAt(length - 1) - '0';
        int secondLast = serialNumber.charAt(length - 2) - '0';

        int sumOfPrevious = 0;

        for (int count = 0; count < length - 2; ++count) 
        {
            sumOfPrevious += serialNumber.charAt(count);
        }

        if (sumOfPrevious == (last + secondLast))
        {
            return true;
        }
        return false;
    }

    private boolean onlyDigits(int length)
    {
        for (int count = 0; count < length; ++count) 
        {
            if (serialNumber.charAt(count) < '0' || serialNumber.charAt(count) > '9') 
            {
                return false;
            }
        }
        return true;
    }
}