/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package fantasyteams;

import java.util.ArrayList;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author pascal
 */
public class UnitFactoryTest {

    public UnitFactoryTest() {
    }

    @BeforeClass
    public static void setUpClass() throws Exception {
    }

    @AfterClass
    public static void tearDownClass() throws Exception {
    }

    @Before
    public void setUp() {
    }

    @After
    public void tearDown() {
    }

    /**
     * Test of getInstance method, of class UnitFactory.
     */
    @Test
    public void testGetInstance() {
        UnitFactory result = UnitFactory.getInstance();
        assertTrue(result instanceof UnitFactory);
        fail("No instance created");
    }

    /**
     * Test of produceUnit method, of class UnitFactory.
     */
    @Test
    public void testProduceUnit() throws Exception {
        UnitFactory instance = UnitFactory.getInstance();
        Unit tmpUnit = new Unit();
        tmpUnit.setType("Tank");
        tmpUnit.setHealth(20);
        tmpUnit.setMovement(5);
        tmpUnit.setAttack(8);
        tmpUnit.setDefense(5);
        tmpUnit.setImageMap("images/tank");

        Unit result = instance.produceUnit(1);
        assertEquals(tmpUnit, result);

        fail("Wrong/no Unit returned.");
    }

    /**
     * Test of deactivate method, of class UnitFactory.
     */
    @Test
    public void testDeactivate() throws CloneNotSupportedException {
        UnitFactory instance = UnitFactory.getInstance();
        Unit tmpUnit = instance.produceUnit(1);
        tmpUnit.setActive(true);

        instance.deactivate(tmpUnit);
        assertFalse(tmpUnit.isActive());
        fail("Not deactivated.");
    }

    /**
     * Test of reactivate method, of class UnitFactory.
     */
    @Test
    public void testReactivate() throws CloneNotSupportedException {
        UnitFactory instance = UnitFactory.getInstance();
        Unit tmpUnit = instance.produceUnit(1);
        tmpUnit.setActive(false);

        instance.reactivate(tmpUnit);
        assertFalse(tmpUnit.isActive());
        fail("Not activated.");
    }

}