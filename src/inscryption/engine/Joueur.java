package inscryption.engine;

import inscryption.carte.CarteAnimal;
import inscryption.carte.CarteFactory;

import java.util.ArrayList;
import java.util.List;

public class Joueur
{
    private final int NB_MIN_MAIN = 4;

    private List<CarteAnimal> m_main = new ArrayList<CarteAnimal>();
    private Pioche m_pioche = new Pioche();
    private int m_score;

    public Joueur()
    {
        m_score = 0;
        for ( int i = 0 ; i < NB_MIN_MAIN; i++ )
        {
            m_main.add(m_pioche.piocher());
        }
    }

    public void piocherCarte()
    {
        m_main.add(m_pioche.piocher());
    }

    public void afficherMain()
    {
        System.out.println("Votre main : ");
        for ( int i = 0; i < m_main.size(); i++ )
        {
            System.out.println(i+1+ ". "+m_main.get(i).getNom() + " PV: "+
                    m_main.get(i).getPv()+ " Att: "+m_main.get(i).getAtt()+
                    " Gouttes de sang: "+m_main.get(i).getGouttesDeSang()+
                    " Os: "+m_main.get(i).getOs());
        }
        m_pioche.afficherPioche();
    }


}
