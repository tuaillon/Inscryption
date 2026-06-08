import inscryption.carte.*;
import inscryption.engine.*;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;

import static org.junit.Assert.*;

public class PierreDeSacrificeTest
{
    /* executerPierreDeSacrifice() nécessite un scanner
    on va donc simuler la méthode :
     */
    @Test
    public void pierreDeSacrificeAucuneCarteDispoPlateau()
    {
        System.out.println("=============Pierre de Sacrifice=============");
        System.out.println("Il est l'heure de sacrifier une carte !");

        List<CarteAnimal> choixPossibles = new ArrayList<>();
        List<Position> positionsPossibles = new ArrayList<>();

        Game g = new Game();

        for ( Position pos : g.getPlateau().getPlateau().keySet() )
        {
            if ( pos.name().startsWith("B") && g.getPlateau().getPlateau().get(pos).isPresent() )
            {
                Carte c = g.getPlateau().getPlateau().get(pos).get();
                if ( c.estAnimal() )
                {
                    choixPossibles.add((CarteAnimal) c);
                    positionsPossibles.add(pos);
                }
            }
        }


        assertTrue(choixPossibles.isEmpty());
        System.out.println("Aucune carte n'est sacrifiable !");
        System.out.println("=============FIN- Pierre de Sacrifice=============");
        return;

    }


    @Test
    public void pierreDeSacrificeValide() throws Exception {
        System.out.println("=============Pierre de Sacrifice=============");
        System.out.println("Il est l'heure de sacrifier une carte !");

        List<CarteAnimal> choixPossibles = new ArrayList<>();
        List<Position> positionsPossibles = new ArrayList<>();

        Game g = new Game();

        CarteAnimal chat = CarteFactory.creerCarteAnimal(TypeAnimal.CHAT);

        g.getPlateau().positionnerCarte(chat, Position.B1);

        for ( Position pos : g.getPlateau().getPlateau().keySet() )
        {
            if ( pos.name().startsWith("B") && g.getPlateau().getPlateau().get(pos).isPresent() )
            {
                Carte c = g.getPlateau().getPlateau().get(pos).get();
                if ( c.estAnimal() )
                {
                    choixPossibles.add((CarteAnimal) c);
                    positionsPossibles.add(pos);
                }
            }
        }

        if ( choixPossibles.isEmpty() )
        {
            System.out.println("Aucune carte n'est sacrifiable !");
            System.out.println("=============FIN- Pierre de Sacrifice=============");
            return;
        }

        int choix = 1; // on prerempli le choix du scanner

        int indexSacre = choix - 1;
        CarteAnimal carteSacrifiee = choixPossibles.get(indexSacre);
        TypePouvoir pouvoirFutur = carteSacrifiee.getPouvoirAssocie();

        System.out.println(carteSacrifiee.getNom() +" a été sacrifié !");

        carteSacrifiee.tuer();
        g.getPlateau().retirerCarteA(positionsPossibles.get(indexSacre));

        System.out.println("Quelle carte détiendra le pouvoir " +pouvoirFutur.name() +" ?");

        int choixCarteMain = 1; //autre scanner

        g.getJoueur().getCarteMain(choixCarteMain-1).activerPouvoir(pouvoirFutur);
        System.out.println(g.getJoueur().getCarteMain(choixCarteMain-1).getNom() +" a obtenu le pouvoir " +
                pouvoirFutur.name() + " !");


        assertTrue(g.getJoueur().getCarteMain(choixCarteMain-1).detientPouvoir(TypePouvoir.NOMBREUSES_VIES) );

        System.out.println("=============FIN- Pierre de Sacrifice=============");

    }

}
