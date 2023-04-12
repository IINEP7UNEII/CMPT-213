package CoursePlanner.Model.RawData;

public class RawData extends CourseData
{
    private String instructor;

    public RawData() 
    {
        instructor = "";
    }

    public void setInstructorsString(String instructor) 
    {
        this.instructor = instructor;
    }

    public String getInstructorsString() 
    {
        return instructor;
    }
}
