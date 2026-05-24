package inscryption.carte;

import java.util.Optional;

public class CarteObstacle extends Carte
{
    public CarteObstacle(String nom, int pv) {
        super(nom, pv);
    }

    //ne renvoie rien un obstacle nattaque pas
    @Override
    public int attaquer(Optional<Carte> carteAdverse) {
        return 0;
    }

    @Override
    public String getInfos() {
        return "["+m_nom+"] PV:"+m_pv;
    }

    @Override
    public String getToutesInfosCarte(){
        return m_nom + nbEspacesMots(this) + "  PV : " + m_pv;
    }
}
