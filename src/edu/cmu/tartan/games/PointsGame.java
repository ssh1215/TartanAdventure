package edu.cmu.tartan.games;

import edu.cmu.tartan.Game;
import edu.cmu.tartan.GameConfiguration;
import edu.cmu.tartan.GameContext;
import edu.cmu.tartan.Player;
import edu.cmu.tartan.goal.GamePointsGoal;
import edu.cmu.tartan.item.*;
import edu.cmu.tartan.room.Room;

/**
 * Demonstrating a points-based game.
 * <p>
 * Project: LG Exec Ed SDET Program
 * 2018 Jeffrey S. Gennari
 * Versions:
 * 1.0 March 2018 - initial version
 */
 public class PointsGame extends GameConfiguration {

    public PointsGame() {
        super("Points");
    }

    /**
     * Configure the game.
     * @param game the Game object that will manage execution
     * @throws InvalidGameException
     */
    @Override
    public boolean configure(GameContext context) throws InvalidGameException{

        String officeD = "You are in an office. It seems that the occupant has only recently left.";
        String officeSD = "Office.";

        Room office = new Room(officeD, officeSD);
        ItemComputer computer = (ItemComputer) Item.getInstance("computer", context.getUserId());
        computer.setInspectMessage("You flip the computer's keyboard over, and unsuprisingly encounter a yellow sticky note. It reads:\n\n9292\n");
        ItemSafe safe = (ItemSafe)Item.getInstance("safe", context.getUserId());
        safe.setInspectMessage("This safe appears to require a 4 digit PIN number.");
        safe.setPIN(9292);

        ItemDocument document = (ItemDocument) Item.getInstance("document", context.getUserId());
        document.setVisible(false);
        document.setInspectMessage("The document is encrypted with a cipher. The cryptographers at the CIA will need to decrypt it.");
        safe.install(document);

        office.putItem(document); // in the room but invisible
        office.putItem(safe);
        office.putItem(computer);

        ItemCoffee coffee = (ItemCoffee)Item.getInstance("coffee", context.getUserId());
        office.putItem(coffee);
        office.putItem(Item.getInstance("light", context.getUserId()));

        // Keep scores for things in this room
        int points = document.value() + coffee.value() + safe.value();

        Player player = new Player(office, Player.DEFAULT_USER_NAME);
        context.setPlayer(player);
        context.addGoal(new GamePointsGoal(points, player));

        context.setGameDescription("The objective of this game is to earn points by doing certain things. You must earn" + points + " to win");

        if (!context.validate()) throw new InvalidGameException("Game improperly configured");
        
        return true;
    }
}
