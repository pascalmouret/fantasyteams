/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package fantasyteams;

/**
 *
 * @author 5im08luneukom
 */
public class AttackHandler {

    private int size=20;

    int x,y;
    private Field from;
    private boolean[][] template=null;
    private boolean[][] mvtemplate=null;
    private Field[][] fields=null;
    private Unit unit;
    private Unit enemy;

    private static AttackHandler instance=null;

    public static AttackHandler getInstance() throws CloneNotSupportedException, Exception {
        if(instance == null) {
            instance = new AttackHandler();
        }
        return instance;
    }

    private AttackHandler() throws CloneNotSupportedException, Exception {
        this.fields = Battlefield.getInstance().getFields();
        template = new boolean[size][size];
        mvtemplate = new boolean[size][size];
    }

    public void init(int x, int y) throws Exception {
        this.unit = fields[x][y].getUnit();
        this.emptyTemplate();
        this.mvtemplate = MovementHandler.getInstance().getTemplate();
        if(!unit.isActive()) {
            return;
        }
        this.from = this.fields[x][y];
        this.x=x;
        this.y=y;
        calculate();
    }

    public void reset() {
        emptyTemplate();
        x=0;
        y=0;
        unit=null;
        enemy=null;
        from=null;
    }

    public Field attack(int x, int y) throws Exception {
        int def,atk,tot;

        enemy = fields[x][y].getUnit();
        def = enemy.getDefense() + fields[x][y].getDefense();
        atk = unit.getAttack();
        tot = atk-def;
        if(tot<0) {
            tot=0;
        }

        enemy.setHealth(enemy.getHealth()-tot);
        if(enemy.getHealth()<=0) {
            fields[x][y].setUnit(null);
            UnitFactory.getInstance().getUnits().remove(enemy);
        }

        Field nField = MovementHandler.getInstance().moveNear(x,y);
        unit.setActive(false);
        return nField;
    }

    private void emptyTemplate() {
        int i,c;
        for(i=0;i<=19;i++) {
            for(c=0;c<=19;c++) {
                this.template[i][c] = false;
            }
        }
    }

    private void calculate() {
        int tmpX,tmpY;
        for(tmpX=0;tmpX<=19;tmpX++) {
            for(tmpY=0;tmpY<=19;tmpY++) {
                if(tmpX+1<=19 && isAttackable(tmpX+1,tmpY) && mvtemplate[tmpX][tmpY] == true) {
                    this.template[tmpX+1][tmpY] = true;
                }
                if(tmpX-1>=0 && isAttackable(tmpX-1,tmpY) && mvtemplate[tmpX][tmpY] == true) {
                    this.template[tmpX-1][tmpY] = true;
                }
                if(tmpY+1<=19 && isAttackable(tmpX,tmpY+1) && mvtemplate[tmpX][tmpY] == true) {
                    this.template[tmpX][tmpY+1] = true;
                }
                if(tmpY-1>=0 && isAttackable(tmpX,tmpY-1) && mvtemplate[tmpX][tmpY] == true) {
                    this.template[tmpX][tmpY-1] = true;
                }
            }
        }
    }

    private boolean isAttackable(int x, int y) {
        if(fields[x][y].getUnit()==null) {
            return false;
        }
        if(fields[x][y].getUnit().getPlayer()==this.unit.getPlayer()) {
            return false;
        }
        return true;
    }

    public boolean[][] getTemplate() {
        return template;
    }
}
