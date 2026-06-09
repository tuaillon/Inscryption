import inscryption.engine.Input;
import inscryption.engine.Joueur;
import inscryption.engine.Plateau;
import org.junit.Test;

import static org.junit.Assert.*;

public class PiocheTest
{
    @Test
    public void testPioche1()
    {
        Joueur j = new Joueur();
        j.piocher();
        assertEquals(5, j.getNbCarteMain());
    }

    @Test
    public void testPasPioche()
    {
        Joueur j = new Joueur();
        assertEquals(4, j.getNbCarteMain());
    }

    @Test
    public void testPioche2()
    {
        Joueur j = new Joueur();
        j.piocher();
        j.piocher();
        assertEquals(6, j.getNbCarteMain());
    }

    @Test
    public void piocheVideTest()
    {
        Joueur j = new Joueur();
        j.getMain().clear();
        j.getPioche().viderPioche();

        //la pioche est vide, si on essaye de piocher
        // la pioche renvoie un ecureuil pour rester dans l'esprit
        // du jeu original avec les deux pioches
        j.piocher();
        assertTrue(j.getCarteMain(0).getNom().equals("Ecureuil") );
    }




}
