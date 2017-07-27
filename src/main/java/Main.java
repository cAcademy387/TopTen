import com.sun.deploy.util.BlackList;
import com.sun.org.omg.SendingContext._CodeBaseStub;

import java.io.FileInputStream;
import java.io.IOException;
import java.net.URL;
import java.sql.Time;
import java.util.*;
import java.util.concurrent.Callable;

public class Main {



    public static void main(String[] args) throws Exception {

    TopTen tt = new TopTen();

        for (WordCount wc : tt.getResults()) System.out.println(wc.count + " - " + wc.word);


    }
    }

