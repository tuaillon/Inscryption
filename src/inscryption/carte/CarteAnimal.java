package inscryption.carte;

import java.util.ArrayList;
import java.util.Optional;

public class CarteAnimal extends Carte
{
    private int m_attk;
    private int m_gouttesDeSang;
    private int m_os;
    private boolean m_bVolant;

    public CarteAnimal(String nom, int pv, int attaque, int gouttes,
                       int os, boolean volant) {
        super(nom, pv);
        m_attk = attaque;
        m_gouttesDeSang = gouttes;
        m_os = os;
        m_bVolant = volant;
        m_pouvoir = TypePouvoir.AUCUN;
        m_pouvoirsActifs = new ArrayList<TypePouvoir>();

    }
    //autre constr avec pouvoir si la carte en a un
    public CarteAnimal(String nom, int pv, int attaque, int gouttes,
                       int os, boolean volant, TypePouvoir pouv) {
        super(nom, pv);
        m_attk = attaque;
        m_gouttesDeSang = gouttes;
        m_os = os;
        m_bVolant = volant;
        m_pouvoir = pouv;
        m_pouvoirsActifs = new ArrayList<TypePouvoir>();

    }

    public int getAttk() { return m_attk; }
    public boolean estVolant() { return m_bVolant; }

    /*prends direct un optional comme on peut appliquer la methode pour toutes les cartes
    //enface et pas besoin de faire des checks dans game pour voir si cest present ou pas
    renvoie le nombre de points a modifier dans le score
    */
    @Override
    public int attaquer(Optional<Carte> carteAdverse)
    {

        if ( carteAdverse.isPresent() )
        {
            if ( carteAdverse.get().detientPouvoir(TypePouvoir.PUANT) )
            {
                impacterAtt(-1);
                System.out.println(this.m_nom + " voit son attaque" +
                        "réduite de 1 à cause du pouvoir PUANT de "+
                        carteAdverse.get().getNom());
            }

            if ( this.m_bVolant ) // cas du volant
            {
                System.out.println(this.m_nom + " attaque directement le score"+
                        " de "+m_attk+ " !");

                return this.m_attk;
            }

            // Attaque l'ennemi
            // Si l'attaque inflige des dégâts supérieurs aux pvs de l'adversaire,
            if (this.m_attk > carteAdverse.get().getPv())
            {
                System.out.println(m_nom + " inflige "+ carteAdverse.get().getPv()+
                        " dégâts à "+carteAdverse.get().getNom());
                int dgtsScore = this.m_attk - carteAdverse.get().getPv();

                carteAdverse.get().impacterPv(carteAdverse.get().getPv());
                System.out.println(m_nom + " attaque le score de "+ dgtsScore + " !");
                return dgtsScore;
            }
            else
            {
                carteAdverse.get().impacterPv(this.m_attk);
                System.out.println(m_nom + " inflige "+ m_attk +
                        " dégâts à "+carteAdverse.get().getNom());
            }


            //contact mortel qui one shot
            if ( detientPouvoir(TypePouvoir.CONTACT_MORTEL) &&
            carteAdverse.get().estAnimal() )
            {
                carteAdverse.get().tuer();
                System.out.println(m_nom + " viens de tuer "+
                        carteAdverse.get().getNom()+" avec son pouvoir" +
                        "CONTACT MORTEL ! ");
            }

            // si la carte de devant se fait attaquer nous aussi
            if ( carteAdverse.get().detientPouvoir(TypePouvoir.PIQUES_POINTUES) )
            {
                impacterPv(1);
                System.out.println(this.m_nom + " subit voit sa santé" +
                        "réduite de 1 à cause du pouvoir PIQUES de " +
                        carteAdverse.get().getNom());
            }

            return 0; //pas de score

        }
        //cas où y a pas de carte en face
        System.out.println(m_nom + " attaque le score de "
                + m_attk + " !");
        return this.m_attk;
    }

    public int getOs()
    {
        return m_os;
    }

    public int getGouttesDeSang()
    {
        return m_gouttesDeSang;
    }

    public int getAtt()
    {
        return m_attk;
    }

    public void impacterAtt(int valeur) { m_attk += valeur; }

    @Override
    public String getInfos() {
        return "["+m_nom+"] PV: "+m_pv+ " Att: "+m_attk+
                (m_bVolant ? " Volant" : " Non Volant");
    }

    @Override
    public String getToutesInfosCarte(){
        return m_nom + nbEspacesMots(this) + "  PV: "+
                m_pv+ "    Att: "+m_attk+
                "   Gouttes de sang: "+ m_gouttesDeSang+
                "   Os: "+m_os +
                (m_bVolant ? "   Volant     " : "   Non Volant ") +
                afficherPouvoirs() ;
    }

    private String afficherPouvoirs()
    {
        if ( m_pouvoirsActifs.isEmpty() )
            return "    Aucun Pouvoir";
        String res = "    ";
        for ( TypePouvoir pv : m_pouvoirsActifs )
        {
            res += pv.name() + " ";
        }
        return res;
    }

    @Override
    public boolean estAnimal() { return true; }
}
