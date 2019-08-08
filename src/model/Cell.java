/**
 * 
 */
package model;

import java.util.ArrayList;
import java.util.List;

import javafx.collections.ObservableList;
import javafx.scene.control.Button;

/**
 * @author kelly.taylor
 *
 */
public class Cell extends Button implements GridLocation {
	private int row, column;
	private List<Actor> actors;
	private static ObservableList<String> btnStyle;
	
    /**
     * Represents a cell on on grid
     * @param row The row index
     * @param column The column index
     */
    public Cell(int row, int column)
    {
    	// List type up for debate
    	actors = new ArrayList<Actor>();
        this.row = row;
        this.column = column;
		btnStyle = this.getStyleClass();
    }
    
    /**
     * @param actor The actor to add to the cell
     * @return Added actor successfully
     */
    public boolean addActor(Actor actor) {
		if (getActorTypes().isEmpty() || (actor.getClass().getSimpleName().equals("Robot")) && (getActorTypes().equals("ChargingPod")) ) {
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
	
	/**
	 * Refreshes all Cell graphics when called
	 */
	public void refreshGraphics() {
		String check = getActorTypes();
		System.out.println(check);
		if (!check.equals("") || check.equals("Robot")) {
			if(check.contains("PackingStation")) {
				this.getStyleClass().add("packing-station-cell");
			} else if(check.contains("ChargingPod")) {
				this.getStyleClass().add("charging-pod-cell");
			} else if(check.contains("StorageShelf")) {
				this.getStyleClass().add("storage-shelf-cell");
			} else {
				this.getStyleClass().clear();
				this.getStyleClass().addAll(btnStyle);
			}
			
			if(check.contains("Robot")) {
				this.setText("R");
			} else {
				this.setText("");
			}
			
		} else {
			this.getStyleClass().clear();
			this.getStyleClass().addAll(btnStyle);
			this.setText("");
		}
	}
}