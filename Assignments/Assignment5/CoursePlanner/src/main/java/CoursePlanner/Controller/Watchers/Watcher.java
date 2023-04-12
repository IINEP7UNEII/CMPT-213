package CoursePlanner.Controller.Watchers;

import java.util.Observer;
import java.util.Observable;
import java.util.ArrayList;
import java.util.Date;
import CoursePlanner.Model.FormattedCourses.*;

public class Watcher implements Observer
{
    private long watcherId;
    private Department department;
    private CourseNumber courseNum;
    private ArrayList<String> events;

    public Watcher() 
    { }

    public Watcher(long watcherId, Department department, CourseNumber courseNum) 
    {
        this.watcherId = watcherId;
        this.department = department;
        this.courseNum = courseNum;
        events = new ArrayList<String>();
    }

    public void stateChanged(CourseOffering offering) 
    {
        Date date = new Date();
        String event = date + ": Added section " + offering.getComponents().get(0).getType() 
        + " with enrollment (" + offering.getComponents().get(0).getEnrolled() 
        + "/" + offering.getComponents().get(0).getMaxCapacity() 
        + ") to offering " + offering.getTerm() + " " + offering.getYear();
        events.add(event);
    }

    public long getId() 
    {
        return watcherId;
    }

    public void setId(long watcherId) 
    {
        this.watcherId = watcherId;
    }

    public Department getDepartment() 
    {
        return department;
    }

    public void setDepartment(Department department)
    {
        this.department = department;
    }

    public CourseNumber getCourse() 
    {
        return courseNum;
    }

    public void setCourse(CourseNumber courseNum) 
    {
        this.courseNum = courseNum;
    }

    public ArrayList<String> getEvents() 
    {
        return events;
    }

    public void setEvents(ArrayList<String> events) 
    {
        this.events = events;
    }

    public void addEvents(String newEvent) 
    {
        this.events.add(newEvent);
    }

    @Override
    public void update(Observable arg0, Object arg1) 
    {
        throw new UnsupportedOperationException("Unimplemented method \"update()\"");
    }
}