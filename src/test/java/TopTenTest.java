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
        String res = tt.removeHtmlTags("<div>TEST</div>" );
        assert (res.compareTo("-TEST-") == 0);

        String res2 = tt.removeHtmlTags("<script>jQuery(function($){\n" +
                "  $('document').ready(function(){\n" +
                "\t\tsetTimeout(function(){\n" +
                "\t\t\t$('#backgroundPopup').fadeIn(1000);\n" +
                "\t\t\t$('#toPopup').fadeIn(1100);\n" +
                "\t\t},100);\n" +
                "\t});\n" +
                "\t$('#close,#backgroundPopup').click(function(){\n" +
                "\t\t$('#toPopup').fadeOut();\n" +
                "\t\t$('#backgroundPopup').fadeOut();\n" +
                "\t});\n" +
                "\t$(this).keyup(function(e){\n" +
                "\t\tif(e.which == 27){\n" +
                "\t\t\t$('#toPopup').fadeOut();\n" +
                "\t\t\t$('#backgroundPopup').fadeOut();\n" +
                "\t\t}\t\n" +
                "\t});\n" +
                "});</script>" );

        int startIndex = res2.indexOf("<script");
        int endIndex = res2.indexOf("</script>" ,startIndex);
        String replacement = "I AM JUST A REPLACEMENT";
        String toBeReplaced = res2.substring(startIndex + 1, endIndex);
        System.out.println(res2.replace(toBeReplaced, replacement));
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

        res = tt.parseText(",jedan i dva i tri sa cetiri, 23mm,", delimiters );
        assert (res.size() == 4);
    }

    @org.junit.Test
    public void countWords() throws Exception {


    }

}