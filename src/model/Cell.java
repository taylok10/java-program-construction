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
    public Cell(int column, int row)
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
			refreshGraphics();
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
			refreshGraphics();
			return true;
		}
		refreshGraphics();
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
	
	public void refreshStyle() {
		List<String> toRemove = new ArrayList<String>();
		
		for (String s : getStyleClass()) {
			switch(s) {
			case "packing-station-cell":
				toRemove.add(s);
				break;
			case "charging-pod-cell":
				toRemove.add(s);
				break;
			case "storage-shelf-cell":
				toRemove.add(s);
				break;
			}
		}
		
		for (String remove : toRemove) {
			getStyleClass().remove(remove);
		}
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
				refreshStyle();
			}
			
			if(check.contains("Robot")) {
				this.setText("R");
			} else {
				this.setText("");
			}
			
		} else {
			refreshStyle();
			this.setText("");
		}
	}

	@Override
	public boolean isBlocked() {
		return getActorTypes().contains("Robot");
	}
}