package br.com.unisul.analisador.util;

import br.com.unisul.analisador.annotations.FuncaoSemantica;
import br.com.unisul.analisador.dto.Token;
import br.com.unisul.analisador.exception.SemanticoException;
import br.com.unisul.analisador.motor.AnalisadorSemantico;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Optional;

public class SemanticoUtils {

    public static void executeAnalisadorSemantico(int idMethod, Token tokenAnterior) throws SemanticoException {
        try {
            Object retorno = Arrays.stream(AnalisadorSemantico.class.getDeclaredMethods())
                    .filter(method -> Optional.ofNullable(method.getDeclaredAnnotation(FuncaoSemantica.class)).map(annotation -> annotation.value() == idMethod).orElse(false))
                    .findFirst()
                    .map(method -> invokeMethodSemantico(method, tokenAnterior))
                    .orElseThrow(() -> new SemanticoException("Erro inesperado"));

            if (retorno instanceof SemanticoException) {
                throw (SemanticoException) retorno;
            } else if (retorno instanceof Exception) {
                throw new SemanticoException((Exception) retorno);
            }
        } catch (SemanticoException se) {
            if (se.getCause() != null) {
                se.getCause().printStackTrace();
            } else {
                se.printStackTrace();
            }
            throw se;
        } catch (Exception e) {
            e.printStackTrace();
            throw new SemanticoException(e);
        }
    }

    private static Object invokeMethodSemantico(Method method, Token tokenAnterior) {
        try {
            FuncaoSemantica anotacao = method.getDeclaredAnnotation(FuncaoSemantica.class);
            System.out.println("Executando ação semantica....: #" + anotacao.value());

            AnalisadorSemantico.setToken(tokenAnterior);

            Object retorno = method.invoke(null);
            if (retorno == null) {
                return 0;
            } else {
                return retorno;
            }
        } catch (InvocationTargetException iv) {
            return iv.getCause();
        } catch (Exception e) {
            return e;
        }
    }
}
