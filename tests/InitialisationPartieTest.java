import inscryption.carte.CarteAnimal;
import inscryption.carte.CarteFactory;
import inscryption.carte.TypeAnimal;
import inscryption.engine.Game;
import org.junit.Test;

import static org.junit.Assert.*;


public class InitialisationPartieTest
{
    @Test
    public void miseEnPlace()
    {
        Game g = new Game();

        assertEquals(0,g.getJoueur().getNbGouttesDeSang());
        assertEquals(0,g.getAdversaire().getNbGouttesDeSang());

        assertEquals(0,g.getJoueur().getNbOsTotal());
        assertEquals(0,g.getAdversaire().getNbOsTotal());

        assertEquals(4,g.getJoueur().getNbCarteMain());
        assertEquals(4,g.getAdversaire().getNbCarteMain());
    }
}
