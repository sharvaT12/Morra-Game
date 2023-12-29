import java.io.IOException;
import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.beans.Observable;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import sun.nio.ch.IOStatus;


public class GuiServer extends Application implements Initializable {
//	public Button exitServer;
	@FXML
	public TextField inputPort;

//	public TextField textPort;
//	public Label player1Play;
//	public Label player2Play;
//	public Label winnerPlayer;
//	public Label clientConnected;
//	public Label player1Points;
//	public Label player2Points;
//	public Label finalWin;
//	public Label playAgain;
	public Label int1Play;
	public Label int2Play;
	public Label intWinnerOfPlay;
	public Label intClientCon;
	public Label int1Points;
	public Label int2Points;
	public Label boolFinalWin;
	public Label boolPlayAgain;

	@FXML
	private BorderPane root;

	ObservableList<String> observableList = FXCollections.observableArrayList();
	Server serverConnection;

	// from the server.fxml
	@FXML
	private Button startServer;

	@Override
	// for the first scene
	public void start(Stage primaryStage){
		// TODO Auto-generated method stub
		try {
			// Read file fxml and draw interface.
			Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/FXML/Server.fxml")));
			Scene s1 = new Scene(root, 700,700);
			s1.getStylesheets().add("/styles/style1.css");
			primaryStage.setScene(s1);
			primaryStage.show();

		}
		catch(Exception e) {
			e.printStackTrace();
			System.exit(1);
		}

//		System.out.println(listViewLog.getItems());

		primaryStage.setOnCloseRequest(t -> {
			Platform.exit();
			System.exit(0);
		});
	}

//	public void setListViewLog()
//	{
//
//	}

	// Event handler for the start server button
	public void startServerMethod(ActionEvent e) throws IOException {
		if(inputPort.getText().isEmpty()){
			startServer.setDisable(false);
		}
		else {
			FXMLLoader loader = new FXMLLoader((getClass().getResource("/FXML/stateGame.fxml")));
			Parent serverStatus = loader.load();
			stateGame stateGameController = loader.getController();
//		stateGameController.addItem("Test");
			root.getScene().setRoot(serverStatus);
			// Create a new instance of the server
			serverConnection = new Server(data -> {
				// When the server receives data from a client
				Platform.runLater(() -> {
					// Update the list view
					stateGameController.addItem(data.toString());

				});
			}, Integer.valueOf(inputPort.getText()));
		}
	}

	@Override
	public void initialize(URL url, ResourceBundle resourceBundle) {
	}

	public static void main(String[] args)
	{
		launch(args);
	}
}