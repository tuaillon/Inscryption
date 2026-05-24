package inscryption.engine;

import inscryption.carte.Carte;

import java.util.Optional;
import java.util.Scanner;

public final class Game
{
    private final int NB_DE_PARTIES = 3;
    private final int NB_DE_PARTIES_POUR_GAGNER = 2;
    private final int NB_DE_POINTS_POUR_GAGNER_PARTIE = 5;
    Joueur m_joueur = new Joueur();
    Adversaire m_adversaire = new Adversaire();
    Plateau m_plateau = new Plateau();



    public Game() {};

    public void lancerJeu() throws Exception {
        boolean gameRunning = true;

        for ( int partie = 1; partie <= NB_DE_PARTIES; partie++ )

            while ( m_joueur.getScore() - m_adversaire.getScore() <= NB_DE_POINTS_POUR_GAGNER_PARTIE )
            {
                m_adversaire.afficherProchain(m_plateau);
                m_plateau.afficherPlateau();
                m_joueur.afficherMain();

                System.out.println("Actions possibles : ");
                System.out.println("[fin] Terminer votre tour");
                System.out.println("[piocher] Piocher une carte");
                System.out.println("[placer] <numero carte> <position> Placer " +
                        "une carte sur la plateau");

                Scanner sc = new Scanner(System.in);
                Input input = new Input(sc.nextLine());
                while ( !input.tryExecuteInput(m_joueur, m_plateau) )
                {
                    input.changerInput(sc.nextLine());
                }

                mettreAjourEtat();

            }
        }

    public void mettreAjourEtat()
    {
        Position[] ligneAdversaire = {Position.A1, Position.A2, Position.A3, Position.A4};
        Position[] ligneJoueur = {Position.B1, Position.B2, Position.B3, Position.B4};
        int pointsJoueur = 0;
        int pointsAdversaire = 0;

        for ( int i = 0; i < ligneJoueur.length; i++ )
        {
            Optional<Carte> carteJoueur = m_plateau.getPlateau().get(ligneJoueur[i]);
            Optional<Carte> carteEnnemi = m_plateau.getPlateau().get(ligneAdversaire[i]);

            if ( carteJoueur.isPresent() )
                pointsJoueur += carteJoueur.get().attaquer(carteEnnemi);

            if ( carteEnnemi.isPresent() )
                pointsAdversaire += carteEnnemi.get().attaquer(carteJoueur);
        }

        m_joueur.modifierScore(pointsJoueur);
        m_adversaire.modifierScore(pointsAdversaire);
        mettreAjourPlateau();
    }

    //permet de mettre a jour le plateau apres chaque tour
    // comme mettre les optional a empty si la carte est morte
    public void mettreAjourPlateau()
    {
        for ( Position pos : m_plateau.getPlateau().keySet() )
        {
            if ( m_plateau.getPlateau().get(pos).isPresent() )
                // bcp de get :(
                if ( m_plateau.getPlateau().get(pos).get().estMort()  )
                {
                    m_plateau.getPlateau().put(pos, Optional.empty());

                    if ( !m_plateau.estEnnemi(pos) )//si la carte morte est nous
                        m_joueur.mettreAJourOs(1);
                }

    }

    }
}
