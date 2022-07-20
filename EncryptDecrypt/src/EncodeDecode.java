import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public abstract class EncodeDecode {

    protected String mode;
    protected int key;

    public EncodeDecode(String mode, int key) {
        this.mode = mode;
        this.key = key;
    }

    private String encryptFile(String fileName) throws IOException {
        String allText = new String(Files.readAllBytes(Paths.get(fileName)));
        return encrypt(allText);
    }

    private String decryptFile(String fileName) throws IOException {
        String allText = new String(Files.readAllBytes(Paths.get(fileName)));
        return decrypt(allText);
    }

    public boolean isModeEncrypt() {
        return mode.equals("enc");
    }

    public String process(String text) {
        String res = isModeEncrypt() ? encrypt(text) : decrypt(text);
        return res;
    }


    public String processFile(String fileName) throws IOException {
        String res = isModeEncrypt() ? encryptFile(fileName) : decryptFile(fileName);
        return res;
    }

    public abstract String encrypt(String text);
    public abstract String decrypt(String text);
}