package inscryption.engine;

import inscryption.carte.Carte;
import inscryption.carte.CarteAnimal;
import inscryption.carte.CarteFactory;
import inscryption.carte.TypePouvoir;

import java.util.*;

public class Joueur extends Entite
{
    public Joueur()
    {
        super();
        for ( int i = 0 ; i < NB_MIN_MAIN; i++ )
        {
            piocher();
        }
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
            retirerCarteMain(c);
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
                retirerCarteMain(c);
            }
            else {
                sacrifier(c, p, pos);
            }
        }
        else if (c.getOs() >= 0)
        {
            // Si le joueur a assez d'os, il place la carte sans problème
            if (m_nbOsTotal >= c.getOs())
            {
                p.positionnerCarte(c,pos);
                afficherTour(p);
                retirerCarteMain(c);
            }
            // Sinon, on dit au joueur qu'il ne peut pas placer la carte
            else
            {
                System.out.println("Vous avez besoin de " + (c.getOs() - m_nbOsTotal) + " os pour placer cette carte...\n");
                afficherTour(p);
            }
        }
    }

    public void sacrifier(CarteAnimal c, Plateau p, Position pos)
    {
        // Condition pour continuer d'afficher ce pop up
        boolean sacrifier = true;
        int nbCartes = 0;

        List<Carte> listeCartesSacrifiables = new ArrayList<>();
        List<Position> listesPositionCartesSacrifiables = new ArrayList<>();

        // Parcours des positions du tableau
        for (Map.Entry<Position, Optional<Carte>> entry : p.getPlateau().entrySet()) {
            // on ne veut parcourir que les cases sur lesquelles le joueur peut poser des cartes
            if (entry.getKey().name().startsWith("B")) {
                // Si elles existent, on les affiche

                if (entry.getValue().isPresent() && entry.getValue().get().estAnimal()) {
                    // Ajouter la carte à la liste
                    listeCartesSacrifiables.add(entry.getValue().get());
                    // Ajouter la position corrspondante
                    listesPositionCartesSacrifiables.add(entry.getKey());
                    nbCartes++;
                }
            }
        }
        do {
            String texteInfosCartes = "\n";
            // Si le joueur n'a aucune carte pouvant être sacrifiée placée sur le plateau, on lui indique et on revient au tour
            if (nbCartes == 0) {
                sacrifier = false;
                System.out.println("\nVous n'avez aucune carte à sacrifier !\n");
            }
            // Si le joueur n'a pas assez de cartes à sacrifier, on lui indique et on revient au tour
            else if (nbCartes + m_nbGouttesDeSangTotal < c.getGouttesDeSang()){
                sacrifier = false;
                System.out.println("\nIl vous manque " + (c.getGouttesDeSang()-nbCartes) + " goutte(s) à sacrifier pour placer cette carte !\n");
            }
            // Sinon on lui propose les cartes qu'il souhaite sacrifier
            else {
                System.out.println("\nQuelle(s) carte(s) voulez-vous sacrifier ?\n");
                // Affichage final des cartes que l'on peut sacrifier
                int j = 1;
                for(Carte carte : listeCartesSacrifiables){
                    texteInfosCartes += "[" + j + "] " + carte.getToutesInfosCarte() + "\n";
                    j++;
                }
                System.out.println("Indiquez votre choix : " + texteInfosCartes);

                // Scanner
                Scanner sc = new Scanner(System.in);
                String choix = sc.nextLine();


                // on parcourt les cartes sacrifiables pour les afficher et que le joueur choisisse quelle(s) carte(s) il souhaite sacrifier
                for(int i = 0; i < listeCartesSacrifiables.size(); i++)
                {
                    if (Integer.parseInt(choix) <= listeCartesSacrifiables.size())
                    {
                        // Augmente le nombre de gouttes de sang
                        m_nbGouttesDeSangTotal++;
                        m_nbOsTotal++;

                        // Enlève la carte dans la liste à sacrifier
                        int emplacement = Integer.parseInt(choix) - 1;

                        //on l'enleve si il a pas le pouvoir
                        if ( !listeCartesSacrifiables.get(emplacement).
                                detientPouvoir(TypePouvoir.NOMBREUSES_VIES) )
                            p.retirerCarteA(listesPositionCartesSacrifiables.get(emplacement));
                        //System.out.println(listesPositionCartesSacrifiables.get(emplacement));

                        listeCartesSacrifiables.remove(listeCartesSacrifiables.get(emplacement));
                        listesPositionCartesSacrifiables.remove(listesPositionCartesSacrifiables.get(emplacement));
                    }
                }
                // On vérifie si les conditions sont remplies pour le sacrifice
                // Si le nombre de gouttes de sang est suffisant

                if (c.getGouttesDeSang() <= m_nbGouttesDeSangTotal || listeCartesSacrifiables.isEmpty())
                {
                    p.changerCarte(pos, c);
                    retirerCarteMain(c);
                    sacrifier = false;

                }

            }
        }
        while(sacrifier);
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

    public void afficherTour(Plateau p)
    {
        afficherMain();
    }

    public void mettreAJourOs(int valeur) { m_nbOsTotal++; }

}
