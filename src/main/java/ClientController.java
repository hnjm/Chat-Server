import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;

public class ClientController implements Initializable{
	
	
	@FXML
	private ListView<String> listItems2;
	
	@FXML
	private TextField c1;
	
	@FXML
	private ListView<String> clientLists;
	
	Client clientConnection;
	
	ClientInfo clientInfo;
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub
		
 
		clientConnection = new Client(data->{
		Platform.runLater(()->{
					
					clientInfo = (ClientInfo) data;
			
					listItems2.getItems().add(clientInfo.getMessage());
					
					clientLists.getItems().clear();
					
					clientLists.getItems().addAll(clientInfo.getClientLists().toString());
					
				});
		});
		
		clientConnection.start();
		
	}
	
	public void instructionMethod() throws IOException{
		
		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setTitle("How to Private Message Client(s)");
		
		alert.setHeaderText(null);
		
	    alert.setContentText("/pm client number(s) message  | for example: "
	    		+ " /pm 1 message " + " or  /pm 1,2 message  " + " | seperate clients with ,");
		
	    alert.showAndWait();
	}
	
	public void sendMethod() throws IOException{
		
		clientInfo = new ClientInfo();
		
		clientInfo.setMessage(c1.getText());
		
		clientConnection.send(clientInfo);
		
		
		c1.clear();
	}

}
