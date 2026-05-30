package inscryption.engine;

import inscryption.carte.*;
import jdk.jshell.JShell;

import java.util.*;

import static java.util.Map.entry;

public class Plateau
{
    public static final int NB_CARTES_PAR_LIGNE = 4;
    private final int NB_MAX_OBSTACLE = 2;

    private final int NB_CASES_PLATEAU = 8;
    private final int NB_CASES_DE_JEU = 4;

    public int getNB_CASES_DE_JEU() { return NB_CASES_DE_JEU; }

    private Map<Position, Optional<Carte>> m_plateau = new HashMap<>(Map.ofEntries(
        entry(Position.A1, Optional.empty()),
        entry(Position.A2, Optional.empty()),
        entry(Position.A3, Optional.empty()),
        entry(Position.A4, Optional.empty()),
        entry(Position.B1, Optional.empty()),
        entry(Position.B2, Optional.empty()),
        entry(Position.B3, Optional.empty()),
        entry(Position.B4, Optional.empty())));

    public Map<Position, Optional<Carte>> getPlateau()
    {
        return m_plateau;
    }

    public Plateau()
    {
        int nbObstacles = new Random().nextInt(0, NB_MAX_OBSTACLE + 1);
        List<Position> taken = new ArrayList<Position>();

        for ( int i = 0; i < nbObstacles; i++ )
        {
            CarteObstacle carte = CarteFactory.creerCarteObstacleRandom();

            Position[] positions = Position.values();

            int index = new Random().nextInt(positions.length);
            Position pos = positions[index];
            while ( taken.contains(pos) )
            {
                index = new Random().nextInt(positions.length);
                pos = positions[index];
            }
            taken.add(pos);
            positionnerCarte(carte,pos);

        }
    }

    public void reinitialiser()
    {
        for ( Position p : m_plateau.keySet() )
            m_plateau.put(p, Optional.empty());
    }

    public void changerCarte(Position pos,Carte carte)
    { m_plateau.replace(pos, Optional.of(carte)); }

    public void positionnerCarte(Carte carte, Position pos)
    {
        m_plateau.put(pos, Optional.of(carte));
    }

    public void retirerCarteA(Position p)
    {
        m_plateau.put(p,Optional.empty());
    }

    public void deplacerCarte(Position source, Position dest)
    {
        if ( m_plateau.get(source).isPresent() )
        {
            positionnerCarte(m_plateau.get(source).get(), dest);
            retirerCarteA(source);
        }
    }

    public void afficherPlateau()
    {
        Position[] ligneAdversaire = {Position.A1, Position.A2, Position.A3,
                Position.A4};

        Position[] ligneJoueur = {Position.B1, Position.B2, Position.B3,
                Position.B4};

        System.out.println("#=========================================");
        System.out.println("\n\nAdversaire : ");

        for ( int i = 0; i < NB_CARTES_PAR_LIGNE; i++ )
        {
            System.out.println("["+ligneAdversaire[i]+"] "+
                    (m_plateau.get(ligneAdversaire[i]).isPresent() ?
                            m_plateau.get(ligneAdversaire[i]).get().getToutesInfosCarte() :
                            "Aucun"));
        }

        System.out.println();
        System.out.println("#=========================================");
        System.out.println("Joueur     : ");

        for ( int i = 0; i < NB_CARTES_PAR_LIGNE; i++ )
        {
            System.out.println("["+ligneJoueur[i]+"] "+
                    (m_plateau.get(ligneJoueur[i]).isPresent() ?
                            m_plateau.get(ligneJoueur[i]).get().getToutesInfosCarte() :
                            "Aucun"));
        }

        System.out.println();
        System.out.println("#=========================================");
    }

    public void afficherPlateauEntier(Optional<CarteAnimal>[] prochaineAdversaire)
    {
        Position[] posA = {Position.A1, Position.A2, Position.A3, Position.A4};
        Position[] posB = {Position.B1, Position.B2, Position.B3, Position.B4};

        Optional<Carte>[] ligneA = new Optional[4];
        Optional<Carte>[] ligneB = new Optional[4];

        for ( int i = 0; i < NB_CARTES_PAR_LIGNE; i++ ) {
            ligneA[i] = m_plateau.get(posA[i]);
            ligneB[i] = m_plateau.get(posB[i]);
        }
        System.out.println();

        //afficher les coups prochains
        afficherLigneCarte(prochaineAdversaire,false);
        System.out.println("               ||              ||              ||              ||");
        System.out.println("               \\/              \\/              \\/              \\/");

        //afficher les deux lignes du plateau
        afficherLigneCarte(ligneA,true);
        System.out.println();
        afficherLigneCarte(ligneB,true);

        System.out.println();


    }

    public void afficherLigneCarte(Optional<? extends Carte>[] ligne, boolean afficherPosition)
    {
        int longeurTotale = 61;
        int maxCharLigne = 11;

        System.out.println("*-----------*   *-----------*   *-----------*   *-----------*");

        String[] ligneCourante = new String[61];
        int pos = 0;
        //nom
        for ( int i = 0 ; i < NB_CARTES_PAR_LIGNE; i++ )
        {
            if ( ligne[i].isPresent() ) {
                String nom = ligne[i].get().getNom();
                ligneCourante[pos] = "|" + nom.substring(0, Math.min(nom.length(), maxCharLigne)) + " | ";
            }
            else
                ligneCourante[pos] = "|           | ";
            pos += 14;
        }
        pos = 0;
        afficherSurUneLigne(ligneCourante);

        for ( int i = 0 ; i < NB_CARTES_PAR_LIGNE; i++ )
        {
            if ( ligne[i].isPresent() )
                ligneCourante[pos] = "|-----------| ";
            else
                ligneCourante[pos] = "|           | ";
            pos += 14;
        }
        pos = 0;
        afficherSurUneLigne(ligneCourante);

        for ( int i = 0 ; i < NB_CARTES_PAR_LIGNE; i++ )
        {
            if ( ligne[i].isPresent() )
                ligneCourante[pos] = "|PV: "+ligne[i].get().getPv()+"| ";
            else
                ligneCourante[pos] = "|           | ";
            pos += 14;
        }
        pos = 0;
        afficherSurUneLigne(ligneCourante);

        for ( int i = 0 ; i < NB_CARTES_PAR_LIGNE; i++ )
        {
            if ( ligne[i].isPresent() && ligne[i].get().estAnimal() )
                ligneCourante[pos] = "|Att: "+((CarteAnimal)ligne[i].get()).getAttk()+"| ";
            else
                ligneCourante[pos] = "|           | ";
            pos += 14;
        }

        pos = 0;
        afficherSurUneLigne(ligneCourante);

        for ( int i = 0 ; i < NB_CARTES_PAR_LIGNE; i++ )
        {
            if ( ligne[i].isPresent() && ligne[i].get().estAnimal() )
                ligneCourante[pos] = "|"+(((CarteAnimal)ligne[i].get()).estVolant() ?
                        "Volant" : "Non Volant") +"| ";
            else
                ligneCourante[pos] = "|           | ";
            pos += 14;
        }

        pos = 0;
        afficherSurUneLigne(ligneCourante);

        for ( int i = 0 ; i < NB_CARTES_PAR_LIGNE; i++ )
        {
            if ( ligne[i].isPresent() && ligne[i].get().estAnimal() )
                ligneCourante[pos] = "|Pouvoir    | ";
            else
                ligneCourante[pos] = "|           | ";
            pos += 14;
        }

        System.out.println("*-----------*   *-----------*   *-----------*   *-----------*");
    }

    private void afficherSurUneLigne(String[] ligne)
    {
        for ( int i = 0; i < ligne.length; i++ )
        {
            System.out.print(ligne[i]);
        }
        System.out.println();
    }

    public boolean placementPossible(Position pos)
    {
        if ( m_plateau.get(pos).isPresent() )
            return false;
        return true;
    }


    public boolean estEnnemi(Position pos)
    {
        if (pos.name().startsWith("A"))
            return true;
        return false;
    }

    public boolean deplacementDroitePossible(Position pos)
    {
        if ( pos == Position.A4 || pos == Position.B4 ) //pas la peine d'y penser
            return false;

        return m_plateau.get(posADroite(pos)).isEmpty();
    }

    public boolean deplacementGauchePossible(Position pos)
    {
        if ( pos == Position.A1 || pos == Position.B1 ) //pas la peine d'y penser
            return false;

        return m_plateau.get(posAGauche(pos)).isEmpty();
    }

    public Position posADroite(Position pos)
    {
        String p = pos.name(); //conversion str espiègle
        int num = Character.getNumericValue(p.charAt(1)) + 1;
        return Position.valueOf(p.charAt(0) + String.valueOf(num));
    }

    public Position posAGauche(Position pos)
    {
        String p = pos.name();
        int num = Character.getNumericValue(p.charAt(1)) - 1;
        return Position.valueOf(p.charAt(0) + String.valueOf(num));
    }

}
