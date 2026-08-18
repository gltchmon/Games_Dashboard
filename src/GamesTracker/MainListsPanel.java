package GamesTracker;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import static GamesTracker.Lists.lists;

// class that holds button to different lists
public class MainListsPanel extends JPanel implements ActionListener {

    public MainListsPanel(){
        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        JLabel title = new JLabel("VIEW LISTS");
        title.setFont(new Font("Arial", Font.BOLD, 35));
        title.setAlignmentX(JComponent.CENTER_ALIGNMENT);
        title.setBorder(new EmptyBorder(0,10,5,10));
        this.add(title);
        generateListButtons();
    }

    // use current list to generate buttons may need to be public after
    private void generateListButtons(){
        for(String list: lists.keySet()){
            JButton button = new JButton(list);
            button.setFocusable(false);
            button.setAlignmentX(JComponent.CENTER_ALIGNMENT);
            button.setMaximumSize(new Dimension(180,40));
            button.addActionListener(this);
            this.add(button);
        }
    }

    // clicking on list makes games appear from that list
    @Override
    public void actionPerformed(ActionEvent e) {

    }
}
