package CoursePlanner.Model.FormattedCourses;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Collections;

import CoursePlanner.Model.GraphData;

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

    public void setId(int deptID) 
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

    public ArrayList<GraphData> getDeptGraphData() 
    {
        ArrayList<GraphData> graph = new ArrayList<GraphData>();
        ArrayList<Integer> semesterCodes = populateSemesterCodes();

        for (Integer semesterCode : semesterCodes) 
        {
            int enrolled = 0;
            for (CourseNumber course : courseNums) 
            {
                enrolled += course.getEnrolledTotal(semesterCode);
            }
            GraphData addPoint = new GraphData(semesterCode, enrolled);
            graph.add(addPoint);
        }

        Collections.sort(graph, new Comparator<GraphData>()
        {
            @Override
            public int compare(GraphData point1, GraphData point2) 
            {
                return point1.getSemester() - point2.getSemester();
            }
        });

        return graph;
    }

    private ArrayList<Integer> populateSemesterCodes()
    {
        ArrayList<Integer> semesterCodes = new ArrayList<Integer>();

        for (CourseNumber course : courseNums)
        {
            for (CourseOffering offering : course.getOfferings())
            {
                if (!semesterInCodes(semesterCodes, offering.getSemester()))
                {
                    semesterCodes.add(offering.getSemester());
                }
            }
        }

        return semesterCodes;
    }

    private Boolean semesterInCodes(ArrayList<Integer> semesterCodes, int semesterToCheck)
    {
        for (Integer semester : semesterCodes)
        {
            if (semesterToCheck == semester)
            {
                return true;
            }
        }
        return false;
    }
}

