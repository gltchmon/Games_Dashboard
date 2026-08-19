package DisplayGames;

import GamesWrapper.GamesWrapper;
import Main.Main;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.TreeMap;
import Menu.PlatformsMenu;
import org.json.JSONArray;
import org.json.JSONObject;

import static HelperMethods.HelperMethods.hasKey;

// main JPanel that will display the games and users tracker list
public class DisplayGamesPanel extends JPanel {


    public  DisplayGamesPanel(){
        this.setLayout(new GridLayout(25,4,5,5));
        this.setBorder(new EmptyBorder(10,10,10,10));
        this.setBackground(Color.white);
        PlatformsMenu platformsMenu = new PlatformsMenu();
        try {
            TreeMap<Integer, String> defaultResults = platformsMenu.returnResults(null, "Nintendo Switch");
            addButtons(defaultResults);
        } catch (IOException | InterruptedException ex) {
            throw new RuntimeException(ex);
        }
    }

    public void addButtons(TreeMap<Integer, String> results){
        this.removeAll();
        for(Integer game: results.keySet()){
            GameButtons gameButton = new GameButtons(game,results.get(game));
            this.add(gameButton);
        }
        this.revalidate();
        this.repaint();
    }

    public void displayList(ArrayList<Integer> gameids) throws IOException, InterruptedException {
        GamesWrapper gw = new GamesWrapper();
        for(Integer gameID: gameids){
            String gameQueryString = gw.getGameById(String.valueOf(gameID));
            JSONArray gameQueryArr = new JSONArray(gameQueryString);
            JSONObject gameDetails = gameQueryArr.getJSONObject(0);
            String coverUrl = "src/images/3674270-200.png";
            if(hasKey(gameDetails.getJSONObject("cover"),"url" )){
                coverUrl = "https:" +gameDetails.getJSONObject("cover").getString("url");
            }
            String name = gameDetails.getString("name");
        }

    }

    // method to create the game and the panel
    private JPanel createGamePanel(String name, String cover){
        JPanel gameNameCover = new JPanel();
        gameNameCover.setLayout(new BoxLayout(gameNameCover, BoxLayout.Y_AXIS));
        return gameNameCover;
    }
}
