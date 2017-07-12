import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URL;
import java.util.*;

public class Main {

//    ArrayList<String> parseText(String text, List<Character> delimiters){
//
//        int d=0; //position of last delimiter
//        for(int i=0; i < text.length(); i++){
//            char c = text.charAt(i);
//            if(delimiters.contains(c)) {
//                String word = text.substring(d,i);
//                if(word.length() > 0) System.out.println(word);
//                d = i+1;
//            }
//        }
//    }
    static class WordCount {
    public WordCount(String word, int count) {
       this.word = word;
        this.count = count;
    }
    String word;
    int count;
}
    public static void main(String[] args) throws Exception{
        List<Character> delimiters = Arrays.asList(' ', ',' ,'.' , ':', '!', '?', '"', '(', ')','-');


        Properties prop = new Properties();
        OutputStream output = null;

        try {

            output = new FileOutputStream("config.properties");

            // set the properties value
            prop.setProperty("WebSites", "klix.ba|oslobodjenje.ba|avaz.ba");
            prop.setProperty("user", "XXXX");
            prop.setProperty("password", "XXX");
            prop.setProperty("LowerCase", "Enabled");

            // save properties to project root folder
            prop.store(output, null);

        } catch (IOException io) {
            io.printStackTrace();
        } finally {
            if (output != null) {
                try {
                    output.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

        }

        URL url = new URL("https://www.klix.ba/");
        TopTen tt = new TopTen();
        String html = tt.getHtml(url);
        String body = tt.getBody(html);
        String text = tt.removeHtmlTags(body);
        List<String> words = tt.parseText(text, delimiters);
        Map<String, Integer> wordCount = tt.countWords(words);
        List<WordCount> listWC = new ArrayList<WordCount>();

        for(Map.Entry<String,Integer> wc : wordCount.entrySet()){
            listWC.add(new WordCount(wc.getKey(), wc.getValue()));
        }
        //sort by count
        Collections.sort(listWC, new Comparator<WordCount>() {
            public int compare(WordCount o1, WordCount o2) {
                return o2.count - o1.count;
            }
        });

        for(WordCount wc : listWC) System.out.println(wc.count + " - " + wc.word);


    }
}
