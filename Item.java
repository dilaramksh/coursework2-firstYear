
/**
 *  This class is a class of the "Shipwreck Adventure" application. 
 *  "Shipwreck Adventure" is a very simple, text based adventure game. 
 *  
 *  This class creates and and holds information about all the items in the game.
 *
 * @author  Dilara Mukasheva
 * @version 2023.11.23
 */
public class Item
{
    String name;
    Room location;
    int weight;
    
    /**
     * constructor 
     */
    public Item(String name, int weight){
        this.name = name;
        this.weight = weight;
    }
    
    //getters and setters 
    public String getName(){ //returns name
        return name;
    }
    
    public int getWeight(){ //returns weight
        return weight;
    }
    
    public void setLocation(Room location){ //set location of the item
        this.location = location;
    }
}
