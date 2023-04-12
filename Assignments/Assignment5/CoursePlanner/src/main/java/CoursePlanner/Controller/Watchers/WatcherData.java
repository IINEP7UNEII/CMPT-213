package CoursePlanner.Controller.Watchers;

public class WatcherData 
{
    private long deptId;
    private long courseId;

    public WatcherData() 
    { }

    public WatcherData(long deptId, long courseId) 
    {
        this.deptId = deptId;
        this.courseId = courseId;
    }

    public long getCourseId() 
    {
        return courseId;
    }

    public long getDeptId() 
    {
        return deptId;
    }
}
