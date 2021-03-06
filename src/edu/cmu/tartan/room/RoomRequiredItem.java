package edu.cmu.tartan.room;

import edu.cmu.tartan.TerminateGameException;
import edu.cmu.tartan.GameInterface.MessageType;
import edu.cmu.tartan.action.Action;
import edu.cmu.tartan.item.Item;

import java.util.LinkedList;

/**
 * The class for a room that requires a certain item to enter.
 * from this class.
 * <p>
 * Project: LG Exec Ed SDET Program
 * 2018 Jeffrey S. Gennari
 * Versions:
 * 1.0 March 2018 - initial version
 */
public class RoomRequiredItem extends Room {
	private Item requiredItem;
    private boolean diesOnItemDiscard;
    private boolean diesOnEntry;
    private String deathMessage;
    private LinkedList<Action> safeDirections;
	String warningDescription;
	String warningShortDescription;


	public RoomRequiredItem(String d, String dd, Item requiredItem) {
		this(d, dd, null, null, requiredItem);
	}

    /**
     * Create a new room the requires an item
     * @param d
     * @param dd
     * @param w
     * @param ws
     * @param requiredItem
     */
	public RoomRequiredItem(String d, String dd, String w, String ws, Item requiredItem) {
		super(d, dd);

		this.warningDescription = w;
		this.warningShortDescription = ws;
		this.requiredItem = requiredItem;
		this.safeDirections = new LinkedList<>();
		this.diesOnItemDiscard = false;
		this.diesOnEntry = false;
		this.deathMessage = null;
	}
	
	//maek room first, set requiredItem later 
	public RoomRequiredItem(String d, String dd, String w, String ws) {
		this(d, dd, w, ws, null);
	}
	
	public void setWarningDescription(String w) {
		this.warningDescription = w;
	}
	
	public void setShortWarningDescription(String ws) {
		this.warningShortDescription = ws;
	}
	
	public void setRequiredItem(Item requiredItem) {
		this.requiredItem = requiredItem;
	}
	
	
	public void playerDidDropRequiredItem() throws TerminateGameException {
		if(this.diesOnItemDiscard) {
			gameInterface.println(getPlayer().getUserName(), MessageType.PRIVATE, this.deathMessage);
			this.getPlayer().terminate();
		}
		else {
			this.getPlayer().lookAround();
		}
	}
	public void setPlayerDiesOnItemDiscard(boolean b) {
		this.diesOnItemDiscard = b;
	}

	public void setPlayerDiesOnEntry(boolean b) {
		this.diesOnEntry = b;
	}

	public boolean diesOnEntry() {
		return this.diesOnEntry;
	}

	public boolean shouldLoseForAction(Action a) {
		return !this.safeDirections.contains(a) && !this.getPlayer().hasItem(this.requiredItem);
	}

	public void setLoseMessage(String s) {
		this.deathMessage = s;
	}

	public String loseMessage() {
		return this.deathMessage;
	}

	public Item requiredItem() {
		return this.requiredItem;
	}

	public void setSafeDirection(Action direction) {
		this.safeDirections.add(direction);
	}

	@Override
	public String toString() {

		if(this.getPlayer().hasItem(this.requiredItem)) {
			return super.toString();
		}
		else {
			return this.warningDescription;
		}
	}
	
	@Override
	public String description() {
		if(this.getPlayer().hasItem(this.requiredItem)) {
			String s = this.roomWasVisited ? this.shortDescription : this.description + visibleItems();
			this.roomWasVisited = true;
			return s;
		}
		return "You cannot visit this room";
	}
}
