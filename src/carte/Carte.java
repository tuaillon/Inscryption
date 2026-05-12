package carte;

public abstract class Carte
{
    protected String m_nom;
    protected int m_pv;

    public Carte(String nom, int pv)
    {
        m_nom = nom;
        m_pv = pv;
    }
}
