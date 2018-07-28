package edu.cmu.tartan.room;

import edu.cmu.tartan.item.Item;

import java.util.ArrayList;

public class RoomExcavatable extends Room {
	/**
	 * serial Version ID
	 */
	private static final long serialVersionUID = 1L;
	protected String digMessage;
	protected boolean wasDugUp;
	protected ArrayList<Item> revealableItems;

	public RoomExcavatable(String description, String shortDescription, String digMessage) {
		super(description, shortDescription);
		this.digMessage = digMessage;
		this.wasDugUp = false;
		this.revealableItems = new ArrayList<>();
	}

    /**
     * Set the item(s) to be excavated
     * @param items the list of excavatable items
     */
	public void setRevealableItems(ArrayList<Item> items) {
		if(items != null) {
			this.revealableItems = items;
		}
	}
	
    /**
     * Perform the excavation
     */
	public boolean dig() {
		if(this.getPlayer().hasItem(Item.getInstance("shovel", this.getPlayer().getUserName()))) {
			gameInterface.println(digMessage);
			this.wasDugUp = true;
			this.getItems().addAll(this.revealableItems);
			return true;
		}
		else {
			gameInterface.println("You do not have an item you can use to dig.");
			return false;
		}
	}
}
