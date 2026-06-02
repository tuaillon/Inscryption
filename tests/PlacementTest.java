import inscryption.carte.*;
import inscryption.engine.Input;
import inscryption.engine.Joueur;
import inscryption.engine.Plateau;
import inscryption.engine.Position;
import org.junit.Test;

import static org.junit.Assert.*;

public class PlacementTest
{
    @Test
    public void placerTest1()
    {
        Plateau p = new Plateau();
        CarteObstacle co = CarteFactory.creerCarteObstacle(TypeObstacle.ROCHER);

        p.positionnerCarte(co, Position.B1);

        CarteAnimal autre = CarteFactory.creerCarteAnimal(TypeAnimal.LOUP);
        //placer sur une meme carte
        assertFalse(p.placementPossible(Position.B1));

    }


}
