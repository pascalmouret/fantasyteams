/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package fantasyteams;

/**
 *
 * @author 5im08pamouret
 */
public class Main {

    private Game game;
    private GUI gui;

    public Main() throws Exception {
        game = new Game();
        gui = new GUI(game);
        gui.setVisible(true);
    }

    public static void main(String args[]) throws Exception {
        Main main = new Main();
    }

}
