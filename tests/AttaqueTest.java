import inscryption.carte.*;
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

    @Test
    public void attkNormale2()
    {
        CarteAnimal loup = CarteFactory.creerCarteAnimal(TypeAnimal.LOUP);
        CarteAnimal elan = CarteFactory.creerCarteAnimal(TypeAnimal.ELAN);

        loup.attaquer(Optional.of(elan));
        elan.attaquer(Optional.of(loup));

        assertEquals(1, elan.getPv());
        assertEquals(0, loup.getPv());
        assertTrue(loup.estMort());
    }

    @Test
    public void attkObstacle()
    {
        CarteObstacle rocher = CarteFactory.creerCarteObstacle(TypeObstacle.ROCHER);

        CarteAnimal elan = CarteFactory.creerCarteAnimal(TypeAnimal.ELAN);

        //la methode attaquer() pour obstacle est override et renvoie 0
        int pvOriginelElan = elan.getPv();

        rocher.attaquer(Optional.of(elan));

        assertEquals(pvOriginelElan, elan.getPv());

        elan.attaquer(Optional.of(rocher));

        assertEquals(3, rocher.getPv());


    }
}
