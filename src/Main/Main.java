package Main;

import javax.swing.*;

public class Main {

    public static MainFrame frame = new MainFrame();

    // main function as main entry point of program introduced in unit 1
    public static void main(String[] args) throws Exception{
        // using invoke later we can make sure only the event thread runs all GUI operations including frame instantiation
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new Main();
            }
        });

    }

}
