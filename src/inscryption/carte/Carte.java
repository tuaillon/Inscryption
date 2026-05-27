package inscryption.carte;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public abstract class Carte
{
    protected String m_nom;
    protected int m_pv;
    protected List<TypePouvoir> m_pouvoirs;

    // 9 = nb max de caractères (louveteau) -> 10 caractère pour un espace au moins
    protected final int NB_MAX_CARACTERE = 9;

    public Carte(String nom, int pv)
    {
        m_nom = nom;
        m_pv = pv;
        m_pouvoirs = new ArrayList<TypePouvoir>();
    }

    public boolean detientPouvoir(TypePouvoir pv)
    {
        return m_pouvoirs.contains(pv);
    }

    public abstract int attaquer(Optional<Carte> carteAdverse);

    public boolean estMort() { return m_pv <= 0; }

    public String getNom()
    {
        return m_nom;
    }

    public int getPv()
    {
        return m_pv;
    }

    protected void impacterPv(int valeur) { m_pv -= valeur; }

    public abstract String getInfos();

    public abstract String getToutesInfosCarte();

    public String nbEspacesMots(Carte carte)
    {
        int nbEspace = carte.getNbMaxCaractere() - carte.getNbLettres();
        String res = "";
        for (int i = 0; i < nbEspace; i++)
        {
            res += " ";
        }

        return res;
    }

    public int getNbLettres()
    {
        return m_nom.length();
    }

    public int getNbMaxCaractere()
    {
        return NB_MAX_CARACTERE;
    }
}
