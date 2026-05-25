package inscryption.engine;

//definit un personnage qui joue le jeu

public abstract class Entite
{
    protected int m_score;
    protected int m_nbOsTotal;
    protected int m_nbGouttesDeSangTotal;

    public int getScore() { return m_score; }
    public void modifierScore(int valeur) { m_score = valeur; }

}
