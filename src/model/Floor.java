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
	private ArrayList<PackingStation> packingStations;
	private ArrayList<StorageShelf> storageShelves;
	private ArrayList<ChargingPod> chargingPods;
	private PathFinder<GridLocation> pathFinder;
	
	public Floor(@NamedArg("width") int width, @NamedArg("height") int height) {
		super();
		robots = new ArrayList<Robot>();
		packingStations = new ArrayList<PackingStation>();
		storageShelves = new ArrayList<StorageShelf>();
		chargingPods = new ArrayList<ChargingPod>();
		this.width = width;
		this.height = height;
		userCell = null;
		cells = new Cell[width][height];
		gCells = new GridCell[width][height];
		resizeGrid();
		// To be determined in the future
		this.setPrefSize(400, 400);
		pathFinder = new BFS<GridLocation>(cells);
	}
	
	public PathFinder<GridLocation> getPathFinder() {
		return pathFinder;
	}

	/**
	 * Resizes the grid of Cells(Buttons) based on set width and height variables
	 */
	private void resizeGrid() {
        this.getChildren().removeAll(getGridCellList());
        cells = new Cell[width][height];
        gCells = new GridCell[width][height];
        robots.clear();
		
		for(int y = 0; y < width; y++) { // Row
	        for(int x = 0; x < height; x++) { // Column
	        	Cell nCell = new Cell(x,y);
	        	GridCell gCell = new GridCell(nCell);
	        	gCell.setPrefSize(400/width, 400/height);
	        	gCell.setOnMouseClicked(e -> {
	        		WarehouseController.setUserCell(nCell);
	        		System.out.println(nCell.getColumn() + "," + nCell.getRow());
	            });
	        	this.add(gCell,x,y);
	        	cells[y][x] = nCell;
	        	gCells[y][x] = gCell;
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
	public boolean addActor(Actor actor, int x, int y) {
		if (x < width && y < height) {
			GridCell gCell = gCells[y][x];
			gCell.addActor(actor);
			switch (actor.getClass().getSimpleName()) {
			case "Robot":
				robots.add((Robot)actor);
				break;
			case "ChargingPod":
				chargingPods.add((ChargingPod)actor);
				break;
			case "StorageShelf":
				storageShelves.add((StorageShelf)actor);
				break;
			case "PackingStation":
				packingStations.add((PackingStation)actor);
				break;
			}
			return true;
		} else {
			System.out.println("ERROR - " + actor.getClass().getSimpleName() + ": " + actor.getUID() + " cannot be placed outside of the grid");
			return false;
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
		GridCell gCell = gCells[y][x];
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
	
	public Cell getCell(int x, int y) {
		return cells[y][x];
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
	
	public ArrayList<PackingStation> getPackingStations(){
		return packingStations;
	}
	
	public ArrayList<StorageShelf> getStorageShelves(){
		return storageShelves;
	}
	
	public ArrayList<ChargingPod> getChargingPods(){
		return chargingPods;
	}

	public void refreshGraphics() {
		for (GridCell gCell : this.getGridCellList()) {
			gCell.refreshGraphics();
		}
	}
}
