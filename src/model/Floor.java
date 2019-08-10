package model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import controller.WarehouseController;
import javafx.beans.NamedArg;
import javafx.geometry.Pos;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;

/**
 * The Floor GridPane that contains Cells and Actors
 * 
 * @author Joseph.Sheargold
 */
public class Floor extends GridPane {

	private int width, height;
	private Cell[][] cells;
	private static Cell userCell;
	
	public Floor(@NamedArg("width") int width, @NamedArg("height") int height) {
		super();
		this.width = width;
		this.height = height;
		userCell = null;
		cells = new Cell[width][height];
		resizeGrid();
		// To be determined in the future
		this.setPrefSize(400, 400);
	}
	

	/**
	 * Resizes the grid of Cells(Buttons) based on set width and height variables
	 */
	private void resizeGrid() {
        this.getChildren().removeAll(getCellList());
        cells = new Cell[width][height];
		
		for(int i = 0; i < width; i++) {
	        for(int j = 0; j < height; j++) {
	        	Cell nCell = new Cell(i,j);
				// C1.1 
	        	nCell.setPrefSize(400/width, 400/height);
	        	nCell.setOnMouseClicked(e -> {
	        		WarehouseController.setUserCell(nCell);
	        		System.out.println(nCell.getColumn() + "," + nCell.getRow());
	            });
	        	this.add(nCell,i,j);
	        	cells[i][j] = nCell;
	        }
        }
		WarehouseController.setUserCell(cells[0][0]);
	}
	
	/**
	 * Removes an Actor to a specified Cell
	 * 
	 * @param actor an actor to be added into a Cell 
	 * @param x the x coordinate of the Cell 
	 * @param y the y coordinate of the Cell 
	 */
	public void addActor(Actor actor, int x, int y) {
		Cell cell = cells[x][y];
		System.out.println(cell.getColumn() + "," + cell.getRow());
		cell.addActor(actor);
		System.out.println(cell.getActorsDesc());
	}
	
	/**
	 * Removes an Actor(based on type) from a specified Cell
	 * 
	 * @param type the type of actor to remove from a Cell
	 * @param x the x coordinate of the Cell 
	 * @param y the y coordinate of the Cell 
	 */
	public void removeActor(String type, int x, int y) {
		Cell cell = cells[x][y];
		System.out.println(cell.getColumn() + "," + cell.getRow());
		cell.removeActor(type);
		System.out.println(cell.getActorsDesc());
	}
	
	/**
	 * Sets width variable for resizing
	 * 
	 * @param width an int representing column count 
	 */
	public void setWidth(int width) {
		this.width = width;
		resizeGrid();
	}
	
	/**
	 * Sets height variable for resizing
	 * 
	 * @param height an int representing row count 
	 */
	public void setHeight(int height) {
		this.height = height;
		resizeGrid();
	}

	public static Cell getUserCell() {
		return userCell;
	}
	
	public List<Cell> getCellList(){
		List<Cell> cellList = Arrays.stream(cells).flatMap(Arrays::stream).collect(Collectors.toList());
		return cellList;
	}
	
}
