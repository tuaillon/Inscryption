package inscryption.engine;

import inscryption.carte.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;

public final class Game
{
    private final int NB_DE_PARTIES = 3;
    private final int NB_DE_PARTIES_POUR_GAGNER = 2;
    private final int NB_DE_POINTS_POUR_GAGNER_PARTIE = 5;

    private Joueur m_joueur = new Joueur();
    private Adversaire m_adversaire = new Adversaire();
    private Plateau m_plateau = new Plateau();
    private boolean m_bApioche = false;
    private boolean m_finTour = false;

    public boolean piochePossible() { return !m_bApioche; }
    public void interdirPioche() { m_bApioche = true; }
    public void autoriserPioche() { m_bApioche = false; }
    public void terminerTour() { m_finTour = true; }

    public Game() {};

    public Plateau getPlateau() {return m_plateau;}

    public void lancerJeu() throws Exception
    {
        int partiesGagnees = 0;
        int tour = 1;

        for ( int partie = 1; partie <= NB_DE_PARTIES; partie++ )
        {

            System.out.println("Partie : "+partie + " -------------------");
            System.out.println();

            preparerJeu(); //bah oui c'est quand même mieux de reset le score

            if ( partie == 3 )
                executerPierreDeSacrifice();

            while ( Math.abs(m_joueur.getScore() - m_adversaire.getScore()) <
                    NB_DE_POINTS_POUR_GAGNER_PARTIE )
            {
                if ( tour == 2 ) //execution de croissance
                    executerPouvoirCroissance();

                //tour de l'adversaire
                m_adversaire.jouerProchain(m_plateau);

                while ( !m_finTour )
                {
                    //m_adversaire.afficherProchain();
                    //m_plateau.afficherPlateau();
                    m_plateau.afficherPlateauEntier(m_adversaire.getProchaineAction());
                    m_joueur.afficherMain();
                    afficherStats();
                    System.out.println("------------------------");
                    System.out.println("Actions possibles : ");
                    System.out.println("[fin] Terminer votre tour");
                    System.out.println("[piocher] Piocher une carte");
                    System.out.println("[placer] <numero carte> <position> Placer " +
                            "une carte sur la plateau");

                    Scanner sc = new Scanner(System.in);
                    Input input = new Input(sc.nextLine());
                    while (!input.tryExecuteInput(m_joueur, m_plateau, this)) {
                        System.out.println("Veuillez entrer des informations valides !");
                        input.changerInput(sc.nextLine());
                    }
                }
                System.out.println("\t\t\t\t-- ACTIONS REALISEES --");
                System.out.println("#-----------------------------");
                System.out.println("Attaques du Joueur");
                System.out.println("#-----------------------------");

                executerTourJoueur();
                executerPouvoirCoureur();
                mettreAjourPlateau();

                System.out.println("\n#-----------------------------");
                System.out.println("Attaques de l'Adversaire");
                System.out.println("#-----------------------------");

                executerTourAdversaire();
                executerPouvoirCoureur();
                mettreAjourPlateau();

                System.out.println("\n#-----------------------------");
                System.out.println("Fin des Attaques");
                System.out.println("#-----------------------------");
                tour++;
                m_finTour = false;

                if ( m_joueur.getScore() - m_adversaire.getScore() >=
                    NB_DE_POINTS_POUR_GAGNER_PARTIE )
                {
                    System.out.println("Partie remportée ");
                    partiesGagnees++;
                }
                else if ( m_adversaire.getScore() - m_joueur.getScore() >=
                        NB_DE_POINTS_POUR_GAGNER_PARTIE )
                {
                    System.out.println("L'adversaire remporte la partie ");
                }

            }

            tour = 1;
        }
        if ( partiesGagnees >= NB_DE_PARTIES_POUR_GAGNER )
            System.out.println("Vous avez gagné !");
        else
            System.out.println("La victoire n'est pas pour tout le monde !");
    }

    public void afficherStats()
    {
        System.out.println("Score de l'adversaire : "+m_adversaire.getScore());
        System.out.println("Votre score : "+ m_joueur.getScore());
        System.out.println("Cartes sacrifiées : "+ m_joueur.getNbGouttesDeSang());
        System.out.println("Cartes mortes : "+ m_joueur.getNbOsTotal());
    }

    public void executerTourJoueur()
    {
        Position[] ligneJoueur = {Position.B1, Position.B2, Position.B3, Position.B4};
        Position[] ligneAdversaire = {Position.A1, Position.A2, Position.A3, Position.A4};

        int pointsJoueur = 0;

        for ( int i = 0; i < ligneJoueur.length; i++ )
        {
            Optional<Carte> carteJoueur = m_plateau.getPlateau().get(ligneJoueur[i]);
            Optional<Carte> carteEnnemi = m_plateau.getPlateau().get(ligneAdversaire[i]);

            if ( carteJoueur.isPresent() )
                pointsJoueur += carteJoueur.get().attaquer(carteEnnemi);
        }
        m_joueur.modifierScore(pointsJoueur);
    }

    public void executerTourAdversaire()
    {
        Position[] ligneJoueur = {Position.B1, Position.B2, Position.B3, Position.B4};
        Position[] ligneAdversaire = {Position.A1, Position.A2, Position.A3, Position.A4};

        int pointsAdversaire = 0;

        for ( int i = 0; i < ligneJoueur.length; i++ )
        {
            Optional<Carte> carteJoueur = m_plateau.getPlateau().get(ligneJoueur[i]);
            Optional<Carte> carteEnnemi = m_plateau.getPlateau().get(ligneAdversaire[i]);

            if ( carteEnnemi.isPresent() )
                pointsAdversaire += carteEnnemi.get().attaquer(carteJoueur);
        }
        m_adversaire.modifierScore(pointsAdversaire);
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

    public void preparerJeu() throws Exception {
        m_adversaire.resetScore();
        m_joueur.resetScore();
        m_adversaire.resetMain();
        m_joueur.resetStats();
        m_adversaire.resetStats();
        m_plateau = new Plateau();
        m_adversaire.reinitialiserProchain();
    }

    public void executerPouvoirCroissance()
    {
        for ( Position pos : m_plateau.getPlateau().keySet() )
        {
            // si la carte a le pouvoir
            if ( m_plateau.getPlateau().get(pos).isPresent() )
                if ( m_plateau.getPlateau().get(pos).get().
                        detientPouvoir(TypePouvoir.CROISSANCE) )
                {
                    m_plateau.changerCarte(pos,
                            CarteFactory.creerCarteAnimal(TypeAnimal.LOUP));
                    System.out.println("Louveteau s'est changé en loup !");
                }
        }
    }

    public void executerPouvoirCoureur()
    {
        for ( Position pos : m_plateau.getPlateau().keySet() )
        {
            if ( m_plateau.getPlateau().get(pos).isPresent() )
            {
                if ( m_plateau.getPlateau().get(pos).get().
                        detientPouvoir(TypePouvoir.COUREUR) )
                {
                    String nom =m_plateau.getPlateau().
                            get(pos).get().getNom();
                    if (m_plateau.deplacementDroitePossible(pos) )
                    {
                        m_plateau.deplacerCarte(pos,
                                m_plateau.posADroite(pos));
                        System.out.println(nom+ " se déplace à droite grâce" +
                                " à son pouvoir COUREUR !");
                    }
                    else if ( m_plateau.deplacementGauchePossible(pos) )
                    {
                        m_plateau.deplacerCarte(pos,
                                m_plateau.posAGauche(pos));
                        System.out.println(nom + " se déplace à gauche grâce" +
                                " à son pouvoir COUREUR !");
                    }

                    //sinon ne pas bouger
                    System.out.println("Pouvoir COUREUR : rien ne s'est produit !");
                }
            }
        }
    }

    public Joueur getJoueur(){ return m_joueur; }
    public Adversaire getAdversaire() { return m_adversaire; }

    public void executerPierreDeSacrifice()
    {
        System.out.println("=============Pierre de Sacrifice=============");
        System.out.println("Il est l'heure de sacrifier une carte !");

        List<CarteAnimal> choixPossibles = new ArrayList<>();
        List<Position> positionsPossibles = new ArrayList<>();

        for ( Position pos : m_plateau.getPlateau().keySet() )
        {
            if ( pos.name().startsWith("B") && m_plateau.getPlateau().get(pos).isPresent() )
            {
                Carte c = m_plateau.getPlateau().get(pos).get();
                if ( c.estAnimal() ) {
                    choixPossibles.add((CarteAnimal) c);
                    positionsPossibles.add(pos);
                }
            }
        }

        int choix = 0;
        Scanner sc = new Scanner(System.in);

        while ( choix <= 0 || choix > choixPossibles.size() )
        {
            for ( int i = 0; i < choixPossibles.size() ; i++ )
            {
                System.out.println("["+(i+1)+"] "+choixPossibles.get(i).getNom()+" Pouvoir associé : "+
                        choixPossibles.get(i).getPouvoirAssocie());
            }
            choix = sc.nextInt();
        }

        int indexSacre = choix-1;
        CarteAnimal carteSacrifiee = choixPossibles.get(indexSacre);
        TypePouvoir pouvoirFutur = carteSacrifiee.getPouvoirAssocie();

        System.out.println(carteSacrifiee.getNom()+" a été sacrifié !");

        carteSacrifiee.tuer();
        m_plateau.retirerCarteA(positionsPossibles.get(indexSacre));

        System.out.println("Quelle carte détiendra le pouvoir "+pouvoirFutur.name()+ " ?");

        int choixCarteMain = 0;

        while ( choixCarteMain <= 0 || choixCarteMain > m_joueur.getMain().size() )
        {
            for ( int i = 0; i < m_joueur.getMain().size(); i ++ )
            {
                System.out.println("["+(i+1)+"] "+m_joueur.getMain().get(i).getNom());
            }
            choixCarteMain = sc.nextInt();
        }

        int indexMain = choixCarteMain-1;

        m_joueur.getCarteMain(indexMain).activerPouvoir(pouvoirFutur);
        System.out.println(m_joueur.getCarteMain(indexMain).getNom() + " a obtenu le pouvoir "+
                pouvoirFutur.name()+ " !");

        System.out.println("=============FIN- Pierre de Sacrifice=============");
    }

}
