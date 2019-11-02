import javafx.application.Application;
import javafx.stage.Stage;

public class Main extends Application {

    public static void main(String[] args) {
//        launch(args);
        LanguageChanger languageChanger = new LanguageChanger();
        String asd = languageChanger.changeToEnglish("фыв");
        System.out.println(asd);
        asd = languageChanger.changeToUkrainian("asd");
        System.out.println(asd);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {

    }
}
