import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;

public class BlackjackGUI extends Application 
{

    Button button;
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        primaryStage.setTitle("Blackjack Game");
        button=new Button();
        button.setText("Start Game");
        StackPane startLayout= new StackPane();
        startLayout.getChildren().add(button);
        Scene start = new Scene(startLayout, 400, 400);
        primaryStage.setScene(start);
        primaryStage.show();

        button.setOnAction(e -> 
        {
            Button hit=new Button("HIT");
            Button stand=new Button("STAND");
            Button surrender=new Button("SURRENDER");
            GridPane gameLayout = new GridPane();
            gameLayout.addRow(0, hit);
            gameLayout.addRow(1, stand);
            gameLayout.addRow(2, surrender);
            gameLayout.setVgap(20);
            gameLayout.setAlignment(Pos.CENTER);
            Scene gameScene = new Scene(gameLayout, 700, 700);
            primaryStage.setScene(gameScene);
        });
    }
}