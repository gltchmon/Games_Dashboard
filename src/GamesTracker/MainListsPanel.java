package GamesTracker;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

// class that holds button to different lists
public class MainListsPanel extends JPanel {

    public MainListsPanel(){
        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        JLabel title = new JLabel("VIEW LISTS");
        title.setFont(new Font("Arial", Font.BOLD, 35));
        title.setAlignmentX(JComponent.CENTER_ALIGNMENT);
        title.setBorder(new EmptyBorder(0,10,5,10));
        this.add(title);
        JButton playedButton = new JButton("Played");
        playedButton.setFocusable(false);
        ImageIcon icon =  createImageIcon("../images/playedButtonIcon.png", "played button icon");
        icon =  new ImageIcon(icon.getImage().getScaledInstance(20,20,Image.SCALE_DEFAULT));
        playedButton.setAlignmentX(JComponent.CENTER_ALIGNMENT);
        playedButton.setIcon(icon);
        playedButton.setMaximumSize(new Dimension(180,40));
        this.add(playedButton);

    }

    private ImageIcon createImageIcon(String path, String desc){
        java.net.URL imgUrl = getClass().getResource(path);
        if(imgUrl != null) {
            return new ImageIcon(imgUrl,desc);
        } else{
            System.err.println("Could not find file "+ path);
            return null;
        }
    }
}
