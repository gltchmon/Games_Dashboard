package GamesTracker;

import Main.Main;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

import static GamesTracker.Lists.getCurrentLists;
import static GamesTracker.Lists.lists;

// class that holds button to different lists
public class MainListsPanel extends JPanel implements ActionListener, MouseListener {

    JComboBox deleteListComboBox;
    public MainListsPanel(){
        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        JLabel title = new JLabel("VIEW LISTS");
        title.setFont(new Font("Arial", Font.BOLD, 35));
        title.setAlignmentX(JComponent.CENTER_ALIGNMENT);
        title.setBorder(new EmptyBorder(0,10,5,10));
        this.add(title);

        // adding labels to delete or add new lists

        createListManageButtons();
        // method to add list buttons to view games in each list
        generateListButtons();
    }

    // use current list to generate buttons may need to be public after
    private void generateListButtons(){
        // remove buttons
        for(String list: lists.keySet()){
            JButton button = new JButton(list);
            button.setFocusable(false);
            button.setName(list);
            button.setAlignmentX(JComponent.CENTER_ALIGNMENT);
            button.setMaximumSize(new Dimension(180,40));
            button.addActionListener(this);
            this.add(button);
        }
    }

    private void addButton(String buttonName){
        JButton button = new JButton(buttonName);
        button.setFocusable(false);
        button.setName(buttonName);
        button.setAlignmentX(JComponent.CENTER_ALIGNMENT);
        button.setMaximumSize(new Dimension(180,40));
        button.addActionListener(this);
        this.add(button);
    }

    // creating labels to add a new list or delete an existing list
    private void createListManageButtons(){
        JPanel addDeleteListPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        JLabel addListLabel = new JLabel("Add list");
        addListLabel.setFont(new Font(addListLabel.getFont().getFontName(),Font.BOLD,15));
        addListLabel.addMouseListener(this);
        JLabel deleteListLabel = new JLabel("Delete list");
        deleteListLabel.setFont(new Font(addListLabel.getFont().getFontName(),Font.BOLD,15));
        deleteListLabel.addMouseListener(this);
        addDeleteListPanel.add(addListLabel);
        JLabel addDeleteListDivider = new JLabel(" | ");
        addDeleteListPanel.add(addDeleteListDivider);
        addDeleteListPanel.add(deleteListLabel);
        addDeleteListPanel.setMaximumSize(new Dimension(270,40));
        //addDeleteListPanel.setBackground(Color.gray);
        this.add(addDeleteListPanel);
    }

    // clicking on list makes games appear from that list
    @Override
    public void actionPerformed(ActionEvent e) {
        JButton buttonClicked = (JButton) e.getSource();
        Main.frame.changeMainGamesPanel(lists.get(buttonClicked.getName()));
    }

    // add or remove lists
    @Override
    public void mouseClicked(MouseEvent e) {
        JLabel source = (JLabel) e.getSource();
        if(source.getText().equals("Add list")){
            String newList = JOptionPane.showInputDialog(this, "Enter new list name", "Add list",JOptionPane.PLAIN_MESSAGE);
            if(newList != null && !newList.isEmpty()){
                lists.put(newList,new ArrayList<>());
                addButton(newList);
                this.repaint();
                this.revalidate();
            }
        } else{
            JPanel deleteListPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
            deleteListPanel.add(new JLabel("Select list to delete: "));
            deleteListComboBox = new JComboBox<>(getCurrentLists());
            deleteListPanel.add(deleteListComboBox);
            int deleteListOption = JOptionPane.showOptionDialog(null, deleteListPanel,"Delete list", JOptionPane.OK_CANCEL_OPTION,JOptionPane.PLAIN_MESSAGE,null,null,null);
            // get the selected option and remove from the list
            if(deleteListOption == 0){
                lists.remove(deleteListComboBox.getSelectedItem().toString());
                for(Component component:this.getComponents()){
                    // find component with the same name
                    if(component.getName() != null && component.getName().equals(deleteListComboBox.getSelectedItem().toString())){
                        this.remove(component);
                        this.revalidate();
                        this.repaint();
                    }
                }
            }
        }
    }

    @Override
    public void mousePressed(MouseEvent e) {

    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    // change colour of text to make clickable
    @Override
    public void mouseEntered(MouseEvent e) {
        JLabel source = (JLabel) e.getSource();
        if(source.getText().equals("Add list")) {
            source.setBackground(Color.white);
        }
    }

    @Override
    public void mouseExited(MouseEvent e) {

    }
}
