package inscryption.engine;

import inscryption.carte.Carte;
import inscryption.carte.CarteAnimal;
import inscryption.carte.CarteFactory;

import java.util.*;

public class Joueur extends Entite
{
    private final int NB_MIN_MAIN = 4;

    private List<CarteAnimal> m_main = new ArrayList<CarteAnimal>();
    private Pioche m_pioche = new Pioche();

    public static final List<String> ACTIONS_POSSIBLES = List.of("placer",
            "fin","piocher");

    public Joueur()
    {
        m_score = 0;
        for ( int i = 0 ; i < NB_MIN_MAIN; i++ )
        {
            piocher();
        }

        m_nbGouttesDeSangTotal = 0;
        m_nbOsTotal = 0;
    }

    public CarteAnimal getCarteMain(int index) { return m_main.get(index); }

    public void retirerCarteMain(int index) { m_main.remove(index); }

    public void afficherMain()
    {
        System.out.println("\nVotre main : ");
        for ( int i = 0; i < m_main.size(); i++ )
        {

            System.out.println(i+1+ ". "+m_main.get(i).getToutesInfosCarte());

        }
        m_pioche.afficherPioche();
    }

    public void piocher()
    {
        m_main.add(m_pioche.piocher());
    }

    public void placerCarte(CarteAnimal c, Plateau p, Position pos) throws Exception
    {
        if ( !peutPlacerCarte(c, p, pos) )
        {
            System.out.println("Emplacement invalide ! ");
            return; // eviter le placement dans le champ ennemi
        }
        // Si la carte peut être placée et qu'aucun sacrifice n'est nécessaire, on la rajoute sans problème
        if ( c.getGouttesDeSang() == 0 && c.getOs() == 0)
        {
            p.positionnerCarte(c,pos);
            afficherTour(p);
            return;
        }

        // Sinon si la carte nécessite un sacrfice
        if (c.getGouttesDeSang() > 0)
        {
            // Si le joueur a assez de gouttes de sang, il place la carte sans problème
            if (m_nbGouttesDeSangTotal >= c.getGouttesDeSang())
            {
                p.positionnerCarte(c,pos);
                afficherTour(p);
            }
            else
            {
                System.out.println("Quelle(s) carte(s) voulez-vous sacrifier ?\n");
                p.afficherPlateau();
                int nbCartes = 1;
                String texteInfosCartes = "\n";
                String texteActionsPossibles = "";
                // Parcours des positions du tableau
                for (Map.Entry<Position, Optional<Carte>> entry : p.getPlateau().entrySet())
                {
                    // on ne veut parcourir que les cases sur lesquelles le joueur peut poser des cartes
                    if (entry.getKey().name().startsWith("B"))
                    {
                        // Si elles existent, on les affiche
                        if (entry.getValue().isPresent())
                        {
                            // mise en forme de l'affichage
                            texteInfosCartes += nbCartes + ". " + entry.getValue().get().getToutesInfosCarte() + "\n";
                            texteActionsPossibles += "[" + nbCartes + "]\n";
                        }
                    }
                }
                // Affichage final des cartes que l'on peut sacrifier
                System.out.println(texteInfosCartes + "\n Indiquez votre choix : \n" + texteActionsPossibles);
                
            }
        }
        else if (c.getOs() >= 0)
        {
            // Si le joueur a assez d'os, il place la carte sans problème
            if (m_nbOsTotal >= c.getOs())
            {
                p.positionnerCarte(c,pos);
                afficherTour(p);
            }
            // Sinon, on dit au joueur qu'il ne peut pas placer la carte
            else
            {
                System.out.println("Vous avez besoin de " + (c.getOs() - m_nbOsTotal) + " os pour placer cette carte...\n");
                afficherTour(p);
            }
        }
    }

    private boolean peutPlacerCarte(CarteAnimal c, Plateau p, Position pos) throws Exception
    {
        boolean valide = false;
        if ( !p.getPlateau().containsKey(pos) )
            throw new Exception("La position n'existe pas !");

        if (p.estEnnemi(pos))
            return valide;

        // isPresent() = il y a un vrai élément dans le Optional
        if ( p.getPlateau().get(pos).isPresent() )
            return valide;

        valide = true;

        return valide;
    }

    public void Sacrifier(Plateau p, Position pos) throws Exception {
        if ( !p.getPlateau().containsKey(pos) )
        {
            throw new Exception("Il n'y a rien à sacrifier !");
        }

        m_nbOsTotal++;
        m_nbGouttesDeSangTotal++;

    }

    public void afficherTour(Plateau p)
    {
        p.afficherPlateau();
        afficherMain();
    }

    public void mettreAJourOs(int valeur) { m_nbOsTotal++; }

}
