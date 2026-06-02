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

        //afficher les coups prochains sans la position
        afficherLigneCarte(prochaineAdversaire,false, false);
        System.out.println("      ||              ||              ||              ||");
        System.out.println("      \\/              \\/              \\/              \\/");

        //afficher les deux lignes du plateau
        //posAdverse permet de savoir si il faut mettre A ou B devant la position si affichée
        afficherLigneCarte(ligneA,true, true);
        System.out.println();
        afficherLigneCarte(ligneB,true, false);

        System.out.println();


    }

    public void afficherLigneCarte(Optional<? extends Carte>[] ligne,
                                   boolean afficherPosition, boolean posAdverse)
    {
        int maxCharLigne = 11;

        System.out.println("*-----------*   *-----------*   *-----------*   *-----------*");

        String[] ligneCourante = new String[NB_CARTES_PAR_LIGNE];

        for ( int i = 0; i < NB_CARTES_PAR_LIGNE; i++ )
        {
            if ( ligne[i].isPresent() )
            {
                String nom = ligne[i].get().getNom();
                ligneCourante[i] = "|" + paddingLigne(nom, maxCharLigne) + "|   ";
            }
            else
                ligneCourante[i] = "|" + paddingLigne("", maxCharLigne) + "|   ";
        }
        afficherSurUneLigne(ligneCourante);

        for ( int i = 0; i < NB_CARTES_PAR_LIGNE; i++ )
        {
            if ( ligne[i].isPresent() )
                ligneCourante[i] = "|-----------|   ";
            else
                ligneCourante[i] = "|" + paddingLigne("", maxCharLigne) + "|   ";
        }
        afficherSurUneLigne(ligneCourante);

        for ( int i = 0; i < NB_CARTES_PAR_LIGNE; i++ )
        {
            if ( ligne[i].isPresent() )
                ligneCourante[i] = "|" + paddingLigne("PV: " +
                        ligne[i].get().getPv(), maxCharLigne) + "|   ";
            else
                ligneCourante[i] = "|" + paddingLigne("", maxCharLigne) + "|   ";
        }
        afficherSurUneLigne(ligneCourante);

        for ( int i = 0; i < NB_CARTES_PAR_LIGNE; i++ )
        {
            if ( ligne[i].isPresent() && ligne[i].get().estAnimal() )
                ligneCourante[i] = "|" + paddingLigne("Att: " +
                        ((CarteAnimal) ligne[i].get()).getAttk(), maxCharLigne) + "|   ";
            else
                if ( afficherPosition && !ligne[i].isPresent() ) // on affiche pas si obstacle
                    ligneCourante[i] = "|" + paddingLigne("    "+(posAdverse ?
                                    "A" : "B")+(i+1),
                            maxCharLigne) + "|   ";
                else
                    ligneCourante[i] = "|" + paddingLigne("", maxCharLigne) + "|   ";
        }
        afficherSurUneLigne(ligneCourante);

        for ( int i = 0; i < NB_CARTES_PAR_LIGNE; i++ )
        {
            if ( ligne[i].isPresent() && ligne[i].get().estAnimal() )
            {
                String txt = ((CarteAnimal) ligne[i].get()).estVolant() ? "Volant" : "Non Volant";
                ligneCourante[i] = "|" + paddingLigne(txt, maxCharLigne) + "|   ";
            }
            else
                ligneCourante[i] = "|" + paddingLigne("", maxCharLigne) + "|   ";
        }
        afficherSurUneLigne(ligneCourante);

        for ( int i = 0; i < NB_CARTES_PAR_LIGNE; i++ )
        {
            if ( ligne[i].isPresent() && ligne[i].get().estAnimal() )
            {
                // Afficher les pouvoirs de la carte
                String pouvoirCarte = "";
                for (TypePouvoir pv : TypePouvoir.values())
                {
                    if (ligne[i].get().detientPouvoir(pv))
                    {
                        if (pv.name().length() > maxCharLigne)
                        {
                            if (pv.name().equals(TypePouvoir.CONTACT_MORTEL.name()))
                                pouvoirCarte += "CONT. MORT.";
                            else if (pv.name().equals(TypePouvoir.PIQUES_POINTUES.name()))
                                pouvoirCarte += "PICS POINT.";
                            else if (pv.name().equals(TypePouvoir.NOMBREUSES_VIES.name()))
                                pouvoirCarte += "NOMB. VIES";
                        }
                        else
                        {
                            pouvoirCarte += pv.name();
                        }

                    }
                }
                ligneCourante[i] = "|" + paddingLigne(pouvoirCarte, maxCharLigne) + "|   ";
            }
            else
                ligneCourante[i] = "|" + paddingLigne("", maxCharLigne) + "|   ";
        }
        afficherSurUneLigne(ligneCourante);

        System.out.println("*-----------*   *-----------*   *-----------*   *-----------*");
    }

    private void afficherSurUneLigne(String[] ligne)
    {
        for ( int i = 0; i < ligne.length; i++ )
            System.out.print(ligne[i]);
        System.out.println();
    }

    private String paddingLigne(String s, int n) {
        return String.format("%-" + n + "s", s);
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
