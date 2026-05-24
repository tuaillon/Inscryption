package inscryption.carte;

import javax.swing.text.html.Option;
import java.util.Optional;

public class CarteAnimal extends Carte
{
    protected int m_attk;
    protected int m_gouttesDeSang;
    protected int m_os;
    protected boolean m_bVolant;

    public CarteAnimal(String nom, int pv, int attaque, int gouttes,
                       int os, boolean volant) {
        super(nom, pv);
        m_attk = attaque;
        m_gouttesDeSang = gouttes;
        m_os = os;
        m_bVolant = volant;

    }

    //prends direct un optional comme on peut appliquer la methode pour toutes les cartes
    //enface et pas besoin de faire des checks dans game pour voir si cest present ou pas
    //renvoie le nombre de points a modifier dans le score
    @Override
    public int attaquer(Optional<Carte> carteAdverse)
    {
        if ( carteAdverse.isPresent() )
        {
            if ( this.m_bVolant ) // cas du volant
                return this.m_attk;

            carteAdverse.get().impacterPv(this.m_attk);
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
                (m_bVolant ? "   Volant" : "   Non Volant");
    }
}
