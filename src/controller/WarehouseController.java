package controller;

import java.io.File;

import javafx.beans.NamedArg;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
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
	@FXML TextField txtMultiTickValue, txtChargeSpeed, txtChargeCapacity;
	@FXML MenuItem menuItemImport, menuItemLog, menuItemFloor;
	@FXML Pane paneFloor, paneReport;
	@FXML TextArea txtAreaReport;
	
	private Main mt;
	private static Cell userCell;
	private WarehouseSimulation warehouseSimulation;
	private int chargeSpeed, capacity;
	private static boolean runnable = false;
	
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
		setRunnable(runnable);
	}

	public void setCapacity(int capacity) {
		this.capacity = capacity;
		txtChargeCapacity.setText(String.valueOf(capacity));
	}
	
	public void toggleRunnable() {
		runnable = !runnable;
	}
	
	public void setChargeSpeed(int chargeSpeed) {
		this.chargeSpeed = chargeSpeed;
		txtChargeSpeed.setText(String.valueOf(chargeSpeed));
	}
	
	public void switchSceneLog() {
		System.out.println("Switching to Log View");
		paneReport.setVisible(true);
		paneFloor.setVisible(false);
	}
	
	public void switchSceneFloor() {
		System.out.println("Switching to Warehouse View");
		paneReport.setVisible(false);
		paneFloor.setVisible(true);
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
	
	public void setRunnable(boolean runnable) {
		if (runnable) {
			btnTick.setDisable(false);
			btnMultiTick.setDisable(false);
			btnEndRun.setDisable(false);
			txtMultiTickValue.setDisable(false);
		} else {
			btnTick.setDisable(true);
			btnMultiTick.setDisable(true);
			btnEndRun.setDisable(true);
			txtMultiTickValue.setDisable(true);
		}
	}
	
	public void menuItemImport() {
		FileChooser fc = new FileChooser();
		fc.getExtensionFilters().addAll(new ExtensionFilter("Simulation Files","*.sim"));
		File selectedFile = fc.showOpenDialog(null);
		if (selectedFile != null) {
			mt.loadImport(selectedFile);
		} else {
			System.out.println("ERROR - Invalid File Selected");
		}
	}
	
	public Floor getFloor() {
		return floor;
	}
	
	public void resetIds() {
		ChargingPod.resetIdCount();
		PackingStation.resetIdCount();
		StorageShelf.resetIdCount();
//		Robot.resetIdCount();
	}
	
	public boolean getRunnable() {
		return runnable;
	}
	
	public void updateReport() {
		txtAreaReport.setText(warehouseSimulation.getReport());
	}
	
	public void setReport(String report) {
		txtAreaReport.setText(report);
	}
	
}
