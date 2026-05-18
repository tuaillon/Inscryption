package inscryption.engine;

public final class Game
{
    private final int NB_DE_PARTIES_POUR_GAGNER = 3;
    private final int NB_DE_POINTS_POUR_GAGNER_PARTIE = 5;
    
    public Game() {};

    public void lancerJeu()
    {
        Joueur joueur = new Joueur();
        Plateau plateau = new Plateau();

        plateau.afficherPlateau();

        joueur.afficherMain();

        System.out.println("Actions possibles : ");
        System.out.println("[fin] Terminer votre tour");
        System.out.println("[piocher] Piocher une carte");
        System.out.println("[placer] <numero carte> <position> Placer " +
                "une carte sur la plateau");

    };

}
