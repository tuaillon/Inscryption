import inscryption.carte.*;
import inscryption.engine.Game;
import inscryption.engine.Joueur;
import inscryption.engine.Plateau;
import inscryption.engine.Position;
import org.junit.Test;
import java.util.Optional;

import static org.junit.Assert.*;

public class ScoreTest
{
    @Test
    public void test1()
    {
        Game g = new Game();
        g.getPlateau().reinitialiser();

        CarteAnimal loup = CarteFactory.creerCarteAnimal(TypeAnimal.LOUP);

        g.getPlateau().positionnerCarte(loup, Position.B1);
        g.executerTourJoueur();

        assertEquals(3, g.getJoueur().getScore());
    }

    @Test
    public void test2()
    {
        Game g = new Game();
        g.getPlateau().reinitialiser();

        CarteAnimal loup = CarteFactory.creerCarteAnimal(TypeAnimal.LOUP);
        CarteAnimal hermine = CarteFactory.creerCarteAnimal(TypeAnimal.HERMINE);

        g.getPlateau().positionnerCarte(loup, Position.B1);
        g.getPlateau().positionnerCarte(hermine, Position.B2);
        g.executerTourJoueur();

        assertEquals(4, g.getJoueur().getScore());
    }

    @Test
    public void test3()
    {
        Game g = new Game();
        g.getPlateau().reinitialiser();

        CarteAnimal loup = CarteFactory.creerCarteAnimal(TypeAnimal.LOUP);
        CarteObstacle rocher = CarteFactory.creerCarteObstacle(TypeObstacle.ROCHER);

        g.getPlateau().positionnerCarte(rocher, Position.A1);
        g.getPlateau().positionnerCarte(loup, Position.B1);
        g.executerTourJoueur();

        assertEquals(0, g.getJoueur().getScore());
        assertEquals(2,g.getPlateau().getPlateau().get(Position.A1).get().getPv());
    }

    @Test
    public void test4()
    {
        Game g = new Game();
        assertEquals(0, g.getJoueur().getScore());
    }

    @Test
    public void test5()
    {
        Game g = new Game();
        assertEquals(0, g.getAdversaire().getScore());
    }

    public void test6()
    {
        Game g = new Game();
        g.getPlateau().reinitialiser();

        CarteAnimal gri = CarteFactory.creerCarteAnimal(TypeAnimal.GRIZZLY);
        CarteObstacle sapin = CarteFactory.creerCarteObstacle(TypeObstacle.SAPIN);

        g.getPlateau().positionnerCarte(gri, Position.B1);
        g.getPlateau().positionnerCarte(sapin, Position.A1);
        g.executerTourJoueur();
        // 4 -3   = 1
        assertEquals(1, g.getJoueur().getScore());
    }

    @Test
    public void test7()
    {
        Game g = new Game();
        g.getJoueur().modifierScore(10);
        g.getJoueur().resetScore();
        assertEquals(0, g.getJoueur().getScore());
    }

    @Test
    public void test8()
    {
        Game g = new Game();
        g.getPlateau().reinitialiser();

        CarteAnimal loup = CarteFactory.creerCarteAnimal(TypeAnimal.LOUP);

        g.getPlateau().positionnerCarte(loup, Position.A1);
        g.executerTourJoueur();
        g.executerTourAdversaire();

        assertEquals(3, g.getAdversaire().getScore());
        assertEquals(0, g.getJoueur().getScore());
    }



}
