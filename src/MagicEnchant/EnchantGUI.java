package MagicEnchant;


import org.rspeer.ui.Log;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class EnchantGUI extends JFrame {

    private JComboBox itemComboBox;
    private JComboBox spellsComboBox;
    private JComboBox speedCombobox;
    private JButton start;
    private JPanel selectionPanel;



    public EnchantGUI(){
        super("AIOfight Configuration");
        selectionPanel = new JPanel();


        super.setContentPane(selectionPanel);
        start = new JButton("Start");

        spellsComboBox = new JComboBox(Spells.values());
        itemComboBox = new JComboBox(Items.values());
        speedCombobox = new JComboBox(new String[] {"Fast","Slow"});
        Log.info("got through set bounds");


        selectionPanel.add(spellsComboBox);
        selectionPanel.add(itemComboBox);
        selectionPanel.add(speedCombobox);

        selectionPanel.add(start);
        Log.info("got through add");
        super.setDefaultCloseOperation(HIDE_ON_CLOSE);

        super.pack();


        start.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Spells spell = (Spells)spellsComboBox.getSelectedItem();
                Items i = (Items)itemComboBox.getSelectedItem();
                String sp = (String) speedCombobox.getSelectedItem();
                MagicEnchant.setPARENT_INDEX(spell.getParent());
                MagicEnchant.setCHILD_INDEX(spell.getChild());

                setVisible(false);
                MagicEnchant.tempController(i,spell,sp);
            }
        });
        super.pack();
    }



    public static void main(String... args){
        new EnchantGUI().setVisible(true);
    }
}
