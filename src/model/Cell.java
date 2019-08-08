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
public class Cell extends Button {
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
		if (getActorTypes().isEmpty() || (actor.getClass().getSimpleName().equals("Robot")) && !getActorTypes().contains("Robot")) {
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
     * @return String description of Actors in cell
     */
	public String getActorsDesc() {
		String output = "Actors Present:\n";		
		for (Actor actor: actors) {
			output += actor.getUID() + "\n";
		}		
		return output;
	}
	
	/**
	 * Refreshes all Cell graphics when called
	 */
	public void refreshGraphics() {
		if (getActorTypes().contains("Robot")) {
//            this.setGraphic(new ImageView(new Image("Robot.png")));
			this.setText("R");
		}
		else {
//            this.setGraphic(new ImageView());
			this.setText("");
		}
		
		if (!actors.isEmpty()) {
			switch(actors.get(0).getClass().getSimpleName()) {
			case "PackingStation":
				this.getStyleClass().add("packing-station-cell");
				break;
			case "StorageShelf":
				this.getStyleClass().add("storage-shelf-cell");
				break;
			case "ChargingPod":
				this.getStyleClass().add("charging-pod-cell");
				break;				
			}			
		} else {
			this.getStyleClass().clear();
			this.getStyleClass().addAll(btnStyle);
		}
	}
}
