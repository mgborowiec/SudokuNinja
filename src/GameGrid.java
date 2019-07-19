import java.util.Objects;

public class GameGrid {
	
	private final Field[][] grid;
	public static final int GRID_DIM = 9;
    public static final int SUBGRID_DIM = GRID_DIM / 3;
    public static final int MAX_VAL = 9;
    public static final int MIN_VAL = 1;
    public static final int EMPTY_VAL = 0;
	
	public GameGrid(int[][] newGrid) {
		Objects.requireNonNull(newGrid);
		grid = initialiseGrid(newGrid);
	}
	
	public GameGrid(String loadedGrid) {
		this(IOUtils.loadFromFile(loadedGrid));
	}

	/**
	 * Create a Sudoku by creating a deep copy of the given one.
	 * @param game Game grid data to be copied
	 */
	public GameGrid(GameGrid game) {
		Objects.requireNonNull(game);
		grid = new Field[GRID_DIM][GRID_DIM];

		for(int row = 0; row < GRID_DIM; row++) {
			for(int column = 0; column < GRID_DIM; column++) {
				grid[row][column] = new Field(
						game.grid[row][column].getValue(),
						game.grid[row][column].getInitial()
				);
			}
		}
	}

	/**
	 * Initialise the game grid with given data
	 * @param grid Sudoku data to be used
	 */
	private Field[][] initialiseGrid(int[][] grid) {
		Field[][] initialised = new Field[GRID_DIM][GRID_DIM];
		for (int row = 0; row < GRID_DIM; row++) {
			for (int column = 0; column < GRID_DIM; column++) {
				if (grid[row][column] == EMPTY_VAL) initialised[row][column] = new Field();
				else initialised[row][column] = new Field(grid[row][column], true);
			}
		}
		return initialised;
	}

	/**
	 * Get the value of the Sudoku field
	 * @param column x-coordinate of the field
	 * @param row y-coordinate of the field
	 * @return the field value
	 */
	public int getField(int column, int row) {
		if (column >= GRID_DIM || column < 0 || row >= GRID_DIM || row < 0) {
			throw new IllegalArgumentException("Invalid argument for column or row");
		}
		else return grid[row][column].getValue();
	}

	/**
	 * Set the Sudoku field to the given value
	 * @param column x-coordinate of the field
	 * @param row y-coordinate of the field
	 * @param value Sudoku value to be set for the field
	 * @return true if successful, false otherwise
	 */
	public boolean setField(int column, int row, int value) {
		if (column >= GRID_DIM || column < 0 || row >=GRID_DIM || row < 0) {
			throw new IllegalArgumentException("Invalid argument for column or row");
		}
		if (value < MIN_VAL || value > MAX_VAL)
			throw new IllegalArgumentException("Given value invalid: " + value);
		
		if(!isInitial(column, row) && isValid(column, row, value)) {
			grid[row][column].setValue(value);
			return true;
		}
		else return false;
	}
	
	/**
	 * Check if the given number appears in the given row
	 * @param column x-coordinate of the field
	 * @param row y-coordinate of the field
	 * @param value Sudoku field value
	 * @return true if allowed, false otherwise
	 */
    private boolean checkRow(int column, int row, int value) {
    	boolean isAllowedRow = true; 
    	
    	for (int i = 0; i < GRID_DIM; i++) {
    		if (i != column && grid[row][i].getValue() == value) {
    			isAllowedRow = false;
    			break;
    		}
    	}
    	return isAllowedRow;
    }

	/**
	 * Check if the given number appears in the given column
	 * @param column x-coordinate of the field
	 * @param row y-coordinate of the field
	 * @param value Sudoku field value
	 * @return true if allowed, false otherwise
	 */
    private boolean checkColumn(int column, int row, int value) {
    	boolean isAllowedColumn = true; 
    	
    	for (int i = 0; i < GRID_DIM; i++) {
    		if (i != row && grid[i][column].getValue() == value) {
    			isAllowedColumn = false;
    			break;
    		}
    	}
    	return isAllowedColumn;
    }

	/**
	 * Check if the given number appears in the given subgrid
	 * @param column x-coordinate of the field
	 * @param row y-coordinate of the field
	 * @param value Sudoku field value
	 * @return true if allowed, false otherwise
	 */
    private boolean checkSubGrid(int column, int row, int value) {
    	boolean isAllowedSubGrid = true;
    	
    	for (int i = (row - row%SUBGRID_DIM); i < (row - row%SUBGRID_DIM + SUBGRID_DIM); i++) {
    	     for (int j = (column - column%SUBGRID_DIM); j < (column - column%SUBGRID_DIM + SUBGRID_DIM); j++) {
    	         if (i != row && j != column && grid[i][j].getValue() == value) {
    	             isAllowedSubGrid = false;
    	             break;
    	         }
    	     }
    	}
    	return isAllowedSubGrid;
    }

	/**
	 * Check if the entry is valid for the Sudoku
	 * @param column x-coordinate of the field
	 * @param row y-coordinate of the field
	 * @param value Sudoku field value
	 * @return true if allowed, false otherwise
	 */
    public boolean isValid(int column, int row, int value) {
    	boolean valid = false;
    	if (checkRow(column,row,value) && checkColumn(column,row,value) && checkSubGrid(column,row,value)) {
    		valid = true;
    	}
    	return valid;
    }

	/**
	 * Check if the field at the given coordinates was set from the beginning
	 * @param column x-coordinate of the field
	 * @param row y-coordinate of the field
	 * @return true if this field was set from the beginning of the game, false otherwise
	 */
	public boolean isInitial(int column, int row) {
		if (column < 0 || column >= GRID_DIM || row < 0 || row >= GRID_DIM) {
			throw new IllegalArgumentException("Invalid argument for column or row");
		}
		return grid[row][column].getInitial();
	}

	/**
	 * Print the Sudoku grid
	 */
    public String toString() {
    	StringBuilder printGrid = new StringBuilder();
		
    	for (int row = 0; row < GRID_DIM; row++) {
    		for (int column = 0; column < GRID_DIM; column++) {

    			printGrid.append(getField(column, row));
    			printGrid.append(" ");
    			if (column % SUBGRID_DIM == 2) {
    				printGrid.append(" ");
    			}
    		}
    			if (row % SUBGRID_DIM == 2) {
    				printGrid.append("\n");
    			}
    			printGrid.append("\n");
    	}
    	return printGrid.toString();
    }
 
	 /**
	  * Clear a Sudoku field
	  * @param column x-coordinate of the field
	  * @param row y-coordinate of the field
	  */
 	public void clearField(int column, int row) {
		if (column >= GRID_DIM || column < 0 || row >= GRID_DIM || row < 0) {
			throw new IllegalArgumentException("Invalid argument for column or row");
		}

    	grid[row][column].setValue(EMPTY_VAL);
    }
}

