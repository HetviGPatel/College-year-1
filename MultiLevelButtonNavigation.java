import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class MultiLevelButtonNavigation extends Application {

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Multi-Level Button Navigation");

        // Create initial scene
        Scene initialScene = createInitialScene(primaryStage);
        primaryStage.setScene(initialScene);
        primaryStage.show();
    }

    private Scene createInitialScene(Stage primaryStage) {
        // Create UI elements for the initial scene
        Button button1 = new Button("Button 1");
        Button button2 = new Button("Button 2");
        Button button3 = new Button("Button 3");
        Button button4 = new Button("Button 4");

        // Set button actions to create new scenes with inner buttons
        button1.setOnAction(e -> primaryStage.setScene(createInnerScene(primaryStage, "Button 1")));
        button2.setOnAction(e -> primaryStage.setScene(createInnerScene(primaryStage, "Button 2")));
        button3.setOnAction(e -> primaryStage.setScene(createInnerScene(primaryStage, "Button 3")));
        button4.setOnAction(e -> primaryStage.setScene(createInnerScene(primaryStage, "Button 4")));

        // Arrange UI elements in a vertical box
        VBox vbox = new VBox(10, button1, button2, button3, button4);
        vbox.setAlignment(Pos.CENTER);
        vbox.setStyle("-fx-padding: 20;");

        return new Scene(vbox, 300, 250);
    }

    private Scene createInnerScene(Stage primaryStage, String parentButton) {
        // Print the label of the button that was clicked
        System.out.println(parentButton);
        Label l=new Label();
        // Create UI elements for the inner scene
        Button innerButton1 = new Button(parentButton + " - Inner Button 1");
        Button innerButton2 = new Button(parentButton + " - Inner Button 2");
        Button innerButton3 = new Button(parentButton + " - Inner Button 3");
        Button innerButton4 = new Button(parentButton + " - Inner Button 4");
        Button backButton = new Button("Back");

        // Set button actions
        innerButton1.setOnAction(e ->  l.setText(innerButton1.getText()));
        innerButton2.setOnAction(e -> System.out.println(innerButton2.getText()));
        innerButton3.setOnAction(e -> System.out.println(innerButton3.getText()));
        innerButton4.setOnAction(e -> System.out.println(innerButton4.getText()));
        backButton.setOnAction(e -> primaryStage.setScene(createInitialScene(primaryStage)));

        // Arrange UI elements in a vertical box
        VBox vbox = new VBox(10, innerButton1, innerButton2, innerButton3, innerButton4, backButton,l);
        vbox.setAlignment(Pos.CENTER);
        vbox.setStyle("-fx-padding: 20;");

        return new Scene(vbox, 300, 250);
    }

    public static void main(String[] args) {
        launch(args);
    }
}

