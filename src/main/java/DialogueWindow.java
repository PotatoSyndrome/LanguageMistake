import javafx.collections.FXCollections;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.awt.*;
import java.io.File;


public class DialogueWindow {

    private static final int WINDOWWIDTH = 500;
    private static final int WINDOWHEIGHT = 300;
    private static final int TEXTFIELDHEIGHT = 140;

    private Group layout;
    private Scene scene;
    private Stage window;
    private Button translate;
    private TextField stringToTranslate;
    private ChoiceBox<String> wrongBox;
    private ChoiceBox<String> correctBox;
    private LanguageChanger languageChanger;
    private Label translated;
    private Button download;


    public DialogueWindow() {
            stringToTranslate = new TextField();
        stringToTranslate.setPrefHeight(TEXTFIELDHEIGHT);
            window = new Stage();
            layout = new Group();
            scene = new Scene(layout, WINDOWWIDTH, WINDOWHEIGHT);
            translate = new Button("Перекласти");
            translated = new Label();
            translated.setBackground(new Background(new BackgroundFill(Color.WHITE, CornerRadii.EMPTY, Insets.EMPTY)));
            languageChanger = new LanguageChanger();
            download = new Button("Cкачати додаткові мови");
            wrongBox = new ChoiceBox<String>();
            wrongBox.setItems(FXCollections.observableArrayList(getLanguages()));
            correctBox = new ChoiceBox<String>(FXCollections.observableArrayList(getLanguages()));
            initBoxes();
            scene.heightProperty().addListener(e -> redraw());
            scene.widthProperty().addListener(e -> redraw());
    }

    public void change() {

        layout.getChildren().addAll(stringToTranslate, translate, wrongBox, correctBox, translated, download);
        translate.setOnAction(event -> translated.setText(languageChanger.translate(stringToTranslate.getText())));
        download.setOnAction(event -> {
                new AddWindow().show();
                setBoxes();
            }
        );
        scene.setOnKeyPressed(new Enter());
        window.setScene(scene);
        redraw();
        window.show();
    }

    private String[] getLanguages() {
        File folder = new File("languages/");
        String[] languages = folder.list();
        for(int i = 0; languages != null &&  i < languages.length; ++i) {
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
        correctBox.setLayoutY(height / 8);
        stringToTranslate.setLayoutY(height / 4);
        translate.setLayoutY(height / 4 * 3);
        download.setLayoutY(height / 8 * 7);
        translated.setLayoutY(height / 2);
        translated.setLayoutX(width / 2);
    }


    private void setBoxes() {
        wrongBox.getItems().remove(0, wrongBox.getItems().size() - 1);
        wrongBox.getItems().addAll(getLanguages());
        wrongBox.getItems().remove(0);
        correctBox.getItems().remove(0, correctBox.getItems().size() - 1);
        correctBox.getItems().addAll(getLanguages());
        correctBox.getItems().remove(0);
        wrongBox.setValue(wrongBox.getItems().get(0));
        correctBox.setValue(correctBox.getItems().get(1));
    }

    private void initBoxes() {
        wrongBox.setOnAction(event -> {
            if (wrongBox.getValue() != null)
                languageChanger.setChosenLanguage(wrongBox.getValue());
        });
        wrongBox.setValue(wrongBox.getItems().get(0));
        correctBox.setOnAction( event -> {
            if (correctBox.getValue() != null)
                languageChanger.setChosenLanguage2(correctBox.getValue());
//            scene.setFill(new ImagePattern(new Image("/flags/" + languageChanger.getChosenLanguage2() + ".png")));
        });
        correctBox.setValue(correctBox.getItems().get(1));
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
