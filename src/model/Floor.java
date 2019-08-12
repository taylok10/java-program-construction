package model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import controller.WarehouseController;
import javafx.beans.NamedArg;
import javafx.scene.layout.GridPane;
import view.GridCell;

/**
 * The Floor GridPane that contains Cells and Actors
 * 
 * @author Joseph.Sheargold
 */
public class Floor extends GridPane {

	private int width, height;
	private Cell[][] cells;
	private GridCell[][] gCells;
	private static Cell userCell;
	private ArrayList<Robot> robots;
	
	public Floor(@NamedArg("width") int width, @NamedArg("height") int height) {
		super();
		robots = new ArrayList<Robot>();
		this.width = width;
		this.height = height;
		userCell = null;
		cells = new Cell[width][height];
		gCells = new GridCell[width][height];
		resizeGrid();
		// To be determined in the future
		this.setPrefSize(400, 400);
	}
	

	/**
	 * Resizes the grid of Cells(Buttons) based on set width and height variables
	 */
	private void resizeGrid() {
        this.getChildren().removeAll(getGridCellList());
        cells = new Cell[width][height];
        gCells = new GridCell[width][height];
        robots.clear();
		
		for(int i = 0; i < width; i++) {
	        for(int j = 0; j < height; j++) {
	        	Cell nCell = new Cell(i,j);
	        	GridCell gCell = new GridCell(nCell);
	        	gCell.setPrefSize(400/width, 400/height);
	        	gCell.setOnMouseClicked(e -> {
	        		WarehouseController.setUserCell(nCell);
	        		System.out.println(nCell.getColumn() + "," + nCell.getRow());
	            });
	        	this.add(gCell,i,j);
	        	cells[i][j] = nCell;
	        	gCells[i][j] = gCell;
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
		GridCell gCell = gCells[x][y];
		gCell.addActor(actor);
		if (actor.getClass().getSimpleName().equals("Robot")) {
			robots.add((Robot)actor);
			System.out.println("Added Robot " + actor.getUID() + " to robots in Floor");
		}
	}
	
	/**
	 * Removes an Actor(based on type) from a specified Cell
	 * 
	 * @param type the type of actor to remove from a Cell
	 * @param x the x coordinate of the Cell 
	 * @param y the y coordinate of the Cell 
	 */
	public void removeActor(String type, int x, int y) {
		GridCell gCell = gCells[x][y];
		gCell.removeActor(type);
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
	
	public List<GridCell> getGridCellList(){
		List<GridCell> cellList = Arrays.stream(gCells).flatMap(Arrays::stream).collect(Collectors.toList());
		return cellList;
	}
	
	public int getRows() {
		return height;
	}
	
	public int getColumns() {
		return width;
	}
	
	public ArrayList<Robot> getRobots(){
		return robots;
	}
}
