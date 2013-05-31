/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package fantasyteams;

import java.util.ArrayList;

/**
 *
 * @author 5im08pamouret
 */
public class Game {

    private ArrayList<Unit> units;
    private UnitFactory unitFactory;
    private Battlefield battlefield;
    private int activePlayer = 0;

    public Game() {
        
    }

    public void makeReady(String battlefieldname) throws CloneNotSupportedException, Exception {
        this.unitFactory = UnitFactory.getInstance();
        this.battlefield = Battlefield.getInstance();
        this.units = unitFactory.getUnits();
        this.battlefield.generateBattlefield(battlefieldname);
        this.setActivePlayer(1);
    }

    public void endRound() {
        if(getActivePlayer() == 1) {
            setActivePlayer(2);
            for(Unit p:units) {
                if(p.getPlayer()==1) {
                    unitFactory.deactivate(p);
                }
                if(p.getPlayer()==2) {
                    unitFactory.reactivate(p);
                }
            }
        } else {
            setActivePlayer(1);
            for(Unit p:units) {
                if(p.getPlayer()==2) {
                    unitFactory.deactivate(p);
                }
                if(p.getPlayer()==1) {
                    unitFactory.reactivate(p);
                }
            }
        }
    }

    public Unit generateUnit() {
        Unit unit = new Unit();
        return unit;
    }

    /**
     * @return the battlefield
     */
    public Field[][] getFields() throws Exception {
        return battlefield.getFields();
    }

    /**
     * @return the activePlayer
     */
    public int getActivePlayer() {
        return activePlayer;
    }

    public Battlefield getBattlefield() {
        return battlefield;
    }
    public int isDefeated() {
        int player1 = 0, player2 = 0;
        for(Unit p: units) {
            if(p.getPlayer() == 1) {
                player1 = 1;
            }
        }
        if(player1 == 0) {
            return 1;
        }
        for(Unit p: units) {
            if(p.getPlayer() == 2) {
                player2 = 1;
            }
        }
        if(player2 == 0) {
            return 1;
        }
        return 0;
    }

    /**
     * @param activePlayer the activePlayer to set
     */
    public void setActivePlayer(int activePlayer) {
        this.activePlayer = activePlayer;
    }

}
