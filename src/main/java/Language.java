import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Getter;
import lombok.Setter;

import java.io.File;
import java.io.IOException;

@Setter
public class Language {
    private static ObjectMapper mapper = new ObjectMapper();

    private String name;
    @Getter
    private char[] keys;

    public Language(){

    }

    public Language(String name) {
        this.name = name;
        try {
            String path ="languages/" + this.name + ".json";
            keys = mapper.readValue(new File(path),char[].class);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
