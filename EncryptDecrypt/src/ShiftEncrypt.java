public class ShiftEncrypt extends EncodeDecode {

    public ShiftEncrypt(String mode, int key) {
        super(mode, key);
    }

    private char encryptSymbol(char curSymbol) {
        final char limitChar = curSymbol >= 'A' && curSymbol <= 'Z' ? 'Z' : 'z';
        final char beginLimit = curSymbol >= 'A' && curSymbol <= 'Z' ? 'A' : 'a';

        if (Character.isLetter(curSymbol)) {
            char newSymbol = (char) ((int) curSymbol + key);
            if (newSymbol > limitChar) {
                newSymbol = (char) (beginLimit + newSymbol - limitChar - 1);
            }
            return newSymbol;
        }
        return curSymbol;
    }

    private char decryptSymbol(char curSymbol) {
        final char limitChar = curSymbol >= 'A' && curSymbol <= 'Z' ? 'A' : 'a';
        final char beginLimit = curSymbol >= 'A' && curSymbol <= 'Z' ? 'Z' : 'z';

        if (Character.isLetter(curSymbol)) {
            char newSymbol = (char) ((int) curSymbol - key);
            if (newSymbol < limitChar) {
                newSymbol = (char) (beginLimit + newSymbol - limitChar + 1);
            }
            return newSymbol;
        }
        return curSymbol;
    }

    @Override
    public String encrypt(String text) {
        StringBuilder str = new StringBuilder();
        for (int i = 0; i < text.length(); i++) {
            char curSymbol = text.charAt(i);
            str.append(encryptSymbol(curSymbol));
        }
        return str.toString();
    }

    @Override
    public String decrypt(String text) {
        StringBuilder str = new StringBuilder();
        for (int i = 0; i < text.length(); i++) {
            char curSymbol = text.charAt(i);
            str.append(decryptSymbol(curSymbol));
        }
        return str.toString();
    }
}
