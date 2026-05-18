package inscryption.engine;

import inscryption.carte.CarteAnimal;
import inscryption.carte.CarteFactory;

import java.util.ArrayList;
import java.util.Stack;

public class Pioche
{
    private final int NB_MAX_CARTES = 15;
    private Stack<CarteAnimal> m_pioche = new Stack<CarteAnimal>();

    public Pioche()
    {
        for (int i = 0; i < NB_MAX_CARTES; i++)
        {
            m_pioche.push(CarteFactory.creerCarteAnimalRandom());
        }
    }

    public CarteAnimal piocher()
    {
        return m_pioche.pop();
    }

    public int nbCartePioche()
    {
        return m_pioche.size();
    }
}
