import java.util.ArrayList;


/**
 *  This class is a class of the "Shipwreck Adventure" application. 
 *  "Shipwreck Adventure" is a very simple, text based adventure game. 
 * 
 *  All characters belong to this class.
 * 
 *  I decided to make one singular class for different characters because, they all share functional similarities, but different names.
 *  Similarities such as their location, and if they are used to take or give to player. 
 *
 *  @author  Dilara Mukasheva
 *  @version 2023.11.23
 */
public class Character
{
    private String name;
    private Room location;
    
    /**
     *  general constructor
     */
    public Character(String name){
        this.name = name;
    }
    
    /**
     *  useful to use to print out commands or information in the main class
     */
    public String getName(){
        return name;
    }
    
    /**
     *  used to set and change the location of the characters based on the randomly generated, or assigned room
     */
    public void setLocation(Room location){
        this.location = location;
    }
    
    /**
     *  this method is used to make the characters move to a different locaiton everytime the player changes theirs
     */
    public void move(){
        this.location.removeCharacter(this);
        boolean counter = false;
        while (counter == false ){
            Room newLocation = Room.rrs.character();
            //ensures that the charcter doesn move to the exact same room, is allocet another room
            if (newLocation != this.location) //ensures that the character is placed into a different room, not the one they are already in 
            {
                newLocation.addCharacter(this);
                this.setLocation(newLocation);
                counter = true;
            }
        }
    }
}
