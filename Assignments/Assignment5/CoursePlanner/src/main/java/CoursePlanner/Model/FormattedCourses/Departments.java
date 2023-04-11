package CoursePlanner.Model.FormattedCourses;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

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
        sortAll();
        setIds();
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
                dept.getCourseNums().add(courseList.get(count));
                break;
            }
        }
    }

    private void setIds() 
    {
        for (int deptCount = 0; deptCount 
        < departments.size(); ++deptCount)
        {
            departments.get(deptCount).setId(deptCount);

            for (int courseNumCount = 0; courseNumCount < departments.get(deptCount)
            .getCourseNums().size(); ++courseNumCount)
            {
                departments.get(deptCount).getCourseNums().get(courseNumCount).setId(courseNumCount);

                for (int offeringCount = 0; offeringCount < departments.get(deptCount)
                .getCourseNums().get(courseNumCount).getOfferings().size(); ++offeringCount)
                {
                    departments.get(deptCount).getCourseNums().get(courseNumCount)
                    .getOfferings().get(offeringCount).setId(offeringCount);
                }
            }
        }
    }

    private void sortAll() 
    {
        sortDepartments();
        sortCourseNums();
        sortOfferings();
    }

    private void sortDepartments()
    {
        Collections.sort(departments, new Comparator<Department>() 
        {
            @Override
            public int compare(Department dept1, Department dept2) 
            {
                return dept1.getName().compareTo(dept2.getName());
            }
        });
    }

    private void sortCourseNums()
    {
        for (Department dept : departments)
        {
            Collections.sort(dept.getCourseNums(), new Comparator<CourseNumber>()  
            {
                @Override
                public int compare(CourseNumber course1, CourseNumber course2) 
                {
                    return course1.getCatalogNumber().compareTo(course2.getCatalogNumber());
                }
            });
        }
    }

    private void sortOfferings()
    {
        for (Department dept : departments)
        {
            for (CourseNumber courseNum : dept.getCourseNums())
            {
                Collections.sort(courseNum.getOfferings(), new Comparator<CourseOffering>()  
                {
                    @Override
                    public int compare(CourseOffering offering1, CourseOffering offering2) 
                    {
                        return Integer.compare(offering1.getSemester(), offering2.getSemester());
                    }
                });
            }
        }
    }
}
