import java.net.URL;
import java.sql.Time;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;

/**
 * Created by arifcengic on 7/9/17.
 */
public interface TopTenInterface {

    String getHtml(URL url);
    String getBody(String html);
    String removeHtmlTags(String body);
    List<String> parseText(String text, List<Character> delimiters, boolean isLowerCase);
    void countWords(List<String> words, Map<String, Integer> postojeca);


}
