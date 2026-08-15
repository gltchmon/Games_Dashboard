package Menu;

import org.json.JSONArray;

import javax.swing.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;

public class SearchMenu implements MenuInterface {


    private String search_res;

    @Override
    public ArrayList<String> getData() throws IOException, InterruptedException {
        ArrayList<String> items = new ArrayList<>();
        items.add("Platform");
        items.add("Name");
        return items;
    }

    // results from search feature are returned from this function
    // displays option pane to type results and returns a hashmap or error messages
    @Override
    public TreeMap<Integer, String> returnResults(String field, String value) throws IOException, InterruptedException {
        String[] options = {"Ok", "Cancel"};
        JPanel dialog = dialog(field);
        int option = JOptionPane.showOptionDialog(null, dialog, "Game Information", JOptionPane.YES_NO_OPTION, JOptionPane.PLAIN_MESSAGE, null,options, null);
        JTextField textField = (JTextField) dialog.getComponent(1);
        if(option == 0 && !textField.getText().isEmpty()){
            String name = textField.getText().trim();
            if (!field.equals("name")) {
                search_res =  gw.searchForGame(field+"s", name);
            } else {
                search_res = gw.searchForGameNameIncluding(name);
            }

            //check if games are empty so that user knows if nothing is returned from search
            if(!search_res.equals("[]")){
                JSONArray searchGamesArr = new JSONArray(search_res);
                return createMap(searchGamesArr);
            } else{
                JOptionPane.showOptionDialog(null, "No games found under this search.", "Games not found", JOptionPane.DEFAULT_OPTION, JOptionPane.ERROR_MESSAGE, null,null, null);
            }

        } else if (option==0 && textField.getText().isEmpty()) {
            JOptionPane.showOptionDialog(null, "Please enter text and try again", "No text entered", JOptionPane.DEFAULT_OPTION, JOptionPane.ERROR_MESSAGE, null,null, null);
        }
            return null;


    }

    // panel to show text and label in the dialog
    private JPanel dialog(String field){
        JPanel textEntry = new JPanel();
        textEntry.setLayout(new BoxLayout(textEntry,BoxLayout.Y_AXIS));
        JLabel label = new JLabel("Enter "+ field +":");
        JTextField name = new JTextField();
        textEntry.add(label);
        textEntry.add(name);
        return textEntry;
    }



}
