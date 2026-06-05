package inscryption.engine;

import inscryption.carte.Carte;
import inscryption.carte.CarteAnimal;

import java.util.Optional;

public class Adversaire extends Entite
{
    private Optional<CarteAnimal>[] m_prochaineAction;

    public Adversaire()
    {
        super();
        m_prochaineAction = new Optional[Plateau.NB_CARTES_PAR_LIGNE];
        reinitialiserProchain();
        for ( int i = 0 ; i < NB_MIN_MAIN; i++ )
            piocher();

    }

    public Optional<CarteAnimal>[] getProchaineAction() { return m_prochaineAction; }

    public void jouerProchain(Plateau p) {
        Position[] ligneA = {Position.A1, Position.A2,
                Position.A3, Position.A4};

        for ( int i = 0; i < m_prochaineAction.length; i++ ) {
            if ( m_prochaineAction[i].isPresent() ) {
                Position posCible = ligneA[i];

                if ( p.getPlateau().get(posCible).isEmpty() ) {
                    p.changerCarte(posCible, m_prochaineAction[i].get());
                    m_prochaineAction[i] = Optional.empty();
                    // on ne peut pas juste appeler reinitialiserProchain
                    // car les cartes peuvent rester en attente si
                    // l'emplacement ennemi n'est pas libre
                }
            }
        }
        executerMeilleur(p); // remplie les prochains moves pour tour suivant

    }

    private void executerMeilleur(Plateau p)
    {
        Position[] ligneB = {Position.B1, Position.B2,
                Position.B3, Position.B4};

        //mauvaise stratégie juste pour tester
        for ( int i = 0; i < m_prochaineAction.length; i++ ) {
            if ( m_prochaineAction[i].isEmpty() && !m_main.isEmpty() )
            {
                CarteAnimal carte = m_main.remove(0);
                m_prochaineAction[i] = Optional.of(carte);
            }
        }

    }

    public void mettreAJourStats()
    {
        //tres espiègle, l'ia triche quelle honte
        m_nbGouttesDeSangTotal++;
        m_nbOsTotal++;
        m_pioche.piocher();
    }


    public void reinitialiserProchain()
    {
        for ( int i = 0; i < m_prochaineAction.length; i++ ) {
            m_prochaineAction[i] = Optional.empty();
        }
    }
}
