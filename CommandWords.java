/**
 *  This class is a class of the "Shipwreck Adventure" application. 
 *  "Shipwreck Adventure" is a very simple, text based adventure game.  * 
 * This class holds an enumeration of all command words known to the game.
 * It is used to recognise commands as they are typed in.
 *
 * @author  Dilara Mukasheva
 * @version 2023.11.23
 */

public class CommandWords
{
    // a constant array that holds all valid command words (new commands: "back", "check" ,"drop", "pick", "give", "play")
    private static final String[] validCommands = 
    {"go", "quit", "help", "back", "check" ,"drop", "pick", "give", "play"};

    
    /**
     * Constructor - initialise the command words.
     */
    //public CommandWords()
    //{
        // nothing to do at the moment...
    //}

    /**
     * Check whether a given String is a valid command word. 
     * @return true if it is, false if it isn't.
     */
    public boolean isCommand(String aString)
    {
        for(int i = 0; i < validCommands.length; i++) {
            if(validCommands[i].equals(aString))
                return true;
        }
        // if we get here, the string was not found in the commands
        return false;
    }

    /**
     * Print all valid commands to System.out.
     */
    public void showAll() 
    {
        for(String command: validCommands) {
            System.out.print(command + "  ");
        }
        System.out.println();
    }
}
