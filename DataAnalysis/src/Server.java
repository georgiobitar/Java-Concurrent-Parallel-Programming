import org.json.simple.JSONObject;
import org.json.simple.JSONValue;

import javax.swing.*;
import java.io.*;
import java.net.*;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;

public class Server {
    private static final String filePath = FileSystems.getDefault().getPath("data.txt").toString();
    private static String fileName;
    private static int portNumber = 4000, len = 1024;
    private static ReaderWriter readerWriter;


    private static Socket socket = null;
    private static ServerSocket server = null;
    private static DataInputStream in = null;

    public Server(String fileName) {

        this.fileName = fileName;
        readerWriter = new ReaderWriter();
    }

    public void StartServer() {
        DatagramPacket dataPacket, returnPacket;
        try {
            readerWriter.startWrite();
            DatagramSocket datasocket = new DatagramSocket(portNumber);
            System.out.println("Starting Server");
            while (true) {
                try {
                    byte[] buf = new byte[len];
                    dataPacket = new DatagramPacket(buf, buf.length);
                    //wait for user to send packet and put it in the datapacket variable
                    datasocket.receive(dataPacket);
                    //InetAddress address = dataPacket.getAddress();
                    //int port = dataPacket.getPort();
                    String data = new String(dataPacket.getData(), 0, dataPacket.getLength());

                    Object result = JSONValue.parse(data);
                    JSONObject jsonObj = (JSONObject) result;
                    String Type = (String) jsonObj.get("Type");

                    String Response = "";
                    switch (Type) {
                        case "ThreeWordsCount":
                            String FirstWord = (String) jsonObj.get("FirstWord");
                            String SecondWord = (String) jsonObj.get("SecondWord");
                            String ThirdWord = (String) jsonObj.get("ThirdWord");
                            Response = CountThreeWords(FirstWord, SecondWord, ThirdWord);
                            break;

                        case "Multi-ThreadedWordsCount":
                            //CountMultithreadedWord(String filePath, String outputFile, int nbThreads)
                            break;

                        case "AddWord":
                            String WordToAdd = (String) jsonObj.get("WordToAdd");
                            AddWord(WordToAdd);
                            break;

                        case "ReplaceWord":
                            //ChangeWord(String word1, String word2, String filePath);
                            break;
                            //default:
                            //System.out.println("Error getting data");

                    }

                    byte[] buffer;
                    buffer = Response.getBytes();
                    returnPacket = new DatagramPacket(buffer, buffer.length, dataPacket.getAddress(), 4001);

                    datasocket.send(returnPacket);


                } catch (SocketTimeoutException e) {
                    break;
                } catch (IOException e) {
                    System.err.println(e);
                }
            }
            datasocket.close();
        } catch (SocketException se) {
            System.err.println(se);
        } finally {
            //readerWriter.endWrite();
        }
    }

    private void AddWord(String wordToAdd) {
        try {
            BufferedWriter out = new BufferedWriter(new FileWriter(fileName, true));
            out.write(wordToAdd + "\n");
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("exception occurred" + e);
        }

    }

    public String CountThreeWords(String firstTextField, String secondTextField, String thirdTextField) {
        try {
            //readerWriter.startRead();
            long start1 = System.nanoTime();
            String TotalResult = "";
            Thread thread1 = null, thread2 = null, thread3 = null;
            WordCountRunnable wc1 = new WordCountRunnable(secondTextField);
            WordCountRunnable wc2 = new WordCountRunnable(secondTextField);
            WordCountRunnable wc3 = new WordCountRunnable(secondTextField);

            if (!firstTextField.equals("")) {
                thread1 = new Thread(wc1);
                thread1.start();
            }
            if (!secondTextField.equals("")) {
                thread2 = new Thread(wc2);
                thread2.start();
            }
            if (!thirdTextField.equals("")) {
                thread3 = new Thread(wc3);
                thread3.start();
            }
            if (thread1 != null) {
                thread1.join();
                TotalResult += wc1.getResult();
            }
            if (thread2 != null) {
                thread2.join();
                TotalResult += wc2.getResult();
            }
            if (thread3 != null) {
                thread3.join();
                TotalResult += wc3.getResult();
            }
            long end1 = System.nanoTime();
            System.out.println("Elapsed Time in nano seconds: " + (end1 - start1));

            return TotalResult;

        } catch (Exception ex) {
            throw new RuntimeException(ex);
        } finally {
            readerWriter.endRead();
        }
    }

    public void CountMultithreadedWord(String filePath, String outputFile, int nbThreads) {
        try {
            readerWriter.startRead();
            MultithreadedWordCount multithreadedWordCount = new MultithreadedWordCount(filePath, outputFile, nbThreads);
            multithreadedWordCount.CountWords();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            readerWriter.endRead();
        }
    }

    public void ChangeWord(String word1, String word2, String filePath) {
        try {
            readerWriter.startWrite();
            Path fileName = Path.of(filePath);
            String str = Files.readString(fileName);
            System.out.println("Old file:\n" + str + "\n\n\n");
            str = str.replaceAll(word1, word2);
            System.out.println("New file:\n" + str);
            BufferedWriter out = new BufferedWriter(new FileWriter(filePath, false));
            out.write(str);
            out.close();
        } catch (Exception e) {
            System.err.println(e);
        } finally {
            readerWriter.endWrite();
        }
    }
}
