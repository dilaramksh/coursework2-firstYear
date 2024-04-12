import java.util.ArrayList;

/**
 *  This class is a class of the "Shipwreck Adventure" application. 
 *  "Shipwreck Adventure" is a very simple, text based adventure game.  
 *  
 *  This class creates the bag that the player is 'carrying'. This stores the total weight, 
 *  the contents of the bag and keeps track of it.
 *
 * @author  Dilara Mukasheva
 * @version 2023.11.23
 */
public class Bag 
{
    private int totalWeight;
    private ArrayList<Item> contents = new ArrayList<Item>();
    
    //getter and setters
    public int getTotalWeight(){ //returns total weight
        return totalWeight;
    }
    
    public void setTotalWeight(int i){//sets total weight of the bag
        totalWeight -= i;
    }
    
    public ArrayList<Item> getContents(){//returns the contents of the bag
        return contents;
    }
    
    //rest of the methods
    /**
     * returns a list of all the names of the items in the bag
     */
    public ArrayList<String> bagItems(){
        ArrayList<String> x = new ArrayList<String>();
        if (contents.size() > 0){
            for (Item i: contents){
            x.add(i.getName());
        }}else{
            x.add("nothing");
        }
        return x;
    }
    
    /**
     * returns if bag contains the item 
     */
    public boolean contains(String secondWord){
        boolean a = false;
        for (Item i: contents){
            if (secondWord.equals(i.getName())){
                a = true;
                break;
            }
        }
        return a;
    }
    
    /**
     * return the Item that was searched for by its name in the contents of the bag
     */
    public Item getItem(String secondWord){
        Item item = null;
        for (Item i: contents)
        {
            if (secondWord.equals(i.getName())){
                item = i;
                break;
            }
        }
        return item;
    }
    
    /**
     * adds items to bag, and increases weight
     */
    public void addToBag(Item i){
        contents.add(i);
        totalWeight += i.getWeight();
    }
    
    /**
     * deletes items from bag, deletes from total weight, and sets the location of the item to the current room 
     */
    public void deleteFromBag(Item i, Room currentRoom){
        contents.remove(i);
        totalWeight -= i.getWeight();
        i.setLocation(currentRoom);
        currentRoom.addItem(i);
    }
    
    /**
     * different to the deleteFromBag because doesn't set the location to a different room.
     * the item 'disappears' forever when used --> used for the 'fish' item 
     */
    public void removeItem(Item i){
        contents.remove(i);
        totalWeight -= i.getWeight();
    }

}
