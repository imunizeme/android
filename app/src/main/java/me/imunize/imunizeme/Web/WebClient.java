package me.imunize.imunizeme.Web;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Scanner;

/**
 * Created by Sr. Décio Montanhani on 14/08/2017.
 */

public class WebClient {
    public String post(String json, String urlAPI) {
        try {
            URL url = new URL(urlAPI);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestProperty("Content-type", "application/json");
            connection.setRequestProperty("Accept", "application/json");

            connection.setDoOutput(true);

            PrintStream output = new PrintStream(connection.getOutputStream());
            output.println(json);

            connection.connect();

            Scanner scanner = new Scanner(connection.getInputStream());
            String resposta = scanner.next();
            return resposta;
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public String get(String json, String urlAPI){

        StringBuilder result = new StringBuilder();
        try {
            URL url = new URL(urlAPI);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();

            connection.setRequestMethod("GET");
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String line;

            while ((line = bufferedReader.readLine()) != null) {
                result.append(line);
            }

        }catch (MalformedURLException e){
                e.printStackTrace();
            }catch (IOException e){
                e.printStackTrace();
            }

            return result.toString();
        }

    }

