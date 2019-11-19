import javafx.collections.FXCollections;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;

import java.io.File;


public class DialogueWindow {

    private static final int WINDOWWIDTH = 500;
    private static final int WINDOWHEIGHT = 300;

    private Group layout;
    private Scene scene;
    private Stage window;
    private Button translate;
    private TextField stringToTranslate;
    private ChoiceBox<String> wrongBox;
    private ChoiceBox<String> correctBox;
    private LanguageChanger languageChanger;

    public DialogueWindow() {
            stringToTranslate = new TextField();
            window = new Stage();
            layout = new Group();
            scene = new Scene(layout, WINDOWWIDTH, WINDOWHEIGHT);
            translate = new Button("Перекласти");
            languageChanger = new LanguageChanger();

            wrongBox = new ChoiceBox<String>(FXCollections.observableArrayList(getLanguages()));
            wrongBox.setOnAction(event -> {
                languageChanger.setChosenLanguage(wrongBox.getValue());
            });
            wrongBox.setValue(wrongBox.getItems().get(0));
            correctBox = new ChoiceBox<String>(FXCollections.observableArrayList(getLanguages()));
            correctBox.setOnAction( event -> {
                languageChanger.setChosenLanguage2(correctBox.getValue());
            });
            correctBox.setValue(correctBox.getItems().get(1));
    }

    public void change() {
        layout.getChildren().addAll(stringToTranslate, translate, wrongBox, correctBox);
        correctBox.setLayoutY(50);
        stringToTranslate.setLayoutY(100);
        translate.setLayoutY(150);
        translate.setOnAction(event -> new AnswerWindow().show(languageChanger.translate(stringToTranslate.getText())));
        scene.setOnKeyPressed(new Enter());
        window.setScene(scene);
        window.show();
    }

    private String[] getLanguages() {
        File folder = new File("languages/");
        String[] languages = folder.list();
        for(int i = 0;languages !=null &&  i < languages.length; ++i) {
            languages[i] = languages[i].replace(".json","");
        }
        return languages;
    }

    private class Enter implements EventHandler<KeyEvent> {

        @Override
        public void handle(KeyEvent event) {
            switch (event.getCode()) {
                case ENTER:
                    new AnswerWindow().show(languageChanger.translate(stringToTranslate.getText()));
                    break;
            }
        }
    }
}
