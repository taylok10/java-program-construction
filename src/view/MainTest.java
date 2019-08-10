package view;

import java.io.File;
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

public class MainTest extends Application {
	public static Stage stage;
	public static Scene scene;
	private static WarehouseSimulation ws;

	private static Queue<Order> orders;
	private static Map<Order, Integer> orderStats;
	private static DataRegistry dr;
	
	@Override
	public void start(Stage primaryStage) {
		try {
			dr = new DataRegistry(this);
			orders = null;
			orderStats = null;
			final FXMLLoader loader = new FXMLLoader();
			loader.setController(new WarehouseController(this));
			loader.setLocation(getClass().getResource("WarehouseSimulation.fxml"));
			final Parent root = loader.load();

			final Scene scene = new Scene(root);
			// Move file to resources
			scene.getStylesheets().add("stylesheet.css");
			primaryStage.setTitle("Warehouse");
			primaryStage.setScene(scene);
			primaryStage.show();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		launch(args);
	}
	
	public void loadImport(File file) {
		dr.loadImport(file);
	}
	
}
