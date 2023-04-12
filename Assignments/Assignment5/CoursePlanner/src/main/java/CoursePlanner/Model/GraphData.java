package CoursePlanner.Model;

public class GraphData 
{
    private int semesterCode;
    private int totalCoursesTaken;

    public GraphData(int semesterCode, int totalCoursesTaken) 
    {
        this.semesterCode = semesterCode;
        this.totalCoursesTaken = totalCoursesTaken;
    }

    public int getCoursesTaken() 
    {
        return totalCoursesTaken;
    }

    public void setCoursesTaken(int totalCoursesTaken)  
    {
        this.totalCoursesTaken = totalCoursesTaken;
    }

    public int getSemester() 
    {
        return semesterCode;
    }

    public void setSemester(int semesterCode)  
    {
        this.semesterCode = semesterCode;
    }
}