package inscryption.engine;

import inscryption.carte.CarteAnimal;

import java.util.Arrays;
import java.util.List;

public class Input
{
    List<InputsPossibles> INPUTS_POSSIBLES = Arrays.asList(InputsPossibles.values());
    String m_input;

    public Input(String input)
    {
        m_input = input;
    }

    public void changerInput(String newInput)
    {
        m_input = newInput;
    }

    public boolean tryExecuteInput(Joueur j, Plateau p, Game g) throws Exception {
        //converti la cmd en liste de mots bien formatee
        String[] parts = m_input.trim().split("\\s+");
        if ( parts.length == 0 )
            return false;

        String cmd = parts[0].toUpperCase(); // la commande est la premiere partie de l'input
        boolean existeCommande = false;

        for ( InputsPossibles ip : INPUTS_POSSIBLES )
        {
            if ( cmd.equals(ip.name()) )
                existeCommande = true;
        }

        if (!existeCommande)
            return false;

        //convertir la cmd en enum
        switch ( InputsPossibles.valueOf(cmd) )
        {
            case FIN:
                g.autoriserPioche();
                g.terminerTour();
                return true; // on verra plus tard

            case PIOCHER:
                if ( g.piochePossible() )
                {
                    g.interdirPioche();
                    if ( parts.length != 1 )
                        return false;

                    j.piocher();
                    return true;
                }
                else {
                    System.out.println("Vous avez déja pioché !\nVeuillez entrer une commande valide !");
                    return false;
                }

            case PLACER:
                if (parts.length != 3)
                    return false;

                int numCarte;
                try
                {
                    numCarte = Integer.parseInt(parts[1]);
                }
                catch ( Exception e )
                {
                    return false;
                }

                Position pos;
                try
                {
                    pos = Position.valueOf(parts[2].toUpperCase());
                }
                catch ( Exception e )
                {
                    return false;
                }

                CarteAnimal carte = j.getCarteMain(numCarte - 1);
                j.placerCarte(carte, p, pos);
                return true;
        }

        return false;
    }
}
