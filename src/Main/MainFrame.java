package Main;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowEvent;
import java.awt.event.WindowStateListener;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.TreeMap;

import DisplayGames.DisplayGamesPanel;
import DisplayGames.GameButtons;
import GamesTracker.MainListsPanel;
import Menu.MainMenuBar;
import Menu.PlatformsMenu;

import static GamesTracker.Lists.lists;

public class MainFrame extends JFrame {

    private JMenuBar menuBar;
    public DisplayGamesPanel mainGamePanel;
    public  JLabel gamesTitle;

    public MainListsPanel mainListsPanel;

    // adding all JPanels to the main frame
    public MainFrame() {
        this.setTitle("My Games");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLayout(new BorderLayout());
        // creating menu
        // objects creating the menu bar object to add to frame
        MainMenuBar menuB = new MainMenuBar();
        menuBar = menuB.menuBar;
        this.setJMenuBar(menuBar);
        // layout : GUI , used flow layout to place things side by side
        menuBar.setLayout(new FlowLayout(FlowLayout.CENTER));

        // adding the main panel

        this.mainGamePanel = new DisplayGamesPanel();
        JScrollPane scrollPane = new JScrollPane(this.mainGamePanel);
        this.gamesTitle = new JLabel("Games: Nintendo Switch");
        gamesTitle.setName("gamesTitle");
        this.add(gamesTitle, BorderLayout.NORTH);
        this.add(scrollPane, BorderLayout.CENTER);

        // adding side panel
        //JPanel sidePanelContainer = new JPanel();
        // add default lists
        lists.put("Played", new ArrayList<>());
        lists.put("Currently playing", new ArrayList<>());
        lists.put("Want to play", new ArrayList<>());
        this.mainListsPanel = new MainListsPanel();
        this.add(mainListsPanel,BorderLayout.WEST);
        this.pack();
        this.setVisible(true);
    }


    // change the text of the title depending on the games being shown
    public void changeLabel(String state){
        this.gamesTitle.setText("Games: " + state);
        Component[] components = this.getContentPane().getComponents();
        for (Component component : components) {
            if (component.getName() != null && component.getName().equals("gamesTitle")) {
                // remove component then re add it with the new text
                this.remove(component);
                this.gamesTitle.setText("Games: " + state);
                this.add(gamesTitle, BorderLayout.NORTH);
                this.repaint();
                this.revalidate();
            }
        }
    }

    public void changeMainGamesPanel(ArrayList<Integer> gameIds){
        try{
            this.mainGamePanel.displayList(gameIds);
        } catch (IOException  | InterruptedException e) {
            System.err.println("Something wrong has happened with getting the IDS");
            System.out.println(e.getMessage());
        }

    }
}
