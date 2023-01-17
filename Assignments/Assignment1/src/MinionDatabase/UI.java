package MinionDatabase;
import java.util.Vector;
import java.util.Scanner;

public class UI 
{
    public String input;
    private Vector<Minion> data = new Vector<Minion>();

    public int inputHandle()
    {
        Scanner scan = new Scanner(System.in);
        System.out.println("Please select an option: ");
        input = scan.nextLine();
        int choice = Integer.parseInt(input);

        while (choice < 1 || choice > 6)
        {
            System.out.println("Please select an option listed below: ");
            input = scan.nextLine();
            choice = Integer.parseInt(input);
        }

        scan.close();
        return choice;
    }

    public void title()
    {
        System.out.println("|Welcome to the Minion Database|\n");
        System.out.println("********************************\n\n");
    }

    public void mainMenu()
    {
        System.out.println("Please select an option:\n");
        System.out.println("1) List Minions\n");
        System.out.println("2) Add Minion\n");
        System.out.println("3) Remove Minion\n");
        System.out.println("4) Attribute and evil deed to a Minion\n");
        System.out.println("5) Debug\n");
        System.out.println("6) Exit\n\n");
    }

    public void menuDescision(int descision)
    {
        switch (descision)
        {
            case 1:
                listMinions();
                break;
            case 2:
                addMinion();
                break;
            case 3:
                removeMinion();
                break;
            case 4:
                attributeDeed();
                break;
            case 5:
                debug();
                break;
            case 6:
                exit();
                break;
        }
    }

    public void listMinions()
    {
        for (Minion count : data)
        {
            System.out.println("Name: " + count.getName() + " | Height: " +
            count.getHeight() + " | Evil deeds: " + count.getEvilDeeds() + '\n');
        }
    }

    public void addMinion()
    {
        
    }

    public void removeMinion()
    {
        
    }

    public void attributeDeed()
    {
        
    }

    public void debug()
    {
        
    }

    public static void exit()
    {

    }
}
