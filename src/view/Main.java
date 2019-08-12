package view;

import java.io.File;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;

import controller.WarehouseController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import model.DataRegistry;
import model.Floor;
import model.Order;
import model.WarehouseSimulation;

public class Main extends Application {
	public static Stage stage;
	public static Scene scene;
	private static WarehouseSimulation ws;
	private static WarehouseController wc;

	private static Queue<Order> orders;
	private static Map<Order, Integer> orderStats;
	private static DataRegistry dr;
	
	@Override
	public void start(Stage primaryStage) {
		try {
			ws = new WarehouseSimulation();
			wc = new WarehouseController(this);
			final FXMLLoader loader = new FXMLLoader();
			loader.setController(wc);
			loader.setLocation(getClass().getResource("WarehouseSimulation.fxml"));
			final Parent root = loader.load();

			final Scene scene = new Scene(root);
			scene.getStylesheets().add("stylesheet.css");
			primaryStage.setTitle("Warehouse");
			primaryStage.setScene(scene);
			primaryStage.setResizable(false);
			primaryStage.show();
			
			ws.setFloor(wc.getFloor());
//			dr = new DataRegistry(this);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		orders = new LinkedList<Order>() ;
		launch(args);
	}
	
	public void loadImport(File file) {
//		dr.loadImport(file);
		ws.readSimulation(file);
	}
	
	public WarehouseController getWarehouseController() {
		return wc;
	}
	
	public Queue<Order> getOrders(){
		return orders;
	}
	
	public void addOrder(Order order) {
		orders.add(order);
		System.out.println(orders.size());
	}
	
	public WarehouseSimulation getWarehouseSimulation() {
		return ws;
	}
	
	
}
