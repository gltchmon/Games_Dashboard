package Menu;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.TreeMap;

// platforms menu selection to get the menu items and results if specific item is selected from this menu
public class PlatformsMenu implements MenuInterface {
    private String platformsResults;


    // get all platform names to display as the menu dropdown
    @Override
    public ArrayList<String> getData() throws IOException, InterruptedException {
        platformsResults = gw.getAllPlatforms();
        ArrayList<String> platforms = new ArrayList<>();
        JSONArray platformResultsArr = new JSONArray(platformsResults);
        return createMenuItems(platformResultsArr);
    }

    // method to return the games of the platform the user has selected in the menu
    @Override
    public TreeMap<Integer, String> returnResults(String field, String platform) throws IOException, InterruptedException {
        platformsResults = gw.getPlatformGames(platform);
        JSONArray platformArr = new JSONArray(platformsResults);
        return createMap(platformArr);
    }

}
