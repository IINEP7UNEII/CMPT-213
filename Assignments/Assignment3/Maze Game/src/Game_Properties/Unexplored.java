package Game_Properties;

public final class Unexplored extends BoardPeice
{
    private BoardPeice underlyingObject;

    public Unexplored()
    {
        super('.');
    }

    public Unexplored(BoardPeice underlyingObject)
    {
        super('.');
    }

    public char getICON() 
    {
        return ICON;
    }

    public BoardPeice getUnderlyingObject() 
    {
        return underlyingObject;
    }
}
