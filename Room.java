import java.util.Set;
import java.util.HashMap;
import java.util.ArrayList;

/**
 *  This class is a class of the "Shipwreck Adventure" application. 
 *  "Shipwreck Adventure" is a very simple, text based adventure game.  
 *
 * A "Room" represents one location in the scenery of the game.  It is 
 * connected to other rooms via exits.  For each existing exit, the room 
 * stores a reference to the neighboring room.
 * 
 * @author  Dilara Mukasheva
 * @version 2023.11.23
 */

public class Room 
{
    private String name;
    private String description;
    private HashMap<String, Room> exits;        // stores exits of this room.
    private ArrayList<Item> items; //stores all items that are in the room
    private ArrayList<Character> characters; //stores all characters that are in the room
    public static RandomRoomSelector rrs = new RandomRoomSelector();

    /**
     * Create a room described "description". Initially, it has
     * no exits. "description" is something like "a kitchen" or
     * "an open court yard".
     * @param description The room's description.
     */
    //all constructors, depending the initial state of room (aka. has characters? has items?)
    /**
     * constructor for rooms w/o an item
     */
    
    public Room(String name, String description) 
    {
        this.name = name;
        this.description = description;
        items = new ArrayList<Item>();
        characters = new ArrayList<Character>();
        exits = new HashMap<>();
    }
    /**
     * constructor for rooms with item
     */
    public Room(String name, String description, Item initialItem) 
    {
        this.name = name;
        this.description = description;
        items = new ArrayList<Item>();
        items.add(initialItem);
        characters = new ArrayList<Character>();
        exits = new HashMap<>();
    }
    /**
     * constructor for rooms with item and character
     */
    public Room(String name, String description, Item initialItem, Character character) 
    {
        this.name = name;
        this.description = description;
        items = new ArrayList<Item>();
        items.add(initialItem);
        characters = new ArrayList<Character>();
        characters.add(character);
        exits = new HashMap<>();
    }
    
    // methods for all items in room 
    /**
     * this method finds the names of all items in the room and returns a list of them 
     */
    public ArrayList<String> getItems(){
        ArrayList<String> allItems = new ArrayList<String>();
        if (items.size() > 0){
            for (Item i: items)
            {
                allItems.add(i.getName());
            }
        }
        else{
            allItems.add("none");
        }
        return allItems;
    }
    
    /**
     * this method removes the item in the room via its name
     */
    public void removeItem(String name){
        for (Item i: items)
        {
            if (name.equals(i.getName())){
                items.remove(i); //removes the item from the room it was picked up in 
                break;
            }
        }
    }
    
    /**
     * return Item if found in the room by its name
     */
    public Item findItem(String name){
        Item item = null;
        for (Item i: items)
        {
            if (name.equals(i.getName())){
                item = i;
                break;
            }
        }
        return item;
    }

    /**
     * returns boolean if the room contains the item i 
     */
    public boolean containsItem(Item i){
        boolean z = false;
        for (Item x: items){
            if (x == i){
                z = true;
            }
        }
        return z;
    }
    
    /**
     * adds item to the rooms contents
     */
    public void addItem(Item i){
        items.add(i);
    }
    
    
    // methods for characters in room 
    /**
     * adds a character to the room 
     */
    public void addCharacter(Character c){
        characters.add(c);
    }
    
    /**
     * removes character from the room 
     */
    public void removeCharacter(Character c){
        characters.remove(c);
    }
    
    /**
     * returns all characters from the room 
     */
    public ArrayList<Character> getCharacters(){
        return characters;
    }
    
    /**
     * returns the name of the room 
     */
    public String getName(){
        return name;
    }
    
    /**
     * returns the name of all characters in the room 
     */
    public ArrayList<String> getCharacterNames(){
        ArrayList<String> c = new ArrayList<String>();
        if (characters.size() > 1){
            for (Character x: characters){
                if (!(x.getName().equals("player"))){
                    c.add(x.getName());                
                }
            }
        }
        else{
            c.add("none");
        }
        return c;
    }
    
    //other methods
    /**
     * Define an exit from this room.
     * @param direction The direction of the exit.
     * @param neighbor  The room to which the exit leads.
     */
    public void setExit(String direction, Room neighbor) 
    {
        exits.put(direction, neighbor);
    }

    /**
     * @return The short description of the room
     * (the one that was defined in the constructor).
     */
    public String getShortDescription()
    {
        return description;
    }

    /**
     * Return a description of the room in the form:
     *     You are in the deck.
     *     Exits: eas
     *     Items in room: [algae]
     * @return A long description of this room
     */
    public String getLongDescription()
    {
        return name + "\n" + description + ".\n" + getExitString() + "\n" + "Items in room: " + getItems() + "\n";
    }

    /**
     * Return a string describing the room's exits, for example
     * "Exits: north west".
     * @return Details of the room's exits.
     */
    private String getExitString()
    {
        String returnString = "Exits:";
        Set<String> keys = exits.keySet();
        for(String exit : keys) {
            returnString += " " + exit;
        }
        return returnString;
    }

    /**
     * Return the room that is reached if we go from this room in direction
     * "direction". If there is no room in that direction, return null.
     * @param direction The exit's direction.
     * @return The room in the given direction.
     */
    public Room getExit(String direction) 
    {
        return exits.get(direction);
    }
}

