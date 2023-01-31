package UserInterface;

import WaterPurificationInventorySystem.Unit;
import java.util.Vector;
import java.util.Scanner;

/**
* Description: The UI class is used to create a database of Unit objects
* and has several menus to interact with said database. Use with the Unit 
* class in the WaterPurificationInventorySystem package.
*
* This class and many of its features were inspired by the UI class in assignment 1.
*
* @author Daniel Tolsky
* @version 1.0
*/

public class UI 
{
    private Vector<Unit> data;
    private Scanner scan;

    public UI()
    { 
        scan = new Scanner(System.in);
        data = new Vector<Unit>();
    }

    public void start()
    {
        title();
        mainMenu();
    }

    private int choiceHandle()
    {
        System.out.print("Please select an option:\n> ");
        int input = Integer.parseInt(scan.nextLine());

        while (input < 1 || input > 6)
        {
            System.out.print("Please select an option listed below:\n> ");
            input = Integer.parseInt(scan.nextLine());
        }
        
        return input;
    }

    private void title()
    {
        System.out.println("|Welcome to the Water Purification Unit Databse by Daniel Tolsky|");
        System.out.println("*****************************************************************\n");
    }

    private void mainMenu()
    {
        System.out.println("Please select an option:");
        System.out.println("1) Display unit info");
        System.out.println("2) Create unit");
        System.out.println("3) Test unit");
        System.out.println("4) Ship a unit");
        System.out.println("5) Print report");
        System.out.println("6) Set sort order\n");
        System.out.println("7) Exit\n");
        menuDescision();
    }

    private void menuDescision()
    {
        int descision = choiceHandle();
        switch (descision)
        {
            case 1:
                displayInfo();
                mainMenu();
                break;
            case 2:
                createUnit();
                mainMenu();
                break;
            case 3:
                testUnit();
                mainMenu();
                break;
            case 4:
                shipUnit();
                mainMenu();
                break;
            case 5:
                printReport();
                mainMenu();
                break;
            case 6:
                setOrder();
                mainMenu();
                break;
            case 7:
                exit();
                break;
        }
    }

    private void displayInfo()
    {
        System.out.println("\nMinions list:");

        if (data.size() == 0)
        {
            System.out.println("The list is empty, no minions to dispaly!\n");
        }
        else
        {
            for (int count = 0; count < data.size(); ++count)
            {
                System.out.print((count + 1) + ") Name: " + data.elementAt(count).getName() + " | Height: " +
                data.elementAt(count).getHeight() + " | Evil deeds: " + data.elementAt(count).getEvilDeeds() + '\n');
            }
            System.out.println();
        }
    }

    private void createUnit()
    {
        Minion newMinion = new Minion();

        System.out.print("\nEnter name (greater than 0 characters)\n> ");
        String input = scan.nextLine();
        newMinion.setName(input);

        System.out.print("\nEnter height (0.0 or more)\n> ");
        input = scan.nextLine();
        newMinion.setHeight(Double.parseDouble(input));
        System.out.println();

        data.add(newMinion);
    }

    private void testUnit()
    {
        displayInfo();

        System.out.print("Enter index of minion to remove (0 to cancel)\n> ");
        int input = selectFromListHandle();

        
    }

    private void shipUnit()
    {
        displayInfo();

        System.out.print("Enter index of minion you wish to increment the number of evil needs of (0 to cancel)\n> ");
        int input = selectFromListHandle();

        if (input != 0)
        {
            int amount = data.elementAt(input - 1).getEvilDeeds();
            data.elementAt(input - 1).setEvilDeeds(++amount);
            System.out.println("Increment successful!\n");
        }
        else
        {
            System.out.println("Canceled\n");
        }
    }

    private int selectFromListHandle()
    {
        String input = scan.nextLine();

        while (Integer.parseInt(input) < 0 || Integer.parseInt(input) > (data.size() + 1))
        {
            System.out.print("\nPlease enter a valid index\n> ");
            input = scan.nextLine();
        }
        return Integer.parseInt(input);
    }

    private void setOrder()
    {

    }

    private void printReport()
    {
        System.out.println();
        for (int count = 0; count < data.size(); ++count)
        {
            System.out.println(data.elementAt(count).toString());
        }
        System.out.println();
    }

    private static void exit()
    {
        System.exit(0);
    }
}
