package WaterPurificationInventorySystem;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Test 
{
    private String date;
    private Boolean isTestPassed;
    private String testResultComment;

    public Test()
    {
        SimpleDateFormat dateFormat = new SimpleDateFormat("YYYY-MM-DD");
        date = dateFormat.format(new Date());
        date = "-";
        isTestPassed = false;
        testResultComment = "";
    }

    public String getDate()
    {
        return date;
    }

    public void setDate(String d)
    {
        date = d;
    }

    public Boolean getStatus()
    {
        return isTestPassed;
    }

    public void setStatus(Boolean s)
    {
        isTestPassed = s;
    }

    public String getComment()
    {
        return testResultComment;
    }

    public void setComment(String status)
    {
        testResultComment = status;
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
