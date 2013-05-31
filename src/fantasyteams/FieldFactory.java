
package fantasyteams;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

/**
 *
 * @author pascal
 */
public class FieldFactory {

    private static FieldFactory instance=null;
    private ArrayList<Field> prototypes;

    public static FieldFactory getInstance() {
        if(instance == null) {
            instance = new FieldFactory();
        }
        return instance;
    }

    private FieldFactory() {
        this.prototypes = new ArrayList<Field>();
        readConfig();
    }

    public Field produceField(int type) throws CloneNotSupportedException {
        Field tmpField = (Field) prototypes.get(type).clone();
        return tmpField;
    }

    private void readConfig() {
        File file = new File("config/fields.ftc");
        String line = new String();
        prototypes.add(null);

        try {
            FileReader fr = new FileReader(file);
            BufferedReader bfr = new BufferedReader(fr);

            line = bfr.readLine();
            for(int id=1;line != null;id++) {
                Field tmpField = new Field();
                //Set ID
                tmpField.setId(id);
                //Set Type
                tmpField.setType(line);
                line = bfr.readLine();
                //Set Defense
                tmpField.setDefense(Integer.valueOf(line).intValue());
                line = bfr.readLine();
                //Set Passable
                tmpField.setPassable(getPassableList(line));
                line = bfr.readLine();
                //Set Texture
                tmpField.setTexture(line);
                line = bfr.readLine();
                prototypes.add(tmpField);
            }

            bfr.close();
            fr.close();

        } catch (FileNotFoundException e) {
          e.printStackTrace();
        } catch (IOException e) {
          e.printStackTrace();
        }
    }

    private int[] getPassableList(String line) {
        int[] ret = new int[line.length()];
        for(int i=0;i<=line.length()-1;i++) {
            ret[i] = Integer.parseInt(line.substring(i,i+1));
        }
        return ret;
    }
    
}
