package br.com.unisul.analisador.util;

import br.com.unisul.analisador.annotations.FuncaoSemantica;
import br.com.unisul.analisador.dto.Token;
import br.com.unisul.analisador.exception.SemanticoException;
import br.com.unisul.analisador.motor.AnalisadorSemantico;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Optional;

public class SemanticoUtils {


    public static Object executeAnalisadorSemantico(int idMethod, Token tokenAnterior) throws SemanticoException {
        Object retorno = Arrays.stream(AnalisadorSemantico.class.getDeclaredMethods())
                .filter(method -> Optional.ofNullable(method.getDeclaredAnnotation(FuncaoSemantica.class)).map(annotation -> annotation.value() == idMethod).orElse(false))
                .findFirst()
                .map(method -> invokeMethodSemantico(method, tokenAnterior))
                .orElseThrow(() -> new RuntimeException());

        if (retorno instanceof Exception) {
            Exception ex = (Exception) retorno;
            throw new SemanticoException(ex);
        }
        return retorno;
    }

    private static Object invokeMethodSemantico(Method method, Token tokenAnterior) {
        try {
            FuncaoSemantica anotacao = method.getDeclaredAnnotation(FuncaoSemantica.class);
            System.out.println("Executando ação semantica....: #" + anotacao.value());

            AnalisadorSemantico.setToken(tokenAnterior);
            return method.invoke(null);
        } catch (Exception e) {
            return e;
        }
    }
}
