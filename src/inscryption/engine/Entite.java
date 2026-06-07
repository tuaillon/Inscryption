package inscryption.engine;

//definit un personnage qui joue le jeu

import inscryption.carte.CarteAnimal;

import java.util.ArrayList;
import java.util.List;

public abstract class Entite
{
    protected final int NB_MIN_MAIN = 4;
    protected int m_score;
    protected int m_nbOsTotal;
    protected int m_nbGouttesDeSangTotal;
    protected Pioche m_pioche = new Pioche();
    protected List<CarteAnimal> m_main = new ArrayList<CarteAnimal>();

    public void resetMain() throws Exception {
        m_main.clear();
        m_pioche = new Pioche();
        for ( int i = 0; i < NB_MIN_MAIN; i++ )
            piocher();
    }

    public int getNbCarteMain(){ return m_main.size(); }
    public CarteAnimal getCarteMain(int index) { return m_main.get(index); }
    public void retirerCarteMain(CarteAnimal c) { m_main.remove(c); }
    public void ajouterCarteMain(CarteAnimal c) { m_main.add(c); }
    public void piocher() { m_main.add(m_pioche.piocher()); };
    public int getScore() { return m_score; }
    public void modifierScore(int valeur) { m_score += valeur; }
    public void resetScore() { m_score = 0; }
    public int getNbOsTotal() { return m_nbOsTotal; }
    public int getNbGouttesDeSang() { return m_nbGouttesDeSangTotal; }
    public List<CarteAnimal> getMain() { return m_main; }
    public void afficherMain()
    {
        System.out.println("\nVotre main : ");
        for ( int i = 0; i < m_main.size(); i++ )
            System.out.println(i+1+ ". "+m_main.get(i).getToutesInfosCarte());
        m_pioche.afficherPioche();
    }

    public Entite()
    {
        m_score = 0;
        m_nbGouttesDeSangTotal = 0;
        m_nbOsTotal = 0;
    }

    public void resetStats()
    {
        m_nbOsTotal = 0;
        m_nbGouttesDeSangTotal = 0;
    }

}
