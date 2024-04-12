import java.util.Stack;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.*; 

/**
 *  This class is the main class of the "Shipwreck Adventure" application. 
 *  "Shipwreck Adventure" is a very simple, text based adventure game.
 * 
 *  To play this game, create an instance of this class and call the "play"
 *  method.
 * 
 *  This main class creates and initialises all the others: it creates all
 *  rooms, characters, items, the bag, the parser and starts the game.  It also evaluates and
 *  executes the commands that the parser returns.
 * 
 * @author  Dilara Mukasheva
 * @version 2023.11.23
 */

public class Game 
{
    private Parser parser;
    private Room currentRoom;
    
    private Stack<Room> previousRooms;//keeps track of all the rooms that the player visits
    
    private final static int maxWeight = 8; //final static because this doesn't and can't change within the program
    private int oxygen = 30; //keep track of the oxygen
    private final static ArrayList<String> checkCommand = new ArrayList<String>(Arrays.asList("weight", "room", "oxygen", "bag", "map")); 
    
    private Room deck;
    private Room grandHall;
    private Room drawingRoom;
    private Room gameRoom;
    private Room diningRoom;
    private Room closet;
    private Room suite;
    private Room maidQuarters;
    private Room chamberRoom;
    
    //used for storing all rooms in the game
    public static ArrayList<Room> allRooms;   
    
    //used for storing all rooms that characters are allowed to go into 
    public static ArrayList<Room> allowedroomsCharacters;    
    
    //used for storing all rooms that transporter room is allowed to transport player to 
    public static ArrayList<Room> allowedroomsTransporter; 

    private Item algae; 
    private Item fish;
    private Item torch;
    private Item flippers;
    private Item key;
    private Item necklace;
    private Bag bag = new Bag(); //instantiating a bag

    private Character oyster; //instantiating  oyster
    private Character octopus; //instantiating octopus 
    private Character skeleton; //instantiating skeleton
    private Character shark; //instantiating shark
    private Character player; // this is used for checking the location of the player in regard to the characters
    
    private boolean gameCounter = false; //ensures that the game in the Game Room can onlyy be played once to win the torch
    private boolean sharkCounter = false; //ensures that the shark is only fed once to retrieve the key in the grand suite
    private boolean helpCounter = false; 
        
    /**
     * Create the game and initialise its internal map.
     */
    public Game() 
    {
        //initialising all the items 
        algae = new Item("algae", 1);
        fish = new Item("fish", 1);
        torch = new Item("torch", 2);
        flippers = new Item("flippers", 2 );
        key = new Item("key", 3);
        necklace = new Item("necklace", 3);
        
        
        //initialising all the characters
        oyster = new Character("oyster");
        octopus = new Character("octopus");
        skeleton = new Character("skeleton");
        shark = new Character("shark");
        player = new Character("player"); //keeping track of where the player is in the game 
        
        // description of all the rooms that the game has
        deck = new Room("The Deck", "You are at the entrance of the lost ship 'Serenity'."
        + "\n" + "Through this room you can enter: The Grand Hall", fish, player);
        
        
        grandHall = new Room("The Grand Hall","Through this room you can enter:  The Dining Room, The Game Room, The Drawing Room, The Deck");
        
        
        drawingRoom = new Room("The Drawing Room", "Through this room you can enter: The Closet, The Grand Hall"
        + "\n" + "By entering the closet you get tranpsorted to a random room within the game", flippers, oyster);
        
        
        
        gameRoom = new Room("The Game Room","If you want to play game to win torch, type 'play'."
        + "\n" + "You can't play the game twice"
        + "\n" + "Through this room you can enter: The Grand Hall", torch, skeleton);
        
        
        diningRoom = new Room("The Dining Room","Through this room you can enter: The Maid Quarters, The Grand Hall", algae, octopus);
        
        
        closet = new Room("The Closet", "The Closet allows you to get transported to a random room in the game"
        + "\n" + "This includes getting transported to The Grand Suite, which can only exclusively be entered via The Closet");
        
        
        suite = new Room("The Grand Suite", "Hooray, luck has faced your side you are in The Grand Suite! ", key, shark);
        
        
        maidQuarters = new Room("The Maid Quarters", "Through this room you can enter: The Chamber Room, The Dining Room"
        + "\n" + "The current starts increasing rapidly in the room!");
        
        
        chamberRoom = new Room("The Chamber Room", "Well Done, you succesfully entered The Chamber Room!"
        + "\n" + "Through this room you can enter: The Maid Quarters"
        + "\n" + "Pick The Pearl Necklace up using the 'pick necklace' command and you win the game!", necklace);
    
        allRooms = new ArrayList<Room>(Arrays.asList(deck, grandHall, drawingRoom, gameRoom, diningRoom, closet, suite, maidQuarters, chamberRoom));
        allowedroomsCharacters = new ArrayList<Room>(Arrays.asList(deck, grandHall, drawingRoom, gameRoom, diningRoom, maidQuarters));
        allowedroomsTransporter = new ArrayList<Room>(Arrays.asList(deck, grandHall, gameRoom, diningRoom, suite, maidQuarters));

        
        
        //ensuring that characters and items know which room they are in
        oyster.setLocation(drawingRoom);
        octopus.setLocation(diningRoom);
        skeleton.setLocation(gameRoom);
        shark.setLocation(suite);
        player.setLocation(deck);
        
        algae.setLocation(diningRoom);
        fish.setLocation(deck);
        torch.setLocation(gameRoom);
        flippers.setLocation(drawingRoom);
        key.setLocation(suite);
        necklace.setLocation(chamberRoom);
        

        previousRooms = new Stack<Room>(); 
        previousRooms. push(deck); // the game always begins on the deck
        createRooms();
        parser = new Parser();
    
    }

    
    
    /**
     * Create all the rooms and link their exits together.
     */
    private void createRooms()
    {
        // initialise room exits
        deck.setExit("east", grandHall);

        grandHall.setExit("north", drawingRoom);
        grandHall.setExit("south", gameRoom);
        grandHall.setExit("east", diningRoom);
        grandHall.setExit("west", deck);

        drawingRoom.setExit("east", closet);
        drawingRoom.setExit("south", grandHall);
        
        gameRoom.setExit("north", grandHall);
        
        diningRoom.setExit("east", maidQuarters);
        diningRoom.setExit("west", grandHall);
        diningRoom.setExit("south", suite);
        
        maidQuarters.setExit("east", chamberRoom);
        maidQuarters.setExit("west", diningRoom);
        
        suite.setExit("north", diningRoom);
        
        chamberRoom.setExit("west", maidQuarters);
        
    
        currentRoom = deck;  // start game outside
    }
    
    /**
     *  Main play routine.  Loops until end of play, win, or oxygen == 0
     */
    public void play() 
    {            
        printWelcome();
        // Enter the main command loop.  Here we repeatedly read commands and
        // execute them until the game is over.
        boolean finished = false;
        while (! finished && oxygen > 0 && !bag.contains("necklace")) {
            Command command = parser.getCommand();
            finished = processCommand(command);
        }
        /**
        *  if oxygen levels equal to 0, then the game stops automatically
        */
        if (oxygen == 0){  
            System.out.println("Your oxygen level is 0. You lost.");
        }
        /**
        *  when player places necklace into bag, the game automatically 
        *  recognises this as the win and ends the play loop
        */
        else if(bag.contains("necklace")){ 
            System.out.println(
                     "--------------------------------------------------------------------------------" 
            +"\n" +  "                                   YOU WON!                                     " 
            + "\n" + "                                  WELL DONE!                                    "  
            +"\n"+   "--------------------------------------------------------------------------------");
        }
        /**
        *  if player enters quit, ends game
        */
        else{
            System.out.println("Thank you for playing.  Good bye.");
        }
        
    }

    /**
     * Print out the opening message for the player.
     */
    private void printWelcome()
    {
        System.out.println();
        System.out.println("Welcome to the Shipwreck Adventure!");
        map();
        System.out.println("Your mission as the player is to find the Legendary Pearl Necklace that was lost in the shipwreck a long time ago..." 
        + "\n" + "This is to be done via visiting rooms and finding clues, as well as picking up certain items along the way." + "\n"
        
        + "\n" + "THE GAME ROOM contains a game that you can play in order to win the TORCH." 
        + "\n" + "THE GRAND SUITE can only be entered via THE CLOSET."
        + "\n" + "Some rooms can only be entered by having certain items in your bag."  + "\n"
        
        
        + "\n" + "You are only allowed to carry " + maxWeight + " units of items at once." 
        + "\n" + "Track weight of what you are currently carrying through the command 'check weight'."
        + "\n" + "You can alter the weight by picking or dropping stuff using the 'drop' and 'pick' commands." + "\n"
        
        + "\n" + "THE WEIGHT OF EACH ITEM: " 
        + "\n" + "Algae: "    + algae.getWeight()    + " unit/s"
        + "\n" + "Fish: "     + fish.getWeight()     + " unit/s"
        + "\n" + "Torch: "    + torch.getWeight()    + " unit/s"
        + "\n" + "Flippers: " + flippers.getWeight() + " unit/s"
        + "\n" + "Key: "      + key.getWeight()      + " unit/s"
        + "\n" + "Necklace: " + necklace.getWeight() + " unit/s" + "\n"
        
        + "\n" + "Item ALGAE is not expendable. That means that after player feeds it to the oyster, the game places it back to THE DINING ROOM," 
        + "\n" + "where the user can pick it up and use it again."+ "\n"

        + "\n" + "There are 4 characters in the game: Oyster, Skeleton, Octopus, Shark. All (apart from the shark) are randomly moving around the ship."+ "\n"
        
        + "\n" + "When encountering an OYSTER, you can increase your oxygen level by feeding it some algae."
        + "\n" + "Your starting oxygen level is " + oxygen + ". If it reaches to 0, you immediately lose the game." + "\n"
        
        + "\n" + "When encountering a SKELETON, your oxygen level is depleted by 5." + "\n"
        
        + "\n" + "When encountering an OCTOPUS all the items that you carry will be placed back to their original positions." 
        + "\n" + "You will have to retrieve them again." + "\n"
        
        + "\n" + "When encountering a SHARK, you could receive a key." + "\n"
        
        + "\n" + "There are yet a few surprises that you have to discover along the way. Not everything is revealed in the instructions..."  + "\n"
        
        + "\n" + "You win the game when you pick up the necklace in THE CHAMBER ROOM."  + "\n"
        
        + "\n" + "The game maker suggests you to explore the ship first. GOOD LUCK!"  + "\n");
        
        printHelp();
        System.out.println("\n" + currentRoom.getLongDescription());
    }

    /**
     * Given a command, process (that is: execute) the command.
     * @param command The command to be processed.
     * @return true If the command ends the game, false otherwise.
     */
    private boolean processCommand(Command command) 
    {
        boolean wantToQuit = false;

        if(command.isUnknown()) {
            System.out.println("I don't know what you mean...");
            return false;
        }

        String commandWord = command.getCommandWord();
        String secondWord = command.getSecondWord();
        String thirdWord = command.getThirdWord();
        
        if (commandWord.equals("help")) {
            printHelp();
        }
        else if (commandWord.equals("go")) {
            goRoom(command);
        }
        else if (commandWord.equals("quit")) {
            wantToQuit = quit(command);
        }
        else if (commandWord.equals("back")){
            back();
        }
        else if (commandWord.equals("play") && currentRoom == gameRoom ){
            if (gameCounter == false){
                playGame();
            }
            else { //if(gameCounter == true)
                System.out.println("You have already played the game once to earn the torch. Now, you can just pick up the torch");
            }
        }
        else if (commandWord.equals("give")){
            if (thirdWord != null)
            {
                if (currentRoom.getCharacters().contains(oyster) || currentRoom.getCharacters().contains(shark))
                {
                    giveItem(secondWord, thirdWord);
                }
                else{
                    System.out.println("Can't carry out the give command for this character");
                }
            }
            else{
                System.out.println("Give who what?" );
            }
        }
        /**
        *  the commands CHECK, DROP, PICK all can't have a third command, therefore the following function ensures that 
        *  there are no third words for all the commands
        */
        else if (thirdWord == null){
            if (commandWord.equals("check")){
                if (secondWord == null){System.out.println("Check what? The options are: weight, room, oxygen, bag, map");}
                else{check(secondWord); }
            }
            else if (commandWord.equals("drop")){
                if (secondWord == null){System.out.println("Drop what?");}
                else{dropItem(secondWord);}
            }
            else if (commandWord.equals("pick")){
                if (secondWord == null){System.out.println("Pick what?");}
                else
                {
                    /**
                    *  this ensures that torch and key can't be picked up before the game is played or shark is fed
                    */
                if (currentRoom == gameRoom || currentRoom == suite){
                       if (currentRoom.containsItem(torch) && secondWord.equals("torch")){
                        if (gameCounter == false){
                        System.out.println("You have to win the torch. Input 'play'");}
                        if (gameCounter == true){
                            pickItem(secondWord);
                        }}
                    else if (currentRoom.containsItem(key) && secondWord.equals("key")){
                        if (sharkCounter == false){
                        System.out.println("You have to feed the shark fish first. Input 'give shark fish'");}
                        if (sharkCounter == true){
                            pickItem(secondWord);
                        }}
                    else{
                        pickItem(secondWord);
                    }
                }
                else{
                       pickItem(secondWord);}
            }
        }}
        else{
        System.out.println("Can't have third word with this command");
        }
        // else command not recognised.
        return wantToQuit;
    }

    // implementations of user commands:
    /** 
     * Try to in to one direction. If there is an exit, enter the new
     * room, otherwise print an error message.
     * 
     */
    private void goRoom(Command command) 
    {
        if(!command.hasSecondWord()) {
            // if there is no second word, we don't know where to go...
            System.out.println("Go where?");
            return;}
        String direction = command.getSecondWord();
        
        if (currentRoom == maidQuarters && direction.equals("east") && !bag.contains("key")){
            //ensures that chamber can only be entered with the presence of a key 
            System.out.println("You can't enter The Chamber Room without the key");
            return;
        } 
        
        // Try to leave current room.
        Room nextRoom = currentRoom.getExit(direction);

        if (nextRoom == null) {
            System.out.println("There is no door!");
        }else {
            currentRoom.removeCharacter(player); 
            /**
             * closet is the tranpsorter room, therefore we need to use the RandomRoomSelector 
             * to generate randomly which room the player will end up in 
             */
            if (nextRoom == closet){
                nextRoom = Room.rrs.transporter();
                System.out.println("YOU ARE TRANSPORTED TO... " + nextRoom.getName() + "\n");
            }
            currentRoom = nextRoom;
            player.setLocation(currentRoom);
            currentRoom.addCharacter(player);
            previousRooms.push(currentRoom); 
            makeCharactersMove();
            if (nextRoom == suite){ 
                sharkSuite(); //interaction with the shark in the room 
                previousRooms.pop(); // this ensures that suite can't be entered via the back command
            }else if(nextRoom == maidQuarters){
                fastCurrent(); //if the player can remain in the room
            }
        }
    }
    
    /**
     * this is the method that is called when check command is used. 
     * it can return the oxygen level, weight, room contents, bag, map depending on the second word entered
     */
    private void check(String secondWord) //second word could either be oxygen, weight, room, bag, map 
    {
        if  (checkCommand.contains(secondWord)){
            //checks which thing the player wants to check the level of or item of
            if (secondWord.equals(checkCommand.get(0))){ //weight
                System.out.println("The current weight that you carry is: " + bag.getTotalWeight() + " unit/s");
            }else if(secondWord.equals(checkCommand.get(1))){ //room contents (characters and items in the room)
                System.out.println("Characters: " + currentRoom.getCharacterNames()
                + "\n" + "Items: " + currentRoom.getItems());
            }else if (secondWord.equals(checkCommand.get(2))){ //oxygen level
                System.out.println("The current oxygen level is: " + oxygen);
            }else if (secondWord.equals(checkCommand.get(3))){ //bag contents
                if (bag.getContents().size() == 0){
                    System.out.println("No items in bag");
                }else{
                    System.out.println("The bag contains: " + bag.bagItems());
                }
            }else if (secondWord.equals(checkCommand.get(4))){ //map of the game
                map();
            }
        }else{
            System.out.println("Invalid check option. The options are: oxygen, weight, room, bag, map");
        }
        
    }
    
    /**
     * drop item and put it into the current room (aka assign the item to the room and add the item to the rooms contents)
     */
    private void dropItem(String secondWord){
        if (bag.contains(secondWord)){ //ensures that item is present in the bag
            Item pickedItem = bag.getItem(secondWord); 
            bag.deleteFromBag(pickedItem, currentRoom); //deletes from bag and places it into the current room
            System.out.println(pickedItem.getName() +" was dropped in " + currentRoom.getName());
        }else{
            System.out.println("The bag doesn't contain the item '" + secondWord +"'");
        }
    }
    
    /**
     * pick item, place into bag and remove it from the rooms contents
     */
    private void pickItem(String secondWord){
        if (currentRoom.getItems().contains(secondWord)){ //ensures that room contains that item
            Item pickedItem = currentRoom.findItem(secondWord);
            if ((pickedItem.getWeight() + bag.getTotalWeight()) <= maxWeight){ 
                //ensures that it doesn't go over the max weight limit of the bag 
                currentRoom.removeItem(secondWord); //removes from the contents of the room
                System.out.println( pickedItem.getName() + " weight is: "+ pickedItem.getWeight() +" unit/s");
                System.out.println(pickedItem.getName() +" was placed into the bag" + "\n");
                bag.addToBag(pickedItem); //adds to bag + increases weight of the bag
            }else{
                System.out.println("You can't add the item, because the weight limit of " + maxWeight + " units be reached." 
                + "\n" + "Drop an item from your bag");
            }
        }else{
            System.out.println("The room you are in doesn't contain this item");
        }
    }
    
    /**
     * this is used when interacting with oyster, shark characters using the give command
     */
    private void giveItem(String secondWord, String thirdWord){
        if (secondWord.equals("oyster") && thirdWord.equals("algae")){ //for algae
            if (bag.contains(thirdWord)){
                bag.deleteFromBag(algae, diningRoom); //deletes algae from bag and places it back into the dining room. 'not expendable'
                oxygen += 10; //adds 10 points to the oxygen
                System.out.println("10 points of oxygen added."
                + "\n" + "Your current oxygen level is: " + oxygen
                + "\n" + "You can find more algae in The Dining Room.");
            }else{
                System.out.println("You don't have an algae in your bag");
            }
        }else if(secondWord.equals("shark") && thirdWord.equals("fish") ){ //for shark
            if (bag.contains(thirdWord) && sharkCounter == false){
                if(bag.getTotalWeight() > (maxWeight - key.getWeight())){ //ensures that the bag weight doesn't exceed the limit if the key will be added
                    System.out.println("The shark can't give you key because weight limit of bag is excededing "  + maxWeight + " units"
                    + "\n" + "Try removing items using the drop command");
                }else{
                    bag.removeItem(fish); //deletes fish from bag, the fish can only be used once
                    bag.addToBag(key); //adds key to bag 
                    currentRoom.removeItem("key"); //removes key from the suite
                    sharkCounter = true; //ensures that can be taken from shark once
                    System.out.println("You've gotten the key!"
                    + "\n" + "Exit the room by going north, and try getting to the chamber room");
                }
            }else if (sharkCounter == true){ //if taken from shark once, then no need to give the fish again
                System.out.println("You've already gotten the key from the shark");
            }
        }else{
            System.out.println("Unfortunately, you can't give " + secondWord + " a/n " + thirdWord);
            System.out.println("Try again");
        }
    }
    
    /**
     * this method allows the user to go back to the previous room they were in. implements stacks
     */
    private void back(){
        if (previousRooms.size() > 1){ //ensures that the index won't be out of bound (always ends up on the deck where the player begins
            currentRoom.removeCharacter(player);
            previousRooms.pop();
            currentRoom = previousRooms.peek();
            player.setLocation(currentRoom);
            currentRoom.addCharacter(player);
            makeCharactersMove();
        }else{
            System.out.println("Back command can't be used because already at the beggining");
        }
        
    }
    
    /**
     * Print out some help information.
     * Here we print some stupid, cryptic message and a list of the 
     * command words.
     */
    private void printHelp() 
    {
        System.out.println("Your command words are: " 
        + "\n" + "go, back, check, drop, pick, give, quit, help"
        + "\n" + "GO, CHECK, DROP, PICK commands need to have a second word to it"
        + "\n" + "The CHECK command has several options: " + checkCommand
        + "\n" + "Use BACK command to go back to the previous room you were in"
        + "\n" + "GIVE command needs three words ie. 'give oyster algae'" + "\n");
        
        if (helpCounter == true){
            System.out.println("Here are some general hints: " 
            + "\n" + "Octopus doesn't steal anything when there is a torch on you" 
            + "\n" + "Shark is lonely in The Grand Suite so wants some fish"
            + "\n" + "Algae can be found in The Dining Room"
            + "\n" + "You will need flippers for The Maid Quarters");
        }
        
        helpCounter = true; //makes it true the first time round, so the extra hints are displayed if the user needs it (not straight from the beginning) 
        
        
    }
    
    /** 
     * "Quit" was entered. Check the rest of the command to see
     * whether we really quit the game.
     * @return true, if this command quits the game, false otherwise.
     */
    private boolean quit(Command command) 
    {
        if(command.hasSecondWord()) {
            System.out.println("Quit what?");
            return false;
        }
        else {
            return true;  // signal that we want to quit
        }
    }
    
    /**
     * image of the map of the game
     */
    private void map(){
        System.out.println(".____________________________________________________________.");
        System.out.println("|             .________.  .________.                         |");
        System.out.println("|             [drawing ]__[closet  ]                         |");
        System.out.println("|             [room    ]__[        ]                         |");
        System.out.println("|             [________]  [________]                         |");
        System.out.println("|               | | |                                        |");
        System.out.println("| .________.  .________.  .________.  .________.  .________. |");
        System.out.println("| [ deck   ]__[grand   ]__[dining  ]__[maid    ]__[chamber ] |");
        System.out.println("| [        ]__[hall    ]__[room    ]__[quarters]__[room    ] |");
        System.out.println("| [________]  [________]  [________]  [________]  [________] |");
        System.out.println("|               | | |                                        |");
        System.out.println("|             .________.    .________.                       |");
        System.out.println("|             [game    ]    [Grand   ]                       |");
        System.out.println("|             [room    ]    [suite   ]                       |");
        System.out.println("|             [________]    [________]                       |");
        System.out.println(".____________________________________________________________.");
    }

    
    
    
    // methods implemented when entering certain rooms:
    /** GAME ROOM
     * this game is played in the game room once, 
     * if won once the game can't be played again --> uses the gameCounter to make sure of that
     */
    private void playGame(){
        boolean counter = false; 
        Random r = new Random();
        int x = r.ints(1, 12).findFirst().getAsInt();
        int y = r.ints(1, 12).findFirst().getAsInt();
        int z = x * y;
        System.out.println( x + " x " + y + " = ?" );
        while (counter == false){ //played until got the right answer
            if (parser.getReader().nextLine().equals(String.valueOf(z))){
                System.out.println("Congrats you earned the torch"
                + "\n" + "Now you can rest assured that octopus won't steal, as long as you have a torch on you!");
                bag.addToBag(torch);
                currentRoom.removeItem("torch");
                gameCounter = true; //ensures that game is played once, and torch is earned once
                counter = true;
            }else{
                System.out.println("Your input is wrong. Try again");
            }}}
    
    /** MAID QUARTERS
     * this checks if the player can stay in the maid quarter becuase of the fast currents. 
     * having flippers gives you the 'power' to remain in the room, otherwise throws you back into the dining room
     */
    private void fastCurrent(){
        if (!bag.contains("flippers")){ //if flippers not in bag, throws the user back to the diningRoom
            System.out.println("Oh no! You don't have flippers on you. You are swept back into the dining room");
                    previousRooms.pop(); // this ensures that the maid quarters can't be entered via the back command if been thrown back to the diningroom 
                    currentRoom.removeCharacter(player);
                    currentRoom = diningRoom; //thrown back to dining room 
                    player.setLocation(currentRoom); 
                    currentRoom.addCharacter(player); 
                    makeCharactersMove();
        }else{//if flippers in bag, allows the user to stay 
            System.out.println("Yes! You have flippers on you. You stay in the room depsite the fast current");
        }
    }
    
    /** THE GRAND SUITE
     * method is used when entering the suite from the transporter room. 
     * This triggers the interation with the shark and the give command
     */
    private void sharkSuite(){ 
        // if no fish then takes half of the current oxygen level
        //
        //makes sure that the game is played once
        if (sharkCounter == false){ //the following commands only happen if the shark hasn't been fed fish
            System.out.println("But wait, there is something moving in here... A shark!");
            if (bag.contains("fish")){ 
                System.out.println("You have a fish in your bag! Use the command 'give shark fish' and it will give you a key in return!");
            }else{
                System.out.println("You don't have a fish in your bag. The shark is angry, it will take half of your oxygen away");
                oxygen = oxygen / 2;
            }
        }else{
            System.out.println("You have already won the key from the shark. You can exit by going north");
        } 
    }
    
    
    
    
    // suplementary methods used to interact with the player character: 
    /**
     * makes each character in game move via using the RandomRoomSelector class. 
     * this creates the 'moving by themselves effect'
     */
    private void makeCharactersMove(){
        oyster.move();
        octopus.move();
        skeleton.move();
        System.out.println(currentRoom.getLongDescription());
        interactionWithCharacter();
    }
    
    /**
     * this method makes the interactions with the characters possible
     * this updates the oxygen and bag of the player
     */
    private void interactionWithCharacter(){
        if (currentRoom.getCharacters().size() > 1){
            if (currentRoom.getCharacters().contains(oyster)){
                System.out.println("There is an oyster in the room! " 
                + "\n" + "If you have algae in you bag give it to the oyster to gain 10 points of oxygen."
                + "\n" + "Use the command: give oyster algae" + "\n");
            }
            if(currentRoom.getCharacters().contains(skeleton)){
                oxygen -= 5;
                System.out.println("There is a skeleton in the room!"
                + "\n" + "Unfortunately, you lose 5 points of your oxygen"
                + "\n" + "You current oxygen level is: " + oxygen + "\n");
            }
            if(currentRoom.getCharacters().contains(octopus)) {
                System.out.println("There is an octopus in the room!");
                if (bag.bagItems().size() == 0){
                    System.out.println("You don't have anything in your bag, octopus retreats" + "\n");
                }else{
                    if (bag.contains("torch")){ //if torch present octopus doesn't steal
                        System.out.println("Dont worry, you are safe because of the torch that you carry!"
                        + "\n" + "Phew" + "\n");
                    }else{ //no torch, gives octopus permision to steal everything in your bag
                        octopusSteals();
                        System.out.println("Oh no, you don't have a torch on you! "
                        + "\n" + "You lost all of your contents in your bag"
                        + "\n" + "You can regain it by going to the rooms that they were originally in" + "\n");
                    }}}}
    }
    
    /**
     * the command for when player encounters the octopus, which takes everything form the bag and the game places 
     * them back to their original positions at the start of the game
     */
    private void octopusSteals(){
        for (Item i: bag.getContents()){
            if(i.getName().equals("algae")){
                i.setLocation(diningRoom);
                diningRoom.addItem(i);
            }
            else if(i.getName().equals("fish")){
                i.setLocation(deck);
                deck.addItem(i);
            }
            else if(i.getName().equals("flippers")){
                i.setLocation(drawingRoom);
                drawingRoom.addItem(i);
            }
            else if(i.getName().equals("key")){
                i.setLocation(suite);
                suite.addItem(i);
            }
        }
        bag.setTotalWeight(bag.getTotalWeight()); //basically sets the weight to 0;
        bag.getContents().clear(); //removes everything from the bag list (aka empty bag)
    }
}
