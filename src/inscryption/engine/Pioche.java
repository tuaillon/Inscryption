package inscryption.engine;

import inscryption.carte.CarteAnimal;
import inscryption.carte.CarteFactory;
import inscryption.carte.TypeAnimal;

import java.util.*;

public class Pioche
{
    private final int NB_MAX_CARTES = 15;

    // Plage de recherches du nombre de cartes écureuils
    private final int NB_MIN_ECUREUIL = 8;
    private final int NB_MAX_ECUREUIL = 11;

    private Stack<CarteAnimal> m_pioche = new Stack<CarteAnimal>();

    public Pioche()
    {
        // Générer une pioche constituée majoritairement d'écureuils
        // Choix d'une plage générée aléatoirement
        Random rnd = new Random();
        int range = NB_MAX_ECUREUIL - NB_MIN_ECUREUIL + 1;
        int nbCartesEcureuil = rnd.nextInt(NB_MIN_ECUREUIL) + range;

        // Ajouter ce nombre choisi aléatoirement à la pioche
        for (int i = 0; i < nbCartesEcureuil; i++)
        {
            m_pioche.push(CarteFactory.creerCarteAnimal(TypeAnimal.ECUREUIL));
        }

        // Ajouter le nombre de cartes restantes à la pioche
        int nbCarteAPiocher = NB_MAX_CARTES - nbCartesEcureuil;

        // Générer le reste de la pioche
        for (int i = 0; i < nbCarteAPiocher; i++)
        {
            m_pioche.push(CarteFactory.creerCarteAnimalRandom());
        }

        // Mélanger les cartes
        Collections.shuffle(m_pioche);
    }

    public CarteAnimal piocher()
    {
        return m_pioche.pop();
    }

    public int nbCartePioche()
    {
        return m_pioche.size();
    }

    public void regenererPiocheAleatoire()
    {
        viderPioche();
        for (int i = 0; i < NB_MAX_CARTES; i++)
        {
            m_pioche.push(CarteFactory.creerCarteAnimalRandom());
        }
    }

    public void regenererPiocheDebutEcureuil() throws Exception
    {
        viderPioche();
        // Générer une pioche constituée majoritairement d'écureuils
        // Choix d'une plage générée aléatoirement
        Random rnd = new Random();
        int range = NB_MAX_ECUREUIL - NB_MIN_ECUREUIL + 1;
        int nbCartesEcureuil = rnd.nextInt(NB_MIN_ECUREUIL) + range;

        // Ajouter ce nombre choisi aléatoirement à la pioche
        for (int i = 0; i < nbCartesEcureuil; i++)
        {
            m_pioche.push(CarteFactory.creerCarteAnimal(TypeAnimal.ECUREUIL));
        }

        // Ajouter le nombre de cartes restantes à la pioche
        int nbCarteAPiocher = NB_MAX_CARTES - nbCartesEcureuil;

        // Générer le reste de la pioche
        for (int i = 0; i < nbCarteAPiocher; i++)
        {
            m_pioche.push(CarteFactory.creerCarteAnimalRandom());
        }

        // Mélanger les cartes
        Collections.shuffle(m_pioche);
    }

    public void viderPioche() {
        m_pioche.clear();
    }

    public void afficherPioche()
    {
        System.out.println("\n\t\t\tPioche : " + nbCartePioche() + " cartes\n");
    }
}
