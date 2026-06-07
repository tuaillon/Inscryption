import inscryption.carte.CarteAnimal;
import inscryption.carte.CarteFactory;
import inscryption.carte.TypeAnimal;
import inscryption.carte.TypePouvoir;
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
        assertEquals(1, carteATester.getPv());
        assertFalse(carteATester.estMort());

    }

    @Test
    public void testTuer()
    {
        CarteAnimal vipere = CarteFactory.creerCarteAnimal(TypeAnimal.VIPERE);
        CarteAnimal grizzly = CarteFactory.creerCarteAnimal(TypeAnimal.GRIZZLY);
        vipere.activerPouvoir(TypePouvoir.CONTACT_MORTEL);
        vipere.attaquer(Optional.of(grizzly));
        assertTrue(grizzly.estMort());
    }

    @Test
    public void attkNormale()
    {
        CarteAnimal grizzly = CarteFactory.creerCarteAnimal(TypeAnimal.GRIZZLY);
        CarteAnimal loup = CarteFactory.creerCarteAnimal(TypeAnimal.LOUP);

        int pointsG = grizzly.attaquer(Optional.of(loup));
        int pointsL = loup.attaquer(Optional.of(grizzly));

        assertEquals(3, grizzly.getPv());
        assertTrue(loup.estMort());

    }
}
