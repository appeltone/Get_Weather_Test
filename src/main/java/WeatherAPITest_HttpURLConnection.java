import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONString;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Map;
import java.util.Scanner;

public class WeatherAPITest_HttpURLConnection {

    private static HttpURLConnection conn;
    private static String city = "Jerusalem";
    private static String units = "metric";
    private static String apiKey = "9127ef624a1cea783eccad4f0b0cab55";

    public static void main(String[] args) {
        // using HttpURLConnection

        BufferedReader reader;
        String line;
        StringBuffer responseText = new StringBuffer();

        Scanner input = new Scanner(System.in);
        System.out.println("What city do you live in?");
        city = input.nextLine();


        try {
            URL url = new URL("http://api.openweathermap.org/data/2.5/weather?q=" + city + "&units=" + units + "&appid=" + apiKey);
            conn = (HttpURLConnection) url.openConnection();

            // Define the request
            conn.setRequestMethod("GET");
            conn.setConnectTimeout(5000);
            conn.setReadTimeout(5000);

            // try to get a response
            int status = conn.getResponseCode();
            //System.out.println(status);

            //handle first a bad http request (conn.getErrorStream or conn.getInputStream)
            if (status > 299) {
                reader = new BufferedReader(new InputStreamReader(conn.getErrorStream()));
                while ((line = reader.readLine()) != null) {
                    responseText.append(line);
                }
                reader.close();
            } else {
                reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                while ((line = reader.readLine()) != null) {
                    responseText.append(line);
                }
                reader.close();
            }

            //get all the response as JSON object
            JSONObject responseJSONObject = new JSONObject(responseText.toString());
            //get the desired object within the response (nested object)
            JSONObject mainObject = responseJSONObject.getJSONObject("main");
            //get the temp and humidity from the main object in the JSON response
            float temp = mainObject.getFloat("temp");
            float humidity = mainObject.getFloat("humidity");
            //print results
            System.out.println("The temperature in " + city + " is --> " + temp);
            System.out.println("The humidity in " + city + " is --> " + humidity);

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            conn.disconnect();
        }

    }


}
