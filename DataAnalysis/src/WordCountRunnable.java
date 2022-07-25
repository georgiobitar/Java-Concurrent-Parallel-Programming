import java.io.*;
import java.nio.file.FileSystems;
import java.util.Objects;
import java.util.Scanner;

import javax.swing.JTextArea;
import javax.swing.SwingUtilities;

public class WordCountRunnable implements Runnable
{
    private String word;
    //private JTextArea textArea;
    private int count = 0;
    private final String filePath = FileSystems.getDefault().getPath("data.txt").toString();
    private static String fileName = FileSystems.getDefault().getPath("testMultithreading.txt").toString();

    /*
    public WordCountRunnable(String word, JTextArea textArea)
    {
        this.word = word;
        this.textArea = textArea;
    }*/

    public WordCountRunnable(String word)
    {
        this.word = word;
    }

    @Override
    public void run() {
        File f = new File(filePath);
        try {
            Scanner scanner = new Scanner(f);
            while (scanner.hasNext())
            {
                String str = scanner.next();
                if (str.equals(word))
                    count++;
            }
            SwingUtilities.invokeLater(new Runnable()
            {
                @Override
                public void run()
                {
                    //textArea.append(word + " appears: " + count + " times\n");
                    try
                    {
                        BufferedWriter out = new BufferedWriter(new FileWriter(fileName, true));
                        out.write(word + " appears: " + count + " times\n");
                        out.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    //System.out.println(SwingUtilities.isEventDispatchThread());
                }
            });
            scanner.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
}

