package inscryption.engine;

import inscryption.carte.CarteAnimal;

import java.util.ArrayList;
import java.util.List;

public class Adversaire extends Entite{
    private final int NB_MIN_MAIN = 4;

    private List<CarteAnimal> m_main = new ArrayList<CarteAnimal>();
    private Pioche m_pioche = new Pioche();

    public Adversaire() { m_score = 0; }

    private boolean peutPlacerCarte(CarteAnimal c, Plateau p, Position pos) throws Exception
    {
        boolean valide = false;
        if ( !p.getPlateau().containsKey(pos) )
            throw new Exception("La position n'existe pas !");

        if ( p.getPlateau().get(pos).isPresent() )
            return valide;

        valide = true;

        return valide;
    }

    public void afficherProchain(Plateau p)
    {

    }
}
