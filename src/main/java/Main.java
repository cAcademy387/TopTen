import com.sun.deploy.util.BlackList;
import com.sun.org.omg.SendingContext._CodeBaseStub;

import java.io.FileInputStream;
import java.io.IOException;
import java.net.URL;
import java.sql.Time;
import java.util.*;
import java.util.concurrent.Callable;

public class Main {



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

            String Black[] = prop.getProperty("Blacklist").split("\\|");
            for (String sadrzaj:Black
                 ) {
                tt.blacklist.add(sadrzaj);
            }

            List<String> words = tt.parseText(text, delimiters, isLowerCase2);
            tt.countWords(words, zajednicka);

        }
        List<WordCount> listWC = new ArrayList<WordCount>();
        for (Map.Entry<String, Integer> wc : zajednicka.entrySet()) {
            listWC.add(new WordCount(wc.getKey(), wc.getValue()));
        }
        listWC.removeIf((wc) -> wc.count < 5); //filter
        //sort by count
        listWC.sort(
                (o1, o2) -> o2.count - o1.count
        );

        for (WordCount wc : listWC) System.out.println(wc.count + " - " + wc.word);

    }


}

