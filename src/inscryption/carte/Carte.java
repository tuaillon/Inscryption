package inscryption.carte;

public abstract class Carte
{
    protected String m_nom;
    protected int m_pv;

    // 9 = nb max de caractères (louveteau) -> 10 caractère pour un espace au moins
    protected final int NB_MAX_CARACTERE = 9;

    public Carte(String nom, int pv)
    {
        m_nom = nom;
        m_pv = pv;
    }

    public String getNom()
    {
        return m_nom;
    }

    public int getPv()
    {
        return m_pv;
    }

    public abstract String getInfos();

    public int getNbLettres()
    {
        return m_nom.length();
    }

    public int getNbMaxCaractere()
    {
        return NB_MAX_CARACTERE;
    }
}
