package AutoWalker.View;

import AutoWalker.AutoWalker;
import org.rspeer.runetek.api.commons.BankLocation;
import org.rspeer.ui.Log;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AutoWalkerGUI extends JFrame {

    private JComboBox locationComboBox;
    private JButton start;
    private JPanel selectionPanel;


    public AutoWalkerGUI(){
        super("AutoWalker Configuration");
        selectionPanel = new JPanel();


        super.setContentPane(selectionPanel);
        start = new JButton("Start");

        locationComboBox = new JComboBox(BankLocation.values());

        selectionPanel.add(locationComboBox);

        selectionPanel.add(start);
        Log.info("got through add");
        super.setDefaultCloseOperation(HIDE_ON_CLOSE);

        super.pack();


        start.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                AutoWalker.setBankToWalkTo((BankLocation) locationComboBox.getSelectedItem());
                AutoWalker.setConfigured(true);
                setVisible(false);
            }
        });
        super.pack();
    }



    public static void main(String... args){
        new AutoWalkerGUI().setVisible(true);
    }
}
