import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;

public class MyController implements Initializable{
	
	@FXML
	private BorderPane root;
	

	
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub
		
	}
	
	public void clientMethod() throws IOException
	{
		//get instance of the loader class
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/FXML/Clientfxml.fxml"));
        Parent root2 = loader.load();
        root.getScene().setRoot(root2);
        

	}
	
	public void severMethod() throws IOException{
		
		//get instance of the loader class
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/FXML/Severfxml.fxml"));
        Parent root2 = loader.load(); //load view into parent
        root.getScene().setRoot(root2);//update scene graph
        
	}
	

}
