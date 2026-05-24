package inscryption.engine;

//definit un personnage qui joue le jeu

public abstract class Entite
{
    protected int m_score;

    public int getScore() { return m_score; }
    public void modifierScore(int valeur) { m_score = valeur; }

}
