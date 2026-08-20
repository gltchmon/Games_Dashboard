package DisplayGames;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

// making a button for every game returned from selecting the menu option or searching a game
public class GameButtons extends JButton implements ActionListener {

    private Integer gameId;
    private String gameName;
    public GameButtons(Integer id, String gameName){
        this.gameName = gameName;
        this.gameId = id;
        this.setText(gameName);
        this.setFont(new Font("Arial", Font.BOLD,13));
        this.setPreferredSize(new Dimension(50,30));
        this.setFocusable(false);
        this.setMaximumSize(this.getPreferredSize());
        this.addActionListener(this);
        this.setActionCommand(String.valueOf(gameId));
        this.setBackground(new Color(242, 242, 242));
        //this.setBorder(BorderFactory.createLineBorder(Color.black, 2));
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String gameName = e.getActionCommand();
        GameInfoModal dialog = new GameInfoModal(gameId,this.gameName);
    }
}
