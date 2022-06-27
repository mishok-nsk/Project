//import java.net.URLConnection;
import java.net.HttpURLConnection;
import java.net.URL;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.File;
import java.io.FileWriter;

public class HttpConnector {
    
    static final int CONNECTION_TIMEOUT = 100;

    public static void main(String[] args) throws Exception{
    final URL url = new URL("https://api.openweathermap.org/data/2.5/weather?q=Novosibirsk&appid=0803df10fa9f10368182e511a2481ba9");
    final HttpURLConnection con = (HttpURLConnection) url.openConnection();

    con.setRequestMethod("GET");
    con.setRequestProperty("Content-Type", "application/json");
    con.setConnectTimeout(CONNECTION_TIMEOUT);
    con.setReadTimeout(CONNECTION_TIMEOUT);
    
    try (final BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()))) {
        String inputLine;
        final StringBuilder content = new StringBuilder();
        while ((inputLine = in.readLine()) != null) {
            content.append(inputLine);
        }
        //return content.toString();
        //System.out.println(content);
        File file = new File("json.txt");
        FileWriter fr = new FileWriter(file); 
        fr.write(content.toString());
        fr.close();

    } catch (final Exception ex) {
        System.out.println("Exception");
        ex.printStackTrace();
        //return "";
        //System.out.println("Exception");
    }
    }
}
