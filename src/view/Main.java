package view;

import java.io.File;

import controller.WarehouseController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import model.WarehouseSimulation;

/**
 * @author Joseph.Sheargold
 *
 */
public class Main extends Application {
	public static Stage stage;
	public static Scene sceneSimulation, sceneLog;
	private static WarehouseSimulation ws;
	private static WarehouseController wc;
	
	@Override
	public void start(Stage primaryStage) {
		try {
			stage = primaryStage;
			ws = new WarehouseSimulation();
			wc = new WarehouseController(this);
			
			final FXMLLoader loader = new FXMLLoader();
			loader.setController(wc);
			loader.setLocation(getClass().getResource("WarehouseSimulation.fxml"));
			final Parent root = loader.load();
			sceneSimulation = new Scene(root);
			sceneSimulation.getStylesheets().add("stylesheet.css");
			primaryStage.setScene(sceneSimulation);
			primaryStage.setTitle("Warehouse");
			primaryStage.setResizable(false);
			primaryStage.show();
			
			ws.setFloor(wc.getFloor());
			ws.setController(wc);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	
	/**
	 * Main method to start the application
	 * 
	 * @param args initial main method arguments
	 */
	public static void main(String[] args) {
		launch(args);
	}
	
	public void loadImport(File file) {
		ws.readSimulation(file);
	}
	
	public static WarehouseController getWarehouseController() {
		return wc;
	}
	
	public WarehouseSimulation getWarehouseSimulation() {
		return ws;
	}
	
}
