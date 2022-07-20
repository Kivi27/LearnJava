public class CesarEncrypt extends EncodeDecode {

    public CesarEncrypt(String mode, int key) {
        super(mode, key);
    }

    @Override
    public String encrypt(String text) {
        StringBuilder str = new StringBuilder();
        for (int i = 0; i < text.length(); i++) {
            char curSymbol = text.charAt(i);
            char newSymbol = (char) ((int) curSymbol + key);
            str.append(newSymbol);
        }
        return str.toString();
    }

    @Override
    public String decrypt(String text) {
        StringBuilder str = new StringBuilder();
        for (int i = 0; i < text.length(); i++) {
            char curSymbol = text.charAt(i);
            char newSymbol = (char) ((int) curSymbol - key);
            str.append(newSymbol);
        }
        return str.toString();
    }
}
