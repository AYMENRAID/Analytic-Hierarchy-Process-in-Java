/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ahp_gui1;

import java.awt.*;

/**
 *
 * @author Un1kalny
 */
public class AHPCanvas extends Canvas {
    StringRectangle[] rects;
    AHP ahp;
    
    public AHPCanvas(AHP ahp) {
        this.ahp = ahp;
        rects = new StringRectangle[ahp.kryteria.size() + ahp.cele.size() + 1];
        rects[0] = new StringRectangle("Overall satisfaction", 200, 200, 200, 100);
        
        this.revalidate();
        this.repaint();
    }
    
    @Override
    public void paint(Graphics g) {
        for(int i = 0; i < rects.length; ++i) {
            rects[i].drawStringRect(g, true, Color.yellow, Color.orange);
        }
    }

}
