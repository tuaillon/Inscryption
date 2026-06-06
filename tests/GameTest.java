import inscryption.carte.CarteAnimal;
import inscryption.carte.CarteFactory;
import inscryption.carte.TypeAnimal;
import inscryption.engine.*;
import org.junit.Test;

import java.util.Optional;

import static org.junit.Assert.*;

public class GameTest
{
    @Test
    public void scoreTest1()
    {
        Game g = new Game();
        Plateau p = g.getPlateau();
        Joueur j = g.getJoueur();
        Adversaire a = g.getAdversaire();
        g.mettreAjourPlateau();
        a.jouerProchain(p);

        assertEquals(0, j.getScore());
        assertEquals(0, a.getScore());
    }

    @Test
    public void gagnerUnePartie()
    {
        Game g = new Game();

        //cartes volantes pour gagner direct la partie
        g.getPlateau().positionnerCarte(CarteFactory.creerCarteAnimal(TypeAnimal.CORBEAU),Position.B1);
        g.getPlateau().positionnerCarte(CarteFactory.creerCarteAnimal(TypeAnimal.CORBEAU),Position.B2);
        g.getPlateau().positionnerCarte(CarteFactory.creerCarteAnimal(TypeAnimal.CORBEAU),Position.B3);
        g.getPlateau().positionnerCarte(CarteFactory.creerCarteAnimal(TypeAnimal.CORBEAU),Position.B4);

        g.executerTourJoueur();
        g.executerTourAdversaire();

        boolean partieGagnee = (g.getJoueur().getScore() - g.getAdversaire().getScore() > 5);

        assertTrue(partieGagnee);

    }

    @Test
    public void perdreUnePartie()
    {
        Game g = new Game();

        //cartes volantes pour gagner direct la partie
        g.getPlateau().positionnerCarte(CarteFactory.creerCarteAnimal(TypeAnimal.CORBEAU),Position.A1);
        g.getPlateau().positionnerCarte(CarteFactory.creerCarteAnimal(TypeAnimal.CORBEAU),Position.A2);
        g.getPlateau().positionnerCarte(CarteFactory.creerCarteAnimal(TypeAnimal.CORBEAU),Position.A3);
        g.getPlateau().positionnerCarte(CarteFactory.creerCarteAnimal(TypeAnimal.CORBEAU),Position.A4);

        g.executerTourJoueur();
        g.executerTourAdversaire();

        boolean partiePerdue = (g.getAdversaire().getScore() - g.getJoueur().getScore() > 5);

        assertTrue(partiePerdue);

    }
}
