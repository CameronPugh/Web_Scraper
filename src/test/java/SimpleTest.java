
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.*;

public class SimpleTest {
    WebScraper w = new WebScraper();
    ArrayList<Result> ar =  w.getPageInfo("http://slashdot.org");

    @Test
    public void returnsID() {
        assertNotNull(ar.get(0).getId());
    }
    @Test
    public void returnsUrl() {
        assertNotNull(ar.get(0).getUrl());
    }
    @Test
    public void returnsType() {
        assertNotNull(ar.get(0).getType());
    }
    @Test
    public void returnsMultipleLinks(){
        assertTrue(ar.size()>100);
    }
    @Test
    public void linkCheckIsFalse(){
        assertSame(ar.get(2).getIsSafe(),"false");
    }
    @Test
    public void linkCheckIsTrue(){
        assertSame(ar.get(6).getIsSafe(),"true");
    }
}
