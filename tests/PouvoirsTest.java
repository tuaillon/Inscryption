import inscryption.carte.*;
import inscryption.engine.Game;
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
        vipere.activerPouvoir(TypePouvoir.CONTACT_MORTEL);
        vipere.attaquer(Optional.of(grizzly));
        assertTrue(grizzly.estMort());
    }

    @Test
    public void pouvoirNombreusesVies()
    {
        CarteAnimal chat = CarteFactory.creerCarteAnimal(TypeAnimal.CHAT);
        chat.activerPouvoir(TypePouvoir.NOMBREUSES_VIES);
        Plateau p = new Plateau();
        p.positionnerCarte(chat, Position.B1);
        Joueur j = new Joueur();

        /* On va simuler cet appel de methode qui utilise un scanner:
        j.sacrifier(chat,p,Position.B1);
        */
        if ( !chat.detientPouvoir(TypePouvoir.NOMBREUSES_VIES) )
            p.retirerCarteA(Position.B1);

        //Chat pas mort
        assertTrue(p.getPlateau().get(Position.B1).isPresent());
    }

    @Test
    public void pouvoirCoureur()
    {
        Game g = new Game();
        CarteAnimal coureur = CarteFactory.creerCarteAnimal(TypeAnimal.ELAN);
        coureur.activerPouvoir(TypePouvoir.COUREUR);

        g.getPlateau().reinitialiser(); // enlever les obstacles
        g.getPlateau().positionnerCarte(coureur, Position.B2);
        g.executerPouvoirCoureur();
        assertTrue(g.getPlateau().getPlateau().get(Position.B3).isPresent());
        assertFalse(g.getPlateau().getPlateau().get(Position.B2).isPresent());
        assertTrue(g.getPlateau().getPlateau().get(Position.B3).get().estAnimal());
    }

    @Test
    public void pouvoirCoureurGauche()
    {
        Game g = new Game();
        CarteAnimal coureur = CarteFactory.creerCarteAnimal(TypeAnimal.ELAN);
        coureur.activerPouvoir(TypePouvoir.COUREUR);

        CarteObstacle truc = CarteFactory.creerCarteObstacleRandom();

        g.getPlateau().reinitialiser(); // enlever les obstacles
        g.getPlateau().positionnerCarte(coureur, Position.B2);
        g.getPlateau().positionnerCarte(truc, Position.B3);
        g.executerPouvoirCoureur();
        assertTrue(g.getPlateau().getPlateau().get(Position.B3).isPresent());
        assertFalse(g.getPlateau().getPlateau().get(Position.B2).isPresent());
        assertTrue(g.getPlateau().getPlateau().get(Position.B1).get().estAnimal());
    }

    @Test
    public void pouvoirCoureurBloque()
    {
        Game g = new Game();
        CarteAnimal coureur = CarteFactory.creerCarteAnimal(TypeAnimal.ELAN);
        coureur.activerPouvoir(TypePouvoir.COUREUR);

        CarteObstacle truc = CarteFactory.creerCarteObstacleRandom();
        CarteObstacle truc2 = CarteFactory.creerCarteObstacleRandom();

        g.getPlateau().reinitialiser(); // enlever les obstacles
        g.getPlateau().positionnerCarte(coureur, Position.B2);
        g.getPlateau().positionnerCarte(truc, Position.B3);
        g.getPlateau().positionnerCarte(truc2, Position.B1);
        g.executerPouvoirCoureur();
        assertTrue(g.getPlateau().getPlateau().get(Position.B3).isPresent());
        assertTrue(g.getPlateau().getPlateau().get(Position.B1).isPresent());
        assertTrue(g.getPlateau().getPlateau().get(Position.B2).get().estAnimal());
    }

    @Test
    public void pouvoirCroissance()
    {
        Game g = new Game();
        CarteAnimal louveteau = CarteFactory.creerCarteAnimal(TypeAnimal.LOUVETEAU);
        louveteau.activerPouvoir(TypePouvoir.CROISSANCE);

        g.getPlateau().positionnerCarte(louveteau, Position.B2);
        g.executerPouvoirCroissance();

        assertTrue(g.getPlateau().getPlateau().
                get(Position.B2).get().getNom().equals("Loup"));

    }

    @Test
    public void pouvoirPiques()
    {
        CarteAnimal piquepique = CarteFactory.creerCarteAnimal(TypeAnimal.PORC_EPIC);
        CarteAnimal louveteau = CarteFactory.creerCarteAnimal(TypeAnimal.LOUVETEAU);
        piquepique.activerPouvoir(TypePouvoir.PIQUES_POINTUES);

        int pvOriginel = louveteau.getPv();
        louveteau.attaquer(Optional.of(piquepique));
        assertEquals(pvOriginel - 1, louveteau.getPv());
    }

    @Test
    public void pouvoirPuant()
    {
        CarteAnimal loup = CarteFactory.creerCarteAnimal(TypeAnimal.LOUP);
        CarteAnimal punaise = CarteFactory.creerCarteAnimal(TypeAnimal.PUNAISE);
        punaise.activerPouvoir(TypePouvoir.PUANT);

        int attkOriginel = loup.getAttk();
        loup.attaquer(Optional.of(punaise));

        assertEquals(attkOriginel -1, loup.getAttk());

    }


}
