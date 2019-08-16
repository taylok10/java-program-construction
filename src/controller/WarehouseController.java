package controller;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Random;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import model.Cell;
import model.ChargingPod;
import model.Floor;
import model.Order;
import model.PackingStation;
import model.Robot;
import model.StorageShelf;
import model.WarehouseSimulation;
import view.Main;

/**
 * @author Joseph.Sheargold
 *
 */
public class WarehouseController {
	@FXML Floor floor;
	@FXML Button btnTick, btnMultiTick, btnEndRun, btnRandomOrder;
	@FXML TextField txtMultiTickValue, txtChargeSpeed, txtChargeCapacity;
	@FXML Menu menuHelp;
	@FXML MenuItem menuItemImport, menuItemExportLog,menuItemLog, menuItemFloor;
	@FXML Pane paneFloor, paneReport;
	@FXML TextArea txtAreaReport, txtAreaOrders, txtAreaRandomOrders;
	
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
	
	/**
	 * Initialise the FXML components with values / filters
	 */
	@FXML public void initialize() {
		txtChargeCapacity.setText(String.valueOf(capacity));
		txtChargeSpeed.setText(String.valueOf(chargeSpeed));
		/* 
		 * Below prevents non-numeric characters from being entered into the multi-tick value
		 * 	TextField as a handler for the multi-tick methods
		 */
		txtMultiTickValue.textProperty().addListener(new ChangeListener<String>() {
		    @Override
		    public void changed(ObservableValue<? extends String> observable, String oldValue, 
		        String newValue) {
		        if (!newValue.matches("\\d*")) {
		        	txtMultiTickValue.setText(newValue.replaceAll("[^\\d]", ""));
		        }
		    }
		});
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
	
	/**
	 * Switched the view from current to Log
	 */
	public void switchSceneLog() {
		System.out.println("Switching to Log View");
		paneReport.setVisible(true);
		paneFloor.setVisible(false);
	}
	
	/**
	 * Switched the view from current to Warehouse Floor
	 */
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
		if (txtMultiTickValue.getText() == null) {
			txtMultiTickValue.setText("0");
		}
		warehouseSimulation.multiTick(Integer.parseInt(txtMultiTickValue.getText()));
		
	}
	
	public void endRun() {
		warehouseSimulation.multiTick(9999);
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
	
	/**
	 * Generates a random new Order based on existing Storage Shelves
	 * 	and adds it to the Order Manager
	 */
	public void generateRandomOrder() {
		Random rand = new Random(); 
		int packingTicks = rand.nextInt(9)+1;
		int amount = rand.nextInt(floor.getStorageShelves().size())+1;
		int i = 0;
		StorageShelf[] shelves = new StorageShelf[amount];
		while (shelves[amount-1] == null) {
			int value = rand.nextInt(10);
			int check = rand.nextInt(10);
			for (StorageShelf ss : floor.getStorageShelves()) {
				if (value == check && i < amount) {
					shelves[i] = ss;
					i++;
				}
			}
		}
		Order nOrder = new Order(packingTicks,shelves);
		WarehouseSimulation.orders.addOrder(nOrder);
		if (txtAreaRandomOrders.getText().equals("Random Orders show here...")) {
			txtAreaRandomOrders.setText("");
		}
		txtAreaRandomOrders.setText(txtAreaRandomOrders.getText() + nOrder.toString());		
	}
	
	/**
	 * Toggles all acting buttons based on boolean runnable
	 * 
	 * @param runnable a boolean value for whether the simulation can run
	 */
	public void setRunnable(boolean runnable) {
		if (runnable) {
			btnTick.setDisable(false);
			btnMultiTick.setDisable(false);
			btnEndRun.setDisable(false);
			txtMultiTickValue.setDisable(false);
			btnRandomOrder.setDisable(false);
		} else {
			btnTick.setDisable(true);
			btnMultiTick.setDisable(true);
			btnEndRun.setDisable(true);
			txtMultiTickValue.setDisable(true);
			btnRandomOrder.setDisable(true);
		}
	}
	
	/**
	 * Opens a file chooser for .sim files to be imported
	 */
	@SuppressWarnings("static-access")
	public void menuItemImport() {
		resetReport();
		FileChooser fc = new FileChooser();
		fc.getExtensionFilters().addAll(new ExtensionFilter("Simulation Files","*.sim"));
		File selectedFile = fc.showOpenDialog(null);
		if (selectedFile != null) {
			mt.loadImport(selectedFile);
		} else {
			System.out.println("ERROR - Invalid File Selected");
		}
		txtAreaOrders.setText(warehouseSimulation.orders.toString());		
	}
	
	/**
	 * Opens a file chooser for .txt logs to be exported
	 */
	public void menuItemExportLog() {
		FileChooser fc = new FileChooser();
		fc.getExtensionFilters().addAll(new ExtensionFilter("TXT files (*.txt)", "*.txt"));
		File selectedFile = fc.showOpenDialog(null);
        if (selectedFile != null) {
        	saveLog(txtAreaReport.getText(), selectedFile);
        }
	}
	
	/**
	 * Saves the current log report into a specified file
	 * 
	 * @param log a String containing the log data
	 * @param file a File to save the log data to
	 */
    private void saveLog(String log, File file) {
        try {
            PrintWriter pw;
            pw = new PrintWriter(file);
            pw.println(log);
            pw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    public void helpDialog() {
    	//add help dialog
    }
	
	public Floor getFloor() {
		return floor;
	}
	
	/**
	 * Resets the id counters for all Actors upon import of new file
	 */
	public void resetIds() {
		ChargingPod.resetIdCount();
		PackingStation.resetIdCount();
		StorageShelf.resetIdCount();
		Robot.resetIdCount();
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
	
	/**
	 * Resets the Text Areas on the log pane
	 */
	private void resetReport() {
		txtAreaReport.setText("Perform Ticks for a report to be created...");
		txtAreaOrders.setText("Imported Orders show here...");
		txtAreaRandomOrders.setText("Random Orders show here...");
	}
	
}
