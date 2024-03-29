package CoursePlanner;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Description: This CoursePlanner class is used to start up the Spring Boot server for the Course Planner application
 *
 * @author Daniel Tolsky
 * @version 1.0
 */
@SpringBootApplication
public class CoursePlanner
{
    public static void main(String[] args)
    {
        SpringApplication.run(CoursePlanner.class, args);
    }
}
