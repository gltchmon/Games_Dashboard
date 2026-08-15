package DisplayGames;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

// making a button for every result returned from the menu items or countries in a list
public class GameButtons extends JButton implements ActionListener {

    private Integer gameId;
    private String gameName;

    public GameButtons(Integer id, String gameName){
        this.gameName = gameName;
        this.gameId = id;
        this.setText(gameName);
        this.setFont(new Font("Arial", Font.BOLD,13));
        this.setPreferredSize(new Dimension(200,50));
        this.setFocusable(false);
        this.setMaximumSize(this.getPreferredSize());
        this.addActionListener(this);
        this.setActionCommand(String.valueOf(gameId));
        this.setBackground(new Color(227, 227, 227));
        this.setBorder(BorderFactory.createLineBorder(Color.black, 2));
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String gameName = e.getActionCommand();
        GameInfoModal dialog = new GameInfoModal(gameId,this.gameName);
    }
}
