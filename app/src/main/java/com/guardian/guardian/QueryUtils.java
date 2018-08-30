package com.guardian.guardian;

import android.text.TextUtils;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

/**
 * helper methods to fetch data from The Guardian API.
 */
public class QueryUtils {
    /** Create a private constructor because no one should ever create a {@link QueryUtils} object. */
    private QueryUtils() {
    }

    /**
     * Ths method (function) for checking internet connection.
     * @return true if internet connection is present else return false
     */


    public static List<News> fetchNewsData(String requestUrl) {
        // Create URL object
        URL url = createUrl(requestUrl);

        // Perform HTTP request to the URL and receive a JSON response.
        String jsonResponse = null;
        try {
            jsonResponse = makeHttpRequest(url);
        } catch (IOException e) {
            e.printStackTrace();
        }

        List<News> newsList = extractFeatureFromJson(jsonResponse);

        return newsList;
    }


    /**
     * @param requestUrl is the given string url.
     * @return the url object.
     */
    private static URL createUrl(String requestUrl) {
        URL url = null;
        try {
            url = new URL(requestUrl);
        } catch (MalformedURLException e) {

            e.printStackTrace();
        }
        return url;
    }


    private static String makeHttpRequest(URL url) throws IOException {
        String jsonResponse = "";

        if (url == null) {
            return jsonResponse;
        }

        HttpURLConnection urlConnection = null;
        InputStream inputStream = null;
        try {
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setReadTimeout(10000 /* milliseconds */);
            urlConnection.setConnectTimeout(15000 /* milliseconds */);
            urlConnection.setRequestMethod("GET");// GET method.
            urlConnection.connect();

            // If the request is successful (code 200)
            // Read the input stream and parse the response.
            if (urlConnection.getResponseCode() == 200) {
                inputStream = urlConnection.getInputStream();
                jsonResponse = readFromStream(inputStream);
            } else {
                Log.d("Error response code: ", String.valueOf(urlConnection.getResponseCode()));
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
            if (inputStream != null) {

                inputStream.close();
            }
        }
        return jsonResponse;
    }

    /**
     * @param inputStream is the JSON stream bytes.
     * @return the JSON stream to string.
     * @throws IOException Problem retrieving the News JSON results.
     */
    private static String readFromStream(InputStream inputStream) throws IOException {
        StringBuilder output = new StringBuilder();
        if (inputStream != null) {
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream, Charset.forName("UTF-8"));
            BufferedReader reader = new BufferedReader(inputStreamReader);
            String line = reader.readLine();
            while (line != null) {
                output.append(line);
                line = reader.readLine();
            }
        }
        return output.toString();
    }

    private static List<News> extractFeatureFromJson(String newsJSON) {
        if (TextUtils.isEmpty(newsJSON)) {
            return null;
        }

        List<News> newsList = new ArrayList<>();


        try {

            JSONObject baseJsonResponse = new JSONObject(newsJSON);
            JSONObject response = baseJsonResponse.getJSONObject("response");
            JSONArray resultsArray = response.getJSONArray("results");

            for (int i = 0; i < resultsArray.length(); i++) {

                JSONObject currentResults = resultsArray.getJSONObject(i);

                String title = currentResults.getString("webTitle");
                String section = currentResults.getString("sectionName");
                String date = currentResults.getString("webPublicationDate");
                String url = currentResults.getString("webUrl");
                JSONArray tagsAuthor = currentResults.getJSONArray("tags");
                String author;
                if (tagsAuthor.length()> 0) {
                    JSONObject currentTagsAuthor = tagsAuthor.getJSONObject(0);
                    author = currentTagsAuthor.getString("webTitle");
                }else{
                    author = "No Author ..";
                }

                // Create a new {@Link News} object
                News news = new News(title, section, date, url, author);
                newsList.add(news);
            }

        } catch (JSONException e) {
            // catch the exception here, so the app doesn't crash. Print a log message.
            e.printStackTrace();
        }

        // Return the newsList
        return newsList;
    }
}
