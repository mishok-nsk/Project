import java.net.http.*;
import java.net.URI;
import java.util.*;
import com.google.gson.Gson;

public class HttpClientTest {
    
    class Forecast {
        
        public List<Daily> daily;
                
        public void cut() {
            for(int i = daily.size() - 1; i > 4; i--) {
                daily.remove(i);
            }
        }

        public void minFeelNightDifference() {
            double diff = Math.abs(daily.get(0).temp.night - daily.get(0).feels_like.night);
            int dayId = 0;
            double currentDiff;
            for(int i = 1; i <= 4; i++) {
                currentDiff = Math.abs(daily.get(i).temp.night - daily.get(i).feels_like.night);
                if(currentDiff < diff) {
                    diff = currentDiff;
                    dayId = i;
                }
            }
            Date date = new Date((long) daily.get(dayId).dt * 1000);
            String stringdate = String.format("%1$td %1$tB %1$tY", date);
            System.out.println("Day with minimum difference between the feels like and actual temperature: " + stringdate);
        }

        public void maxDayLength() {
            int maxDayLength = daily.get(0).sunset - daily.get(0).sunrise;
            int dayId = 0;
            int dayLength;
            for(int i = 1; i <= 4; i++) {
                dayLength = daily.get(i).sunset - daily.get(i).sunrise;
                if(dayLength > maxDayLength) {
                    maxDayLength = dayLength;
                    dayId = i;
                }
            }
            String dayLengthTime = dayLengthToTime(maxDayLength);
            Date date = new Date((long) daily.get(dayId).dt * 1000); 
            String stringdate = String.format("%1$td %1$tb %1$tY", date);
            System.out.println("Maximum day length " + dayLengthTime + " at " + stringdate);
        }

        private String dayLengthToTime(int DayLength) {
            int second = DayLength % 60;
            int min = DayLength / 60 % 60;
            int hours = DayLength / 3600 % 60;
            return hours + " h " + min + " m " + second + " s";
        }
    }

    class Daily {
        public int dt;
        public int sunrise;
        public int sunset;
        public Temp temp;
        public Temp feels_like;
       
    }

    class Temp {
        public double night;
    }

    private static String dataReciver() throws Exception {
        
        HttpClient client = HttpClient.newBuilder().build();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("https://api.openweathermap.org/data/2.5/onecall?lat=54.96781445&lon=82.95159894278376&exclude=minutely,hourly,alerts&units=metric&appid=0803df10fa9f10368182e511a2481ba9"))
                .header("Content-Type","application/x-www-form-urlencoded")
                .GET()
                .build();
 
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        
        if(response.statusCode()!=200) {
            System.out.println(response.statusCode());
            System.out.println(response.body());
            System.exit(1);
        }
        return response.body();
       
    } 
    
    public static void main(String[] args) throws Exception{

        String response = dataReciver();
        
        Gson gson = new Gson();
        Forecast forecast = gson.fromJson(response, Forecast.class);
        forecast.cut();
        forecast.minFeelNightDifference();
        forecast.maxDayLength();
                
    }

}
