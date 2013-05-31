package fantasyteams;

/**
 *
 * @author Pascal Mouret
 * @version 1.0
 */
public class Field implements Cloneable {

    private int id;
    private String type;
    private Unit unit;
    private int defense;
    private int[] passable;

    private String texture;

    public Field() {

    }

    @Override
    public Object clone() throws CloneNotSupportedException {
        return super.clone();
    }

    public boolean checkPassable(Unit unit) {
        if(this.unit!=null) {
            return false;
        }
        for(int p:passable) {
            if(p == unit.getId()) {
                return true;
            }
        }
        return false;

    }

    /**
     * @return the type
     */
    public String getType() {
        return type;
    }

    /**
     * @param type the type to set
     */
    public void setType(String type) {
        this.type = type;
    }

    /**
     * @return the unit
     */
    public Unit getUnit() {
        return unit;
    }

    /**
     * @param unit the unit to set
     */
    public void setUnit(Unit unit) {
        this.unit = unit;
    }

    /**
     * @return the defense
     */
    public int getDefense() {
        return defense;
    }

    /**
     * @param defense the defense to set
     */
    public void setDefense(int defense) {
        this.defense = defense;
    }

    /**
     * @return the passable
     */
    public int[] getPassable() {
        return passable;
    }

    /**
     * @param passable the passable to set
     */
    public void setPassable(int[] passable) {
        this.passable = passable;
    }

    /**
     * @return the id
     */
    public int getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * @return the texture
     */
    public String getTexture() {
        return texture;
    }

    /**
     * @param texture the texture to set
     */
    public void setTexture(String texture) {
        this.texture = texture;
    }

}
