package DisplayGames;

import GamesWrapper.GamesWrapper;
import Main.Main;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.TreeMap;
import Menu.PlatformsMenu;
import org.json.JSONArray;
import org.json.JSONObject;

import static GamesTracker.Lists.getCurrentLists;
import static GamesTracker.Lists.lists;
import static HelperMethods.HelperMethods.hasKey;

// main JPanel that will display the games and users tracker list
public class DisplayGamesPanel extends JPanel implements ActionListener {

    String currentList;
    JPanel container;
    JComboBox chooseListComboBox;
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

    // add the buttons from the search or menu
    public void addButtons(TreeMap<Integer, String> results){
        this.removeAll();
        this.setLayout(new GridLayout(25,4,5,5));
        for(Integer game: results.keySet()){
            GameButtons gameButton = new GameButtons(game,results.get(game));
            this.add(gameButton);
        }
        this.revalidate();
        this.repaint();
    }

    // display the games in the current list
    public void displayList(String listName, ArrayList<Integer> gameids) throws IOException, InterruptedException {
        GamesWrapper gw = new GamesWrapper();
        this.currentList = listName;
        this.removeAll();
        container = new JPanel(new FlowLayout(FlowLayout.LEFT));
        container.setPreferredSize(new Dimension(100,this.getHeight()));
        for(Integer gameID: gameids){
            String gameQueryString = gw.getGameById(String.valueOf(gameID));
            JSONArray gameQueryArr = new JSONArray(gameQueryString);
            JSONObject gameDetails = gameQueryArr.getJSONObject(0);
            String coverUrl = "src/images/3674270-200.png";
            if(hasKey(gameDetails.getJSONObject("cover"),"url" )){
                coverUrl = "https:" +gameDetails.getJSONObject("cover").getString("url");
            }
            String name = gameDetails.getString("name");
            container.add(createGamePanel(name,coverUrl,gameID));
            container.setBackground(Color.white);
        }
        this.add(container);
        this.repaint();
        this.revalidate();

    }

    // method to create the game and the panel
    private JPanel createGamePanel(String name, String cover, Integer gameId) {
        JPanel gameNameCover = new JPanel();
        gameNameCover.setLayout(new BoxLayout(gameNameCover,BoxLayout.Y_AXIS));
        // get image
        try {
            // set cover image when displaying the games on the list
            URL url = new URL(cover);
            Image c = ImageIO.read(url);
            c.getScaledInstance(90,90,Image.SCALE_SMOOTH);
            JLabel coverImg = new JLabel(new ImageIcon(c));
            coverImg.setAlignmentX(JComponent.CENTER_ALIGNMENT);
            gameNameCover.add(coverImg);

            JLabel nameLabel = new JLabel(String.format(name));
            nameLabel.setAlignmentX(JComponent.CENTER_ALIGNMENT);
            gameNameCover.add(nameLabel);

            // buttons
            JButton removeFromListButton = new JButton("Remove");
            removeFromListButton.setAlignmentX(JComponent.CENTER_ALIGNMENT);
            JButton moveToNewList = new JButton("Move");
            moveToNewList.setAlignmentX(JComponent.CENTER_ALIGNMENT);

            removeFromListButton.setMaximumSize(new Dimension(150,20));
            moveToNewList.setMaximumSize(new Dimension(150,20));

            removeFromListButton.addActionListener(this);
            moveToNewList.addActionListener(this);
            removeFromListButton.setFocusable(false);
            moveToNewList.setFocusable(false);
            removeFromListButton.setName(String.valueOf(gameId));
            moveToNewList.setName(String.valueOf(gameId));

            gameNameCover.add(removeFromListButton);
            gameNameCover.add(moveToNewList);
        } catch (IOException e) {
            // handle not finding the image
            System.err.println("could not find url");
            System.out.println(e.getMessage());
            // deal with cover
        }
        gameNameCover.setName("panel"+gameId);
        gameNameCover.setPreferredSize(new Dimension(150,250));
        gameNameCover.setBackground(Color.white);
        return gameNameCover;
    }


    @Override
    public void actionPerformed(ActionEvent e) {
        JButton source = (JButton) e.getSource();
        Integer gameID = Integer.valueOf(source.getName());
        if(source.getText().equals("Remove")){
            System.out.println(Arrays.toString(this.getComponents()));
            // remove the gameid from the list
            lists.get(currentList).remove(gameID);
            // remove game id from panel
            removePanel("panel"+gameID);
        } else{
            // move game to new panel
            int option = JOptionPane.showOptionDialog(null,moveGametoNewListPanel(),"Move game to new list",
                    JOptionPane.OK_CANCEL_OPTION,JOptionPane.PLAIN_MESSAGE,null,null,null);
            if(option == 0){
                // move from current list if they are not equal meaning it is not already in the list
                if(!currentList.equals(chooseListComboBox.getSelectedItem().toString())){
                    lists.get(currentList).remove(gameID);
                    // get the list user wants to move it too
                    lists.get(chooseListComboBox.getSelectedItem().toString()).add(gameID);
                    removePanel("panel"+gameID);
                } else{
                    JOptionPane.showMessageDialog(null,"Game is already in this list.");
                }
            }
        }
    }

    // method to remove panel
    private void removePanel(String panelName){
        for(Component component: container.getComponents()){
            if(component.getName() != null && component.getName().equals(panelName)){
                container.remove(component);
                container.repaint();
                container.revalidate();
            }
        }
    }

    // method to add combo box to option dialog to move the game to new list
    private JPanel moveGametoNewListPanel(){
        JPanel chooseGamePanel = new JPanel();
        chooseGamePanel.setLayout(new BoxLayout(chooseGamePanel,BoxLayout.Y_AXIS));
        chooseListComboBox = new JComboBox(getCurrentLists());
        JLabel title = new JLabel("Choose new list");
        title.setAlignmentX(JComponent.CENTER_ALIGNMENT);
        chooseGamePanel.add(title);
        chooseGamePanel.add(chooseListComboBox);
        return chooseGamePanel;
    }
}
