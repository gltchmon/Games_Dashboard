package Menu;

import GamesWrapper.GamesWrapper;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.TreeMap;

// interface for menu items in the menu bar
// interface was used because the menu item implementations will all follow the same structure and contain the same methods as they perform
// the same functionality but in different ways

public interface MenuInterface {
    final GamesWrapper gw = new GamesWrapper();


    // options for the menu item
    ArrayList<String>  getData() throws IOException, InterruptedException;

    // method to return results when menu a specific item has been clicked
    TreeMap<Integer,String> returnResults(String field, String value) throws IOException, InterruptedException;

    default TreeMap<Integer,String> createMap(JSONArray arr){
        TreeMap<Integer, String> result = new TreeMap<>();
        for (int i = 0; i < arr.length(); i++) {
            JSONObject jsonobject = arr.getJSONObject(i);
            String name = jsonobject.getString("name");
            Integer id = jsonobject.getInt("id");
            result.put(id,name);
        }
        return result;
    }

    default ArrayList<String> createMenuItems(JSONArray arr) {
        ArrayList<String> result = new ArrayList<>();
        for (int i = 0; i < arr.length(); i++) {
            JSONObject jsonobject = arr.getJSONObject(i);
            String name = jsonobject.getString("name");
            result.add(name);
        }
        return result;
    }

}
