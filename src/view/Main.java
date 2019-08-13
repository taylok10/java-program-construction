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
import model.Floor;
import model.Order;
import model.WarehouseSimulation;

public class Main extends Application {
	public static Stage stage;
	public static Scene scene;
	private static WarehouseSimulation ws;
	private static WarehouseController wc;
	
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
			ws.setController(wc);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		launch(args);
	}
	
	public void loadImport(File file) {
		ws.readSimulation(file);
	}
	
	public WarehouseController getWarehouseController() {
		return wc;
	}
	
	public WarehouseSimulation getWarehouseSimulation() {
		return ws;
	}
	
	
}
