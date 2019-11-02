import javafx.collections.FXCollections;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;


public class DialogueWindow {

    private static final int WINDOWWIDTH = 500;
    private static final int WINDOWHEIGHT = 300;

    private Group layout;
    private Scene scene;
    private Stage window;
    private Button translate;
    private TextField stringToTranslate;
    private ChoiceBox<String> choiceBox;
    private LanguageChanger languageChanger;

    public DialogueWindow() {
            stringToTranslate = new TextField();
            window = new Stage();
            layout = new Group();
            scene = new Scene(layout, WINDOWWIDTH, WINDOWHEIGHT);
            translate = new Button("Перекласти");
            languageChanger = new LanguageChanger();
            choiceBox= new ChoiceBox<String>(FXCollections.observableArrayList("Українську на англійську","Англійську на українську"));
    }

    public void change() {
        layout.getChildren().addAll(stringToTranslate,translate,choiceBox);
        stringToTranslate.setLayoutY(50);
        translate.setLayoutY(100);
        translate.setOnAction(event -> {
            if(choiceBox.getSelectionModel().isSelected(0)) {
                new AnswerWindow().show(languageChanger.changeToEnglish(stringToTranslate.getText()));
            }
            else new AnswerWindow().show(languageChanger.changeToUkrainian(stringToTranslate.getText()));
        });
        window.setScene(scene);
        window.show();
    }
}
