package AIOFight.View;

import AIOFight.Constants.Locations;
import AIOFight.Constants.Mobs;
import AIOFight.FightChicken;
import org.rspeer.ui.Log;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AIOfightGUI extends JFrame {

    private JComboBox  mobComboBox;
    private JComboBox locationComboBox;
    private JButton start;
    private JPanel selectionPanel;


    public AIOfightGUI(){
        super("AIOfight Configuration");
        selectionPanel = new JPanel();

        Log.info(Locations.values());
        super.setContentPane(selectionPanel);
        start = new JButton("Start");

        locationComboBox = new JComboBox(Locations.values());
        mobComboBox = new JComboBox();

        Log.info("got through set bounds");


        selectionPanel.add(locationComboBox);
        selectionPanel.add(mobComboBox);

        selectionPanel.add(start);
        Log.info("got through add");
        super.setDefaultCloseOperation(HIDE_ON_CLOSE);

        super.pack();
        locationComboBox.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                Locations location = (Locations) locationComboBox.getSelectedItem();
                mobComboBox.setModel(new DefaultComboBoxModel(location.getMonster()));
                AIOfightGUI.super.pack();
                start.repaint();//button gets pushed off needs to repaint whatever gets pushed off just repaint
            }
        });

        start.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                FightChicken.setLocations((Locations) locationComboBox.getSelectedItem());
                FightChicken.setMobs((Mobs)mobComboBox.getSelectedItem());
                FightChicken.setConfigured(true);
                setVisible(false);
                FightChicken.tempController();
            }
        });
        super.pack();
    }



    public static void main(String... args){
        new AIOfightGUI().setVisible(true);
    }
}
