package controller;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Slider;
import model.Cell;
import model.ChargingPod;
import model.Floor;
import model.PackingStation;
import model.Robot;
import model.StorageShelf;

public class WarehouseController {
	@FXML Slider sliderWidth, sliderHeight;
	@FXML Floor floor;
	@FXML Button buttonAddRobot, buttonRemoveRobot, buttonAddShelf, buttonRemoveShelf, buttonAddPacker, buttonRemovePacker;
	
	private static Cell userCell;
	private static int userX, userY;
	
	public WarehouseController() {
		userCell = null;
		userX = 0;
		userY = 0;
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
				userCell.refreshGraphics();
			}
		});
		
		buttonRemoveRobot.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent e) {
				floor.removeActor("ChargingPod",userX,userY);
				floor.removeActor("Robot",userX, userY);
				userCell.refreshGraphics();
			}
		});
		
		buttonAddShelf.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent e) {
				int x = userCell.getColumn();
				int y = userCell.getRow();
				floor.addActor(new StorageShelf(x,y),x,y);
				userCell.refreshGraphics();
			}
		});
		
		buttonRemoveShelf.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent e) {
				floor.removeActor("StorageShelf",userX, userY);
				userCell.refreshGraphics();
			}
		});
		
		buttonAddPacker.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent e) {
				int x = userCell.getColumn();
				int y = userCell.getRow();
				floor.addActor(new PackingStation(x,y),x,y);
				userCell.refreshGraphics();
			}
		});
		
		buttonRemovePacker.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent e) {
				floor.removeActor("PackingStation",userX, userY);
				userCell.refreshGraphics();
			}
		});
	}
	
	@FXML public void updateWidth() {
		//TESTING Check update is correct
		System.out.println(sliderWidth.getValue());
	}
	
	@FXML public void updateHeight() {
		//TESTING Check update is correct
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
	
	
}
