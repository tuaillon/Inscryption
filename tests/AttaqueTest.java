import inscryption.carte.CarteAnimal;
import inscryption.carte.CarteFactory;
import inscryption.carte.TypeAnimal;
import org.junit.Test;

import java.util.Optional;

import static org.junit.Assert.*;

public class AttaqueTest
{
    @Test
    public void testVolant()
    {
        CarteAnimal volant = CarteFactory.creerCarteAnimal(TypeAnimal.CORBEAU);
        CarteAnimal carteATester = CarteFactory.creerCarteAnimal(TypeAnimal.ECUREUIL);
        int pointsGagnes = volant.attaquer(Optional.of(carteATester));

        assertEquals(2, pointsGagnes);
        assertFalse(carteATester.estMort());

    }

    @Test
    public void testTuer()
    {
        CarteAnimal vipere = CarteFactory.creerCarteAnimal(TypeAnimal.VIPERE);
        CarteAnimal grizzly = CarteFactory.creerCarteAnimal(TypeAnimal.GRIZZLY);
        vipere.attaquer(Optional.of(grizzly));
        assertTrue(grizzly.estMort());
    }
}
