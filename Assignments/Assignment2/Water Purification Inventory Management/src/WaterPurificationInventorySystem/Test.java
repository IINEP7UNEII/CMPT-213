package WaterPurificationInventorySystem;

import java.text.SimpleDateFormat;
import java.util.Date;

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
