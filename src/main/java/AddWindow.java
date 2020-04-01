import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import javafx.collections.FXCollections;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.net.URL;

public class AddWindow {
    private static final String LANGUAGE_PATH = "http://localhost:8080/language?language=";

    private static ObjectMapper mapper = new ObjectMapper();


    private static final int WINDOWWIDTH = 500;
    private static final int WINDOWHEIGHT = 350;

    private Group layout;
    private Scene scene;
    private Stage window;
    private Label message;
    private ChoiceBox<String> languages;
    private Button choose;
    private Button close;

    public AddWindow() {
        window = new Stage();
        layout = new Group();
        scene = new Scene(layout, WINDOWWIDTH, WINDOWHEIGHT);
        message = new Label();
        languages = new ChoiceBox<>();
        try {
            languages.setItems(FXCollections.observableArrayList(
                    mapper.readValue(new URL("http://localhost:8080/getAll"), String[].class)));
        } catch (Throwable e) {
            new AnswerWindow().show("Виникла помилка,\nперевірте з'єднання");
        }
        choose = new Button("Підтвердити");
        choose.setOnAction(event -> write());
        close = new Button("Закрити");
        close.setOnAction(event -> window.close());
    }

    public void show() {
        layout.getChildren().addAll(message, languages, choose, close);
        message.setText("Виберіть мову для скачування");
        window.setScene(scene);
        draw();
        if(!languages.getItems().isEmpty())
            window.showAndWait();
    }

    private void write() {
        try {
            Language toWrite = mapper.readValue(new URL(LANGUAGE_PATH + languages.getValue()), Language.class);
            if(!languages.getSelectionModel().isEmpty())
                mapper.writeValue(new File("languages/" + languages.getValue() + ".json"), toWrite);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void draw() {
        double height = scene.getHeight();
        double width = scene.getWidth();
        message.setLayoutY(0);
        languages.setLayoutY(height / 3);
        choose.setLayoutY(height / 2);
        close.setLayoutY(height / 3 * 2);
    }
}
