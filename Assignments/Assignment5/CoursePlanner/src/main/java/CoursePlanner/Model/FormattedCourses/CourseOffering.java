package CoursePlanner.Model.FormattedCourses;

import CoursePlanner.Model.RawData.CourseData;

/**
 * Description: This CourseOffering class is the main class which hold information for each individual course offering.
 * It contains an ArrayList of components which hold the specific class components (ie: Tutorials, or Lectures) for the course and holds
 * basic identifying information for that course.
 *
 * @author Daniel Tolsky
 * @version 1.0
 */
import java.util.ArrayList;

public class CourseOffering
{
    private int semester;
    private String location;
    private ArrayList<String> instructors;
    private ArrayList<Component> components;

    public CourseOffering() 
    {
        semester = 0;
        location = null;
        instructors = null;
        components = new ArrayList<Component>();
    }

    public CourseOffering(CourseData data) 
    {
        semester = data.getSemester();
        location = data.getLocation();
        instructors = data.getInstructors();
        components = new ArrayList<Component>();
        components.add(new Component(
        data.getComponentCode(), 
        data.getEnrollmentTotal(), 
        data.getEnrollmentCapacity()));
    }

    public int getSemester() 
    {
        return semester;
    }

    public void setSemester(int semester) 
    {
        this.semester = semester;
    }

    public String getLocation() 
    {
        return location;
    }

    public void setLocation(String location) 
    {
        this.location = location;
    }

    public ArrayList<String> getInstructors() 
    {
        return instructors;
    }

    public void setInstructors(ArrayList<String> instructors) 
    {
        this.instructors = instructors;
    }

    public ArrayList<Component> getComponents()
    {
        return components;
    }
}