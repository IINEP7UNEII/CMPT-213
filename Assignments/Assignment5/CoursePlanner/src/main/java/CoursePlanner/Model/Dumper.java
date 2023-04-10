package CoursePlanner.Model;

import java.util.ArrayList;

import CoursePlanner.Model.FormattedCourses.*;
import CoursePlanner.Model.RawData.CourseData;

/**
 * Description: This Dumper class is respondible for dumping (outputing) the formatted and compiled model information
 * into the console when called. It contains a dumpBasicCourses() method which is used for debugging and the required
 * dumpFormattedCourses() method which dumps the compiled and formatted model information.
 *
 * @author Daniel Tolsky
 * @version 1.0
 */
public class Dumper 
{
    public Dumper() 
    { }

    public void dumpBasicCourses(ArrayList<CourseData> courseData) //for debugging
    {
        for (CourseData data : courseData)
        {
            data.dataDump();
            System.out.println();
        }
    }

    public void dumpFormattedCourses(CourseList courseList)
    {
        for (int courseNumCount = 0; courseNumCount < courseList.getList().size(); ++courseNumCount)
        {
            System.out.println(courseList.getList().get(courseNumCount).getSubject()
            + courseList.getList().get(courseNumCount).getCatalogNumber());

            for (int courseOfferingCount = 0; courseOfferingCount 
            < courseList.getList().get(courseNumCount).getOfferings().size(); ++courseOfferingCount)
            {
                dumpOutputHelper(courseList, courseNumCount, courseOfferingCount);
            }
        }
    }

    private void dumpOutputHelper(CourseList courseList, int courseNumCount, int courseOfferingCount)
    {
        System.out.println("\t" + courseList.getList().get(courseNumCount)
        .getOfferings().get(courseOfferingCount).getSemester()
        + " in " + courseList.getList().get(courseNumCount).getOfferings()
        .get(courseOfferingCount).getLocation()
        + " by " + getInstructorsString(courseList.getList().get(courseNumCount)
        .getOfferings().get(courseOfferingCount).getInstructors()));

        for (int componentCount = 0; componentCount
        < courseList.getList().get(courseNumCount).getOfferings()
        .get(courseOfferingCount).getComponents().size(); ++componentCount)
        {
            System.out.println("\t\tType=" + courseList.getList()
            .get(courseNumCount).getOfferings().get(courseOfferingCount).getComponents()
            .get(componentCount).getType()
            + ", Enrollment=" + courseList.getList()
            .get(courseNumCount).getOfferings().get(courseOfferingCount).getComponents()
            .get(componentCount).getEnrolled()
            + "/" + courseList.getList()
            .get(courseNumCount).getOfferings().get(courseOfferingCount).getComponents()
            .get(componentCount).getMaxCapacity());
        }
    }

    private String getInstructorsString(ArrayList<String> instructors)
    {
        StringBuilder builder = new StringBuilder();
        
        for (int count = 0; count < instructors.size(); ++count)
        {
            if (count < (instructors.size() - 1))
            {
                builder.append(instructors.get(count) + ", ");
            }
            else
            {
                builder.append(instructors.get(count));
            }
        }

        return builder.toString();
    }
}
