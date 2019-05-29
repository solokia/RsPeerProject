package AutoMine.view;

import AutoMine.AutoMine;
import AutoMine.Constants.Locations;
import AutoMine.Constants.Rocks;

import org.rspeer.ui.Log;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AutoMineGUI extends JFrame {

    private JComboBox  mobComboBox;
    private JComboBox locationComboBox;
    private JButton start;
    private JPanel selectionPanel;


    public AutoMineGUI(){
        super("AutoMine Configuration");
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
                Locations locations = (Locations) locationComboBox.getSelectedItem();
                mobComboBox.setModel(new DefaultComboBoxModel(locations.getRockList()));
                AutoMineGUI.super.pack();
                start.repaint();//button gets pushed off needs to repaint whatever gets pushed off just repaint
            }
        });

        start.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                AutoMine.setLocations((Locations) locationComboBox.getSelectedItem());
                AutoMine.setRocks((Rocks)mobComboBox.getSelectedItem());
                AutoMine.setConfigured(true);
                setVisible(false);
                AutoMine.tempController();
            }
        });
        super.pack();
    }


}
