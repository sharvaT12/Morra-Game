import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;

public class stateGame {

    @FXML
    private Label boolFinalWin;

    @FXML
    private Label boolPlayAgain;

    @FXML
    private Label int1Play;

    @FXML
    private Label int1Points;

    @FXML
    private Label int2Play;

    @FXML
    private Label int2Points;

    @FXML
    private Label intClientCon;

    @FXML
    private Label intWinnerOfPlay;

    @FXML
    private ListView<String> listViewLog;

    /// this is just the text on the left hand side of the function
    @FXML
    private Label clientConnected;

    @FXML
    private Button exitServer;

    @FXML
    private Label finalWin;

    @FXML
    private Label playAgain;

    @FXML
    private Label player1Play;

    @FXML
    private Label player1Points;

    @FXML
    private Label player2Play;

    @FXML
    private Label player2Points;

    @FXML
    private Label winnerPlayer;
    ////////

    public void addItem(String text)
    {
        listViewLog.getItems().add(text);
    }

    // getting the result from the client
    // pass from the client which number they choose 0-5
    public void setInt1Play(Integer num)
    {
        int1Play.setText(String.valueOf(num));
    }

    public void setInt2Play(Integer num)
    {
        int2Play.setText(String.valueOf(num));
    }

    //compare the number and tell points they win
    public void setIntWinnerOfPlay(String num)
    {
        intWinnerOfPlay.setText(String.valueOf(num));
    }

    //how many clients are connected
    public void setIntClientCon(Integer num)
    {
        intClientCon.setText(String.valueOf(num));
    }

    // player 1 point
    public void setInt1Points(Integer num)
    {
        int1Points.setText(String.valueOf(num));
    }
    // player 2 point
    public void setInt2Points(Integer num)
    {
        int2Points.setText(String.valueOf(num));
    }

    // to check if someone ever win
    public void setBoolFinalWin(Boolean num)
    {
        num = true;
        boolFinalWin.setText(String.valueOf(num));
    }

    // to check if they will play again
    public void setBoolPlayAgain(Boolean num)
    {
        num = true;
        boolPlayAgain.setText(String.valueOf(num));
    }

}
