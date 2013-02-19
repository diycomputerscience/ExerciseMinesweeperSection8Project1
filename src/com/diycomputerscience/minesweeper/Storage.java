package com.diycomputerscience.minesweeper;

public class Storage {

	private static final String FILENAME = "/path/to/jminesweeper.db";
	
	public static void save(Board boardToSave) throws PersistenceException {
		// TODO: Implement the save method so that the state of the board is saved to the file specified by the variable FILENAME		
	}
	
	public static Board load() throws PersistenceException {
		// TODO: Implement the load method to read the saved file and return corresponding Board object initialized from the data in the file
		return null;
	}
	
}