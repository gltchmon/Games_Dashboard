package GamesWrapper;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

// this is the class that deals with getting the correct type of information
public class GamesWrapper {
    private final String BASE_URL = "https://api.igdb.com/v4/games";
    private final HttpClient client;
    // may need to hide this later on
    private final String[] headers = {"Client-ID", System.getenv("CLIENT_ID"),
            "Authorization",  System.getenv("AUTHORIZATION"),
            "Accept", System.getenv("ACCEPT")};

    public GamesWrapper() {
        client = HttpClient.newHttpClient();
    }

    public String getAllGames () throws IOException, InterruptedException{
        /*
         in order to run this code and view the dashboard you will need to get your own API key from the igdb api
         whhich can be found here https://api-docs.igdb.com/#getting-started
         follow the instructions on getting your own api key and add it to the program to be able to run this
         */
        HttpRequest request = HttpRequest.newBuilder()
                // turn base url into uri
                .uri(URI.create(BASE_URL))
                .headers("Client-ID", System.getenv("CLIENT_ID"),
                        "Authorization", System.getenv("AUTHORIZATION"),
                        "Accept", System.getenv("ACCEPT"))
                .POST(HttpRequest.BodyPublishers.ofString("fields name; limit 200;"))
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        return response.body();
    }

    public String getGamesBeginningWith(String letter)  throws IOException, InterruptedException {
        String fields =  "fields name;\n" + "where name ~ \"%s\"*;\n";
        fields = String.format(fields,letter);
        HttpRequest request = HttpRequest.newBuilder()
                // turn base url into uri
                .uri(URI.create(BASE_URL))
                .headers(headers)
                .POST(HttpRequest.BodyPublishers.ofString(fields))
                .build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        return response.body();
    }

    public String getAllGenres() throws IOException, InterruptedException{
        return makeRequest("genres","fields name; limit 50;");

    }

    public String getGenreGames(String genre) throws IOException, InterruptedException {
        String fields =  "fields name;\n" +
                "where genres.name = \"%s\"; limit 100;";
        fields = String.format(fields, genre);
        return makeRequest(null,fields);
    }

    public String getAllThemes() throws IOException, InterruptedException{
        return makeRequest("themes","fields name; limit 50;");
    }

    public String getThemeGames(String theme) throws IOException, InterruptedException {
        String fields =  "fields name;\n" +
                "where themes.name = \"%s\"; limit 100;";
        fields = String.format(fields, theme);
        return makeRequest(null, fields);
    }

    public String getAllPlatforms() throws IOException, InterruptedException {
        return makeRequest("platforms","fields name; limit 15;");
    }

    public String getPlatformGames(String platform) throws IOException, InterruptedException {
        String fields =  "fields name;\n" +
                "where platforms.name = \"%s\"; limit 100;";
        fields = String.format(fields, platform);
        return makeRequest(null, fields);
    }

    public String getLetterGames(String letter) throws IOException, InterruptedException {
        String fields =  "fields name; where name ~ \"%s\"*; limit 100;";
        fields = String.format(fields, letter);
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(BASE_URL))
                .headers(headers)
                .POST(HttpRequest.BodyPublishers.ofString(fields))
                .build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        return response.body();
    }

    // method to search for a game in a specific field
    public String searchForGame(String field, String entry) throws IOException, InterruptedException {
        String fields =  "fields name; where %s.name ~ *\"%s\"*; limit 100;";
        fields = String.format(fields, field,entry);
        return makeRequest(null,fields);
    }

    // search for game including certain words
    public String searchForGameNameIncluding(String name) throws IOException, InterruptedException {
        String fields =  "fields name; search \"%s\";limit 100;";
        fields = String.format(fields,name);
        return makeRequest(null,fields);
    }

    public String getGameById(String id) throws IOException, InterruptedException {
        String fields = String.format("fields name, themes.name, storyline, total_rating, " +
                "genres.name, first_release_date, cover.url, alternative_names.name; " +
                "where id = %s;limit 100;", id);
        return makeRequest(null,fields);
    }

    // method to make the requests
    private String makeRequest(String endpoint, String fields) throws IOException, InterruptedException {
        String res = "";
        if(endpoint != null){
            String path = String.format("https://api.igdb.com/v4/%s",endpoint);
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(path))
                    .headers(headers)
                    .POST(HttpRequest.BodyPublishers.ofString(fields))
                    .build();
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            res = response.body();
        } else {
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(BASE_URL))
                    .headers(headers)
                    .POST(HttpRequest.BodyPublishers.ofString(fields))
                    .build();
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            res = response.body();
        }
        return res;
    }
}
