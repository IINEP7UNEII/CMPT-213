package UserInterface;

import WaterPurificationInventorySystem.*;
import java.util.Scanner;
import java.util.ArrayList;

/**
* Description: The UI class is used to create a database of Unit objects
* and has several menus to interact with said database. Use with the Unit 
* class in the WaterPurificationInventorySystem package.
*
* This class and many of its features were inspired by the UI class
* which I myself created in assignment 1.
*
* @author Daniel Tolsky
* @version 1.0
*/

public class UI 
{
    private ArrayList<Unit> data;
    private Scanner scan;
    private JsonLoader loader;

    public UI()
    { 
        scan = new Scanner(System.in);
        data = new ArrayList<Unit>();
        loader = new JsonLoader();
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

        while (input < 1 || input > 8)
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
        System.out.println("\nPlease select an option:");
        System.out.println("1) Read JSON input file");
        System.out.println("2) Display unit info");
        System.out.println("3) Create unit");
        System.out.println("4) Test unit");
        System.out.println("5) Ship a unit");
        System.out.println("6) Print report");
        System.out.println("7) Set report sort order");
        System.out.println("8) Exit\n");
        menuDescision();
    }

    private void menuDescision()
    {
        int descision = choiceHandle();
        switch (descision)
        {
            case 1:
                readJSON();
                mainMenu();
                break;
            case 2:
                displayInfoSelection();
                mainMenu();
                break;
            // case 3:
            //     createUnit();
            //     mainMenu();
            //     break;
            // case 4:
            //     testUnit();
            //     mainMenu();
            //     break;
            // case 5:
            //     shipUnit();
            //     mainMenu();
            //     break;
            // case 6:
            //     printReport();
            //     mainMenu();
            //     break;
            // case 7:
            //     setOrder();
            //     mainMenu();
            //     break;
            case 8:
                exit();
                break;
        }
    }

    private String inputFilePath()
    {
        System.out.println("Enter the full path of the JSON file: ");
        String filePath = scan.nextLine();
        return filePath;
    }

    private void readJSON()
    {
        String path = inputFilePath();
        data = loader.loadJson(scan, path);
        System.out.println("Read " + data.size() + " products from JSON file \"" + path + "\"");
    }

    private void displayInfoSelection()
    {
        System.out.println("Enter the serial number (0 for list, -1 for cancel): ");
        String input = scan.nextLine();
        
        switch (input)
        {
            case "-1":
                break;
            case "0":
                displayGeneralInfo();
                break;
            default:
                displayUnitInfo(input);
                break;
        }
    }

    private void displayGeneralInfo()
    {
        System.out.println("\n\nList of Water Purification Units:");
        System.out.println("*********************************");

        if (data.size() == 0)
        {
            System.out.println("The list is empty, no Water Purification Units to dispaly!\n");
        }
        else
        {
            System.out.println("     Model           Serial     # Tests   Ship Date");
            System.out.println("----------  ---------------  ----------  ----------");

            String modelFormat = "%10.10s";
            String serialFormat = "%17.17s";
            String testsFormat = "%12.12s";
            String shipFormat = "%12.12s";

            for (Unit unit : data)
            {
                System.out.format(modelFormat, unit.getModel());
                System.out.format(serialFormat, unit.getSerialNumber());
                System.out.format(testsFormat, unit.getTests().size());
                System.out.format(shipFormat, unit.getDateShipped());
                System.out.println();
            }
            System.out.println();
        }
    }

    private void displayUnitInfo(String serial)
    {
        for (Unit unit : data)
        {
            if (unit.getSerialNumber().equals(serial))
            {
                System.out.println("Unit info:");
                System.out.println("**********");

                String unitFormat = "%11.11s";
                System.out.print("Serial:");
                System.out.format(unitFormat, unit.getSerialNumber());
                System.out.print("Model:");
                System.out.format(unitFormat, unit.getModel());
                System.out.print("Ship date:");
                System.out.format(unitFormat, unit.getDateShipped());

                System.out.println("\nTests:");
                System.out.println("******");

                System.out.println("     Date           Passed?     Test Comments");
                System.out.println("---------  ----------------  ----------------");
            }
        }
    }

    // private void createUnit()
    // {
    //     Minion newMinion = new Minion();

    //     System.out.print("\nEnter name (greater than 0 characters)\n> ");
    //     String input = scan.nextLine();
    //     newMinion.setName(input);

    //     System.out.print("\nEnter height (0.0 or more)\n> ");
    //     input = scan.nextLine();
    //     newMinion.setHeight(Double.parseDouble(input));
    //     System.out.println();

    //     data.add(newMinion);
    // }

    // private void testUnit()
    // {
    //     displayInfo();

    //     System.out.println("Date: " + unit.getDate());
    //     System.out.println("Serial number test: ");
    //     if (testSerialNumberString(unit.getSerialNumber(), unit.getSerialNumber().length()))
    //     {
    //         System.out.print("Passed! All checks completed!");
    //     }
    //     else
    //     {
    //         System.out.print("Failed!\nPlease check that the serial number only contains"
    //         + "\ndigits, is 3 to 15 digits long, and passes the checksum test!");
    //     }

    //     System.out.println("\nModel name test: ");
    //     if (testModelString(unit.getModel()))
    //     {
    //         System.out.print("Passed! All checks completed!");
    //     }
    //     else
    //     {
    //         System.out.print("Failed!\nPlease check that the model name is up to 10 characters long!");
    //     }
    // }

    // private void shipUnit()
    // {
    //     displayInfo();

    //     System.out.print("Enter index of minion you wish to increment the number of evil needs of (0 to cancel)\n> ");
    //     int input = selectFromListHandle();

    //     if (input != 0)
    //     {
    //         int amount = data.elementAt(input - 1).getEvilDeeds();
    //         data.elementAt(input - 1).setEvilDeeds(++amount);
    //         System.out.println("Increment successful!\n");
    //     }
    //     else
    //     {
    //         System.out.println("Canceled\n");
    //     }
    // }

    // private int selectFromListHandle()
    // {
    //     String input = scan.nextLine();

    //     while (Integer.parseInt(input) < 0 || Integer.parseInt(input) > (data.size() + 1))
    //     {
    //         System.out.print("\nPlease enter a valid index\n> ");
    //         input = scan.nextLine();
    //     }
    //     return Integer.parseInt(input);
    // }

    // private void setOrder()
    // {

    // }

    // private void printReport()
    // {
    //     System.out.println();
    //     for (int count = 0; count < data.size(); ++count)
    //     {
    //         System.out.println(data.elementAt(count).toString());
    //     }
    //     System.out.println();
    // }

    private static void exit()
    {
        System.exit(0);
    }
}
