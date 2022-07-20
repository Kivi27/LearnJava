import java.io.FileWriter;
import java.io.IOException;

public class MyFileHandler {

    public static void WriteFile(String fileName, String data) throws IOException {
        FileWriter writer = new FileWriter(fileName);
        writer.write(data);
        writer.close();
    }

}
