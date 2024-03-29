import java.io.*;
import java.nio.file.FileSystems;
import java.util.Objects;
import java.util.Scanner;

import javax.swing.JTextArea;
import javax.swing.SwingUtilities;

public class WordCountRunnable implements Runnable {
    private String word;
    private int count = 0;
    private final String filePath = FileSystems.getDefault().getPath("data.txt").toString();
    private volatile String result;

    public WordCountRunnable(String word) {
        this.word = word;
    }

    @Override
    public void run() {
        File f = new File(filePath);
        try {
            Scanner scanner = new Scanner(f);
            while (scanner.hasNext()) {
                String str = scanner.next();
                if (str.equals(word))
                    count++;
            }

            result = word + " appears: " + count + " times\n";


            scanner.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public String getResult() {
        if (result == null)
            return "";
        return result;
    }
}

