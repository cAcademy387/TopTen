import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.*;
import java.util.concurrent.Callable;


public class TopTen  implements TopTenInterface {


    public List<WordCount> getResults() throws MalformedURLException {

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
            String html = getHtml(url);
            String body = getBody(html);

            String Tags []= prop.getProperty("Tags").split("\\|");
            String text;
            ArrayList<String> T = new ArrayList<String>(Arrays.asList(Tags));
            text = removeHtmlTags(T, body);

            String Black[] = prop.getProperty("Blacklist").split("\\|");
            for (String sadrzaj:Black
                    ) {
                blacklist.add(sadrzaj);
            }

            List<String> words = parseText(text, delimiters, isLowerCase2);
            countWords(words, zajednicka);

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

        return listWC;

    }



    public String getHtml(URL url) {
        InputStream is = null;
        BufferedReader br;
        String line;
        StringBuilder sb = new StringBuilder();

        try {
            // url = new URL("https://www.klix.ba/");

            URLConnection connection = url.openConnection();

            //configure connection
            is = connection.getURL().openStream();  // throws an IOException
        br = new BufferedReader(new InputStreamReader(is));
        while ((line = br.readLine()) != null) {
            sb.append(line);
            //System.out.println(line);
        }
        } catch (MalformedURLException mue) {
            mue.printStackTrace();
        } catch (IOException ioe) {
            ioe.printStackTrace();
        } finally {
            try {
                if (is != null) is.close();
            } catch (IOException ioe) {
                // nothing to see here
            }
        }
        return sb.toString();
    }


    public String getBody(String html) {
        int startIndex = html.indexOf("<body");
        int endIndex = html.indexOf("</body>", startIndex);
        return html.substring(startIndex, endIndex);
    }
    private String SkipTags (List<String> Tags, String body)

    {

        StringBuilder sb = new StringBuilder();
        int firstI=-1;
        int lastI =-1;
        int i=0;
        while (i<body.length()){
            for (String tag : Tags) {
                firstI = body.indexOf("<"+tag, i);
                lastI = body.indexOf("/"+tag+">", firstI) + tag.length()+2;
                if (firstI < 0) {
                    sb.append(body.substring(i));
                    i = body.length();
                } else {
                    sb.append(body.substring(i, firstI));
                    i = lastI;
                }
            }
        }
        return sb.toString();
    }

	

	
    public String removeHtmlTags(List<String> tags, String body) {
        StringBuilder sb = new StringBuilder();
		body = SkipTags (tags, body);
        int start = -1;
        //int end = -1;
        for(int i=0; i < body.length(); i++)
        {
            char c = body.charAt(i);
            if(c == '<') {
                start = i;
                continue;
            }

            if(c == '>') {
                start = -1;
                sb.append('-');
                continue;
            }

            if(start > -1) continue;
            sb.append(c);

        }

        return sb.toString();
    }

    HashSet<String> blacklist = new HashSet<>();
            //Arrays.asList("Sarajevo", "BiH");

     public List<String> parseText(String text, List<Character> delimiters, boolean isLowerCase){

        List<String> res = new ArrayList<>();
        int d=0; //position of last delimiter
        for(int i=0; i < text.length(); i++){
            char c = text.charAt(i);
            if(delimiters.contains(c)) {
                String word = text.substring(d,i);
                if (isLowerCase == true) {
                    word = word.toLowerCase();
                }
                if(     word.length() > 2 &&
                        !blacklist.contains(word) &&
                        Character.isLetter(word.charAt(0))) res.add(word);
                d = i+1;
            }
        }
            //TODO
         String word = text.substring(d);
         word = word.toLowerCase();
         if(word.length() > 2 && !blacklist.contains(word) && Character.isLetter(word.charAt(0))) res.add(word);
        return res;
    }

    public void countWords(List<String> words, Map<String, Integer> postojeca) {
        Map<String, Integer> WordCount = postojeca;
        for(String w: words){
            if(WordCount.containsKey(w)) WordCount.put(w, WordCount.get(w) + 1);
            else WordCount.put(w,1);
        }

    }



}
