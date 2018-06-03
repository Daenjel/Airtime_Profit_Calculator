package airtime;
import javafx.animation.PauseTransition;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;

public class SplashScreen extends Application {

	@Override
	public void start(Stage primaryStage) throws Exception {
		Parent root = FXMLLoader.load(getClass().getResource("SplashScreen.fxml"));
				
		Scene scene = new Scene(root);
		primaryStage.initStyle(StageStyle.TRANSPARENT);
		primaryStage.setScene(scene);
		primaryStage.show();
	
		PauseTransition pause = new PauseTransition(Duration.seconds(3));
		pause.setOnFinished(f ->{
			new Sales();
			primaryStage.hide();
    		primaryStage.close();
		});
		pause.playFromStart();
	}

	public static void main(String[] args)
	{
		launch(args);
	}
}



