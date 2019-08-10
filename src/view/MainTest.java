package view;

import controller.WarehouseController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import model.Floor;

public class MainTest extends Application {
	public static Stage stage;
	public static Scene scene;
	private static Floor floor;

	@Override
	public void start(Stage primaryStage) {
		try {
			final FXMLLoader loader = new FXMLLoader();
			loader.setController(new WarehouseController());
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
	
/*	@Override
	public void start(Stage arg0) throws Exception {
		arg0.setTitle("Chess Game");

        BorderPane root = new BorderPane();
        Scene mainScene = new Scene(root);
        arg0.setScene(mainScene);


        // draw chessboard
        floor = new Floor(5,5);
        floor.setGridLinesVisible(true);
        root.setCenter(floor); // sized 400x400
		arg0.show();
	}
*/

	public static void main(String[] args) {
		launch(args);
	}
	
}
