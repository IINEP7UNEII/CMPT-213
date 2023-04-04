package CoursePlanner.Model.FormattedCourses;

import CoursePlanner.Model.RawData.CourseData;

/**
 * Description: This Component class handles and store the components of a course being the
 * componentCode (type), enrolmentTotal (enrolled), and enrolmentCapacity (maxCapacity).
 * This was a made a separate class from course for easier compilation of different class components
 * found in the csv files.
 *
 * @author Daniel Tolsky
 * @version 1.0
 */
public class Component 
{
    private String type;
    private int enrolled;
    private int maxCapacity;

    public Component()
    { }

    public Component(String type, int enrolled, int maxCapacity)
    { 
        this.type = type;
        this.enrolled = enrolled;
        this.maxCapacity = maxCapacity;
    }

    public Component(CourseData data)
    { 
        type = data.getComponentCode();
        enrolled = data.getEnrollmentTotal();
        maxCapacity = data.getEnrollmentCapacity();
    }

    public String getType() 
    {
        return type;
    }

    public void setType(String type) 
    {
        this.type = type;
    }

    public int getEnrolled()
    {
        return enrolled;
    }

    public void setEnrolled(int enrolled) 
    {
        this.enrolled = enrolled;
    }

    public int getMaxCapacity() 
    {
        return maxCapacity;
    }

    public void setMaxCapacity(int maxCapacity) 
    {
        this.maxCapacity = maxCapacity;
    }
}
