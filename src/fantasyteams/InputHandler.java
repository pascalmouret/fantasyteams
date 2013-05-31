/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package fantasyteams;

import java.util.logging.Level;
import java.util.logging.Logger;

/** This class handles the mouse input of the user on the battlefield, mainly mouse clicks on fields.
 *
 *
 * @author Luca Neukom
 */
public class InputHandler {
    private Field first;
    private Field second;
    private boolean needTarget;
    private MovementHandler movementhandler;
    private AttackHandler attackhandler;
    private Battlefield battlefield;
    private int action;


    public InputHandler() {
        try {
            this.movementhandler = MovementHandler.getInstance();
        } catch (Exception ex) {
            Logger.getLogger(playField.class.getName()).log(Level.SEVERE, null, ex);
        }
        try {
            this.attackhandler = AttackHandler.getInstance();
        } catch (Exception ex) {
            Logger.getLogger(playField.class.getName()).log(Level.SEVERE, null, ex);
        }
        try {
            this.battlefield = Battlefield.getInstance();
        } catch (Exception ex) {
            Logger.getLogger(playField.class.getName()).log(Level.SEVERE, null, ex);
        }
        needTarget = false;
    }

    /** Calculates the coordinates of the clicked field based on the X and Y coodinates of the mouse event.
     * If there is a first field selected, the correct action is chosen (Attack, Move or Deselect).
     *
     * @param evt The event of the mouse click on the battlefield
     * @throws Exception
     */
    public void calculateField(java.awt.event.MouseEvent evt) throws Exception {
        if(evt.getButton() == 3) {
            if(first != null) {
                reset();
            }
            action = 0;
        }else if(!isNeedTarget()) {
            int fieldX;
            int fieldY;

            fieldX = (int) Math.floor(evt.getX() / 40);
            fieldY = (int) Math.floor(evt.getY() / 40);
            if(battlefield.getFields()[fieldX][fieldY].getUnit() != null) {
                if(battlefield.getFields()[fieldX][fieldY].getUnit().isActive()) {
                    if(evt.getButton() == 1) {
                        needTarget = true;
                        getMovementhandler().init(fieldX, fieldY);
                    }
                }
            } 
            first = battlefield.getFields()[fieldX][fieldY];
        }

        else if(evt.getButton() == 1) {
            int fieldX;
            int fieldY;

            fieldX = (int) Math.floor(evt.getX() / 40);
            fieldY = (int) Math.floor(evt.getY() / 40);
            try {
                second = battlefield.getFields()[fieldX][fieldY];
                if(getAttackhandler().getTemplate()[fieldX][fieldY]) {
                    second = getAttackhandler().attack(fieldX, fieldY);
                    action = 1;
                } else if(getMovementhandler().getTemplate()[fieldX][fieldY] && second != first) {
                    getMovementhandler().move(fieldX,fieldY);
                    action = 1;
                } else {
                    action = 0;
                }
            } catch (Exception ex) {
                Logger.getLogger(InputHandler.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

    }

    /** Resets the all handlers to a state, where they can process another mouse click.
     *
     */
    public void reset() {
        first = null;
        second = null;
        needTarget = false;
        getMovementhandler().reset();
        getAttackhandler().reset();
        action = 0;
    }

    /**
     * @return The first field
     */
    public Field getFirst() {
        return first;
    }

    /**
     * @return The second field
     */
    public Field getSecond() {
        return second;
    }

    /**
     * @return The Movementhandler
     */
    public MovementHandler getMovementhandler() {
        return movementhandler;
    }

    /**
     * @return The Attackhandler
     */
    public AttackHandler getAttackhandler() {
        return attackhandler;
    }

    /**
     * @return The finished action
     */
    public int getAction() {
        return action;
    }

    /**
     * @return If there is a target needed
     */
    public boolean isNeedTarget() {
        return needTarget;
    }
    

}
