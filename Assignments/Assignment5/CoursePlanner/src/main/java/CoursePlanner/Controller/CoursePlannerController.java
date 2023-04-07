package CoursePlanner.Controller;

import CoursePlanner.Model.*;
import CoursePlanner.Model.RawData.CSVReader;
import CoursePlanner.Model.RawData.CourseData;

import org.springframework.web.bind.annotation.*;
import org.springframework.http.HttpStatus;

import java.util.ArrayList;

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
    private ArrayList<CourseData> courseData;
    private CSVReader reader;

    public CoursePlannerController()
    {
        reader = new CSVReader();
        reader.readCSVFile();
        courseData = reader.getCourseDataList();
    }

    @GetMapping("/api/about")
    @ResponseStatus(HttpStatus.OK)
    public String getAbout()
    {
        return "Daniel Tolsky";
    }

    @GetMapping("/api/dump-model")
    @ResponseStatus(HttpStatus.OK)
    public void getDump()
    {
        Dumper dumper = new Dumper();
        dumper.dumpFormattedCourses(courseData);
    }
}
