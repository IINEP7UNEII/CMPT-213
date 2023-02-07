package UserInterface;

import WaterPurificationInventorySystem.*;
import java.util.Scanner;
import java.util.ArrayList;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
* Description: The UI class is used to create a database of Unit objects
* and has several menus to interact with said database. Use with the Unit, Test, Sorter 
* class in the WaterPurificationInventorySystem package and the JsonLoader class in this 
* UserInterface package.
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

    private int choiceHandle(int minOption, int maxOption)
    {
        System.out.print("Please select an option:\n> ");
        int input = Integer.parseInt(scan.nextLine());

        while (input < minOption || input > maxOption)
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
        int descision = choiceHandle(1, 8);
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
            case 7: // 7) Set report sort order
                setOrder();
                mainMenu();
                break;
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
        String title = "List of Water Purification Units:\n*********************************";
        
        switch (input)
        {
            case "-1":
                break;
            case "0":
                displayGeneralInfo(title, 1); //print all
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

    private void displayGeneralInfo(String menuTitleName, int filter) //filter: 1) all 2) defective 3) ready to ship
    {
        System.out.println(menuTitleName);

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
                if (filter == 1) //all
                {
                    displayWithFormat(modelFormat, serialFormat, testsFormat, shipFormat, unit);
                }
                else if (filter == 2 && !getLastTestStatus(unit)) // if defective (last test not passed)
                {
                    displayWithFormat(modelFormat, serialFormat, testsFormat, shipFormat, unit);
                }
                else if (filter == 3 && getLastTestStatus(unit) && unit.getDateShipped().equals("-")) //if ready to ship (pass test, not shipped)
                {
                    displayWithFormat(modelFormat, serialFormat, testsFormat, shipFormat, unit);
                }
            }
        }
    }

    private void displayWithFormat(String modelFormat, String serialFormat, String testsFormat, String shipFormat, Unit unit)
    {
        System.out.format(modelFormat, unit.getModel());
        System.out.format(serialFormat, unit.getSerialNumber());
        System.out.format(testsFormat, unit.getTests().size());
        System.out.format(shipFormat, unit.getDateShipped());
        System.out.println();
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

                if (unit.getTests().size() > 0)
                {
                    System.out.println("\n\nTests:");
                    System.out.println("******");
                    System.out.println("        Date   Passed?  Test Comments");
                    System.out.println("------------  --------  -------------");

                    for (Test test : unit.getTests())
                    {
                        System.out.println("  " + test.getDate() + "    "
                        + test.getStatus() + "  " + test.getComment());
                    }
                }
                else
                {
                    System.out.println("\nNo tests to display!");
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
            input = unableAddModel();
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
            input = unableAddSerial();
            if (isBlankString(input))
            {
                return;
            }
        }
        newUnit.setSerialNumber(input);
        data.add(newUnit);
        System.out.println("New unit added successfully!\n");
    }

    private String unableAddModel()
    {
        System.out.println("Unable to add the product!");
        System.out.println("\t>Model Error: String must be between 0 and 10 characters (inclusive).<");
        System.out.println("\tPlease try again.");

        System.out.print("\nModel: ");
        String input = scan.nextLine();
        return input;
    }

    private String unableAddSerial()
    {
        Unit tempUnit = new Unit();
        String input = "";
        System.out.println("Unable to add the product!");
        if (!tempUnit.isValidSerialString(input, input.length()))
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
        tempUnit = null;
        return input;
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
                if (input.equals("y") || input.equals("Y") || input.equals("") || input.equals(" "))
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

    private void printReport()
    {
        System.out.println("\nSelect repot option:");
        System.out.println("1) ALL:\t\t\tAll products");
        System.out.println("2) DEFECTIVE:\t\tProducts which failed their last test");
        System.out.println("3) READY-TO-SHIP\tProducts which passed their test, not shipped");
        System.out.println("4) Cancel");
        int input = choiceHandle(1, 4);
        System.out.println();
        String title = "";

        switch (input)
        {
            case 1:
                title = "List of Water Purification Units:\n*********************************";
                break;

            case 2:
                title = "DEFECTIVE Water Purification Units:\n***********************************";
                break;

            case 3:
                title = "READ-TO-SHIP Water Purification Units:\n**************************************";
                break;

            default:
                break;
        }
        displayGeneralInfo(title, input);
        System.out.println();
    }

    private Boolean getLastTestStatus(Unit unit) //false if unit is defective, true otherwise
    {
        ArrayList<Test> tempTestList = unit.getTests();
        int size = tempTestList.size();

        if (size == 0)
        {
            return true;
        }

        String status = tempTestList.get(size - 1).getStatus();

        if (status == "FAILED")
        {
            return false;
        }
        return true;
    }

    private void setOrder()
    {
        System.out.println("\nSelect sort order:");
        System.out.println("1) Sort by serial number");
        System.out.println("2) Sort by model, then by serial number");
        System.out.println("3) Sort by most recent test date");
        System.out.println("4) Cancel");
        int input = choiceHandle(1, 4);
        System.out.println();

        if (!(input == 4))
        {
            Sorter sorter = new Sorter();
            sorter.setOrder(input);
            sorter.sortUnits(data);
        }
    }

    private static void exit()
    {
        System.exit(0);
    }
}
