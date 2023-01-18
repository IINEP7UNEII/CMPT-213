package MinionDatabase;

public class Minion 
{
    private String name;
    private double height;
    private int evilDeeds;

    public Minion()
    {
        this.name = "";
        this.height = 0.0;
        this.evilDeeds = 0;
    }

    public String getName()
    {
        return name;
    }

    public void setName(String str)
    {
        name = str;
    }

    public double getHeight()
    {
        return height;
    }

    public void setHeight(double h)
    {
        height = h;
    }

    public int getEvilDeeds()
    {
        return evilDeeds;
    }

    public void setEvilDeeds(int deeds)
    {
        evilDeeds = deeds;
    }

    @Override
    public String toString() 
    {
		return getClass().getName() + " >\t[Name: " + name + " | Height: "
        + height + " | EvilDeeds: " + evilDeeds + ']';
	}
}
