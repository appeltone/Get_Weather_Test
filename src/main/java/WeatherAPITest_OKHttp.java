import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;
import org.json.JSONArray;
import org.json.JSONObject;


import java.io.IOException;
import java.util.Scanner;

public class WeatherAPITest_OKHttp {
    // using HttpClient (java 11 and up)
    private static String city = "Jerusalem";
    private static String units = "metric";
    private static String apiKey = "9127ef624a1cea783eccad4f0b0cab55";
    private static String tempResponse;
    public static void main(String[] args) {

        Scanner input = new Scanner(System.in);
        System.out.println("What city do you live in?");
        city = input.nextLine();

        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder().url("http://api.openweathermap.org/data/2.5/weather?q=" + city + "&units=" + units + "&appid=" + apiKey).build();
        try {
            Response response = client.newCall(request).execute();

            //get all the response as JSON object - notice using OKHTTP is response.body().string() and not response.toString()
            JSONObject responseJSONObject = new JSONObject(response.body().string());
            //get the desired object within the response (nested object)
            JSONObject mainObject = responseJSONObject.getJSONObject("main");
            //get the temp and humidity from the main object in the JSON response
            float temp = mainObject.getFloat("temp");
            float humidity = mainObject.getFloat("humidity");
            //print results
            System.out.println("The temperature in " + city + " is --> " + temp);
            System.out.println("The humidity in " + city + " is --> " + humidity);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }





}
