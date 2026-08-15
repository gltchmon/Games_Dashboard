package Menu;
import DisplayGames.GameButtons;
import Main.Main;
import com.sun.source.tree.Tree;

import javax.swing.*;
import javax.swing.border.EmptyBorder;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.TreeMap;

// class that creates the menu bar
public class MainMenuBar extends JFrame implements ActionListener {
    public JMenuBar menuBar;
    private JMenu genres;
    private JMenu platforms;
    private JMenu themes;
    private JMenu search;

    GenreMenu genreMenu = new GenreMenu();
    PlatformsMenu platformsMenu = new PlatformsMenu();
    SearchMenu searchMenu = new SearchMenu();
    ThemesMenu themesMenu = new ThemesMenu();

    ArrayList<String> gamesToDisplay;

    public MainMenuBar() {
        menuBar = new JMenuBar();
        this.genres = new JMenu("GENRE");
        this.genres.setName("genres");
        this.platforms = new JMenu("PLATFORMS");
        this.platforms.setName("platforms");
        this.themes = new JMenu("THEMES");
        this.themes.setName("themes");
        this.search = new JMenu("SEARCH BY");
        this.search.setName("search");

        JLabel displayGamesLabel = new JLabel("Display games by:");
        displayGamesLabel.setBorder(new EmptyBorder(10, 0, 10, 30));
        menuBar.add(displayGamesLabel);
        menuBar.add(genres);
        menuBar.add(platforms);
        menuBar.add(themes);
        menuBar.add(search);
        genres.setBorder(new EmptyBorder(10, 20, 10, 20));
        themes.setBorder(new EmptyBorder(10, 20, 10, 20));
        platforms.setBorder(new EmptyBorder(10, 20, 10, 20));
        search.setBorder(new EmptyBorder(10, 20, 10, 20));
        try {
            addMenuItems(genres, genreMenu);
            addMenuItems(themes, themesMenu);
            addMenuItems(platforms, platformsMenu);
            addMenuItems(search, searchMenu);
        } catch ( Exception e) {
            throw new RuntimeException(e);
        }

    }
// add all the menu items on the menu depending on the get data results
    private void addMenuItems(JMenu menu, MenuInterface menuInstance ) throws IOException, InterruptedException {
        for (String item: menuInstance.getData()) {
            JMenuItem menuItem = new JMenuItem(item);
            menuItem.addActionListener(this);
            menuItem.setName(item);
            menu.add(menuItem);
        }
    }

    // gets the selected menu item so that the related games are displayed on the main panel
    @Override
    public void actionPerformed(ActionEvent e) {
        // getting the selected item
        JMenuItem selectedItem = (JMenuItem) e.getSource();
        JPopupMenu parent = (JPopupMenu) selectedItem.getParent();
        JMenu eventFrom = (JMenu)parent.getInvoker();
        TreeMap<Integer, String> results = new TreeMap<>();
        if(eventFrom.getName().equals("genres")){
            try {
                results  = genreMenu.returnResults(null,selectedItem.getName());
            } catch (IOException | InterruptedException ex) {
                throw new RuntimeException(ex);
            }
        } else if(eventFrom.getName().equals("platforms")){
            try {
                results  = platformsMenu.returnResults(null,selectedItem.getName());
            } catch (IOException | InterruptedException ex) {
                throw new RuntimeException(ex);
            }
        } else if(eventFrom.getName().equals("themes")){
            try {
                results  = themesMenu.returnResults(null,selectedItem.getName());
            } catch (IOException | InterruptedException ex) {
                throw new RuntimeException(ex);
            }
        } else if(eventFrom.getName().equals("search")){
            try {
                results  = searchMenu.returnResults(selectedItem.getName().toLowerCase(),null);
            } catch (IOException | InterruptedException ex) {
                throw new RuntimeException(ex);
            }
        }
        if(results != null){
            Main.frame.mainGamePanel.addButtons(results);
            Main.frame.changeLabel(selectedItem.getName());
        }
    }


}
