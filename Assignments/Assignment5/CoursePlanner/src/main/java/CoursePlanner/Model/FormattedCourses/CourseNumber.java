package CoursePlanner.Model.FormattedCourses;

import java.util.ArrayList;

import CoursePlanner.Controller.Watchers.Watcher;
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
    private int courseId;
    private ArrayList<CourseOffering> offerings;
    private ArrayList<Watcher> watchers;

    public CourseNumber() 
    {
        subject = "";
        catalogNumber  = "";
        courseId = 0;
        offerings = new ArrayList<CourseOffering>();
        watchers = new ArrayList<Watcher>();
    }

    public CourseNumber(CourseData data) 
    {
        subject = data.getSubject();
        catalogNumber = data.getCatalogNumber();
        courseId = 0;
        offerings = new ArrayList<CourseOffering>();
        watchers = new ArrayList<Watcher>();
    }

    public CourseNumber(String subject, String catalogNumber) 
    {
        this.subject = subject;
        this.catalogNumber = catalogNumber;
        courseId = 0;
        offerings = new ArrayList<CourseOffering>();
        watchers = new ArrayList<Watcher>();
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

    public int getId() 
    {
        return courseId;
    }

    public void setId(int courseId) 
    {
        this.courseId = courseId;
    }

    public ArrayList<CourseOffering> getOfferings() 
    {
        return offerings;
    }

    public void setOfferings(ArrayList<CourseOffering> offerings, Component component)
    {
        this.offerings = offerings;
    }

    public void addOffering(CourseOffering courseOffering)
    {
        offerings.add(courseOffering);
        notifyWatcher(courseOffering);
    }

    public void addWatcher(Watcher watcher) 
    {
        watchers.add(watcher);
    }

    public void removeWatcher(long id)
    {
        for (Watcher watcher : watchers)
        {
            if (watcher.getId() == id) 
            {
                watchers.remove(watcher);
            }
        }
    }

    public int watcherAmount()
    {
        return watchers.size();
    }

    public void notifyWatcher(CourseOffering offering) 
    {
        for (Watcher watcher : watchers) 
        {
            watcher.stateChanged(offering);
        }
    }

    public int getEnrolledTotal(int semesterCode) 
    {
        int total = 0;
        for (CourseOffering offering : offerings) 
        {
            if (offering.getSemester() == semesterCode) 
            {
                total += offering.getLectureEnrollTotal();
            }
        }
        return total;
    }
}
