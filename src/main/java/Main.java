import com.sun.org.omg.SendingContext._CodeBaseStub;

import java.io.FileInputStream;
import java.io.IOException;
import java.net.URL;
import java.sql.Time;
import java.util.*;
import java.util.concurrent.Callable;

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

        try {
            FileInputStream input = new FileInputStream("config.properties");
            prop.load(input);
        } catch (IOException io) {
            io.printStackTrace();
        }

        String adrese[] = prop.getProperty("WebSites").split("\\|");
        String isLowerCase = prop.getProperty("LowerCase");
        boolean isLowerCase2 = Boolean.parseBoolean(isLowerCase);


        Map<String, Integer> zajednicka = new HashMap<String, Integer>();
        for (String adresa : adrese) {
            URL url = new URL(adresa);
            TopTen tt = new TopTen();
            String html = tt.getHtml(url);
            String body = tt.getBody(html);


            String Tags []= prop.getProperty("Tags").split("\\|");

            String text;
            ArrayList<String> T = new ArrayList<String>(Arrays.asList(Tags));
            text = tt.removeHtmlTags(T, body);



            for(int i=0; i < 10000; i++) tt.blacklist.add("Test" + i); //test

            long startTime = System.currentTimeMillis();

            List<String> words = tt.parseText(text, delimiters, isLowerCase2);
            long stopTime = System.currentTimeMillis();
            long elapsedTime = stopTime - startTime;
            System.out.println("Metod parseText "+ elapsedTime + " ms");

            tt.countWords(words, zajednicka);


        }
        List<WordCount> listWC = new ArrayList<WordCount>();
        for (Map.Entry<String, Integer> wc : zajednicka.entrySet()) {
            listWC.add(new WordCount(wc.getKey(), wc.getValue()));
        }
        //sort by count
        listWC.removeIf((wc) -> wc.count < 5);
        listWC.sort((o1, o2) -> o1.word.compareTo(o2.word));
        listWC.sort(
                (o1, o2) -> o2.count - o1.count
        );
        //listWC.removeIf((wc) -> wc.word.charAt(0) != 's');

        for (WordCount wc : listWC) System.out.println(wc.count + " - " + wc.word);


        //System.out.format("Milli = %s, ( S_Start : %s, S_End : %s ) \n", elapsedTime, startTime, stopTime );


    }


}

