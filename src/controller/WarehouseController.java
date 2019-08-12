package controller;

import java.io.File;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextField;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import model.Cell;
import model.Floor;
import model.WarehouseSimulation;
import view.Main;

public class WarehouseController {
	@FXML Floor floor;
	@FXML Button btnTick, btnMultiTick, btnEndRun;
	@FXML TextField txtMultiTickValue, txtChargeSpeed, txtChargeCapacity;
	@FXML MenuItem menuItemImport, menuItemLog, menuItemFloor;
	
	private Main mt;
	private static Cell userCell;
	private WarehouseSimulation warehouseSimulation;
	private int chargeSpeed, capacity;
	
	public WarehouseController(Main mt) {
		this.chargeSpeed = 0;
		this.capacity = 0;
		this.mt = mt;
		userCell = null;
		warehouseSimulation = mt.getWarehouseSimulation();
	}
	
	@FXML public void initialize() {
		txtChargeCapacity.setText(String.valueOf(capacity));
		txtChargeSpeed.setText(String.valueOf(chargeSpeed));
	}

	public void setCapacity(int capacity) {
		this.capacity = capacity;
		txtChargeCapacity.setText(String.valueOf(capacity));
	}
	
	public void setChargeSpeed(int chargeSpeed) {
		this.chargeSpeed = chargeSpeed;
		txtChargeSpeed.setText(String.valueOf(chargeSpeed));
	}
	
	public void switchScene() {
		
	}
	
	public int getChargeSpeed() {
		return chargeSpeed;
	}
	
	public int getCapacity() {
		return capacity;
	}
	
	public void tick() {
		warehouseSimulation.tick();
	}
	
	public void multiTick() {
		warehouseSimulation.multiTick(Integer.parseInt(txtMultiTickValue.getText()));
	}
	
	public void endRun() {
		System.out.println("Run to the end of Simulation");
	}
	
	/**
	 * Sets the current selected Cell
	 * 
	 * @param nCell the new Cell to be set to userCell
	 */
	public static void setUserCell(Cell nCell) {
		userCell = nCell;
		System.out.println(userCell.getActorsDesc());
	}
	
	public void menuItemImport() {
		FileChooser fc = new FileChooser();
		fc.getExtensionFilters().addAll(new ExtensionFilter("Simulation Files","*.sim"));
		File selectedFile = fc.showOpenDialog(null);
		if (selectedFile != null) {
			mt.loadImport(selectedFile);
		} else {
			System.out.println("Invalid File Selected");
		}
	}
	
	public Floor getFloor() {
		return floor;
	}
	
	
}
