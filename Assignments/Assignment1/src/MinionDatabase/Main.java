package MinionDatabase;

/**
* Description: The Main class is used to start the UI (user interface)
* for the Minion class. All other aspects of the program are handeled in
* the UI and Minion classes. Use with the UI and Minion classes in the
* MinionDatabase package.
*
* @author Daniel Tolsky
* @version 1.0
*/

public class Main 
{
    public static void main(String[] args) throws Exception 
    {
        UI display = new UI();
        display.start();
    }
}