package DisplayGames;


import GamesTracker.Lists;
import GamesWrapper.GamesWrapper;
import org.json.JSONArray;
import org.json.JSONObject;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.time.LocalDate;
import java.sql.Timestamp;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

import static GamesTracker.Lists.getCurrentLists;
import static GamesTracker.Lists.lists;
import static HelperMethods.HelperMethods.hasKey;


// class to display modal when button is pressed to diplsya information about the game
public class GameInfoModal {

    private Integer gameId;
    private String gameName;

    // store information about country
    private String alternativeNames;
    private String coverUrl;
    private String releaseDate;
    private String genres;
    private String summary;
    private String totalRating;
    private String themes;
    private JComboBox comboBoxlists;

    ImageIcon gameImg;
    private final GamesWrapper gw = new GamesWrapper();
    private int option;
    private String[] options = new String[3];
    public GameInfoModal(Integer gameId, String gameName){
        this.gameId = gameId;
        this.gameName = gameName;
        options[1] = "Close";
        options[0] = "Add to list";
        try {
            getGame();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        gameImg(coverUrl);
        option = JOptionPane.showOptionDialog(null, createInfoText(), "Game Information", JOptionPane.OK_CANCEL_OPTION, JOptionPane.INFORMATION_MESSAGE, gameImg,null, null);
        addToList(option);
    }

    // get the information on the selected game and store the information in the variables
    private void getGame() throws IOException, InterruptedException {
        String gameRequestResult = gw.getGameById(String.valueOf(this.gameId));
        JSONArray jsonArr = new JSONArray(gameRequestResult);
        JSONObject jsonobject = jsonArr.getJSONObject(0);
        // get alternative names and place into array
        if(hasKey(jsonobject, "alternative_names")){
            JSONArray alternativeNamesObj = jsonobject.getJSONArray("alternative_names");
            ArrayList<String> alternativeNamesArr = getNames(alternativeNamesObj);
            alternativeNames = String.join(", ",alternativeNamesArr);
        } else{
            alternativeNames = "";
        }


        // get cover
        if(hasKey(jsonobject.getJSONObject("cover"),"url" )){
            coverUrl = "https:" +jsonobject.getJSONObject("cover").getString("url");
        } else {
            coverUrl = "src/images/3674270-200.png";
        }

        if(hasKey(jsonobject, "first_release_date" )){
            // get and convert release date
            long releaseDateObj = jsonobject.getLong("first_release_date");
            LocalDate releaseDateParse = new Timestamp(releaseDateObj).toLocalDateTime().toLocalDate();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMMM dd, yyyy");
            releaseDate = formatter.format(releaseDateParse);
        } else {
            releaseDate = "";
        }

        // get all genres
        if(hasKey(jsonobject, "genres")){
            JSONArray genresObj = jsonobject.getJSONArray("genres");
            ArrayList<String> genresArr = getNames(genresObj);
            genres = String.join(", ",genresArr);
        } else {
            genres = "";
        }

        // get summary
        if(hasKey(jsonobject, "storyline")){
            summary = jsonobject.getString("storyline");
        } else {
            summary = "";
        }

        // get the total rating

        if(hasKey(jsonobject,"total_rating")){
            totalRating = String.valueOf(jsonobject.getInt("total_rating"));
        } else{
            totalRating = "";
        }

        if(hasKey(jsonobject, "themes")){
            // get themes
            JSONArray themesObj = jsonobject.getJSONArray("themes");
            ArrayList<String> themesArr = getNames(themesObj);
            themes = String.join(", ",themesArr);
        } else {
            themes = "";
        }

    }




    // get game cover image
    private void gameImg(String imgUrl){
        Image image = null;
        try{
            URL url = new URL(imgUrl);
            URLConnection conn = url.openConnection();
            conn.setRequestProperty("User-Agent", "Mozilla/5.0");
            conn.connect();
            InputStream urlStream = conn.getInputStream();
            image = ImageIO.read(urlStream);
            image = image.getScaledInstance(150,150,Image.SCALE_DEFAULT);
            gameImg = new ImageIcon(image);
        } catch (IOException e){
            e.printStackTrace();
            // set to default  image if something has gone wrong to avoid errors if image has not been found
            gameImg = getDefault();
        }
    }
    // incase the game has no cover image we can use a default one
    private ImageIcon getDefault(){
        ImageIcon image = null;
        try{
            image = new ImageIcon(ImageIO.read(new File("src/images/3674270-200.png")));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return image;
    }

    // create the text that holds the information of the game
    private JPanel createInfoText(){
        JPanel info = new JPanel();
        info.setLayout(new BoxLayout(info,BoxLayout.Y_AXIS));
        JLabel gameNameLabel = new JLabel("<html><u>Name:</u> " + this.gameName + "</html>");
        gameNameLabel.setBorder(new EmptyBorder(0,0,5,0));
        gameNameLabel.setAlignmentX(JComponent.CENTER_ALIGNMENT);
        info.add(gameNameLabel);
        JLabel altNamesLabel = new JLabel();
        String altNamesText = String.format("<html><div style=\"width:%dpx;\">%s</div></html>", 400, "<u>Alternative Names:</u> \n" +alternativeNames);
        altNamesLabel.setText(altNamesText);
        altNamesLabel.setBorder(new EmptyBorder(0,0,5,0));
        altNamesLabel.setAlignmentX(JComponent.CENTER_ALIGNMENT);
        info.add(altNamesLabel);
        JLabel summaryLabel = new JLabel();
        String labelText = String.format("<html><div style=\"width:%dpx;\">%s</div></html>", 400, "<u>Summary:</u> \n" +summary);
        summaryLabel.setText(labelText);
        summaryLabel.setBorder(new EmptyBorder(0,0,5,0));
        summaryLabel.setAlignmentX(JComponent.CENTER_ALIGNMENT);
        info.add(summaryLabel);
        JLabel releaseDateLabel = new JLabel("<html><u>Release Date:</u> " + releaseDate + "</html>");
        releaseDateLabel.setBorder(new EmptyBorder(0,0,5,0));
        releaseDateLabel.setAlignmentX(JComponent.CENTER_ALIGNMENT);
        info.add(releaseDateLabel);
        JLabel genresLabel = new JLabel("<html><u>Genres:</u> " + genres + "</html>");
        genresLabel.setBorder(new EmptyBorder(0,0,5,0));
        genresLabel.setAlignmentX(JComponent.CENTER_ALIGNMENT);
        info.add(genresLabel);
        JLabel themesLabel = new JLabel("<html><u>Themes:</u> " + themes + "</html>");
        themesLabel.setBorder(new EmptyBorder(0,0,5,0));
        themesLabel.setAlignmentX(JComponent.CENTER_ALIGNMENT);
        info.add(themesLabel);
        JLabel ratingLabel = new JLabel("<html><u>Rating:</u> " + totalRating + "/100"  + "</html>");
        ratingLabel.setBorder(new EmptyBorder(0,0,5,0));
        ratingLabel.setAlignmentX(JComponent.CENTER_ALIGNMENT);
        info.add(ratingLabel);
        JPanel addToListsPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        addToListsPanel.add(new JLabel("Add to list: "));
        comboBoxlists = new JComboBox<>(getCurrentLists());
        addToListsPanel.add(comboBoxlists);
        info.add(addToListsPanel);
        return info;
    }

    // helper function to check if values are null and return empty string

    // get the name key from a returned json array
    private ArrayList<String> getNames(JSONArray arr){
        ArrayList<String> res = new ArrayList<>();
        for (int i = 0; i < arr.length(); i++) {
            JSONObject obj = arr.getJSONObject(i);
            String name = obj.getString("name");
            res.add(name);
        }
        return res;
    }

    private void addToList(int option){
        if(option == 0){
            String list = comboBoxlists.getSelectedItem().toString();
            Lists.addToList(list,gameId);

        }
    }

}
