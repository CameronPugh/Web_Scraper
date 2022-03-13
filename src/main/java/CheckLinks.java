import org.apache.commons.io.IOUtils;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class CheckLinks {

    ArrayList<Result> results;

    CheckLinks( ArrayList<Result> results){
        this.results = results;
    }

    public ArrayList<Result> checkLinks() {
        for (int i = 0; i < results.size(); i++) {
            try {
                URL temp = new URL(results.get(i).getUrl());
                URI uri = temp.toURI();
                System.out.println("URL from URI: " + temp);
                System.out.println("URI created: " + uri);
                results.get(i).setUri(uri);

                String command =
                        "curl -s http://link-checker-api.dev.gov.uk/check\\?uri\\=" + uri.toString();
                ProcessBuilder processBuilder = new ProcessBuilder(command.split(" "));

                processBuilder.directory(new File("/Users/cameronpugh/"));
                Process process = processBuilder.start();
                InputStream inputStream = process.getInputStream();
                String result = IOUtils.toString(inputStream, StandardCharsets.UTF_8);
                System.out.println(result);
                result = "true";
                results.get(i).setIsSafe(result);
                int exitCode = process.waitFor();

            } catch (URISyntaxException e) {
                System.out.println("URI Syntax Error: " + e.getMessage());
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        return results;
    }


}

