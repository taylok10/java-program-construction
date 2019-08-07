/**
 * 
 */
package model;

/**
 * @author kelly.taylor
 *
 */
public class Cell {
	private int row;
	private int column;
	
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
