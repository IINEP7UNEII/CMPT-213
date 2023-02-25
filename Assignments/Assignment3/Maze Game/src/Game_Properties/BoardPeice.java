package Game_Properties;

public class BoardPeice 
{
    private BoardPeice underlyingObject;
    protected char ICON;
    private int coordX;
    private int coordY;

    public BoardPeice()
    {
        ICON = '0';
        coordX = 0;
        coordY = 0;
    }

    public BoardPeice(char ICON)
    {
        this.ICON = ICON;
        coordX = 0;
        coordY = 0;
    }

    public BoardPeice(char ICON, int x, int y)
    {
        this.ICON = ICON;
        coordX = x;
        coordY = y;
    }

    public char getICON() 
    {
        return ICON;
    }

    public int getCoordX()
    {
        return coordX;
    }

    public int getCoordY()
    {
        return coordY;
    }

    public void setCoordX(int x)
    {
        coordX = x;
    }

    public void setCoordY(int y)
    {
        coordY = y;
    }

    public BoardPeice getUnderlyingObject() 
    {
        return underlyingObject;
    }

    public void setUnderlyingObject(BoardPeice peice) 
    {
        underlyingObject = peice;
    }

    public final class Wall extends BoardPeice
    {
        public Wall()
        {
            super('#');
        }
    }

    public final class Cat extends BoardPeice
    {
        public Cat()
        {
            super('!');
        }
    }

    public final class Cheese extends BoardPeice
    {
        public Cheese()
        {
            super('$');
        }
    }

    public final class Mouse extends BoardPeice
    {
        public Mouse()
        {
            super('@');
            underlyingObject = new Empty();
        }
    }

    public final class Unexplored extends BoardPeice
    {
        public Unexplored()
        {
            super('.');
        }
    }

    public final class Empty extends BoardPeice
    {
        public Empty()
        {
            super(' ');
        }
    }

    public final class Dead extends BoardPeice
    {
        public Dead()
        {
            super('X');
        }
    }
}
