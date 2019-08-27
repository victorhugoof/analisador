package br.com.unisul.analisador.motor;

import br.com.unisul.analisador.constants.Constants;
import br.com.unisul.analisador.dto.Token;
import br.com.unisul.analisador.exception.SemanticoExcepion;

public class AnalisadorSemantico implements Constants
{
    public void executeAction(int action, Token token)	throws SemanticoExcepion
    {
        System.out.println("Ação #"+action+", Token: "+token);
    }	
}
