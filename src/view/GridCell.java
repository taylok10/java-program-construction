package view;

import java.util.ArrayList;
import java.util.List;

import javafx.scene.control.Button;
import model.Actor;
import model.Cell;

public class GridCell extends Button {
	private Cell cell;
	
	public GridCell(Cell cell) {
		this.cell = cell;
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
		String check = cell.getActorTypes();
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
				this.setText(cell.getRobot().getUID().toUpperCase());
			} else {
				this.setText("");
			}
			
		} else {
			refreshStyle();
			this.setText("");
		}
	}
	
	public void addActor(Actor actor) {
		if(cell.addActor(actor)) {
			refreshGraphics();
		}
	}
	
	public void removeActor(String type) {
		if(cell.removeActor(type)) {
			refreshGraphics();
		}
	}
}
