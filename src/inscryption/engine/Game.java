package inscryption.engine;

import inscryption.carte.Carte;
import inscryption.carte.CarteFactory;
import inscryption.carte.TypeAnimal;
import inscryption.carte.TypePouvoir;

import java.util.Map;
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

    public void lancerJeu() throws Exception
    {
        boolean gameRunning = true;
        int partiesGagnees = 0;
        int tour = 1;

        for ( int partie = 1; partie <= NB_DE_PARTIES; partie++ ) {
            while (m_joueur.getScore() - m_adversaire.getScore() <= NB_DE_POINTS_POUR_GAGNER_PARTIE) {

                if ( tour == 2 ) //execution de croissance
                    executerPouvoirCroissance();

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
                while (!input.tryExecuteInput(m_joueur, m_plateau)) {
                    input.changerInput(sc.nextLine());
                }

                mettreAjourEtat();
                tour++;

            }
            tour = 1;
        }
        if ( partiesGagnees >= NB_DE_PARTIES_POUR_GAGNER )
            System.out.println("Vous avez gagné !");
        else
            System.out.println("La victoire n'est pas pour tout le monde !");
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

    public void executerPouvoirCroissance()
    {
        for ( Map.Entry<Position, Optional<Carte>> entry :
                m_plateau.getPlateau().entrySet() )
        {
            // si la carte a le pouvoir
            if ( entry.getValue().isPresent() &&
            entry.getValue().get().detientPouvoir(TypePouvoir.CROISSANCE))
                m_plateau.changerCarte(entry.getKey(),
                        CarteFactory.creerCarteAnimal(TypeAnimal.LOUP));
        }
    }
}
