package view;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import com.mysql.cj.jdbc.Driver;

public class Interface extends Application {

    @Override
    public void start(Stage stage) {

        Button botao = new Button("Teste");

        VBox root = new VBox(botao);

        Scene scene = new Scene(root, 400, 300);

        stage.setScene(scene);
        stage.setTitle("Sistema");
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}
