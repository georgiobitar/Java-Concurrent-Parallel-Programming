
import java.io.*;
        import java.util.HashMap;
        import java.util.Map;
//two files, find similarities
//img processing

public class DataAnalysis {
    public static void main(String[] args) throws IOException {
        File file = new File("C:\\Georgio\\University\\Sem8\\Java\\TextReader.txt");
        FileInputStream fileInputStream = new FileInputStream(file);
        InputStreamReader inputStreamReader = new InputStreamReader((fileInputStream));
        BufferedReader bufferedReader = new BufferedReader((inputStreamReader));

        //storing frequencies
        Map<String, Integer> freqMap = new HashMap<>();

        String line;
        int wordCount = 0;
        int characterCount = 0;
        int paraCount = 0;
        int whiteSpaceCount = 0;
        int sentenceCount = 0;

        while((line = bufferedReader.readLine()) !=null){
            if(line.strip().equals("")){
                paraCount+=1;
            }
            else{
                characterCount += line.length();
                String words[] = line.split("\\s+");

                for(int i =0;i<words.length;i++) {
                    if (freqMap.containsKey(words[i])) {
                        Integer count = freqMap.get(words[i]);
                        freqMap.put(words[i], count + 1);
                    } else {
                        freqMap.put(words[i], 1);
                    }
                }
                wordCount += words.length;
                whiteSpaceCount += wordCount - 1;
                String sentence[] = line.split("[!?.:]+");
                sentenceCount += sentence.length;
            }
        }
        if (sentenceCount >= 1) {
            paraCount++;
        }
        System.out.println("Total word count = "+ wordCount);
        System.out.println("Total number of sentences = "+ sentenceCount);
        System.out.println("Total number of characters = "+ characterCount);
        System.out.println("Number of paragraphs = "+ paraCount);
        System.out.println("Total number of whitespaces = "+ whiteSpaceCount);

        System.out.println("Frequencies:");
        for (Map.Entry<String,Integer> entry : freqMap.entrySet())
            System.out.println("Key = " + entry.getKey() +
                    ", Value = " + entry.getValue());
    }
}
