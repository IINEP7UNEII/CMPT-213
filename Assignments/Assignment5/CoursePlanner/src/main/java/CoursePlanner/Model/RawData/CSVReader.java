package CoursePlanner.Model.RawData;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Description: This CSVReader class is responsible for reading the CSV file provided and populates the CourseData objects
 * with the relevant information. It returns the populated CourseData object via and ArrayList which contains these CourseData objects.
 *
 * @author Daniel Tolsky
 * @version 1.0
 */
public class CSVReader 
{
    private static String CSV_FILE;
    private static int NUMBER_OF_FIELDS;
    private ArrayList<CourseData> courseData;

    public CSVReader()
    {
        CSV_FILE = "data/course_data_2018.csv";
        NUMBER_OF_FIELDS = 8;
        courseData = new ArrayList<CourseData>();
    }

    public ArrayList<CourseData> readCSVFile() 
    {
        try (BufferedReader reader = new BufferedReader(new FileReader(CSV_FILE))) 
        {
            String line = "";
            boolean header = true;
            while ((line = reader.readLine()) != null) 
            {
                if (header)
                {
                    header = false;
                    continue;
                }
                int numCommas = countCommas(line);
                int numInstructors = numCommas - NUMBER_OF_FIELDS + 2;
                int instructorEndIndex = numInstructors + 6;
                populateCourseData(line, instructorEndIndex);
            }
        } 
        catch (IOException exception)
        {
            System.err.println("Error reading CSV file at: " + CSV_FILE);
            System.exit(1);
        }
        return courseData;
    }

    public ArrayList<CourseData> getCourseDataList()
    {
        return courseData;
    }

    public void addData(CourseData data)
    {
        courseData.add(data);
    }

    private void populateCourseData(String line, int instructorEndIndex) 
    {
        String[] values = line.split(",");
        CourseData newCourseData = new CourseData();
        newCourseData.setSemester(Integer.parseInt(values[0]));
        newCourseData.setSubject(values[1].trim());
        newCourseData.setCatalogNumber(values[2].trim());
        newCourseData.setLocation(values[3].trim());
        newCourseData.setEnrollmentCapacity(Integer.parseInt(values[4]));
        newCourseData.setEnrollmentTotal(Integer.parseInt(values[5]));
        newCourseData.setInstructors(setMultipleInstructors(values, instructorEndIndex));
        newCourseData.setComponentCode(values[instructorEndIndex]);
        courseData.add(newCourseData);
    }

    private ArrayList<String> setMultipleInstructors(String[] values, int instructorEndIndex) 
    {
        ArrayList<String> instructors = new ArrayList<String>();
        removeQuotes(values);
        for (int count = 6; count < instructorEndIndex; ++count)
        {
            if (values[count] == null || values[count].equals("<null>") || values[count].equals("(null)")) 
            {
                instructors.add("");
            } 
            else 
            {
                instructors.add(values[count].trim());
            }
        }
        return instructors;
    }

    private static void removeQuotes(String[] list) 
    {
        for (int count = 0; count < list.length; ++count) 
        {
            String str = list[count];
            str = str.replace("\"", "");
            list[count] = str;
        }
    }

    private static int countCommas(String str) 
    {
        int charCount = 0;
        for (int count = 0; count < str.length(); ++count) 
        {
            if (str.charAt(count) == ',') 
            {
                ++charCount;
            }
        }
        return charCount;
    }
}