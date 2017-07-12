import java.util.Arrays;
import java.util.List;

/**
 * Created by arifcengic on 7/10/17.
 */
public class TopTenTest {
    TopTen tt;

    @org.junit.Before
    public void setUp() throws Exception {
        tt = new TopTen();
    }

    @org.junit.After
    public void tearDown() throws Exception {

    }

    @org.junit.Test
    public void getHtml() throws Exception {

    }

    @org.junit.Test
    public void getBody() throws Exception {

    }

    @org.junit.Test
    public void removeHtmlTags() throws Exception {

    }
    
    //        long startTime = System.currentTimeMillis();
//        List<Character> delimiters = Arrays.asList(' ', ',' ,'.' , ':', '!', '?', '"', '(', ')');
//
//        FileReader fr = new FileReader("./src/main/java/sample.txt");
//        BufferedReader txtReader = new BufferedReader(fr);
//        //StringBuilder text = new StringBuilder();
//        String line1;
//        TopTen tt = new TopTen();
//        while ((line1 = txtReader.readLine()) != null) tt.parseText(line1, delimiters);
//
//
//        long stopTime = System.currentTimeMillis();
//        long elapsedTime = stopTime - startTime;
//        System.out.println("Time " + elapsedTime);
//
//        fr.close();
//        txtReader.close();
    @org.junit.Test
    public void parseText() throws Exception {
        List<Character> delimiters = Arrays.asList(' ', ',' ,'.' , ':', '!', '?', '"', '(', ')');
        List<String> res = tt.parseText(",, ,,", delimiters );
        assert (res.size() == 0);

        res = tt.parseText(",jedan, ,dva,", delimiters );
        assert (res.size() == 2);

        res = tt.parseText(",jedan i dva i tri sa cetiri", delimiters );
        assert (res.size() == 4);

        res = tt.parseText(",jedan i dva i tri sa cetiri, 23mm", delimiters );
        assert (res.size() == 4);
    }

    @org.junit.Test
    public void countWords() throws Exception {

    }

}