package CoursePlanner.Model;

import CoursePlanner.Model.RawData.CSVReader;

/**
 * Description: This Main class is mainly used for easier debugging and it not part of the phase 1 solution.
 *
 * @author Daniel Tolsky
 * @version 1.0
 */
public class Main 
{
    public static void main(String[] args) 
    {
        CSVReader reader = new CSVReader();
        reader.readCSVFile();

        Dumper dumper = new Dumper();
        dumper.dumpFormattedCourses(reader.getCourseDataList());
    }
}