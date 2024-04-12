import java.util.Random;

/**
 *  This class is a class of the "Shipwreck Adventure" application. 
 *  "Shipwreck Adventure" is a very simple, text based adventure game.  
 * 
 * This class generates random rooms from an allowed list of rooms from the main class 'game'
 * This is used for the characters and the transporter room.
 *
 * @author  Dilara Mukasheva
 * @version 2023.11.23
 * 
 */
public class RandomRoomSelector
{
    /**
     *  attributes and methods are static so can be accessed anytwhere within the program
     */
    public static Random r;
    
    
    /**
     *  method assigns randomly which room the character goes to
     */
    public static Room character()
    {
        r = new Random();
        int x = r.ints(0, 5).findFirst().getAsInt();
        return Game.allowedroomsCharacters.get(x);
    }
    
    /**
     *  method assigns randomly which room the player is sent to by the transporter
     */
    public static Room transporter(){
        r = new Random();
        int x = r.ints(0, 5).findFirst().getAsInt();
        return Game.allowedroomsTransporter.get(x);
    }
    
   
}
