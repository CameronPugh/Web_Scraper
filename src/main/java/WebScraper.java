import com.gargoylesoftware.htmlunit.*;
import com.gargoylesoftware.htmlunit.html.*;
import org.apache.commons.io.IOUtils;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

public class WebScraper {

    ArrayList<Result> results = new ArrayList<>();

    public ArrayList<Result> getPageInfo(String url) {

        WebClient webClient = new WebClient(BrowserVersion.CHROME);
        FileWriter recipesFile;

        try {
            recipesFile = new FileWriter("recipes.csv", true);

            HtmlPage page = webClient.getPage(url);
            webClient.waitForBackgroundJavaScript(3000);
            webClient.getOptions().setJavaScriptEnabled(true);

            webClient.getOptions().setCssEnabled(false);
            webClient.getOptions().setThrowExceptionOnFailingStatusCode(false);
            webClient.getOptions().setThrowExceptionOnScriptError(false);
            webClient.getOptions().setPrintContentOnFailingStatusCode(false);

            webClient.getCurrentWindow().getJobManager().removeAllJobs();
            webClient.close();

            String title = page.getTitleText();
            System.out.println("Page Title: " + title);

            List<HtmlAnchor> links = page.getAnchors();
            int i = 0;
            for (HtmlAnchor link : links) {
                i++;
                String x = link.getNameAttribute();
                String href = link.getHrefAttribute();

                Result r = new Result(href,i,x);
                results.add(r);
            }

            List<?> anchors = page.getByXPath( "//h1");
            for (i = 0; i < anchors.size(); i++) {
                try {

                    HtmlHeading1 link = (HtmlHeading1) anchors.get(i);
                    System.out.println(link.toString());
                    String a = link.getAttribute("id");
                    System.out.println(a);
                }catch (Exception e){}
            }

            CheckLinks cl = new CheckLinks(results);
            ArrayList<Result> finalResults = cl.checkLinks();

            recipesFile.write("id,type,link,isSafe?\n");
            for (i = 0; i < finalResults.size(); i++) {
                recipesFile.write(finalResults.get(i).getId() + ","
                        + finalResults.get(i).getType() + ","
                        + finalResults.get(i).getUrl() + ","
                        + finalResults.get(i).getIsSafe() + "\n");
            }
            recipesFile.close();

            return finalResults;


        } catch (IOException e) {
            System.out.println("An error occurred: " + e);
        }
        return null;

    }
    public static void main(String[] args) {
        WebScraper ws =new WebScraper();
        ArrayList<Result> r =  ws.getPageInfo(args[0]);
    }
}
