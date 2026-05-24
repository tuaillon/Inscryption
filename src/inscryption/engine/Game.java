package inscryption.engine;

import inscryption.carte.Carte;

import java.util.Optional;
import java.util.Scanner;

public final class Game
{
    private final int NB_DE_PARTIES_POUR_GAGNER = 3;
    private final int NB_DE_POINTS_POUR_GAGNER_PARTIE = 5;
    Joueur m_joueur = new Joueur();
    Adversaire m_adversaire = new Adversaire();
    Plateau m_plateau = new Plateau();



    public Game() {};

    public void lancerJeu() {
        boolean gameRunning = true;

        for ( int partie = 1; partie <= NB_DE_PARTIES_POUR_GAGNER; partie++ )

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
                while ( !input.tryExecuteInput() )
                {
                    input.changerInput(sc.nextLine());
                }

                mettreAjourEtat();

            }
        }

    public void mettreAjourEtat()
    {
        for ( int i = 0; i < m_plateau.getNB_CASES_DE_JEU(); i++ )
        {
            //pour la case joueur on fait case + NBCASESPARJOUEUR pour avoir la carte enface
            int indexCarteEnnemie = i+ m_plateau.getNB_CASES_DE_JEU();
            m_plateau.getPlateau().get(indexCarteEnnemie).get();
        }
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
