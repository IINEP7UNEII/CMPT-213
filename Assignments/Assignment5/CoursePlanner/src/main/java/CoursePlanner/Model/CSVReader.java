package CoursePlanner.Model;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class CSVReader 
{
    private static String CSV_FILE;
    private static int NUMBER_OF_FIELDS;
    private ArrayList<Course> courses;

    public CSVReader()
    {
        CSV_FILE = "data/course_data_2018.csv";
        NUMBER_OF_FIELDS = 8;
        courses = new ArrayList<>();
    }

    public ArrayList<Course> readCSVFile() 
    {
        try (BufferedReader reader = new BufferedReader(new FileReader(CSV_FILE))) 
        {
            String line = "";
            boolean header = true;
            while ((line = reader.readLine()) != null) 
            {
                int numCommas = countCommas(line);
                int numInstructors = numCommas - NUMBER_OF_FIELDS + 2;
                int instructorEndIndex = numInstructors + 6;
                if (header) // first line
                {
                    header = false;
                    continue;
                }
                populateCourse(line, courses, instructorEndIndex);
            }
        } 
        catch (IOException exception)
        {
            System.err.println("Error reading CSV file at: " + CSV_FILE);
            System.exit(1);
        }
        return courses;
    }

    public void dumpCSV() //make so dumb is formatted to requirements
    {
        for (Course course : courses)
        {
            course.dump();
            System.out.println();
        }
    }

    private void populateCourse(String line, ArrayList<Course> courses, int instructorEndIndex) 
    {
        String[] values = line.split(",");
        Course course = new Course();
        course.setSemester(Integer.parseInt(values[0]));
        course.setSubject(values[1].trim());
        course.setCatalogNumber(values[2].trim());
        course.setLocation(values[3].trim());
        course.setEnrollmentCapacity(Integer.parseInt(values[4]));
        course.setEnrollmentTotal(Integer.parseInt(values[5]));
        course.setInstructors(setMultipleInstructors(values, instructorEndIndex));
        course.setComponentCode(values[instructorEndIndex]);
        courses.add(course);
    }

    private ArrayList<String> setMultipleInstructors(String[] values, int instructorEndIndex) 
    {
        ArrayList<String> instructors = new ArrayList<String>();
        removeQuotes(values);
        for (int count = 6; count < instructorEndIndex; ++count)
        {
            if (values[count] == null || values[count].equalsIgnoreCase("<null>")) 
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