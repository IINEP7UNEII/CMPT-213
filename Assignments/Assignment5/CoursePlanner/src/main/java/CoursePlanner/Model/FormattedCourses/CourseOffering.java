package CoursePlanner.Model.FormattedCourses;

import CoursePlanner.Model.RawData.CourseData;
import CoursePlanner.Model.RawData.RawData;

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
    private int offeringId;
    private ArrayList<String> instructors;
    private ArrayList<Component> components;

    public CourseOffering() 
    {
        semester = 0;
        location = "";
        offeringId = 0;
        instructors = new ArrayList<String>();
        components = new ArrayList<Component>();
    }

    public CourseOffering(CourseData data) 
    {
        semester = data.getSemester();
        location = data.getLocation();
        offeringId = 0;
        instructors = data.getInstructors();
        components = new ArrayList<Component>();
        components.add(new Component(
        data.getComponentCode(), 
        data.getEnrollmentTotal(), 
        data.getEnrollmentCapacity()));
    }

    public CourseOffering(CourseData data, Component component) 
    {
        semester = data.getSemester();
        location = data.getLocation();
        offeringId = 0;
        instructors = data.getInstructors();
        components = new ArrayList<Component>();
        components.add(component);
    }

    public CourseOffering(int semester, String location, String instructor, Component component) 
    {
        this.semester = semester;
        this.location = location;
        offeringId = 0;
        instructors = new ArrayList<String>();
        instructors.add(instructor);
        components = new ArrayList<Component>();
        components.add(component);
    }

    public int getSemester() 
    {
        return semester;
    }

    public int getYear() 
    {
        return (semester / 10) + 1900;
    }

    public String getTerm()
    {
        String result = "";
        switch (semester % 10)
        {
            case 1:
                result = "Spring";
                break;

            case 4:
                result = "Summer";
                break;

            case 7:
                result = "Fall";
                break;
        }
        return result;
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

    public String getInstructorsString() 
    {
        StringBuilder builder = new StringBuilder();
        for (int count = 0; count < instructors.size(); ++count) 
        {
            builder.append(instructors.get(count));

            if (count < instructors.size() - 1) 
            {
                builder.append(", ");
            }
        }
        return builder.toString();
    }

    public void setInstructors(ArrayList<String> instructors) 
    {
        this.instructors = instructors;
    }

    public int getId() 
    {
        return offeringId;
    }

    public void setId(int offeringId) 
    {
        this.offeringId = offeringId;
    }

    public ArrayList<Component> getComponents()
    {
        return components;
    }

    public int getLectureEnrollTotal() 
    {
        int total = 0;
        for (Component component : components)
        {
            if (component.getType().equals("LEC")) 
            {
                total += component.getEnrolled();
            }
        }
        return total;
    }
}