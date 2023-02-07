package WaterPurificationInventorySystem;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Collections;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
* Description: This Sorter class is mainly used for the setOrder() function in the UI.java file.
* This class handle all the operations for sorting the Water Purification Unit data in the desired order.
* This class also includes a UnitComparator class which implements a comparator who's operations 
* could be specified (using the setOrder() function) to  handle different types of data which need to be sorted
* for the database.
*
* @author Daniel Tolsky
* @version 1.0
*/

public class Sorter 
{
    private int sortOrder;
    UnitComparator comparator;

    public Sorter()
    {
        sortOrder = 1;
        comparator = new UnitComparator();
    }

    public String getOrder()
    {
        String order = "";

        switch(sortOrder)
        {
            case 1:
                order = "serial";
                break;

            case 2:
                order = "model";
                break;

            case 3:
                order = "test date";
                break;

            default:
                throw new IllegalStateException("ERROR: Current order must be between 1"
                + "and 3 (inclusive) in getOrder(), Sorter.java");
        }
        return order;
    }

    public void setOrder(int order)
    {
        if (order < 1 || order > 3)
        {
            throw new IllegalStateException("ERROR: Order must be between 1"
            + "and 3 (inclusive) in setOrder(), Sorter.java");
        }
        else
        {
            sortOrder = order;
            comparator.updateOrder(order);
        }      
    }

    public void sortUnits(ArrayList<Unit> units)
    {
        Collections.sort(units, comparator);
    }
}

class UnitComparator implements Comparator<Unit> 
{
    private int order = 0;
    private SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

    public void updateOrder(int newOrder)
    {
        order = newOrder;
    }

    @Override
    public int compare(Unit a, Unit b)
    {
        long result = 0;
        try 
        {
            switch (order)
            {
                case 1:
                    result = compareLong(getStringLexiValue(a.getSerialNumber()), getStringLexiValue(b.getSerialNumber()));
                    break;
                case 2:
                    result = compareLong(getStringLexiValue(a.getModel()), getStringLexiValue(b.getModel()));
                    if (result == 0)
                    {
                        result = compareLong(getStringLexiValue(a.getSerialNumber()), getStringLexiValue(b.getSerialNumber()));
                    }
                    break;
                case 3:
                    result = compareDate(a, b);
                    break;
                default:
                    throw new IllegalStateException("ERROR: Current order must be between 1"
                    + "and 3 (inclusive) in UnitComparator->compare(), Sorter.java");
            }
        } 
        catch (NumberFormatException exception) 
        {
            exception.printStackTrace();
        }
        return (int) result;
    }

    private long compareDate(Unit a, Unit b)
    {
        long result = 0;

        if (a.getDateShipped() != "-" && b.getDateShipped() != "-")
        {
            try 
            {
                Date dateA = dateFormat.parse(a.getDateShipped());
                Date dateB = dateFormat.parse(b.getDateShipped());
                if (dateA.compareTo(dateB) > 0)
                {
                    result = 1;
                }
                else if (dateA.compareTo(dateB) < 0)
                {
                    result = -1;
                }
                else
                {
                    result = 0;
                }
            } 
            catch (ParseException exception) 
            {
                exception.printStackTrace();
            }
        }
        else
        {
            result = compareLong(getStringLexiValue(a.getDateShipped()), getStringLexiValue(b.getDateShipped()));
        }

        if (result == 0)
        {
            if (a.getTests().size() > b.getTests().size())
            {
                result = 1;
            }
            else if (a.getTests().size() < b.getTests().size())
            {
                result = -1;
            }
        }
        return result;
    }

    private long compareLong(long a, long b)
    {
        long result = 0;

        if (a > b)
        {
            result = 1;
        }
        else if (a < b)
        {
            result = -1;
        }
        else
        {
            result = 0;
        }
        return result;
    }

    private long getStringLexiValue(String str)
    {
        long value = 0;

        for (int count = 0; count < str.length(); ++count) 
        {
            value += (int) str.charAt(count);
        }
        return value;
    }
}