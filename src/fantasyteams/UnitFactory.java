
package fantasyteams;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

/**
 * This class is responsible for the "production" of units and the activation/deactivation of units.
 * When it is first initialised, it reads the config file and creates a prototype for each unit.
 * Afterwards it clones the prototype for each unit ordered by Battlefield and also stores them.
 * UnitFactory is a Singleton because it has to store the prototypes and units.
 *
 * @author Pascal Mouret
 * @version 1.1
 * 
 */
public class UnitFactory {

    private static UnitFactory instance=null;
    private ArrayList<Unit> prototypes;
    private ArrayList<Unit> units;

    /**
     * Returns the UnitFactory-Object and, if it is not created yet, initialises it.
     * @return instance of UnitFactory
     */
    public static UnitFactory getInstance() {
        if(instance==null) {
            instance = new UnitFactory();
        }
        return instance;
    }

    /**
     * The private constructor for UnitFactory. Object is only available via getInstance().
     */
    private UnitFactory() {
        this.prototypes = new ArrayList<Unit>();
        this.units =  new ArrayList<Unit>();
        readConfig();
    }

    /**
     * Clones a ordered unit and returns a reference.
     * @param int needed unit-type
     * @return a reference to the new unit
     * @throws CloneNotSupportedException
     */
    public Unit produceUnit(int type) throws CloneNotSupportedException {
        // Unit gets cloned and stored in the units-List
        Unit tmpUnit = (Unit) prototypes.get(type).clone();
        units.add(tmpUnit);
        return tmpUnit;
    }

    /**
     * Reads the configfile and creates the prototypes
     */
    private void readConfig() {
        // File and buffer, prototypes gets a null. This is because if you want a field without a unit, you give 0, and get null
        File file = new File("config/units.ftc");
        String line = new String();
        prototypes.add(null);

        // Main section in which the file will be read
        try {
            // The readers
            FileReader fr = new FileReader(file);
            BufferedReader bfr = new BufferedReader(fr);

            // Go through the whole file
            line = bfr.readLine();
            for(int id=1;line != null;id++) {
                Unit tmpUnit = new Unit();
                //Set ID
                tmpUnit.setId(id);
                //Set Type
                tmpUnit.setType(line);
                line = bfr.readLine();
                //Set Health
                tmpUnit.setHealth(Integer.valueOf(line).intValue());
                line = bfr.readLine();
                //Set Movement
                tmpUnit.setMovement(Integer.valueOf(line).intValue());
                line = bfr.readLine();
                //Set Attack
                tmpUnit.setAttack(Integer.valueOf(line).intValue());
                line = bfr.readLine();
                //Set Defense
                tmpUnit.setDefense(Integer.valueOf(line).intValue());
                line = bfr.readLine();
                //Set Map Image
                tmpUnit.setImageMap(line);
                line = bfr.readLine();
                prototypes.add(tmpUnit);
            }

            // Don't need them anymore
            bfr.close();
            fr.close();

        // Catching possible Exceptions
        } catch (FileNotFoundException e) {
          e.printStackTrace();
        } catch (IOException e) {
          e.printStackTrace();
        }
    }

    /**
     * @return the units
     */
    public ArrayList<Unit> getUnits() {
        return units;
    }

    /**
     * Deactivates a unit
     * @param Unit unit which should be deactivated
     */
    public void deactivate(Unit p) {
        p.setActive(false);
    }

    /**
     * Activate the unit
     * @param Unit unit which sould be activated
     */
    public void reactivate(Unit p) {
        p.setActive(true);
        // Give the movement-points back
        int mov = prototypes.get(p.getId()).getMovement();
        p.setMovement(mov);
    }
}
