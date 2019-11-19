import javafx.scene.input.Clipboard;
import javafx.scene.input.ClipboardContent;
import java.util.HashMap;


public class LanguageChanger {

    private String chosenLanguage;
    private Language ukrainian;
    private String chosenLanguage2;
    private Language english;
    private HashMap<Character,Character> translator;

    public LanguageChanger() {


        chosenLanguage = "ukrainian";
        chosenLanguage2 = "english";
        rewriteLanguages();
    }

    public String translate(String message) {

        char [] firstLanguage = message.toCharArray();
        char [] secondLanguage = new char[firstLanguage.length];

        for (int i = 0; i < firstLanguage.length; ++i) {
            if(translator.containsKey(firstLanguage[i])) {
                secondLanguage[i] = translator.get(firstLanguage[i]);
            }
            else secondLanguage[i] = firstLanguage[i];
        }
        String translated = String.copyValueOf(secondLanguage);
        copyToClipboard(translated);
        return translated;
    }

    private void rewriteLanguages() {
        ukrainian = new Language(chosenLanguage);
        english = new Language(chosenLanguage2);
        translator = new HashMap<>();
        for (int i = 0;i < ukrainian.getKeys().length; ++i) {
            translator.put(ukrainian.getKeys()[i],english.getKeys()[i]);
        }
    }

    private void copyToClipboard(String translated) {
        ClipboardContent content = new ClipboardContent();
        content.putString(translated);
        Clipboard.getSystemClipboard().setContent(content);
    }

    public void setChosenLanguage(String chosenLanguage) {
        this.chosenLanguage = chosenLanguage;
        rewriteLanguages();
    }

    public void setChosenLanguage2(String chosenLanguage2) {
        this.chosenLanguage2 = chosenLanguage2;
        rewriteLanguages();
    }
}
