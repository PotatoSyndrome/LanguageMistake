import java.util.HashMap;

public class LanguageChanger {

    HashMap<Character,Character> ukrainianToEnglish;
    HashMap<Character,Character> englishToUkrainian;

    public LanguageChanger() {
        ukrainianToEnglish = new HashMap<>();
        englishToUkrainian = new HashMap<>();

        ukrainianToEnglish.put('й','q');
        ukrainianToEnglish.put('ц','w');
        ukrainianToEnglish.put('у','e');
        ukrainianToEnglish.put('к','r');
        ukrainianToEnglish.put('е','t');
        ukrainianToEnglish.put('н','y');
        ukrainianToEnglish.put('г','u');
        ukrainianToEnglish.put('ш','i');
        ukrainianToEnglish.put('щ','o');
        ukrainianToEnglish.put('з','p');
        ukrainianToEnglish.put('х','[');
        ukrainianToEnglish.put('ї',']');
        ukrainianToEnglish.put('ф','a');
        ukrainianToEnglish.put('і','s');
        ukrainianToEnglish.put('в','d');
        ukrainianToEnglish.put('а','f');
        ukrainianToEnglish.put('п','g');
        ukrainianToEnglish.put('р','h');
        ukrainianToEnglish.put('о','j');
        ukrainianToEnglish.put('л','k');
        ukrainianToEnglish.put('д','l');
        ukrainianToEnglish.put('ж',';');
        ukrainianToEnglish.put('є','\'');
        ukrainianToEnglish.put('я','z');
        ukrainianToEnglish.put('ч','x');
        ukrainianToEnglish.put('с','c');
        ukrainianToEnglish.put('м','v');
        ukrainianToEnglish.put('и','b');
        ukrainianToEnglish.put('т','n');
        ukrainianToEnglish.put('ь','m');
        ukrainianToEnglish.put('б',',');
        ukrainianToEnglish.put('ю','.');
        ukrainianToEnglish.put('.','/');
        ukrainianToEnglish.put('ы','s');

        englishToUkrainian.put('q','й');
        englishToUkrainian.put('w','ц');
        englishToUkrainian.put('e','у');
        englishToUkrainian.put('r','к');
        englishToUkrainian.put('t','е');
        englishToUkrainian.put('y','н');
        englishToUkrainian.put('u','г');
        englishToUkrainian.put('i','ш');
        englishToUkrainian.put('o','щ');
        englishToUkrainian.put('p','з');
        englishToUkrainian.put('[','х');
        englishToUkrainian.put(']','ї');
        englishToUkrainian.put('a','ф');
        englishToUkrainian.put('s','і');
        englishToUkrainian.put('d','в');
        englishToUkrainian.put('f','а');
        englishToUkrainian.put('g','п');
        englishToUkrainian.put('h','р');
        englishToUkrainian.put('j','о');
        englishToUkrainian.put('k','л');
        englishToUkrainian.put('l','д');
        englishToUkrainian.put(';','ж');
        englishToUkrainian.put('\'','є');
        englishToUkrainian.put('z','я');
        englishToUkrainian.put('x','ч');
        englishToUkrainian.put('c','с');
        englishToUkrainian.put('v','м');
        englishToUkrainian.put('b','и');
        englishToUkrainian.put('n','т');
        englishToUkrainian.put('m','ь');
        englishToUkrainian.put(',','б');
        englishToUkrainian.put('.','ю');
        englishToUkrainian.put('/','.');
    }

    public String changeToEnglish(String ukrainian) {
        char [] ukr = ukrainian.toCharArray();
        char [] engl = new char[ukr.length];

        for (int i = 0;i < ukr.length;++i) {
            try {
                engl[i] = ukrainianToEnglish.get(ukr[i]);
            }
            catch (NullPointerException e) {
                engl[i] = ukr[i];
            }
        }
        return String.copyValueOf(engl);
    }

    public String changeToUkrainian(String english) {
        char [] engl = english.toCharArray();
        char [] ukr = new char[engl.length];

        for (int i = 0;i < engl.length;++i) {
            try {
                ukr[i] = englishToUkrainian.get(engl[i]);
            }
            catch (NullPointerException e) {
                ukr[i] = engl[i];
            }
        }
        return String.copyValueOf(ukr);
    }
}
