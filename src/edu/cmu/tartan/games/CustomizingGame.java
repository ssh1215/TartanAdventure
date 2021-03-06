package edu.cmu.tartan.games;

import java.util.ArrayList;

import edu.cmu.tartan.GameConfiguration;
import edu.cmu.tartan.GameContext;
import edu.cmu.tartan.Player;
import edu.cmu.tartan.goal.*;
import edu.cmu.tartan.room.*;

/**
 * Example game to explore a series of rooms. This is the configuraion discussed in the project description.
 * <p>
 * Project: LG Exec Ed SDET Program
 * 2018 h.h.tak
 * Versions:
 * 1.0 July 2018 - initial version
 */
public class CustomizingGame extends GameConfiguration {

	public CustomizingGame() {
		super("Customizing game made with XML");
	}

	private ArrayList<Room> rooms = new ArrayList<>();
	private ArrayList<GameGoal> goals = new ArrayList<>();


	public void addRoom(Room room) {
		rooms.add(room);
	}

	public Room getRoomIndex(int i) {
		return rooms.get(i);
	}

	public void addGoal(GameGoal goal) {
		goals.add(goal); 
	}

	public GameGoal getGameGoalIndex(int i) {
		return goals.get(i);
	}

	/**
	 * Configure the game
	 * @param game the Game object that will manage exectuion
	 * @throws InvalidGameException
	 */
	@Override
	public boolean configure(GameContext context) throws InvalidGameException {

		// Set the initial room
		Player player = new Player(rooms.get(0), Player.DEFAULT_USER_NAME);
		context.setPlayer(player);

		for (GameGoal gameGoal : goals) {
			gameGoal.setPlayer(player);
			context.addGoal(gameGoal);
		}
		context.setRooms(rooms);
		context.setGameDescription(makeGameDescription());


		if (!context.validate()) throw new InvalidGameException("Game improperly configured");

		return true;
	}

	public String makeGameDescription() {
		
		StringBuilder gameDescription = new StringBuilder();
		
		gameDescription.append("This is ");
		
		for (GameGoal gameGoal : goals) {

			if(gameGoal instanceof GameCollectGoal) {
				gameDescription.append("[Collect Item] ");
			}
			else if(gameGoal instanceof GameExploreGoal) {
				gameDescription.append("[Explore Room] ");
			}
			else if(gameGoal instanceof GamePointsGoal) {
				gameDescription.append("[Get Point] ");
			}
		}
		
		gameDescription.append("Game");

		return gameDescription.toString(); 
	}
}