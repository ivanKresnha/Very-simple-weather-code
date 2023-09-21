import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import org.json.JSONObject;
import java.text.DecimalFormat;
import java.util.Scanner;

public class appcuacajava {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Masukkan nama kota: ");
        String kota = scanner.nextLine();
        scanner.close();

        try {
            String apiKey = "82e27d9552ac49ca99b104842231909"; // Ganti dengan API key Anda dari WeatherAPI
            String urlString = "https://api.weatherapi.com/v1/current.json?key=" + apiKey + "&q=" + kota;
            URL url = new URL(urlString);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();

            BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            StringBuilder buffer = new StringBuilder();
            String line;

            while ((line = reader.readLine()) != null) {
                buffer.append(line);
            }

            reader.close();
            connection.disconnect();

            String jsonString = buffer.toString();

            // Analisis data JSON
            JSONObject json = new JSONObject(jsonString);
            String namaKota = json.getJSONObject("location").getString("name");
            JSONObject current = json.getJSONObject("current");
            double suhuCelsius = current.getDouble("temp_c"); // Suhu sudah dalam Celsius

            // Memformat suhu ke satu angka dibelakang koma
            DecimalFormat df = new DecimalFormat("#.#");
            String suhuFormatted = df.format(suhuCelsius);

            String kondisiCuaca = current.getJSONObject("condition").getString("text");

            System.out.println("Cuaca di " + namaKota + ":");
            System.out.println("Suhu: " + suhuFormatted + "°C");
            System.out.println("Kondisi Cuaca: " + kondisiCuaca);
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
}
