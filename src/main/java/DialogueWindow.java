import javafx.collections.FXCollections;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.stage.Stage;

import java.awt.*;
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
    private Label translated;


    public DialogueWindow() {
            stringToTranslate = new TextField();
            window = new Stage();
            layout = new Group();
            scene = new Scene(layout, WINDOWWIDTH, WINDOWHEIGHT);
            translate = new Button("Перекласти");
            translated = new Label();
            translated.setBackground(new Background(new BackgroundFill(Color.WHITE, CornerRadii.EMPTY, Insets.EMPTY)));
            languageChanger = new LanguageChanger();

            wrongBox = new ChoiceBox<String>(FXCollections.observableArrayList(getLanguages()));
            wrongBox.setOnAction(event -> {
                languageChanger.setChosenLanguage(wrongBox.getValue());
            });
            wrongBox.setValue(wrongBox.getItems().get(0));
            correctBox = new ChoiceBox<String>(FXCollections.observableArrayList(getLanguages()));
            correctBox.setOnAction( event -> {
                languageChanger.setChosenLanguage2(correctBox.getValue());
                scene.setFill(new ImagePattern(new Image("/flags/" + languageChanger.getChosenLanguage2() + ".png")));
            });
            correctBox.setValue(correctBox.getItems().get(1));
            scene.heightProperty().addListener(e -> redraw());
            scene.widthProperty().addListener(e -> redraw());
    }

    public void change() {
        layout.getChildren().addAll(stringToTranslate, translate, wrongBox, correctBox, translated);
        translate.setOnAction(event -> translated.setText(languageChanger.translate(stringToTranslate.getText())));
        scene.setOnKeyPressed(new Enter());
        window.setScene(scene);
        redraw();
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

    private void redraw() {
        System.out.println(scene.getHeight());
        double height;
        double width;
        if (window.isMaximized()) {
            Dimension sSize = Toolkit.getDefaultToolkit ().getScreenSize ();
            height = sSize.height;
            width  = sSize.width;

        }
        else {
            height = scene.getHeight();
            width = scene.getWidth();
        }
        correctBox.setLayoutY(height / 4);
        stringToTranslate.setLayoutY(height / 2);
        translate.setLayoutY(height / 4 * 3);
        translated.setLayoutY(height / 2);
        translated.setLayoutX(width / 2);
    }

    private class Enter implements EventHandler<KeyEvent> {

        @Override
        public void handle(KeyEvent event) {
            switch (event.getCode()) {
                case ENTER:
//                    new AnswerWindow().show(languageChanger.translate(stringToTranslate.getText()));
                    translated.setText(languageChanger.translate(stringToTranslate.getText()));
                    break;
            }
        }
    }
}
