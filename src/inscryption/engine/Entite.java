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

    public void piocher() { m_main.add(m_pioche.piocher()); };
    public int getScore() { return m_score; }
    public void modifierScore(int valeur) { m_score += valeur; }
    public void resetScore() { m_score = 0; }
    public int getNbOsTotal() { return m_nbOsTotal; }
    public int getNbGouttesDeSang() { return m_nbGouttesDeSangTotal; }
    public List<CarteAnimal> getMain() { return m_main; }

    public Entite()
    {
        m_score = 0;
        m_nbGouttesDeSangTotal = 0;
        m_nbOsTotal = 0;
    }

    public void afficherScore(){
        System.out.println("Score : " + this.m_score + "\n");
    }

    public void resetStats()
    {
        m_nbOsTotal = 0;
        m_nbGouttesDeSangTotal = 0;
    }

}
