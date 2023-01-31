package WaterPurificationInventorySystem;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Tester 
{
    public void test(Unit unit)
    {
        SimpleDateFormat dateFormat = new SimpleDateFormat("YYYY-MM-DD");
        String date = dateFormat.format(new Date());

        System.out.println("Date: " + date);
        System.out.println("Serial number test: ");
        if (testSerialNumberString(unit.getSerialNumber(), unit.getSerialNumber().length()))
        {
            System.out.print("Passed! All checks completed!");
        }
        else
        {
            System.out.print("Failed!\nPlease check that the serial number only contains"
            + "\ndigits, is 3 to 15 digits long, and passes the checksum test!");
        }

        System.out.println("\nModel name test: ");
        if (testModelString(unit.getModel()))
        {
            System.out.print("Passed! All checks completed!");
        }
        else
        {
            System.out.print("Failed!\nPlease check that the model name is up to 10 characters long!");
        }
    }

    private boolean testSerialNumberString(String serial, int length)
    {
        if ((length < 3 || length > 15)
        || !onlyDigits(serial, length)
        || !checksumSerialNumber(serial, length))
        {
            return false;
        }
        return true;
    }

    private boolean testModelString(String model)
    { 
        if (model.length() > 10)
        {
            return false;
        }
        return true;
    }

    private boolean checksumSerialNumber(String serial, int length)
    {
        int last = serial.charAt(length - 1) - '0';
        int secondLast = serial.charAt(length - 2) - '0';

        int sumOfPrevious = 0;

        for (int count = 0; count < length - 2; ++count) 
        {
            sumOfPrevious += serial.charAt(count);
        }

        if (sumOfPrevious == (last + secondLast))
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
