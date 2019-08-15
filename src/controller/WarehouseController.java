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
	
	@FXML public void initialize() {
		txtChargeCapacity.setText(String.valueOf(capacity));
		txtChargeSpeed.setText(String.valueOf(chargeSpeed));
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
		txtAreaRandomOrders.setText(txtAreaRandomOrders.getText() + nOrder.toString());		
	}
	
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
	
	public void menuItemExportLog() {
		FileChooser fc = new FileChooser();
		fc.getExtensionFilters().addAll(new ExtensionFilter("TXT files (*.txt)", "*.txt"));
		File selectedFile = fc.showOpenDialog(null);
        if (selectedFile != null) {
        	saveLog(txtAreaReport.getText(), selectedFile);
        }
	}
	
    private void saveLog(String log, File file) {
        try {
            PrintWriter pw;
            pw = new PrintWriter(file);
            pw.println(log);
            pw.close();
        } catch (IOException e) {
            //Handle issue
        }
    }
    
    public void helpDialog() {
    	//add help dialog
    }
	
	public Floor getFloor() {
		return floor;
	}
	
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
	
	private void resetReport() {
		txtAreaReport.setText("Perform Ticks for a report to be created...");
		txtAreaOrders.setText("Imported Orders show here...");
		txtAreaRandomOrders.setText("Random Orders show here...");
	}
	
}
