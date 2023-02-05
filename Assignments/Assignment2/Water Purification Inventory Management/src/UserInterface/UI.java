package UserInterface;

import WaterPurificationInventorySystem.*;
import java.util.Scanner;
import java.util.ArrayList;
import java.text.SimpleDateFormat;
import java.util.Date;

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
            System.out.print(">Please select an option listed below<\n> ");
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
            case 1: // 1) Read JSON input file
                readJSON();
                mainMenu();
                break;
            case 2: // 2) Display unit info
                displayInfoSelection(2);
                mainMenu();
                break;
            case 3: // 3) Create unit
                createUnit();
                mainMenu();
                break;
            case 4: // 4) Test unit
                displayInfoSelection(4);
                mainMenu();
                break;
            case 5: // 5) Ship a unit
                displayInfoSelection(5);
                mainMenu();
                break;
            case 6: // 6) Print report
                printReport();
                mainMenu();
                break;
            // case 7: // 7) Set report sort order
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
        System.out.print("Enter the full path of the JSON file:\n> ");
        String filePath = scan.nextLine();
        System.out.println();
        return filePath;
    }

    private void readJSON()
    {
        String path = inputFilePath();
        data = loader.loadJson(scan, path);
        System.out.println("Read " + data.size() + " products from JSON file \"" + path + "\"\n");
    }

    private void displayInfoSelection(int prevMenu)
    {
        System.out.print("Enter the serial number (0 for list, -1 for cancel):\n> ");
        String input = scan.nextLine();
        System.out.println();
        
        switch (input)
        {
            case "-1":
                break;
            case "0":
                displayGeneralInfo();
                displayInfoSelection(prevMenu);
                break;
            default:
                switch (prevMenu)
                {
                    case 2: //coming from Display unit info
                        displayUnitInfo(input);
                        break;
                    case 4: //coming from Test unit
                        testUnit(input);
                        break;
                    case 5: //coming from Ship unit
                        shipUnit(input);
                        break;
                }
                break;
        }
    }

    private void displayGeneralInfo()
    {
        System.out.println("List of Water Purification Units:");
        System.out.println("*********************************");

        if (data.size() == 0)
        {
            System.out.println(">The list is empty, no Water Purification Units to dispaly!<\n");
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

                System.out.print("   Serial: " + unit.getSerialNumber());
                System.out.print("\n    Model: " + unit.getModel());
                System.out.print("\nShip date: " + unit.getDateShipped());

                System.out.println("\n\nTests:");
                System.out.println("******");

                System.out.println("        Date   Passed?  Test Comments");
                System.out.println("------------  --------  -------------");

                for (Test test : unit.getTests())
                {
                    System.out.println("  " + test.getDate() + "    " + test.getStatus()
                    + "  " + test.getComment());
                }
                System.out.println();
                
                return;
            }
        }
        System.out.println(">Serial number not found in the system! Returning to main menu...<\n");
    }

    private void createUnit()
    {
        Unit newUnit = new Unit();

        System.out.println("\nEnter product info; blank line to quit.");
        System.out.print("Model: ");
        String input = scan.nextLine();

        if (isBlankString(input))
        {
            return;
        }

        while (!newUnit.isValidModelString(input))
        {
            System.out.println("Unable to add the product!");
            System.out.println("\t>Model Error: String must be between 0 and 10 characters (inclusive).<");
            System.out.println("\tPlease try again.");

            System.out.print("\nModel: ");
            input = scan.nextLine();
    
            if (isBlankString(input))
            {
                return;
            }
        }
        newUnit.setModel(input);

        System.out.print("Serial number: ");
        input = scan.nextLine();

        if (isBlankString(input))
        {
            return;
        }

        while (!newUnit.isValidSerialString(input, input.length()) || serialExists(input))
        {
            System.out.println("Unable to add the product!");
            if (!newUnit.isValidSerialString(input, input.length()))
            {
                System.out.println("\t>Serial Number Error: Checksum does not match.<");
            }
            else
            {
                System.out.println("\t>Serial Number Error: Serial number already exists in the system.<");
            }
            System.out.println("\tPlease try again.");

            System.out.print("Serial number: ");
            input = scan.nextLine();
    
            if (isBlankString(input))
            {
                return;
            }
        }
        newUnit.setSerialNumber(input);
        data.add(newUnit);
        System.out.println("New unit added successfully!\n");
    }

    private Boolean serialExists(String serial)
    {
        for (Unit unit : data)
        {
            if (unit.getSerialNumber().equals(serial))
            {
                return true;
            }
        }
        return false;
    }

    private Boolean isBlankString(String str)
    {
        if (str.equals(" ") || str.equals(""))
        {
            return true;
        }
        return false;
    }

    private void testUnit(String serial)
    {
        for (Unit unit : data)
        {
            if (unit.getSerialNumber().equals(serial))
            {
                System.out.println("New test:");
                System.out.println("**********");
                Test newTest = new Test();

                System.out.print("Pass? (Y/n): ");
                String input = scan.nextLine();
                if (input.equals("y") || input.equals("Y"))
                {
                    newTest.setStatus("Passed");
                }
                else if (input.equals("n") || input.equals("N"))
                {
                    newTest.setStatus("FAILED");
                }

                System.out.print("Comment: ");
                input = scan.nextLine();
                newTest.setComment(input);

                unit.addTest(newTest);
                System.out.println("Test recorded!\n");
        
                return;
            }
        }
        System.out.println(">Serial number not found in the system! Returning to main menu...<\n");
    }

    private void shipUnit(String serial)
    {
        for (Unit unit : data)
        {
            if (unit.getSerialNumber().equals(serial))
            {
                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                String date = dateFormat.format(new Date());
                unit.setDateShipped(date);
                System.out.println("Unit shipped out and updated successfully!\n");
        
                dateFormat = null;
                date = null;
                return;
            }
        }
        System.out.println(">Serial number not found in the system! Returning to main menu...<\n");
    }

    private void printReport() // continue from scratch
    {
        System.out.println();
        for (int count = 0; count < data.size(); ++count)
        {
            System.out.println(data.elementAt(count).toString());
        }
        System.out.println();
    }

    // private void setOrder()
    // {

    // }

    private static void exit()
    {
        System.exit(0);
    }
}
