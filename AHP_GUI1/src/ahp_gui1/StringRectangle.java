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

public class StringRectangle extends Rectangle {
    public String s;
    
    public StringRectangle(String s, int x, int y, int w, int h) {
        super(x,y,w,h);
        this.s = s;
    }
    
    public void drawStringRect(Graphics g, boolean fill, Color rectColor, Color stringColor) {
        Color old = g.getColor();
        g.setColor(rectColor);
        
        if(fill)
            g.fillRect(x, y, width, height);
        else
            g.drawRect(x, y, width, height);
        
        FontMetrics fm = g.getFontMetrics();
        g.setColor(stringColor);
        int stringWidth = fm.stringWidth(s);
        int startX = x + ((width - stringWidth) / 2);
        int startY = y + ((height + fm.getHeight()) / 2);
        g.drawString(s, startX, startY);
        g.setColor(old);
    }    
}