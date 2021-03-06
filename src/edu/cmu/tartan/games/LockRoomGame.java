package edu.cmu.tartan.games;

import edu.cmu.tartan.Game;
import edu.cmu.tartan.GameConfiguration;
import edu.cmu.tartan.GameContext;
import edu.cmu.tartan.Player;
import edu.cmu.tartan.action.Action;
import edu.cmu.tartan.goal.GameExploreGoal;
import edu.cmu.tartan.item.Item;
import edu.cmu.tartan.item.ItemLock;
import edu.cmu.tartan.room.Room;
import edu.cmu.tartan.room.RoomLockable;

import java.util.ArrayList;

/**
 * Demonstrate a locked room game.
 * <p>
 * Project: LG Exec Ed SDET Program
 * 2018 Jeffrey S. Gennari
 * Versions:
 * 1.0 March 2018 - initial version
 */
public class LockRoomGame extends GameConfiguration {

    public LockRoomGame() {
        super("Lock Demo");
    }

    /**
     * Configure the game
     * @param game the Game object that will manage execution
     * @throws InvalidGameException
     */
    @Override
    public boolean configure(GameContext context) throws InvalidGameException {

        Room mid1 = new Room("There is a fork", "Fork");
        Room mid2 = new Room("Ferocious bear", "bear");
        Item key = Item.getInstance("key", context.getUserId());
        Room end = new RoomLockable("You are inside of a building", "interior",
                true, key);

        end.setAdjacentRoom(Action.ACTION_GO_NORTHEAST, mid1);

        ArrayList<Item> startItems = new ArrayList<>();
        Item lock = Item.getInstance("lock", context.getUserId());

        // Install the lock and key to unlock the locked room. You must 'open' or 'unlock' the lock
        // to go through the door into the locked room
        ((ItemLock) lock).install(key);
        lock.setRelatedRoom(end);
        mid2.putItem(lock);

        startItems.add(key);
        startItems.add(lock);

        Room start = new Room("There is a tree, with a building to the West. There is a lock on the door.", "Tree" );
        start.setAdjacentRoom(Action.ACTION_GO_NORTH, mid1);
        start.setAdjacentRoom(Action.ACTION_GO_EAST, mid2);
        start.setAdjacentRoom(Action.ACTION_GO_WEST, end);
        start.putItems(startItems);

        ArrayList<String> goals = new ArrayList<>();
        goals.add("Fork");
        goals.add("bear");
        goals.add("interior");

        Player player = new Player(start, Player.DEFAULT_USER_NAME);

        context.setPlayer(player);
        context.addGoal(new GameExploreGoal(goals, context.getPlayer()));

        context.setGameDescription("The objective of this game is to unlock a room.");

        if (!context.validate()) throw new InvalidGameException("Game improperly configured");
        
        return true;
    }
}
