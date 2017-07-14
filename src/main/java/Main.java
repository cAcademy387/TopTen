import java.io.FileInputStream;
import java.io.IOException;
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

        try {
            FileInputStream input = new FileInputStream("config.properties");
            prop.load(input);
        } catch (IOException io) {
            io.printStackTrace();
        }

        String adrese[] = prop.getProperty("WebSites").split("\\|");
        Map<String, Integer> zajednicka = new HashMap<String, Integer>();
        for (String adresa : adrese) {
            URL url = new URL(adresa);
            TopTen tt = new TopTen();
            String html = tt.getHtml(url);
            String body = tt.getBody(html);
            String text = tt.removeHtmlTags(body);

            List<String> words = tt.parseText(text, delimiters);
            tt.countWords(words, zajednicka);


        }
        List<WordCount> listWC = new ArrayList<WordCount>();
        for (Map.Entry<String, Integer> wc : zajednicka.entrySet()) {
            listWC.add(new WordCount(wc.getKey(), wc.getValue()));
        }
        //sort by count
        Collections.sort(listWC, new Comparator<WordCount>() {
            public int compare(WordCount o1, WordCount o2) {
                return o2.count - o1.count;
            }
        });

        for (WordCount wc : listWC) System.out.println(wc.count + " - " + wc.word);

    }
}
