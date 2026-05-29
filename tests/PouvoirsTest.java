import inscryption.carte.CarteAnimal;
import inscryption.carte.CarteFactory;
import inscryption.carte.TypeAnimal;
import inscryption.engine.Joueur;
import inscryption.engine.Plateau;
import inscryption.engine.Position;
import org.junit.Test;
import java.util.Optional;

import static org.junit.Assert.*;

public class PouvoirsTest
{
    @Test
    public void pouvoirContactMortel()
    {
        CarteAnimal vipere = CarteFactory.creerCarteAnimal(TypeAnimal.VIPERE);
        CarteAnimal grizzly = CarteFactory.creerCarteAnimal(TypeAnimal.GRIZZLY);
        vipere.attaquer(Optional.of(grizzly));
        assertTrue(grizzly.estMort());
    }

    @Test
    public void pouvoirNombreusesVies()
    {
        CarteAnimal chat = CarteFactory.creerCarteAnimal(TypeAnimal.CHAT);
        Plateau p = new Plateau();
        p.positionnerCarte(chat, Position.B1);
        Joueur j = new Joueur();
        j.sacrifier(chat,p,Position.B1);
        //Chat pas mort
        assertTrue(p.getPlateau().get(Position.B1).isPresent());
    }
}
