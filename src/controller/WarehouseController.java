package controller;

import java.io.File;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.MenuItem;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import model.Cell;
import model.ChargingPod;
import model.Floor;
import model.PackingStation;
import model.Robot;
import model.StorageShelf;
import model.WarehouseSimulation;
import view.Main;

public class WarehouseController {
	@FXML Floor floor;
	@FXML Button btnTick, btnMultiTick, btnEndRun;
	@FXML TextField txtMultiTickValue;
	@FXML MenuItem menuItemImport;
	
	private Main mt;
	private static Cell userCell;
	private WarehouseSimulation warehouseSimulation;
	
	public WarehouseController(Main mt) {
		this.mt = mt;
		userCell = null;
		warehouseSimulation = mt.getWarehouseSimulation();
	}
	
	@FXML public void initialize() {

	}

	public void tick() {
		System.out.println("Tick");
	}
	
	public void multiTick() {
		System.out.println("Multiple Ticks, Amount = " + txtMultiTickValue.getText());
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
