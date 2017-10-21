/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ahp_gui1;


import javax.swing.JFrame;
import javax.swing.JScrollPane;
/**
 *
 * @author Un1kalny
 */
public class MyFrame extends JFrame{
    AHP ahp;
    public MyFrame(AHP ahp) {
        super("Kacper Szalwa - AHP");
        this.ahp = ahp;
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        pack();
        java.awt.Dimension d = java.awt.Toolkit.getDefaultToolkit().getScreenSize();
        JScrollPane panel = new MyPanel(d.width, d.height, ahp);
        add(panel);
        setVisible(true);
        setSize(d.width, d.height);
    }
}
