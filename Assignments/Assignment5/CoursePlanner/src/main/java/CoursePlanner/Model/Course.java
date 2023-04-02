package WebApp.Model;

import java.util.ArrayList;

public class Course 
{
    private int semester;
    private String subject;
    private String catalogNumber;
    private String location;
    private int enrollmentCapacity;
    private int enrollmentTotal;
    private ArrayList<String> instructors;
    private String componentCode;

    public Course() 
    {
        semester = 0;
        subject = null;
        catalogNumber  = null;
        location = null;
        enrollmentCapacity = 0;
        enrollmentTotal = 0;
        instructors = null;
        componentCode = null;
    }

    public int getSemester() 
    {
        return semester;
    }

    public void setSemester(int semester) 
    {
        this.semester = semester;
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

    public String getLocation() 
    {
        return location;
    }

    public void setLocation(String location) 
    {
        this.location = location;
    }

    public int getEnrollmentCapacity() 
    {
        return enrollmentCapacity;
    }

    public void setEnrollmentCapacity(int enrollmentCapacity) 
    {
        this.enrollmentCapacity = enrollmentCapacity;
    }

    public int getEnrollmentTotal() 
    {
        return enrollmentTotal;
    }

    public void setEnrollmentTotal(int enrollmentTotal) 
    {
        this.enrollmentTotal = enrollmentTotal;
    }

    public ArrayList<String> getInstructors() 
    {
        return instructors;
    }

    public void setInstructors(ArrayList<String> instructors) 
    {
        this.instructors = instructors;
    }

    public String getComponentCode() 
    {
        return componentCode;
    }

    public void setComponentCode(String componentCode) 
    {
        this.componentCode = componentCode;
    }

    public void debugDisplay() 
    {
        System.out.println("Semester: " + semester);
        System.out.println("Subject: " + subject);
        System.out.println("Catalog Number: " + catalogNumber);
        System.out.println("Location: " + location);
        System.out.println("Enrollment Capacity: " + enrollmentCapacity);
        System.out.println("Enrollment Total: " + enrollmentTotal);
        System.out.println("Instructors: " + instructors);
        System.out.println("Component Code: " + componentCode);
    }
}