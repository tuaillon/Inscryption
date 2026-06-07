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
    public void simulertourComplet()
    {
        Game g = new Game();
        Plateau p = g.getPlateau();
        Joueur joueur = g.getJoueur();
        Adversaire adversaire = g.getAdversaire();

        p.reinitialiser();

        joueur.getMain().clear();
        CarteAnimal loup = CarteFactory.creerCarteAnimal(TypeAnimal.LOUP);
        CarteAnimal hermine = CarteFactory.creerCarteAnimal(TypeAnimal.HERMINE);
        CarteAnimal corbeau = CarteFactory.creerCarteAnimal(TypeAnimal.CORBEAU);
        joueur.ajouterCarteMain(loup);
        joueur.ajouterCarteMain(hermine);
        joueur.ajouterCarteMain(corbeau);
        assertEquals(3, joueur.getNbCarteMain());

        int mainAvantPioche = joueur.getNbCarteMain();
        joueur.piocher();
        assertEquals(mainAvantPioche+1, joueur.getNbCarteMain());

        p.positionnerCarte(loup, Position.B1);
        p.positionnerCarte(hermine, Position.B2);
        p.positionnerCarte(corbeau, Position.B3);

        joueur.retirerCarteMain(loup);
        joueur.retirerCarteMain(hermine);
        joueur.retirerCarteMain(corbeau);

        assertTrue(p.getPlateau().get(Position.B1).isPresent());
        assertTrue(p.getPlateau().get(Position.B2).isPresent());
        assertTrue(p.getPlateau().get(Position.B3).isPresent());

        //on mets des cartes adverses
        CarteAnimal louveteau = CarteFactory.creerCarteAnimal(TypeAnimal.LOUVETEAU);
        CarteAnimal grizzly = CarteFactory.creerCarteAnimal(TypeAnimal.GRIZZLY);
        p.positionnerCarte(louveteau, Position.A1);
        p.positionnerCarte(grizzly, Position.A2);

        int scoreJoueurAvant = joueur.getScore();
        g.executerTourJoueur();

        assertEquals(scoreJoueurAvant +4, joueur.getScore());

        assertEquals(5, grizzly.getPv());

        int osAvant = joueur.getNbOsTotal();
        g.mettreAjourPlateau();

        assertFalse(p.getPlateau().get(Position.A1).isPresent());
        assertTrue(p.getPlateau().get(Position.A2).isPresent());
        assertEquals(osAvant, joueur.getNbOsTotal());

        int scoreAdversaireAvant = adversaire.getScore();
        g.executerTourAdversaire();

        assertEquals(scoreAdversaireAvant +1, adversaire.getScore());

        assertTrue(hermine.estMort());

        g.mettreAjourPlateau();

        assertFalse(p.getPlateau().get(Position.B2).isPresent());
        assertEquals(osAvant +1, joueur.getNbOsTotal());

        assertTrue(p.getPlateau().get(Position.B1).isPresent());
        assertTrue(p.getPlateau().get(Position.B3).isPresent());

        assertTrue(Math.abs(joueur.getScore() -adversaire.getScore()) <5);
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
