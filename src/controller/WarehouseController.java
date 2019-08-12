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
	@FXML Slider sliderWidth, sliderHeight;
	@FXML Floor floor;
	@FXML Button buttonAddRobot, buttonRemoveRobot, buttonAddShelf, buttonRemoveShelf, buttonAddPacker, buttonRemovePacker;
	@FXML MenuItem menuItemImport;
	
	private Main mt;
	private static Cell userCell;
	private static int userX, userY;
	private WarehouseSimulation warehouseSimulation;
	
	public WarehouseController(Main mt) {
		this.mt = mt;
		userCell = null;
		userX = 0;
		userY = 0;
		warehouseSimulation = mt.getWarehouseSimulation();
	}
	
	@FXML public void initialize() {
		
		sliderWidth.valueProperty().addListener(new ChangeListener() {
			@Override
			public void changed(ObservableValue arg0, Object arg1, Object arg2) {
				floor.setWidth((int)sliderWidth.getValue());
			}
		});
		
		sliderHeight.valueProperty().addListener(new ChangeListener() {
			@Override
			public void changed(ObservableValue arg0, Object arg1, Object arg2) {
				floor.setHeight((int)sliderHeight.getValue());
			}
		});
		
		buttonAddRobot.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent e) {
				int x = userCell.getColumn();
				int y = userCell.getRow();
				floor.addActor(new ChargingPod(x,y),x,y);
				floor.addActor(new Robot(x,y),x,y);
			}
		});
		
		buttonRemoveRobot.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent e) {
				floor.removeActor("ChargingPod",userX,userY);
				floor.removeActor("Robot",userX, userY);
			}
		});
		
		buttonAddShelf.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent e) {
				int x = userCell.getColumn();
				int y = userCell.getRow();
				floor.addActor(new StorageShelf(x,y),x,y);
			}
		});
		
		buttonRemoveShelf.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent e) {
				floor.removeActor("StorageShelf",userX, userY);
			}
		});
		
		buttonAddPacker.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent e) {
				int x = userCell.getColumn();
				int y = userCell.getRow();
				floor.addActor(new PackingStation(x,y, warehouseSimulation.getOrders()),x,y);
			}
		});
		
		buttonRemovePacker.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent e) {
				floor.removeActor("PackingStation",userX, userY);
			}
		});
	}
	
	@FXML public void updateWidth() {
		System.out.println(sliderWidth.getValue());
	}
	
	@FXML public void updateHeight() {
		System.out.println(sliderHeight.getValue());
	}

	/**
	 * Sets the current selected Cell
	 * 
	 * @param nCell the new Cell to be set to userCell
	 */
	public static void setUserCell(Cell nCell) {
		userCell = nCell;
		userX = userCell.getColumn();
		userY = userCell.getRow();
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
