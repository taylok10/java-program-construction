/**
 * 
 */
package model;

import java.util.List;

/**
 * @author kelly.taylor
 *
 */
public class Cell {
	private int row;
	private int column;
	private List<Actor> actors;
	
    /**
     * Represents a cell on on grid
     * @param row The row index
     * @param column The column index
     */
    public Cell(int row, int column)
    {
        this.row = row;
        this.column = column;
    }
    
    /**
     * @param actor The actor to add to the cell
     * @return Added actor successfully
     */
    public boolean addActor(Actor actor) {
    	return actors.add(actor);
    }
	
    /**
     * @return The cell column
     */
	public int getColumn() {
		return column;
	}
	
    /**
     * @return The cell row
     */
	public int getRow() {
		return row;
	}
}
