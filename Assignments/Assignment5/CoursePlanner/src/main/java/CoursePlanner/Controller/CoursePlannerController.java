package CoursePlanner.Controller;

import CoursePlanner.Controller.Watchers.*;
import CoursePlanner.Model.*;
import CoursePlanner.Model.FormattedCourses.*;
import CoursePlanner.Model.RawData.*;

import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.http.HttpStatus;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;

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
    private AtomicLong watcherId = new AtomicLong();

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

    @GetMapping("/api/stats/students-per-semester")
    @ResponseStatus(HttpStatus.OK)
    public ArrayList<Map<String, Integer>> getGraphData(@RequestParam(value="deptId") int id) 
    {
        ArrayList<Map<String, Integer>> mapPoints = new ArrayList<Map<String, Integer>>();

        try 
        {
            for (GraphData data : depts.getDepartments().get(id).getDeptGraphData())
            {
                Map<String, Integer> point = new HashMap<String, Integer>();
                point.put("semesterCode", data.getSemester());
                point.put("totalCoursesTaken", data.getCoursesTaken());
                mapPoints.add(point);
            }
        }
        catch (ArrayIndexOutOfBoundsException exception)
        {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, 
            "ERROR: Department or Course ID not found in system!");
        }
        return mapPoints;
    }

    @PostMapping("/api/addoffering")
    @ResponseStatus(HttpStatus.CREATED)
    public CourseOffering newOffering(@RequestBody RawData data)
    {
        Component newComponent = new Component(data);
        CourseOffering newOffering = new CourseOffering(data.getSemester(), 
        data.getLocation(), data.getInstructorsString() ,newComponent);
        CourseNumber newCourseNumber = new CourseNumber(data.getSubject(), data.getCatalogNumber());
        newCourseNumber.addOffering(newOffering);

        data.setSubject(watchers.get(watchers.size() - 1).getCourse().getSubject());
        data.setCatalogNumber(watchers.get(watchers.size() - 1).getCourse().getCatalogNumber());
        reader.addData(data);
        courseList.populateCourseList(reader.getCourseDataList());
        depts = new Departments(courseList);
        return newOffering;
    }

    @GetMapping("/api/watchers")
    @ResponseStatus(HttpStatus.OK)
    public ArrayList<Watcher> getWatchers()
    {
        return watchers;
    }

    @PostMapping("/api/watchers")
    @ResponseStatus(HttpStatus.CREATED)
    public Watcher newWatcher(@RequestBody WatcherData data) 
    {
        try 
        {
            Watcher newWatcher = new Watcher
            (watcherId.incrementAndGet(), depts.get((int) data.getDeptId()),
            depts.get((int) data.getDeptId()).getCourseNums().get((int) data.getCourseId()));

            depts.get((int) data.getDeptId()).getCourseNums().get((int) data.getCourseId()).addWatcher(newWatcher);
            watchers.add(newWatcher);
            return newWatcher;
        }
        catch (ArrayIndexOutOfBoundsException exception)
        {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, 
            "ERROR: Department or Course ID not found in system!");
        }
    }

    @GetMapping("/api/watchers/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Watcher getWatcher(@PathVariable("id") long id) 
    {
        try 
        {
            Watcher returnWatcher = new Watcher();
            for (Watcher watcher : watchers) 
            {
                if (watcher.getId() == id) 
                {
                    returnWatcher = watcher;
                }
            }
            return returnWatcher;
        }
        catch (ArrayIndexOutOfBoundsException exception)
        {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, 
            "ERROR: Department or Course ID not found in system!");
        }
    }

    @DeleteMapping("/api/watchers/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteWatcher(@PathVariable("id") long id) 
    {
        try 
        {
            for (Watcher watcher : watchers)
            {
                if (watcher.getId() == id) 
                {
                    watchers.remove(watcher);
                }
            }

            for (Department dept : depts.getDepartments())
            {
                for (CourseNumber course : dept.getCourseNums())
                {
                    course.removeWatcher(id);
                }
            }
        }
        catch (ArrayIndexOutOfBoundsException exception)
        {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, 
            "ERROR: Department or Course ID not found in system!");
        }
    }
}
