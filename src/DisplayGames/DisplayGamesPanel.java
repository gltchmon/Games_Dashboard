package DisplayGames;

import Main.Main;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.io.IOException;
import java.util.TreeMap;
import Menu.PlatformsMenu;

// main JPanel that will display the games and users tracker list
public class DisplayGamesPanel extends JPanel {


    public  DisplayGamesPanel(){
        this.setLayout(new GridLayout(20,5,5,5));
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
}
