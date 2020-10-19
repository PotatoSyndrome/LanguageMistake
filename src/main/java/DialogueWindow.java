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

    private static final int WINDOW_WIDTH = 700;
    private static final int WINDOW_HEIGHT = 500;
    private static final int TEXTFIELD_HEIGHT = 140;
    private static final int TEXTFIELD_WIDTH = 250;

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
            stringToTranslate.setPrefHeight(TEXTFIELD_HEIGHT);
            stringToTranslate.setPrefWidth(TEXTFIELD_WIDTH);


            window = new Stage();

            layout = new Group();

            scene = new Scene(layout, WINDOW_WIDTH, WINDOW_HEIGHT);

            translate = new Button("Перекласти");
            translate.setPrefWidth(100);

            translated = new Label();
            translated.setBackground(new Background(new BackgroundFill(Color.WHITE, CornerRadii.EMPTY, Insets.EMPTY)));
            translated.setMaxWidth(TEXTFIELD_WIDTH);
            translated.setMaxHeight(TEXTFIELD_HEIGHT);
            translated.setWrapText(true);

            languageChanger = new LanguageChanger();

            download = new Button("Cкачати додаткові мови");
            download.setPrefWidth(200);

            wrongBox = new ChoiceBox<String>();
            wrongBox.setItems(FXCollections.observableArrayList(getLanguages()));
            wrongBox.setPrefWidth(100);

            correctBox = new ChoiceBox<String>(FXCollections.observableArrayList(getLanguages()));
            correctBox.setPrefWidth(100);

            initBoxes();

            scene.getStylesheets().add("/styles.css");
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

        wrongBox.setLayoutY(height / 24);
        wrongBox.setLayoutX(width / 2 - wrongBox.getPrefWidth() - 10);

        correctBox.setLayoutY(height / 24);
        correctBox.setLayoutX(width / 2 + 10);

        stringToTranslate.setLayoutY(height / 6);
        stringToTranslate.setLayoutX((width - stringToTranslate.getPrefWidth()) / 2);

        translate.setLayoutY(stringToTranslate.getLayoutY() + stringToTranslate.getPrefHeight() + height / 24);
        translate.setLayoutX((width - translate.getPrefWidth()) / 2);


        download.setLayoutY(height / 8 * 7);
        download.setLayoutX((width - download.getPrefWidth()) / 2);

        translated.setLayoutY(height * 7 / 12);
        translated.setLayoutX(width / 3);
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
