package CoursePlanner.Model.FormattedCourses;

import java.util.ArrayList;

public class Departments 
{
    private ArrayList<Department> departments;

    public Departments()
    {
        departments = new ArrayList<Department>();
    }

    public Departments(CourseList courseList)
    {
        departments = new ArrayList<Department>();
        populateDepartmentsList(courseList);
    }

    public ArrayList<Department> getDepartments() 
    {
        return departments;
    }

    public Department get(int index) 
    {
        return departments.get(index);
    }

    private void populateDepartmentsList(CourseList courseList) 
    {
        int deptCount = 0;
        for (int count = 0; count < courseList.getList().size(); ++count)
        {
            if (!isInDepartmentList(courseList.get(count).getSubject()))
            {
                Department dept = new Department(deptCount, courseList.get(count).getSubject());
                courseList.get(count).setId(dept.getCourseNums().size());
                dept.getCourseNums().add(courseList.get(count));
                departments.add(dept);
                ++deptCount;
            }
            else
            {
                addToDepartment(count, courseList);
            }
        }
    }

    private Boolean isInDepartmentList(String subject) 
    {
        for (Department department : departments)
        {
            if (department.getName().equals(subject))
            {
                return true;
            }
        }
        return false;
    }

    private void addToDepartment(int count, CourseList courseList) 
    {
        for (Department dept : departments)
        {
            if (dept.getName().equals(courseList.get(count).getSubject()))
            {
                courseList.get(count).setId(dept.getCourseNums().size());
                dept.getCourseNums().add(courseList.get(count));
                break;
            }
        }
    }
}
