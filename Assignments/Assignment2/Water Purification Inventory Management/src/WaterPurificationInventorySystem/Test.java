package WaterPurificationInventorySystem;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
* Description: This Test class handles the information and format of a single test used
* for the Water Purification System Units. It has some data elements which hold the
* necessary information for the test which are done on said units. Mehods are provided
* to handle the specific data for each test.
*
* @author Daniel Tolsky
* @version 1.0
*/

public class Test 
{
    private String date;
    private String status;
    private String testResultComment;

    public Test()
    {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        date = dateFormat.format(new Date());
        status = "-";
        testResultComment = "-";
    }

    public String getDate()
    {
        return date;
    }

    public void setDate(String d)
    {
        date = d;
    }

    public String getStatus()
    {
        return status;
    }

    public void setStatus(String s)
    {
        status = s;
    }

    public String getComment()
    {
        return testResultComment;
    }

    public void setComment(String status)
    {
        testResultComment = status;
    }
}
