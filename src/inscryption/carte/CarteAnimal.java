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
        m_pouvoirs = new ArrayList<TypePouvoir>();

    }
    //autre constr avec pouvoir si la carte en a un
    public CarteAnimal(String nom, int pv, int attaque, int gouttes,
                       int os, boolean volant, TypePouvoir pouv) {
        super(nom, pv);
        m_attk = attaque;
        m_gouttesDeSang = gouttes;
        m_os = os;
        m_bVolant = volant;
        m_pouvoirs = new ArrayList<TypePouvoir>();
        m_pouvoirs.add(pouv);

    }

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
                impacterAtt(-1);

            if ( this.m_bVolant ) // cas du volant
                return this.m_attk;

            carteAdverse.get().impacterPv(this.m_attk);

            //contact mortel qui one shot
            if ( detientPouvoir(TypePouvoir.CONTACT_MORTEL) &&
            carteAdverse.get().estAnimal() )
                carteAdverse.get().tuer();

            // si la carte de devant se fait attaquer nous aussi
            if ( carteAdverse.get().detientPouvoir(TypePouvoir.PIQUES_POINTUES) )
                impacterPv(1);

            return 0; //pas de score

        }
        //cas où y a pas de carte en face
        return this.m_attk;
    }

    public void modifierPv(int valeur) { m_pv = valeur; }

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
        if ( m_pouvoirs.isEmpty() )
            return "    Aucun Pouvoir";
        String res = "    ";
        for ( TypePouvoir pv : m_pouvoirs )
        {
            res += pv.name() + " ";
        }
        return res;
    }

    @Override
    public boolean estAnimal() { return true; }
}
