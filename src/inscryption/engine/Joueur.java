package inscryption.engine;

import inscryption.carte.Carte;
import inscryption.carte.CarteAnimal;
import inscryption.carte.CarteFactory;

import java.util.ArrayList;
import java.util.List;

public class Joueur
{
    private final int NB_MIN_MAIN = 4;

    private List<CarteAnimal> m_main = new ArrayList<CarteAnimal>();
    private Pioche m_pioche = new Pioche();
    private int m_score;
    private int m_nb_espace_nom_carte;

    private int m_nbOsTotal;
    private int m_nbGouttesDeSangTotal;

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

    public void afficherMain()
    {
        System.out.println("Votre main : ");
        for ( int i = 0; i < m_main.size(); i++ )
        {
            System.out.println(i+1+ ". "+m_main.get(i).getNom() + nbEspacesMots(m_main.get(i)) + "  PV: "+
                    m_main.get(i).getPv()+ "    Att: "+m_main.get(i).getAtt()+
                    "   Gouttes de sang: "+m_main.get(i).getGouttesDeSang()+
                    "   Os: "+m_main.get(i).getOs());
        }
        m_pioche.afficherPioche();
    }

    public String nbEspacesMots(CarteAnimal carte)
    {
        int nbEspace = carte.getNbMaxCaractere() - carte.getNbLettres();
        String res = "";
        for (int i = 0; i < nbEspace; i++)
        {
            res += " ";
        }

        return res;
    }

    public void piocher()
    {
        m_main.add(m_pioche.piocher());
    }

    public void placerCarte(CarteAnimal c, Plateau p, Position pos) throws Exception
    {
        // Si la carte peut être placée et qu'aucun sacrifice n'est nécessaire, on la rajoute sans problème
        if ( peutPlacerCarte(c, p, pos) && c.getGouttesDeSang() == 0 && c.getOs() == 0)
        {
            p.positionnerCarte(c,pos);
            p.afficherPlateau();
        }
        // Sinon si la carte nécessite un sacrfice
        else if (c.getGouttesDeSang() >= 0)
        {
            // Si le joueur a assez de gouttes de sang, il place la carte sans problème
            if (m_nbGouttesDeSangTotal >= c.getGouttesDeSang())
            {
                p.positionnerCarte(c,pos);
                p.afficherPlateau();
            }
            else
            {
                System.out.println("Quelle(s) carte(s) voulez-vous sacrifier ?\n");
                p.afficherPlateau();
            }
        }
        else if (c.getOs() >= 0)
        {
            // Si le joueur a assez d'os, il place la carte sans problème
            if (m_nbOsTotal >= c.getOs())
            {
                p.positionnerCarte(c,pos);
                p.afficherPlateau();
            }
            // Sinon, on dit au joueur qu'il ne peut pas placer la carte
            else
            {
                System.out.println("Vous avez besoin de " + (c.getOs() - m_nbOsTotal) + " os pour placer cette carte...\n");
                p.afficherPlateau();
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

    private void Sacrifier(Plateau p, Position pos) throws Exception {
        if ( !p.getPlateau().containsKey(pos) )
        {
            throw new Exception("Il n'y a rien à sacrifier !");
        }

        m_nbOsTotal++;
        m_nbGouttesDeSangTotal++;



    }

}
