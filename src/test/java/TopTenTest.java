import java.util.ArrayList;
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
        String res = tt.removeHtmlTags(null, "<div>TEST</div>" );
        assert (res.compareTo("-TEST-") == 0);
    }

    @org.junit.Test
    public void SkipTags() throws Exception {
        ArrayList<String> Tags = new ArrayList<String>();
        Tags.add("script");
        Tags.add("header");
        String res = tt.removeHtmlTags(Tags,"<script nesto > Text1 </script> <header> Text 2 </header> <h2> Text 3 </h2>");
        assert (res.compareTo("Text3")==1);
        String res1 = tt.removeHtmlTags(null,"<script nesto > Text1 </script> <header> Text 2 </header> <h2> Text 3 </h2>");
        assert (res.compareTo("Text1Text2Text3")==1);
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
        List<String> res = tt.parseText(",, ,,", delimiters, true );
        assert (res.size() == 0);

        res = tt.parseText(",jedan, ,dva,", delimiters, true );
        assert (res.size() == 2);

        res = tt.parseText(",jedan i dva i tri sa cetiri", delimiters, true );
        assert (res.size() == 4);

        res = tt.parseText(",jedan i dva i tri sa cetiri, 23mm,", delimiters, true );
        assert (res.size() == 4);

        res = tt.parseText(",jedan i dva i tri sa cetiri, samo sve 23mm,", delimiters, true );
        assert (res.size() == 4);
    }

    @org.junit.Test
    public void VelikaMalaSlova() throws Exception {
        List<Character> delimiters = Arrays.asList(' ', ',' ,'.' , ':', '!', '?', '"', '(', ')');
        List<String> res = tt.parseText(",, ,,", delimiters, true );
        assert (res.size() == 0);

        res = tt.parseText(",Veliko malo Veliko malo Veliko Malo,", delimiters, true );
        assert (res.size() == 6);

        res = tt.parseText(",Veliko malo Veliko malo Veliko Malo,", delimiters, false );
        assert (res.size() == 6);
    }

    @org.junit.Test
    public void countWords() throws Exception {


    }

}