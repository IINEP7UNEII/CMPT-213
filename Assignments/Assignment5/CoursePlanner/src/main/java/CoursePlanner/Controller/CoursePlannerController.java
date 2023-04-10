package CoursePlanner.Controller;

import CoursePlanner.Model.*;
import CoursePlanner.Model.FormattedCourses.*;
import CoursePlanner.Model.RawData.CSVReader;

import org.springframework.web.bind.annotation.*;
import org.springframework.http.HttpStatus;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Description: This CoursePlannerController class handles the /api/about and /api/dump-model
 * requests for phase 1 of the assignment 5 using a springboot framework.
 *
 * @author Daniel Tolsky
 * @version 1.0
 */
@RestController
public class CoursePlannerController
{
    private CourseList courseList;
    private CSVReader reader;
    private Departments depts;

    public CoursePlannerController()
    {
        reader = new CSVReader();
        reader.readCSVFile();
        courseList = new CourseList();
        courseList.populateCourseList(reader.getCourseDataList());
        depts = new Departments(courseList);
    }

    @GetMapping("/api/about")
    @ResponseStatus(HttpStatus.OK)
    public Map<String, String> getAbout()
    {
        Map<String, String> aboutReturn = new HashMap<>();
        aboutReturn.put("appName", "the Cool Course Planner");
        aboutReturn.put("authorName", "Daniel Tolsky");
        return aboutReturn;
    }

    @GetMapping("/api/dump-model")
    @ResponseStatus(HttpStatus.OK)
    public void getDump()
    {
        Dumper dumper = new Dumper();
        dumper.dumpFormattedCourses(courseList);
    }

    @GetMapping("/api/departments")
    @ResponseStatus(HttpStatus.OK)
    public ArrayList<Map<String, String>> getDepartments()
    {
        ArrayList<Map<String, String>> onlyDepts = new ArrayList<Map<String, String>>();
        for (Department department : depts.getDepartments())
        {
            Map<String, String> dept = new HashMap<>();
            dept.put("deptId", Integer.toString(department.getId()));
            dept.put("name", department.getName());
            onlyDepts.add(dept);
        }
        return onlyDepts;
    }

    @GetMapping("/api/departments/{id}/courses")
    @ResponseStatus(HttpStatus.OK)
    public ArrayList<Map<String, String>> getDepartmentCourses(@PathVariable("id") int id)
    {
        ArrayList<Map<String, String>> courses = new ArrayList<Map<String, String>>();

        for (CourseNumber courseNum : depts.get(id).getCourseNums())
        {
            Map<String, String> dept = new HashMap<>();
            dept.put("courseId", Integer.toString(courseNum.getId()));
            dept.put("catalogNumber", courseNum.getCatalogNumber());
            courses.add(dept);
        }
        return courses;
    }

    @GetMapping("/api/departments/{deptId}/courses/{courseId}/offerings")
    @ResponseStatus(HttpStatus.OK)
    public ArrayList<Map<String, String>> getDepartmentCourses
    (@PathVariable("deptId") int deptId, @PathVariable("courseId") int courseId)
    {
        ArrayList<Map<String, String>> offering = new ArrayList<Map<String, String>>();

        for (CourseOffering courseOffer : depts.get(deptId).getCourseNums().get(courseId).getOfferings())
        {
            Map<String, String> course = new HashMap<>();
            course.put("courseOfferingId", Integer.toString(courseOffer.getId())); //////////////////////
            course.put("location", courseOffer.getLocation());
            course.put("instructors", courseOffer.getInstructorsString());
            course.put("year", Integer.toString(courseOffer.getYear()));
            course.put("semesterCode", Integer.toString(courseOffer.getSemester()));
            course.put("term", courseOffer.getTerm()); ////////////////////
            offering.add(course);
        }
        return offering;
    }
}
