package CoursePlanner.Model.FormattedCourses;

import java.util.ArrayList;

public class Department 
{
    private int deptID;
    private String name;
    private ArrayList<CourseNumber> courseNums;

    public Department()
    {
        deptID = 0;
        name = "";
        courseNums = new ArrayList<CourseNumber>();
    }

    public Department(int deptID, String name)
    {
        this.deptID = deptID;
        this.name = name;
        courseNums = new ArrayList<CourseNumber>();
    }

    public Department(int deptID, String name, ArrayList<CourseNumber> courseNums)
    {
        this.deptID = deptID;
        this.name = name;
        this.courseNums = courseNums;
    }

    public int getId() 
    {
        return deptID;
    }

    public void setDeptID(int deptID) 
    {
        this.deptID = deptID;
    }

    public String getName() 
    {
        return name;
    }

    public void setName(String name) 
    {
        this.name = name;
    }

    public ArrayList<CourseNumber> getCourseNums() 
    {
        return courseNums;
    }

    public void setCourseNums(ArrayList<CourseNumber> courseNums)
    {
        this.courseNums = courseNums;
    }
}

