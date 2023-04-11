package CoursePlanner.Controller;

import CoursePlanner.Controller.Watchers.Watcher;
import CoursePlanner.Model.*;
import CoursePlanner.Model.FormattedCourses.*;
import CoursePlanner.Model.RawData.CSVReader;

import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
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
    private ArrayList<Watcher> watchers = new ArrayList<Watcher>();

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
    public ArrayList<Map<String, String>> getCourses(@PathVariable("id") int id)
    {
        ArrayList<Map<String, String>> courses = new ArrayList<Map<String, String>>();

        try 
        {
            for (CourseNumber courseNum : depts.get(id).getCourseNums())
            {
                Map<String, String> dept = new HashMap<>();
                dept.put("courseId", Integer.toString(courseNum.getId()));
                dept.put("catalogNumber", courseNum.getCatalogNumber());
                courses.add(dept);
            }
        }
        catch (ArrayIndexOutOfBoundsException exception)
        {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "ERROR: Department ID not found in system!");
        }
        return courses;
    }

    @GetMapping("/api/departments/{deptId}/courses/{courseId}/offerings")
    @ResponseStatus(HttpStatus.OK)
    public ArrayList<Map<String, String>> getOfferings
    (@PathVariable("deptId") int deptId, @PathVariable("courseId") int courseId)
    {
        ArrayList<Map<String, String>> offering = new ArrayList<Map<String, String>>();

        try 
        {
            for (CourseOffering courseOffer : depts.get(deptId).getCourseNums().get(courseId).getOfferings())
            {
                Map<String, String> course = new HashMap<>();
                course.put("courseOfferingId", Integer.toString(courseOffer.getId()));
                course.put("location", courseOffer.getLocation());
                course.put("instructors", courseOffer.getInstructorsString());
                course.put("year", Integer.toString(courseOffer.getYear()));
                course.put("semesterCode", Integer.toString(courseOffer.getSemester()));
                course.put("term", courseOffer.getTerm());
                offering.add(course);
            }
        }
        catch (ArrayIndexOutOfBoundsException exception)
        {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, 
            "ERROR: Department or Course ID not found in system!");
        }
        return offering;
    }

    @GetMapping("/api/departments/{deptId}/courses/{courseId}/offerings/{courseOfferingId}")
    @ResponseStatus(HttpStatus.OK)
    public ArrayList<Map<String, String>> getComponents
    (@PathVariable("deptId") int deptId, @PathVariable("courseId") int courseId, 
    @PathVariable("courseOfferingId") int courseOfferingId)
    {
        ArrayList<Map<String, String>> sections = new ArrayList<Map<String, String>>();

        try 
        {
            for (Component section : depts.get(deptId).getCourseNums().get(courseId)
            .getOfferings().get(courseOfferingId).getComponents())
            {
                Map<String, String> course = new HashMap<>();
                course.put("type", section.getType());
                course.put("enrollmentTotal", Integer.toString(section.getEnrolled()));
                course.put("enrollmentCap", Integer.toString(section.getMaxCapacity()));
                sections.add(course);
            }
        }
        catch (ArrayIndexOutOfBoundsException exception)
        {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, 
            "ERROR: Department or Course ID not found in system!");
        }
        return sections;
    }

    /////////////////////bonus

    @PostMapping("/api/addoffering")
    @ResponseStatus(HttpStatus.CREATED)
    public void newOffering(@RequestBody String semester, @RequestBody String subjectName, 
    @RequestBody String catalogNumber, @RequestBody String location, @RequestBody String enrollmentCap,
    @RequestBody String component, @RequestBody String enrollmentTotal, @RequestBody String instructor) ///////////////////test
    {
        Component newComponent = new Component(component, Integer.parseInt(enrollmentTotal),
        Integer.parseInt(enrollmentCap));
        CourseOffering newOffering = new CourseOffering(Integer.parseInt(semester), 
        location, instructor, newComponent);
        CourseNumber newCourseNumber = new CourseNumber(subjectName, catalogNumber);
        newCourseNumber.addOffering(newOffering);

        courseList.getList().add(newCourseNumber);
        depts = new Departments(courseList);
    }

    @GetMapping("/api/watchers")
    @ResponseStatus(HttpStatus.OK)
    public ArrayList<Watcher> getWatchers()
    {
        return watchers;
    }

    @PostMapping("/api/watchers")
    @ResponseStatus(HttpStatus.CREATED) //finish watchers
    public Watcher newWatcher(@RequestBody int deptId, @RequestBody int courseId) 
    {
        Department registerDept = null;
        Course registerCourse = null;

        int courseExceptionFlag = 0;

        for (Department eachDept : departments) 
        {
            if (eachDept.getDeptId() == watcherData.getDeptId()) 
            {
                courseExceptionFlag = 1;
                registerDept = eachDept;

                if (eachDept.findCourseById(watcherData.getCourseId()) != null) 
                {
                    registerCourse = eachDept.findCourseById(watcherData.getCourseId());
                }
            }
        }

        if (registerCourse != null) 
        {
            Watcher newWatcher = new Watcher(nextId.incrementAndGet(), registerDept, registerCourse);
            watchers.add(newWatcher);
            return newWatcher;
        }


        if (courseExceptionFlag == 1) 
        {
            String error = "Course of ID " + watcherData.getCourseId() + " not found.";
            throw new CourseNotFoundException(error);
        }

        String error = "Department of ID " + watcherData.getDeptId() + " not found.";
        throw new DepartmentNotFoundException(error);
    }
}
