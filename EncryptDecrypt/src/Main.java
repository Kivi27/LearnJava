import java.io.IOException;
import java.util.*;

public class Main {
    private static HashMap<String, String> initDefaultValueHashMap() {
        HashMap<String, String> map = new HashMap<>();
        map.put("-mode", "enc");
        map.put("-key", "0");
        map.put("-data", "");
        map.put("-in", "");
        map.put("-out", "");
        map.put("-alg", "shift");
        return map;
    }

    private static HashMap<String, String> parseArgs(String[] args) {
        HashMap<String, String> mapArgument = initDefaultValueHashMap();
        for (var keyMap : mapArgument.keySet()) {
            for (int j = 0; j < args.length; j++) {
                if (args[j].equals(keyMap)) {
                    mapArgument.put(keyMap, args[j + 1]);
                }
            }
        }
        return mapArgument;
    }


    public static void main(String[] args) {
        try {

            HashMap<String, String> mapArgs = parseArgs(args);
            EncodeDecode encodeDecode;
            if (mapArgs.get("-alg").equals("shift")) {
                encodeDecode = new ShiftEncrypt(mapArgs.get("-mode"), Integer.parseInt(mapArgs.get("-key")));
            } else {
                encodeDecode = new CesarEncrypt(mapArgs.get("-mode"), Integer.parseInt(mapArgs.get("-key")));
            }

            String res;
            if (mapArgs.get("-in").isEmpty()) {
                res = encodeDecode.process(mapArgs.get("-data"));
            } else if (!mapArgs.get("-data").isEmpty()) {
                res = encodeDecode.process(mapArgs.get("-data"));
            } else {
                res = encodeDecode.processFile(mapArgs.get("-in"));
            }

            if (mapArgs.get("-out").isEmpty()) {
                System.out.println(res);
            } else {
                MyFileHandler.WriteFile(mapArgs.get("-out"), res);
            }

        } catch (IOException ioException) {
            System.out.println("Error: file not found! " + ioException.getMessage());
        } catch (IndexOutOfBoundsException exception) {
            System.out.println("Error: Wrong count parameter!");
        }
    }
}
