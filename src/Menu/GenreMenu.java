package Menu;
import GamesWrapper.GamesWrapper;
import HelperMethods.HelperMethods;
import com.mashape.unirest.http.exceptions.UnirestException;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.TreeMap;



// class to hold data on genremenu
public class GenreMenu implements MenuInterface {
    private String genres_res;
    @Override
    public ArrayList<String> getData() throws IOException, InterruptedException {
        genres_res = gw.getAllGenres();
        ArrayList<String> genres = new ArrayList<>();
        JSONArray genreGamesArr = new JSONArray(genres_res);
        return createMenuItems(genreGamesArr);
    }

    // return results of genre clicked
    @Override
    public TreeMap<Integer , String> returnResults(String field ,String value) throws IOException, InterruptedException {
        genres_res = gw.getGenreGames(value);
        JSONArray genreGamesArr = new JSONArray(genres_res);
        return createMap(genreGamesArr);
    }



}
