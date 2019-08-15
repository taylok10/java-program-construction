/**
 * 
 */
package model;

import java.util.ArrayList;
import java.util.List;

import javafx.collections.ObservableList;
import javafx.scene.control.Button;
import view.GridCell;

/**
 * @author kelly.taylor
 *
 */
public class Cell implements GridLocation {
	private int row, column;
	private List<Actor> actors;
	
    /**
     * Represents a cell on on grid
     * @param row The row index
     * @param column The column index
     */
    public Cell(int column, int row)
    {
    	// List type up for debate
    	actors = new ArrayList<Actor>();
        this.row = row;
        this.column = column;
    }
    
    /**
     * @param actor The actor to add to the cell
     * @return Added actor successfully
     */
    public boolean addActor(Actor actor) {
		if (getActorTypes().isEmpty() || (actor.getClass().getSimpleName().equals("Robot") && !getActorTypes().equals("Robot"))) {
			actors.add(actor);
			return true;
		}
		System.out.println("ERROR - Cannot add " + actor.getClass().getSimpleName() + " to cell " + column + "," + row);
		return false;
    }
    
	/**
	 * Removes an Actor(based on type) from the Cell
	 * 
	 * @param type the type of actor to remove from the Cell
	 * @return boolean value indicating success or failure 
	 */
	public boolean removeActor(String type) {
		Actor toRemove = null;
		for (Actor actor : actors) {
			if (actor.getClass().getSimpleName().equals(type)) {
				toRemove = actor;
			}
		}
		if (toRemove != null) {
			actors.remove(toRemove);
			return true;
		}
		return false;
	}
	
	public boolean removeActor(Actor actor) {
		return removeActor(actor.getClass().getSimpleName());
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
	
    /**
     * @return The actors in this Cell
     */
	public List<Actor> getActors() {
		return actors;
	}
	
    /**
     * @return String list of Actors in Cell
     */
	public String getActorTypes() {
		String output = "";
		
		for (Actor actor: actors) {
			output += actor.getClass().getSimpleName();
		}
		return output;
	}
	
    /**
     * Temporary test class for Cell checks
     * 
     * @return String description of Actors in cell
     */
	public String getActorsDesc() {
		String output = "";
		if (!actors.isEmpty()) {
			output = "Actors Present:\n";		
			for (Actor actor: actors) {
				output += actor.getUID() + "\n";
			}
		}
		return output;
	}
	
	public Robot getRobot() {
		for (Actor actor : actors) {
			if (actor.getClass().getSimpleName().equals("Robot")) {
				return (Robot)actor;
			}
		}
		return null;
	}
	
	@Override
	public boolean isBlocked() {
		return getActorTypes().contains("Robot");
	}
	
	@Override
    public boolean equals(Object o) { 
		if(o instanceof Cell) {
			Cell cell = (Cell)o;
			return cell.getRow() == getRow() && cell.getColumn() == getColumn();
		}
		return false;
    } 
}