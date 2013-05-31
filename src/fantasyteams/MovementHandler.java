/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package fantasyteams;

/**
 *
 * @author Pascal Mouret
 */
public class MovementHandler {

    private int size=20;

    int x,y;
    private Field from;
    private boolean[][] template=null;
    private int[][] mptemplate=null;
    private Field[][] fields=null;
    private Unit unit;

    private static MovementHandler instance=null;

    public static MovementHandler getInstance() throws CloneNotSupportedException, Exception {
        if(instance == null) {
            instance = new MovementHandler();
        }
        return instance;
    }

    private MovementHandler() throws CloneNotSupportedException, Exception {
        this.fields = Battlefield.getInstance().getFields();
        template = new boolean[size][size];
        mptemplate = new int[size][size];
    }

    public void init(int x, int y) throws Exception {
        this.unit = fields[x][y].getUnit();
        if(!unit.isActive()) {
            return;
        }
        this.from = this.fields[x][y];
        this.x=x;
        this.y=y;
        this.calculate(unit.getMovement(),x,y);
        AttackHandler.getInstance().init(x, y);
    }

    public void reset() {
        emptyTemplate();
        x=0;
        y=0;
        unit=null;
        from=null;
    }

    private void emptyTemplate() {
        int i,c;
        for(i=0;i<=19;i++) {
            for(c=0;c<=19;c++) {
                this.template[i][c] = false;
                this.mptemplate[i][c] = size++;
            }
        }
    }

    public void move(int x, int y) throws Exception {
        fields[x][y].setUnit(unit);
        fields[this.x][this.y].setUnit(null);
        unit.setMovement(unit.getMovement()-mptemplate[x][y]);
    }

    public Field moveNear(int x, int y) throws Exception {
        if((x+1==this.x&&y==this.y) ||
            (x-1==this.x&&y==this.y) ||
            (x==this.x&&y+1==this.y) ||
            (x==this.x&&y-1==this.y)) {
            return fields[x][y];
        }
        int resX = x;
        int resY = y;
        int min = 20;
        if(mptemplate[x+1][y]<min && fields[x+1][y].checkPassable(unit)) {
            min = mptemplate[x+1][y];
            resY = y;
            resX = x+1;
        }
        if(mptemplate[x-1][y]<min && fields[x-1][y].checkPassable(unit)) {
            min = mptemplate[x-1][y];
            resX = x-1;
            resY = y;
        }
        if(mptemplate[x][y+1]<min && fields[x][y+1].checkPassable(unit)) {
            min = mptemplate[x][y+1];
            resX = x;
            resY = y+1;
        }
        if(mptemplate[x][y-1]<min && fields[x][y-1].checkPassable(unit)) {
            min = mptemplate[x][y-1];
            resX = x;
            resY = y-1;
        }

        move(resX,resY);
        return fields[resX][resY];
    }

    private void calculate(int radius, int x, int y) {
        template[x][y] = true;
        if(radius==0) {
            return;
        }
        
        if((unit.getMovement()-radius) < mptemplate[x][y]) {
            mptemplate[x][y] = unit.getMovement()-radius;
        }

        if(x+1<=19 && fields[x+1][y].checkPassable(this.from.getUnit())) {
            this.template[x+1][y] = true;
            this.calculate(radius-1,x+1,y);
        }
        if(x-1>=0 && fields[x-1][y].checkPassable(this.from.getUnit())) {
            this.template[x-1][y] = true;
            this.calculate(radius-1,x-1,y);
        }
        if(y+1<=19 && fields[x][y+1].checkPassable(this.from.getUnit())) {
            this.template[x][y+1] = true;
            this.calculate(radius-1,x,y+1);
        }
        if(y-1>=0 && fields[x][y-1].checkPassable(this.from.getUnit())) {
            this.template[x][y-1] = true;
            this.calculate(radius-1,x,y-1);
        }
    }

    public boolean[][] getTemplate() {
        return template;
    }
}
