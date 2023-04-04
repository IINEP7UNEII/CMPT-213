package CoursePlanner.Model.FormattedCourses;

import java.util.ArrayList;

import CoursePlanner.Model.RawData.CourseData;

/**
 * Description: This CourseNumber hold the parameters which identify a certain course in the databse (ie CMPT 213, or MACM 201).
 * It holds an ArrayList of course offerings which themselves make up the components of the course. This class is needs for easier search
 * and tracking of the courses in the databse.
 *
 * @author Daniel Tolsky
 * @version 1.0
 */
public class CourseNumber 
{
    private String subject;
    private String catalogNumber;
    private ArrayList<CourseOffering> offerings;

    public CourseNumber() 
    {
        subject = null;
        catalogNumber  = null;
        offerings = null;
    }

    public CourseNumber(CourseData data) 
    {
        subject = data.getSubject();
        catalogNumber = data.getCatalogNumber();
        offerings = new ArrayList<CourseOffering>();
    }

    public String getSubject() 
    {
        return subject;
    }

    public void setSubject(String subject) 
    {
        this.subject = subject;
    }

    public String getCatalogNumber() 
    {
        return catalogNumber;
    }

    public void setCatalogNumber(String catalogNumber)
    {
        this.catalogNumber = catalogNumber;
    }

    public ArrayList<CourseOffering> getOfferings() 
    {
        return offerings;
    }

    public void setOfferings(ArrayList<CourseOffering> offerings)
    {
        this.offerings = offerings;
    }
}
