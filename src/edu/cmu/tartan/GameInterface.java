package edu.cmu.tartan;

import java.util.Scanner;
import java.util.logging.*;

public class GameInterface {

	class GameInterfaceFormatter extends Formatter {
		 
		@Override
		public String format(LogRecord record) {
			if (record.getLevel() != Level.FINEST)
				return "";

			return record.getMessage();
		}

	}
	
	/**
	 * Logger for log message
	 */
	private static final Logger logger = Logger.getGlobal();
	
	/**
	 * Static variable for singleton
	 */
	private static GameInterface instance = null;

    /**
     * Reads input from the command line.
     */
    private Scanner scanner;

	public GameInterface() {
		super();
        logger.setUseParentHandlers(false);
        logger.setLevel(Level.ALL);

        ConsoleHandler logHandler = new ConsoleHandler();
        logHandler.setLevel(Level.INFO);
        logger.addHandler(logHandler);

        ConsoleHandler messageHandler = new ConsoleHandler();
        messageHandler.setFormatter(new GameInterfaceFormatter());
        messageHandler.setLevel(Level.ALL);
        logger.addHandler(messageHandler);
        
        scanner = new Scanner(System.in);
	}
	
	public static GameInterface getInterface() {
		if (instance == null) {
			instance = new GameInterface();
		}
		
		return instance;
	}
	
	public void resetInterface() {
		scanner = new Scanner(System.in);
	}
	
	// For log message
	public void severe(String msg) {
		logger.severe(msg);
	}

	public void warning(String msg) {
		logger.warning(msg);
	}

	public void info(String msg) {
		logger.info(msg);
	}

	// For game message
	public void print(String msg) {
		logger.finest(msg);
	}
	
	public void println(String msg) {
		logger.finest(msg);
		logger.finest("\n");
	}
	
	public String getCommand() {
		return scanner.nextLine();
	}
}
