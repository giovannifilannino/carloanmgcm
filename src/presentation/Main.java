package presentation;
	
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;



public class Main extends Application {
	
	 Stage primary;
	 AnchorPane root;
	
	@Override
	public void start(Stage primaryStage) {
		try {
			primary = primaryStage;
			primary.setTitle("CarLoan - Benvenuto");
			FXMLLoader fx = new FXMLLoader();
			fx.setLocation(getClass().getResource("controller/fxmlclass/LoginWin.fxml"));
			root = (AnchorPane) fx.load();
			Scene scene = new Scene(root);
			primary.setScene(scene);
			primary.show();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}
