/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ahp_gui1;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JFrame;
import javax.swing.JScrollPane;

/**
 *
 * @author Un1kalny
 */
public class MyPanel extends JScrollPane{
    
    private class Punkt {
        int x;
        int y;
        Punkt(int x, int y) {
            this.x = x;
            this.y = y;
        }
    }
    
    AHP ahp;
    double width;
    double height;
    StringRectangle[] rects;
    public MyPanel(double width, double height, AHP ahp) {
        this.ahp = ahp;
        this.width = width;
        this.height = height;
        rects = new StringRectangle[ahp.cele.size() + ahp.kryteria.size() + 1];
        rects[0] = new StringRectangle("Overall satisfaction", (int)(width / 2) - 100, 0, 200, 100);
        setPreferredSize(new java.awt.Dimension(400, 400));
    }
    @Override
    protected void paintComponent(java.awt.Graphics g) {
        super.paintComponent(g);
        //final double scale = width / (y2 - y1);
        //final double scale1 = height / (x2 - x1);
        for(int i = 0; i < rects.length; ++i) {
            if(rects[i] != null)
                rects[i].drawStringRect(g, true, Color.yellow, Color.orange);
        }
        
        List<Kryterium> lista = new ArrayList<>();
        
        for(int i = 0; i < ahp.kryteria.size(); ++i) {
            if(ahp.kryteria.get(i).parent == null)
                lista.add(ahp.kryteria.get(i));
        }
        
        
        
        java.awt.Graphics2D g2d = (java.awt.Graphics2D) g;
        
    }
}
