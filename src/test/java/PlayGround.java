import org.junit.jupiter.api.Test;
import utils.FileStream;

import java.io.IOException;
import java.nio.file.Paths;

public class PlayGround {
    @Test
    void main() {

        try {
            FileStream.generateBigFiles(Paths.get("src\\main\\java\\outputFiles"), 3);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
