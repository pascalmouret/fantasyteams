/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package fantasyteams;

/**
 *
 * @author Luca
 */
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.*;

public class playField extends JComponent {

    private Image img;
    private Battlefield battlefield;
    private InputHandler inputHandler;
    private int win = 0;

    public playField() {
    }

    public playField(InputHandler inputHandler) {
        try {
            this.battlefield = Battlefield.getInstance();
        } catch (Exception ex) {
            Logger.getLogger(playField.class.getName()).log(Level.SEVERE, null, ex);
        }
        this.inputHandler = inputHandler;
    }

    private void welcome(Graphics g) {
        g.setColor(Color.black);
        g.setFont(new Font("Verdana", 0, 48));
        g.drawString("Willkommen zu Fantasy Teams", 30, 380);
        g.setFont(new Font("Verdana", 0, 24));
        g.drawString("Auf \"Neues Spiel\" klicken um zu beginnen", 170, 480);
    }

    private void paintGrid(Graphics g) {
        g.setColor(Color.GRAY);
        for(int y=0;y<20;y++) {
            for(int x=0;x<20;x++) {
                g.drawRect(x*40, y*40, 40, 40);
            }
        }
    }

    private void paintMovement(Graphics g, int x, int y) {
        g.setColor(new Color(90, 100, 255, 80));
        if(inputHandler.getMovementhandler().getTemplate()[x][y]) {
            g.fillRect(x*40, y*40, 40, 40);
        }
    }

    private void paintAttack(Graphics g, int x, int y) {
        g.setColor(new Color(255, 100, 90, 80));
        if(inputHandler.getAttackhandler().getTemplate()[x][y]) {
            g.fillRect(x*40, y*40, 40, 40);
        }
    }

    private void paintBattlefield(Graphics g) {
        for (int y = 0; y < 20; y++) {
            for (int x = 0; x < 20; x++) {
                try {
                    img = ImageIO.read(new File(battlefield.getFields()[x][y].getTexture()));
                } catch (IOException ex) {
                    Logger.getLogger(playField.class.getName()).log(Level.SEVERE, null, ex);
                }
                g.drawImage(img, x * 40, y * 40, this);
                try {
                    if(battlefield.getFields()[x][y].getUnit() != null) {
                        img = ImageIO.read(new File(battlefield.getFields()[x][y].getUnit().getImageMap()));
                    }
                } catch (IOException ex) {
                    Logger.getLogger(playField.class.getName()).log(Level.SEVERE, null, ex);
                }
                g.drawImage(img, x * 40, y * 40, this);
                paintMovement(g,x,y);
                paintAttack(g,x,y);
            }
        }
    }
    
    @Override
    public void paint(Graphics g)
    {
        if(win == 1) {
            g.setColor(Color.black);
            g.setFont(new Font("Verdana", 0, 48));
            g.drawString("Player 2 gewinnt!", 30, 380);
        }
        if(win == 2) {
            g.setColor(Color.black);
            g.setFont(new Font("Verdana", 0, 48));
            g.drawString("Player 1 gewinnt!", 30, 380);
        }
        if(battlefield.isReady()) {
            paintBattlefield(g);
            paintGrid(g);
        } else {
            welcome(g);
        }
    }

    /**
     * @param win the win to set
     */
    public void setWin(int win) {
        this.win = win;
    }
    
}
