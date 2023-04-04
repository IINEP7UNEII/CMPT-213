package CoursePlanner.Model.FormattedCourses;

import java.util.ArrayList;

import CoursePlanner.Model.RawData.CourseData;

/**
 * Description: This CourseList class is the main database class of the CoursePlanner application.
 * It holds the database of courses and handles the population of courses from a dataset.
 *
 * @author Daniel Tolsky
 * @version 1.0
 */
public class CourseList
{
    private ArrayList<CourseNumber> courseNums;

    public CourseList()
    {
        courseNums = new ArrayList<CourseNumber>();
    }

    public ArrayList<CourseNumber> getList()
    {
        return courseNums;
    }

    public void populateCourseList(ArrayList<CourseData> dataList)
    {
        for (CourseData data : dataList)
        {
            int courseNumIndex = courseNumberInList(data);
            if (courseNumIndex != -1)
            {
                int offeringIndex = courseDataInListIndex(data);
                if (offeringIndex != -1)
                {
                    populateExistingCourseList(data, courseNumIndex, offeringIndex);
                }
                else
                {
                    CourseOffering newCourse = new CourseOffering(data);
                    courseNums.get(courseNumIndex).getOfferings().add(newCourse);
                }
            }
            else
            {
                CourseNumber newCourseNum = new CourseNumber(data);
                courseNums.add(newCourseNum);

                CourseOffering newCourseOffering = new CourseOffering(data);
                courseNums.get(0).getOfferings().add(newCourseOffering);
            }
        }
    }

    public void populateExistingCourseList(CourseData data, int courseNumIndex, int offeringIndex)
    {
        Component component = new Component(data);

        int componentTypeIndex = componentTypeInCourseIndex
        (courseNums.get(courseNumIndex).getOfferings().get(offeringIndex), component.getType());

        if (componentTypeIndex != -1)
        {
            int currEnrollment = courseNums.get(courseNumIndex).getOfferings()
            .get(offeringIndex).getComponents().get(componentTypeIndex).getEnrolled();
            int addedEnrollment = component.getEnrolled();
            int currCapacity = courseNums.get(courseNumIndex).getOfferings()
            .get(offeringIndex).getComponents().get(componentTypeIndex).getMaxCapacity();
            int addedCapacity = component.getMaxCapacity();

            courseNums.get(courseNumIndex).getOfferings().get(offeringIndex)
            .getComponents().get(componentTypeIndex).setEnrolled(currEnrollment + addedEnrollment);
            courseNums.get(courseNumIndex).getOfferings().get(offeringIndex).getComponents()
            .get(componentTypeIndex).setMaxCapacity(currCapacity + addedCapacity);
        }
        else
        {
            courseNums.get(courseNumIndex).getOfferings().get(offeringIndex).getComponents().add(component);
        }

        int newInstructorIndex = newInstructorIndex(data, courseNumIndex, offeringIndex);
        if (newInstructorIndex != -1)
        {
            courseNums.get(courseNumIndex).getOfferings()
            .get(offeringIndex).getInstructors().add(data.getInstructors().get(newInstructorIndex));
        }
    }

    private int courseNumberInList(CourseData data)
    {
        for (int count = 0; count < courseNums.size(); ++count)
        {
            if (data.getSubject().equals(courseNums.get(count).getSubject())
            && data.getCatalogNumber().equals(courseNums.get(count).getCatalogNumber()))
            {
                return count;
            }
        }
        return -1;
    }

    private int courseDataInListIndex(CourseData data)
    {
        for (int numsCount = 0; numsCount < courseNums.size(); ++numsCount)
        {
            for (int offeringCount = 0; offeringCount 
            < courseNums.get(numsCount).getOfferings().size(); ++offeringCount)
            {
                if (data.getSubject().equals(courseNums.get(numsCount).getSubject())
                && data.getCatalogNumber().equals(courseNums.get(numsCount).getCatalogNumber())
                && data.getSemester() == courseNums.get(numsCount).getOfferings().get(offeringCount).getSemester()
                && data.getLocation().equals(courseNums.get(numsCount).getOfferings().get(offeringCount).getLocation()))
                {
                    return offeringCount;
                }
            }
        }
        return -1;
    }

    private int componentTypeInCourseIndex(CourseOffering course, String type) 
    {
        for (int count = 0; count < course.getComponents().size(); ++count)
        {
            if (type.equals(course.getComponents().get(count).getType()))
            {
                return count;
            }
        }
        return -1;
    }

    private int newInstructorIndex(CourseData data, int courseNumIndex, int coursesIndex)
    {
        ArrayList<String> dataInstructors = new ArrayList<String>();
        ArrayList<String> courseInstructors = new ArrayList<String>();
        dataInstructors = data.getInstructors();
        courseInstructors = courseNums.get(courseNumIndex).getOfferings().get(coursesIndex).getInstructors();
        int index = -1;

        for (int coursesCount = 0; coursesCount < courseInstructors.size(); ++coursesCount)
        {
            for (int dataCount = 0; dataCount < dataInstructors.size(); ++dataCount)
            {
                if (dataInstructors.get(dataCount).equals(courseInstructors.get(coursesCount)))
                {
                    return -1;
                }
                else
                {
                    index = dataCount;
                }
            }
        }
        return index;
    }
}
