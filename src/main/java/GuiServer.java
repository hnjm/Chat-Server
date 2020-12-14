import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

public class GuiServer extends Application{

	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		launch(args);
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		// TODO Auto-generated method stub

		

		
		primaryStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
            @Override
            public void handle(WindowEvent t) {
                Platform.exit();
                System.exit(0);
            }
        });
		
		
		try {
			// Read file fxml and draw interface.
            Parent root = FXMLLoader.load(getClass()
                    .getResource("/FXML/StartScenefxml.fxml"));
    		primaryStage.setTitle("The Networked Client/Server");
    		Scene startScene = new Scene(root, 800,800);

    		primaryStage.setScene(startScene);
    		primaryStage.show();
    		
		}
		catch(Exception e) {	            
			e.printStackTrace();
	        System.exit(1);
		}

	}
	


}
