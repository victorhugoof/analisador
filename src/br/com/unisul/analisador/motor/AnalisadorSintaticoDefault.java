package br.com.unisul.analisador.motor;

import br.com.unisul.analisador.constants.Constants_old;
import br.com.unisul.analisador.dto.Token;
import br.com.unisul.analisador.exception.LexicoException;
import br.com.unisul.analisador.exception.SintaticoException;

import java.util.Stack;

public class AnalisadorSintaticoDefault implements Constants_old
{
    private Stack stack = new Stack();
    private Token currentToken;
    private Token previousToken;
    private AnalisadorLexico scanner;

    private static final boolean isTerminal(int x)
    {
        return x < FIRST_NON_TERMINAL;
    }

    private static final boolean isNonTerminal(int x)
    {
        return x >= FIRST_NON_TERMINAL && x < FIRST_SEMANTIC_ACTION;
    }

    private static final boolean isSemanticAction(int x)
    {
        return x >= FIRST_SEMANTIC_ACTION;
    }

    private boolean step() throws LexicoException, SintaticoException
    {
        if (currentToken == null)
        {
            int pos = 0;
            if (previousToken != null)
                pos = previousToken.getPosition()+previousToken.getToken().length();

            currentToken = new Token(DOLLAR, "$", pos, "FINAL");
        }

        int x = ((Integer)stack.pop()).intValue();
        int a = currentToken.getId();

        if (x == EPSILON)
        {
            return false;
        }
        else if (isTerminal(x))
        {
            if (x == a)
            {
                if (stack.empty())
                    return true;
                else
                {
                    previousToken = currentToken;
                    currentToken = scanner.proximoToken();
                    return false;
                }
            }
            else
            {
                throw new SintaticoException(PARSER_ERROR[x], currentToken.getPosition());
            }
        }
        else if (isNonTerminal(x))
        {
            if (pushProduction(x, a))
                return false;
            else
                throw new SintaticoException(PARSER_ERROR[x], currentToken.getPosition());
        }
        else // isSemanticAction(x)
        {
            return false;
        }
    }

    private boolean pushProduction(int topStack, int tokenInput)
    {
        int p = PARSER_TABLE[topStack-FIRST_NON_TERMINAL][tokenInput-1];
        if (p >= 0)
        {
            int[] production = PRODUCTIONS[p];
            //empilha a produ��o em ordem reversa
            for (int i=production.length-1; i>=0; i--)
            {
                stack.push(new Integer(production[i]));
            }
            return true;
        }
        else
            return false;
    }

    public void parse(AnalisadorLexico scanner) throws LexicoException, SintaticoException
    {
        this.scanner = scanner;

        stack.clear();
        stack.push(new Integer(DOLLAR));
        stack.push(new Integer(START_SYMBOL));

        currentToken = scanner.proximoToken();

        while ( ! step() )
            ;
    }
}
