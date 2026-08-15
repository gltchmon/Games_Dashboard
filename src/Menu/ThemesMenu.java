package Menu;

import GamesWrapper.GamesWrapper;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.TreeMap;

// class that holds data of the game theme menu
public class ThemesMenu implements MenuInterface {

    private String themes_results;

    // get all the different themes to put in the menu drop down
    @Override
    public ArrayList<String> getData() throws IOException, InterruptedException {
        themes_results = gw.getAllThemes();
        ArrayList<String> themes = new ArrayList<>();
        JSONArray genreGamesArr = new JSONArray(themes_results);
        for (int i = 0; i < genreGamesArr.length(); i++) {
            JSONObject jsonobject = genreGamesArr.getJSONObject(i);
            String name = jsonobject.getString("name");
            themes.add(name);
        }
        return themes;
    }

    // return the correct games depending on the theme the user has selected
    @Override
    public TreeMap<Integer, String> returnResults(String field, String theme) throws IOException, InterruptedException {
        themes_results = gw.getThemeGames(theme);
        TreeMap<Integer, String> themeGames = new TreeMap<>();
        JSONArray themeArr = new JSONArray(themes_results);
        for (int i = 0; i < themeArr.length(); i++) {
            JSONObject jsonobject = themeArr.getJSONObject(i);
            String name = jsonobject.getString("name");
            Integer id = jsonobject.getInt("id");
            themeGames.put(id,name);
        }
        return themeGames;
    }
}
