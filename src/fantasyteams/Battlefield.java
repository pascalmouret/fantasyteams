package fantasyteams;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

/**
 *
 * @author Pascal Mouret
 * @version 1.2
 */
public class Battlefield {

    private int size=20;
    private Field[][] fields;
    private UnitFactory unitFactory;
    private FieldFactory fieldFactory;
    private boolean ready=false;
    
    private static Battlefield instance=null;

    public static Battlefield getInstance() throws CloneNotSupportedException, Exception {
        if(instance == null) {
            instance = new Battlefield();
        }
        return instance;
    }

    private Battlefield() throws CloneNotSupportedException, Exception {
        fields = new Field[size][size];
        this.unitFactory = UnitFactory.getInstance();
        this.fieldFactory = FieldFactory.getInstance();
    }

    /**
     * @return the fields
     */
    public Field[][] getFields() {
        return fields;
    }

    /**
     * @param fields the fields to set
     */
    public void setFields(Field fields[][]) {
        this.fields = fields;
    }

    public void generateBattlefield(String map) throws CloneNotSupportedException {
        File file = new File("maps/"+map);
        String line = new String();

        int chr = 0;
        int y = 0;
        int x = 0;
        int chrCount = 0;

        try {
            FileReader fr = new FileReader(file);
            BufferedReader bfr = new BufferedReader(fr);

            for(chr=bfr.read();chr!=-1;chr=bfr.read()) {
                // Jump to next line
                if(chr=='\n') {
                    y++;
                    x=0;
                    chrCount=0;
                    bfr.readLine();
                    continue;
                }
                // Check which action to perform
                switch(chrCount) {
                    case 0:
                        this.fields[x][y] = this.fieldFactory.produceField(chr-48);
                        chrCount++;
                        break;
                    case 1:
                        if(chr-48!=0) {
                            this.fields[x][y].setUnit(this.unitFactory.produceUnit(chr-48));
                        }
                        chrCount++;
                        break;
                    case 2:
                        if(this.fields[x][y].getUnit()!=null) {
                            this.fields[x][y].getUnit().setPlayer(chr-48);
                            this.fields[x][y].getUnit().setImageMap(this.fields[x][y].getUnit().getImageMap()+"_"+this.fields[x][y].getUnit().getPlayer()+".png");
                            if(this.fields[x][y].getUnit().getPlayer()==1) {
                                this.fields[x][y].getUnit().setActive(true);
                            }
                        }
                        chrCount++;
                        break;
                    case 3:
                        x++;
                        chrCount=0;
                        break;
                }
            }

            bfr.close();
            fr.close();

        } catch (FileNotFoundException e) {
          e.printStackTrace();
        } catch (IOException e) {
          e.printStackTrace();
        }
        ready = true;
    }

    /**
     * @return the ready
     */
    public boolean isReady() {
        return ready;
    }
}
