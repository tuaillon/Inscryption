package inscryption.engine;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Input
{
    List<InputsPossibles> INPUTS_POSSIBLES = Arrays.asList(InputsPossibles.values());
    String m_input;

    Input(String input)
    {
        m_input = input;
    }

    public void changerInput(String newInput)
    {
        m_input = newInput;
    }

    public boolean tryExecuteInput()
    {   //a implementer
        return true;
    }
}
