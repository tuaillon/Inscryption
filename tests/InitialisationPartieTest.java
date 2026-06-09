import inscryption.carte.CarteAnimal;
import inscryption.carte.CarteFactory;
import inscryption.carte.TypeAnimal;
import inscryption.engine.Game;
import org.junit.Test;

import static org.junit.Assert.*;


public class InitialisationPartieTest
{
    @Test
    public void miseEnPlace() throws Exception {
        Game g = new Game();

        assertEquals(0,g.getJoueur().getNbGouttesDeSang());
        assertEquals(0,g.getAdversaire().getNbGouttesDeSang());

        assertEquals(0,g.getJoueur().getNbOsTotal());
        assertEquals(0,g.getAdversaire().getNbOsTotal());

        assertEquals(4,g.getJoueur().getNbCarteMain());
        assertEquals(4,g.getAdversaire().getNbCarteMain());
        
        assertEquals(11, g.getJoueur().getPioche().nbCartePioche());
        assertEquals(11, g.getJoueur().getPioche().nbCartePioche());

        g.getJoueur().piocher();
        g.getJoueur().piocher();
        g.getJoueur().piocher();
        g.getAdversaire().piocher();
        g.getAdversaire().piocher();

        g.preparerJeu(); // les pioches sont reinitialisées a la deuxieme partie

        assertEquals(11, g.getJoueur().getPioche().nbCartePioche());
        assertEquals(11, g.getJoueur().getPioche().nbCartePioche());


    }

    @Test
    public void simulationChoixParmi2_3emePartie() throws Exception {
        /*
        On va simuler la méthode proposerUneCarte(2) qui utilise un scanner
        On suppose qu'on est a la fin de la deuxieme partie
         */
        Game g = new Game();
        g.preparerJeu();

        CarteAnimal[] options = new CarteAnimal[2];
        for ( int i = 0; i < options.length ; i++ )
        {
            options[i] = CarteFactory.creerCarteAnimalRandom();
        }

        // options contient 2 cartes on suppose que l'utilisateur choisie
        // la premiere

        g.getJoueur().ajouterCarteMain(options[0]);

        assertEquals(5, g.getJoueur().getNbCarteMain());


    }
}
