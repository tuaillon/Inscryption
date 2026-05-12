package carte;

public abstract class CarteAnimal extends Carte
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
}
