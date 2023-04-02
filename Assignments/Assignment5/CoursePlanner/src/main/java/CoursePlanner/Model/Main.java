package CoursePlanner.Model;

public class Main 
{
    public static void main(String[] args) 
    {
        CSVReader reader = new CSVReader();
        reader.readCSVFile();
        reader.dumpCSV();
    }
}