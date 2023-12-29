import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

import javax.swing.plaf.synth.SynthEditorPaneUI;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.net.Socket;
import java.net.URL;
import java.util.HashMap;
import java.util.ResourceBundle;
import java.util.function.Consumer;
import static javafx.application.Application.launch;

public class ClientGUI extends Application implements Initializable {
	public BorderPane root;

	@FXML
	private TextField textIP;
	@FXML
	private TextField textPort;
	@FXML
	private Button connect;


	Client clientConnection;


	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		primaryStage.setTitle("Client");

		try {
			Parent root = FXMLLoader.load(getClass().getResource("/FXML/Client.fxml"));
			Scene introScene = new Scene(root, 700,700);
			primaryStage.setScene(introScene);
			primaryStage.show();

		} catch(Exception e) {
			e.printStackTrace();
			System.exit(1);
		}

		primaryStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
			@Override
			public void handle(WindowEvent t) {
				Platform.exit();
				System.exit(0);
			}
		});
	}


	public void connectMethod(ActionEvent e) throws IOException {

		// to check if there is no input it will not let it pass
		if(textIP.getText().isEmpty() && textPort.getText().isEmpty()) {
			connect.setDisable(false);
		}
		else{
			//		gameController.setClient(clientConnection);
			FXMLLoader loader = new FXMLLoader((getClass().getResource("/FXML/Game.fxml")));
			Parent gameScreen = loader.load();
//			game gameController = loader.getController();
			clientConnection = new Client(data -> {
				// When the client receives data, it updates the listview with the data
//				gameController.addListViewClient(data.toString());
			});
			root.getScene().setRoot(gameScreen);
//			gameController.connection = clientConnection;
			clientConnection.setIP(textIP.getText());
			clientConnection.setPort(Integer.valueOf(textPort.getText()));
			clientConnection.start();
		}
	}



	public void initialize(URL location, ResourceBundle resources) {
	}
}
