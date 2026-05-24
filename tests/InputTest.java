import inscryption.engine.Input;
import inscryption.engine.Joueur;
import inscryption.engine.Plateau;
import org.junit.Test;

import static org.junit.Assert.*;


public final class InputTest
{
    @Test
    public void testInput1() throws Exception {
        Joueur j = new Joueur();
        Plateau p = new Plateau();
        Input inp = new Input("piocher");
        assertTrue(inp.tryExecuteInput(j, p));

    }

    @Test
    public void testInput2() throws Exception {
        Joueur j = new Joueur();
        Plateau p = new Plateau();
        Input inp = new Input("fin");
        assertTrue(inp.tryExecuteInput(j, p));

    }

    @Test
    public void testInput3() throws Exception {
        Joueur j = new Joueur();
        Plateau p = new Plateau();
        Input inp = new Input("placer");
        assertFalse(inp.tryExecuteInput(j, p));

    }

    @Test
    public void testInput4() throws Exception {
        Joueur j = new Joueur();
        Plateau p = new Plateau();
        Input inp = new Input("placer 1 B1");
        assertTrue(inp.tryExecuteInput(j, p));

    }

    @Test
    public void testInput5() throws Exception {
        Joueur j = new Joueur();
        Plateau p = new Plateau();
        Input inp = new Input("placer 1 A1");
        assertFalse(inp.tryExecuteInput(j, p));

    }

    @Test
    public void testInput6() throws Exception {
        Joueur j = new Joueur();
        Plateau p = new Plateau();
        Input inp = new Input("fni");
        assertFalse(inp.tryExecuteInput(j, p));

    }

    @Test
    public void testInput7() throws Exception {
        Joueur j = new Joueur();
        Plateau p = new Plateau();
        Input inp = new Input("place 1 B1");
        assertFalse(inp.tryExecuteInput(j, p));

    }

    @Test
    public void testInput8() throws Exception {
        Joueur j = new Joueur();
        Plateau p = new Plateau();
        Input inp = new Input("pioche");
        assertFalse(inp.tryExecuteInput(j, p));

    }


}
