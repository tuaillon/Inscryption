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

        CarteAnimal carteJoueur = CarteFactory.creerCarteAnimal(TypeAnimal.ECUREUIL);
        CarteAnimal carteAdversaire = CarteFactory.creerCarteAnimal(TypeAnimal.ECUREUIL);

        assertEquals(0, j.getScore());
        assertEquals(0, a.getScore());
    }
}
