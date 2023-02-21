package Game_Properties;

public class Unexplored extends BoardPeice
{
    private BoardPeice underlyingObject;

    public Unexplored()
    {
        super('.');
    }

    public char getICON() 
    {
        return ICON;
    }

    public Object getUnderlyingObject() 
    {
        return underlyingObject;
    }

    public void setUnderlyingObject(BoardPeice object)
    {
        underlyingObject = object;
    }
}
