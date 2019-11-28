package br.com.unisul.analisador.motor;

import br.com.unisul.analisador.annotations.FuncaoSemantica;
import br.com.unisul.analisador.dto.Categoria;
import br.com.unisul.analisador.dto.Instrucao;
import br.com.unisul.analisador.dto.Simbolo;
import br.com.unisul.analisador.dto.TabelaSimbolos;
import br.com.unisul.analisador.dto.Token;
import br.com.unisul.analisador.exception.SemanticoException;
import br.com.unisul.analisador.util.SemanticoUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Stack;

public class AnalisadorSemantico {

    private static Hipotetica hipotetica;
    private static AreaInstrucoes areaInstrucoes;
    private static AreaLiterais areaLiterais;
    private static List<Instrucao> codigoIntermediario = new ArrayList<>();

    private static TabelaSimbolos tabelaSimbolos;
    private static Simbolo constant;
    private static Simbolo procedure;
    private static Simbolo simboloAux;

    private static Stack<Integer> pilhaIf;
    private static Stack<Integer> pilhaWhile;
    private static Stack<Integer> pilhaRepeat;
    private static Stack<Integer> pilhaCase;
    private static Stack<Integer> pilhaFor;
    private static Stack<Integer> pilhaParametros;
    private static Stack<Integer> pilhaProcedure;
    private static Stack<Simbolo> pilhaSimbolos;
    private static Stack<Simbolo> pilhaSimbolosAux;
    private static Instrucao desvioAux;
    private static Integer pilhaAuxiliar;

    private static Integer parametros;
    private static Integer deslocamentoAmem;
    private static Integer nivelAtual;
    private static Integer nivel;
    private static Integer variaveis;
    private static Integer nivelAuxiliar;
    private static Integer deslocamentoAuxiliar;
    private static Integer indiceTabelaSimbolos;
    private static Boolean procedurePossuiParametros;
    private static String categoria;
    private static String acao;

    private static String nomeCall;
    private static Token token;

    public static void setToken(Token token) {
        AnalisadorSemantico.token = token;
    }

    public static void setCall(String call) {
        System.out.println("Setando call: " + call);
        AnalisadorSemantico.nomeCall = call;
    }

    public static void executeAction(Integer idCode, Token token) throws SemanticoException {
        SemanticoUtils.executeAnalisadorSemantico(idCode, token);
        codigoIntermediario.stream().forEach(codigo -> System.out.println(codigo.toString()));
    }

    public static void interpretar() {
        hipotetica.Interpreta(areaInstrucoes, areaLiterais);
    }

    @FuncaoSemantica(100)
    public static void inicializacao() {

        pilhaIf = new Stack<>();
        pilhaWhile = new Stack<>();
        pilhaRepeat = new Stack<>();
        pilhaParametros = new Stack<>();
        pilhaProcedure = new Stack<>();
        pilhaCase = new Stack<>();
        pilhaFor = new Stack<>();
        pilhaSimbolos = new Stack<>();
        pilhaSimbolosAux = new Stack<>();
        tabelaSimbolos = new TabelaSimbolos(8);
        hipotetica = new Hipotetica();
        areaInstrucoes = new AreaInstrucoes();
        areaLiterais = new AreaLiterais();
        hipotetica.InicializaAI(areaInstrucoes);
        hipotetica.InicializaAL(areaLiterais);
        nivelAtual = 0;
        variaveis = 0;
        deslocamentoAmem = 3;
        areaInstrucoes.LC = 1;
        areaLiterais.LIT = 1;
        nivelAuxiliar = nivelAtual;
        deslocamentoAuxiliar = deslocamentoAmem;
    }

    @FuncaoSemantica(101)
    public static void para() {

        codigoIntermediario.add(new Instrucao(areaInstrucoes.LC, "PARA", "-", "-"));
        hipotetica.IncluirAI(areaInstrucoes, 26, 0, 0);
    }

    @FuncaoSemantica(102)
    public static void amem() {

        deslocamentoAmem = 3;
        codigoIntermediario.add(new Instrucao(areaInstrucoes.LC, "AMEM", "-", (deslocamentoAmem + variaveis) + ""));
        hipotetica.IncluirAI(areaInstrucoes, 24, 0, (deslocamentoAmem + variaveis));
    }

    @FuncaoSemantica(104)
    public static void f4() {

        if (Categoria.VARIAVEL.getNome().equals(categoria)) {
            simboloAux = tabelaSimbolos.buscar(token.getName(), nivelAtual);
            if (simboloExiste(simboloAux)) {
                throw new SemanticoException("Simbolo ja existe");
            } else {
                tabelaSimbolos.adiciona(new Simbolo(token.getName(), Categoria.VARIAVEL, nivelAtual, deslocamentoAmem, 0));
                pilhaSimbolos.add(new Simbolo(token.getName(), Categoria.VARIAVEL, nivelAtual, deslocamentoAmem, 0));
                pilhaSimbolosAux.add(new Simbolo(token.getName(), Categoria.VARIAVEL, nivelAtual, deslocamentoAmem, 0));
                deslocamentoAmem++;
                variaveis++;
            }

        } else if (Categoria.PARAMETRO.getNome().equals(categoria)) {
            simboloAux = tabelaSimbolos.buscar(token.getName(), nivelAtual);
            if (simboloExiste(simboloAux)) {
                throw new SemanticoException("Simbolo ja existe");
            } else {
                tabelaSimbolos.adiciona(new Simbolo(token.getName(), Categoria.PARAMETRO, nivelAtual, 0, 0));
                pilhaSimbolos.add(new Simbolo(token.getName(), Categoria.PARAMETRO, nivelAtual, 0, 0));
                pilhaSimbolosAux.add(new Simbolo(token.getName(), Categoria.PARAMETRO, nivelAtual, 0, 0));
                parametros++;
            }
        }
    }

    @FuncaoSemantica(105)
    public static void f5() {

        simboloAux = tabelaSimbolos.buscar(token.getName(), nivelAtual);
        if (simboloExiste(simboloAux)) {
            throw new SemanticoException("Simbolo ja existe");
        } else {
            constant = new Simbolo(token.getName(), Categoria.CONSTANT, nivelAtual, 0, 0);
            tabelaSimbolos.adiciona(constant);
            pilhaSimbolosAux.add(constant);
        }
    }

    @FuncaoSemantica(106)
    public static void f6() {

        constant.setGeneralA(token.getValue());
    }

    @FuncaoSemantica(107)
    public static void f7() {

        categoria = Categoria.VARIAVEL.getNome();
    }

    @FuncaoSemantica(108)
    public static void f8() {

        procedure = new Simbolo(token.getName(), Categoria.PROCEDURE, nivelAtual, areaInstrucoes.LC + 1, 0);
        tabelaSimbolos.adiciona(procedure);
        pilhaSimbolosAux.add(procedure);

        parametros = 0;
        procedurePossuiParametros = false;
        nivelAtual++;
        deslocamentoAmem = 3;
        variaveis = 0;
    }

    @FuncaoSemantica(109)
    public static void f9() {


        if (procedurePossuiParametros) {
            procedure.setGeneralB(parametros);
            for (int i = 0; i < parametros; i++) {
                Simbolo p = pilhaSimbolos.pop();
                p.setGeneralA(-(parametros - i));
            }
        }

        codigoIntermediario.add(new Instrucao(areaInstrucoes.LC, "DSVS", "-", "?"));
        pilhaProcedure.add(codigoIntermediario.get(codigoIntermediario.size()-1).getPosicao());
        hipotetica.IncluirAI(areaInstrucoes, 19, 0, 0);
        pilhaParametros.add(areaInstrucoes.LC - 1);
        pilhaParametros.add(parametros);
        parametros = 0;

    }

    @FuncaoSemantica(110)
    public static void f10() {

        Integer parametro = pilhaParametros.pop();
        codigoIntermediario.add(new Instrucao(areaInstrucoes.LC, "RETU", "-", String.valueOf(parametro)));
        hipotetica.IncluirAI(areaInstrucoes, 1, 0, parametro);
        codigoIntermediario.get(pilhaProcedure.get(pilhaProcedure.size() - 1) - 1).setOperador2(areaInstrucoes.LC + "");
        hipotetica.AlterarAI(areaInstrucoes, pilhaProcedure.get(pilhaProcedure.size() - 1), 0, areaInstrucoes.LC);
        removeSimboloAuxiliar();
        nivelAtual--;
    }

    @FuncaoSemantica(111)
    public static void f11() {

        categoria = Categoria.PARAMETRO.getNome();
        procedurePossuiParametros = true;
    }

    @FuncaoSemantica(114)
    public static void f14() {

        simboloAux = tabelaSimbolos.buscar(token.getName());
        if (simboloExiste(simboloAux)) {
            if (!simboloAux.getCategoria().getNome().equals(Categoria.VARIAVEL.getNome())) {
                throw new SemanticoException("Não é uma variável.");
            } else {
                nivelAuxiliar = nivelAtual - simboloAux.getNivel();
                deslocamentoAuxiliar = simboloAux.getGeneralA();
            }
        } else {
            throw new SemanticoException("Identificador \"" + token.getName() + "\" não declarado.");
        }
    }

    @FuncaoSemantica(115)
    public static void f15() {

        codigoIntermediario.add(new Instrucao(areaInstrucoes.LC, "ARMZ", String.valueOf(nivelAuxiliar), String.valueOf(deslocamentoAuxiliar)));
        hipotetica.IncluirAI(areaInstrucoes, 4, nivelAuxiliar, deslocamentoAuxiliar);
    }

    @FuncaoSemantica(116)
    public static void f16() {

        simboloAux = tabelaSimbolos.buscar(token.getName());
        if (simboloExiste(simboloAux)) {
            if (tabelaSimbolos.getTable().get(indiceTabelaSimbolos).getCategoria().equals(Categoria.PROCEDURE)) {
                pilhaAuxiliar = pilhaProcedure.pop();

                desvioAux = codigoIntermediario.get(pilhaAuxiliar+1);
                desvioAux.setOperador2(String.valueOf(areaInstrucoes.LC));
                codigoIntermediario.set(pilhaAuxiliar+1, desvioAux);
                hipotetica.AlterarAI(areaInstrucoes, 21, 0, desvioAux.getOp2());
            } else {
                throw new SemanticoException("Não é uma procedure.");
            }
        } else {
            throw new SemanticoException("Procedure não declarada.");
        }
        parametros = 0;
    }

    @FuncaoSemantica(117)
    public static void f17() {

        simboloAux = tabelaSimbolos.buscar(nomeCall);

        if (simboloAux.getGeneralB() != parametros) {
            throw new SemanticoException("Número de parâmetros inválido.");
        }

        codigoIntermediario.add(new Instrucao(areaInstrucoes.LC, "CALL", String.valueOf(nivelAtual), String.valueOf(deslocamentoAuxiliar)));
        hipotetica.IncluirAI(areaInstrucoes, 4, nivelAuxiliar, deslocamentoAuxiliar);
    }

    @FuncaoSemantica(118)
    public static void f18() {

        parametros++;
    }

    @FuncaoSemantica(120)
    public static void f20() {

        pilhaIf.add(areaInstrucoes.LC);
        codigoIntermediario.add(new Instrucao(areaInstrucoes.LC, "DVSF", "-", "?"));
        hipotetica.IncluirAI(areaInstrucoes, 20, -1, -1);
    }

    @FuncaoSemantica(121)
    public static void f21() {

        pilhaAuxiliar = pilhaIf.pop();
        desvioAux = codigoIntermediario.get(pilhaAuxiliar - 1);
        desvioAux.setOperador2(String.valueOf(areaInstrucoes.LC));
        codigoIntermediario.set(pilhaAuxiliar - 1, desvioAux);
        hipotetica.AlterarAI(areaInstrucoes, 20, 0, desvioAux.getOp2());
    }

    @FuncaoSemantica(122)
    public static void f22() {

        pilhaAuxiliar = pilhaIf.pop();
        desvioAux = codigoIntermediario.get(pilhaAuxiliar - 1);
        desvioAux.setOperador2(String.valueOf(areaInstrucoes.LC + 1));
        
        codigoIntermediario.set(pilhaAuxiliar - 1, desvioAux);
        hipotetica.AlterarAI(areaInstrucoes, 20, 0, desvioAux.getOp2());
        
        pilhaIf.add(areaInstrucoes.LC);
        codigoIntermediario.add(new Instrucao(areaInstrucoes.LC, "DSVS", "-", "?"));
        hipotetica.IncluirAI(areaInstrucoes, 19, 0, 0);
    }

    @FuncaoSemantica(123)
    public static void f23() {
        
        pilhaWhile.add(areaInstrucoes.LC);
    }

    @FuncaoSemantica(124)
    public static void f24() {
        
        codigoIntermediario.add(new Instrucao(areaInstrucoes.LC, "DVSF", "-", "?"));
        pilhaWhile.add(areaInstrucoes.LC);
        hipotetica.IncluirAI(areaInstrucoes, 20, 0, 0);
    }

    @FuncaoSemantica(125)
    public static void f25() {
        
        pilhaAuxiliar = pilhaWhile.pop();
        hipotetica.AlterarAI(areaInstrucoes, pilhaAuxiliar, 0, areaInstrucoes.LC + 1);
        desvioAux = codigoIntermediario.get(pilhaAuxiliar - 1);
        desvioAux.setOperador2(String.valueOf(areaInstrucoes.LC + 1));
        codigoIntermediario.set(pilhaAuxiliar - 1, desvioAux);
        pilhaAuxiliar = pilhaWhile.pop();
        codigoIntermediario.add(new Instrucao(areaInstrucoes.LC, "DSVS", "-", String.valueOf(pilhaAuxiliar)));
        hipotetica.IncluirAI(areaInstrucoes, 19, 0, pilhaAuxiliar);
    }

    @FuncaoSemantica(126)
    public static void f26() {
        
        pilhaRepeat.add(areaInstrucoes.LC);
    }

    @FuncaoSemantica(127)
    public static void f27() {
        
        pilhaAuxiliar = pilhaRepeat.pop();
        codigoIntermediario.add(new Instrucao(areaInstrucoes.LC, "DVSF", "-", String.valueOf(pilhaAuxiliar)));
        hipotetica.IncluirAI(areaInstrucoes, 20, 0, pilhaAuxiliar);
    }

    @FuncaoSemantica(128)
    public static void f28() {
        
        acao = "readln";
    }

    @FuncaoSemantica(129)
    public static void f29() {
        
        simboloAux = tabelaSimbolos.buscar(token.getName());
        if (simboloExiste(simboloAux)) {
            if (acao.equals("readln")) {
                
                if (tabelaSimbolos.getTable().get(indiceTabelaSimbolos).getCategoria().equals(Categoria.VARIAVEL)) {
                    codigoIntermediario.add(new Instrucao(areaInstrucoes.LC, "LEIT", "-", "-"));
                    hipotetica.IncluirAI(areaInstrucoes, 21, 0, 0);
                    Integer localNivel = tabelaSimbolos.getTable().get(indiceTabelaSimbolos).getNivel() - nivelAtual;
                    Integer localDeslocamento = tabelaSimbolos.getTable().get(indiceTabelaSimbolos).getGeneralA();
                    codigoIntermediario.add(new Instrucao(areaInstrucoes.LC, "ARMZ", String.valueOf(localNivel), String.valueOf(localDeslocamento)));
                    hipotetica.IncluirAI(areaInstrucoes, 4, localNivel, localDeslocamento);
                } else {
                    throw new SemanticoException("Não é uma variável.");
                }

            } else if (acao.equals("EXPRESSAO")) {

                if (tabelaSimbolos.getTable().get(indiceTabelaSimbolos).getCategoria().equals(Categoria.PROCEDURE)) {
                    throw new SemanticoException("Não é possível ler uma procedure!");
                } else if (tabelaSimbolos.getTable().get(indiceTabelaSimbolos).getCategoria().equals(Categoria.CONSTANT)) {
                    Integer i = tabelaSimbolos.getTable().get(indiceTabelaSimbolos).getGeneralA();
                    codigoIntermediario.add(new Instrucao(areaInstrucoes.LC, "CRCT", "-", String.valueOf(i)));
                    hipotetica.IncluirAI(areaInstrucoes, 3, 0, i);
                } else {
                    Integer nivelDiferenca = nivelAtual - tabelaSimbolos.getTable().get(indiceTabelaSimbolos).getNivel();
                    Integer localDeslocamento = tabelaSimbolos.getTable().get(indiceTabelaSimbolos).getGeneralA();
                    codigoIntermediario.add(new Instrucao(areaInstrucoes.LC, "CRVL", String.valueOf(nivelDiferenca), String.valueOf(localDeslocamento)));
                    hipotetica.IncluirAI(areaInstrucoes, 2, nivelDiferenca, localDeslocamento);
                }
            }
        }
    }

    @FuncaoSemantica(130)
    public static void f30() {

        codigoIntermediario.add(new Instrucao(areaInstrucoes.LC, "IMPL", "-", String.valueOf(areaLiterais.LIT)));
        hipotetica.IncluirAL(areaLiterais, token.getName());
        hipotetica.IncluirAI(areaInstrucoes, 23, 0, areaLiterais.LIT - 1);
    }

    @FuncaoSemantica(131)
    public static void f31() {

        codigoIntermediario.add(new Instrucao(areaInstrucoes.LC, "IMPR", "-", "-"));
        hipotetica.IncluirAI(areaInstrucoes, 22, 0, 0);
    }

    @FuncaoSemantica(132)
    public static void f32() {

    }

    @FuncaoSemantica(133)
    public static void f33() {

        while (!pilhaCase.isEmpty()) {
            pilhaAuxiliar = pilhaCase.pop();
            codigoIntermediario.get(pilhaAuxiliar).setOperador2(String.valueOf(areaInstrucoes.LC + 1));
        }
        codigoIntermediario.add(new Instrucao(areaInstrucoes.LC, "AMEM", "-", "-1"));
        hipotetica.IncluirAI(areaInstrucoes, 24, 0, -1);
    }

    @FuncaoSemantica(134)
    public static void f34() {

        codigoIntermediario.add(new Instrucao(areaInstrucoes.LC, "COPI", "-", "-"));
        hipotetica.IncluirAI(areaInstrucoes, 28, 0, 0);
        codigoIntermediario.add(new Instrucao(areaInstrucoes.LC, "CRCT", "-", token.getName()));
        hipotetica.IncluirAI(areaInstrucoes, 3, 0, 1);
        codigoIntermediario.add(new Instrucao(areaInstrucoes.LC, "CMIG", "-", "-"));
        hipotetica.IncluirAI(areaInstrucoes, 15, 0, 0);

        while (!pilhaCase.isEmpty()) {
            pilhaAuxiliar = pilhaCase.pop();
            if (codigoIntermediario.get(pilhaAuxiliar).getNome().equals("DSVS")) {
                break;
            }
            codigoIntermediario.get(pilhaAuxiliar).setOperador2(String.valueOf(areaInstrucoes.LC + 1));
        }
        pilhaCase.add(areaInstrucoes.LC);
        codigoIntermediario.add(new Instrucao(areaInstrucoes.LC, "DVSF", "-", "?"));
        hipotetica.IncluirAI(areaInstrucoes, 20, 0, 0);
    }

    @FuncaoSemantica(135)
    public static void f35() {

        codigoIntermediario.add(new Instrucao(areaInstrucoes.LC, "DSVS", "-", "?"));
        hipotetica.IncluirAI(areaInstrucoes, 19, 0, 0);
        pilhaAuxiliar = pilhaCase.pop();
        String op2 = String.valueOf(areaInstrucoes.LC + 1);
        codigoIntermediario.get(pilhaAuxiliar).setOperador2(op2);
        pilhaCase.add(areaInstrucoes.LC - 1);
    }

    @FuncaoSemantica(136)
    public static void f36() {

        codigoIntermediario.add(new Instrucao(areaInstrucoes.LC, "COPI", "-", "-"));
        hipotetica.IncluirAI(areaInstrucoes, 28, 0, 0);
        codigoIntermediario.add(new Instrucao(areaInstrucoes.LC, "CRCT", "-", token.getName()));
        hipotetica.IncluirAI(areaInstrucoes, 3, 0, 1);
        codigoIntermediario.add(new Instrucao(areaInstrucoes.LC, "CMIG", "-", "-"));
        hipotetica.IncluirAI(areaInstrucoes, 15, 0, 0);
        pilhaCase.add(areaInstrucoes.LC);
        codigoIntermediario.add(new Instrucao(areaInstrucoes.LC, "DSVT", "-", "?"));
        hipotetica.IncluirAI(areaInstrucoes, 19, 0, 0);
    }

    @FuncaoSemantica(137)
    public static void f37() {

        simboloAux = tabelaSimbolos.buscar(token.getName());
        if (Objects.isNull(simboloAux) || simboloAux.getIndex() == -1) {
            throw new SemanticoException(token.getName() + " não declarado.");
        } else {
            indiceTabelaSimbolos = simboloAux.getIndex();
        }
        nivelAuxiliar = tabelaSimbolos.getTable().get(indiceTabelaSimbolos).getNivel();
    }

    @FuncaoSemantica(138)
    public static void f38() {

        deslocamentoAuxiliar = tabelaSimbolos.getTable().get(indiceTabelaSimbolos).getGeneralA();
        codigoIntermediario.add(new Instrucao(areaInstrucoes.LC, "ARMZ", String.valueOf(nivelAuxiliar), String.valueOf(deslocamentoAuxiliar)));
        hipotetica.IncluirAI(areaInstrucoes, 4, nivelAuxiliar, deslocamentoAuxiliar);
    }

    @FuncaoSemantica(139)
    public static void f39() {

        pilhaFor.add(areaInstrucoes.LC);
        codigoIntermediario.add(new Instrucao(areaInstrucoes.LC, "COPI", "-", "-"));
        hipotetica.IncluirAI(areaInstrucoes, 28, nivelAuxiliar, deslocamentoAuxiliar);
        nivel = tabelaSimbolos.getTable().get(indiceTabelaSimbolos).getNivel() - nivelAtual;
        deslocamentoAuxiliar = tabelaSimbolos.getTable().get(indiceTabelaSimbolos).getGeneralA();
        codigoIntermediario.add(new Instrucao(areaInstrucoes.LC, "CRVL", String.valueOf(nivel), String.valueOf(deslocamentoAuxiliar)));
        hipotetica.IncluirAI(areaInstrucoes, 2, nivel, deslocamentoAuxiliar);
        codigoIntermediario.add(new Instrucao(areaInstrucoes.LC, "CMAI", "-", "-"));
        hipotetica.IncluirAI(areaInstrucoes, 18, 0, 0);
        codigoIntermediario.add(new Instrucao(areaInstrucoes.LC, "DSVF", "-", "?"));
        pilhaFor.add(areaInstrucoes.LC);
        hipotetica.IncluirAI(areaInstrucoes, 20, 0, 0);
        pilhaFor.add(indiceTabelaSimbolos);
    }

    @FuncaoSemantica(140)
    public static void f40() {

        indiceTabelaSimbolos = pilhaFor.pop();
        nivel = tabelaSimbolos.getTable().get(indiceTabelaSimbolos).getNivel() - nivelAtual;
        deslocamentoAuxiliar = tabelaSimbolos.getTable().get(indiceTabelaSimbolos).getGeneralA();
        codigoIntermediario.add(new Instrucao(areaInstrucoes.LC, "CRVL", String.valueOf(nivel), String.valueOf(deslocamentoAuxiliar)));
        hipotetica.IncluirAI(areaInstrucoes, 2, nivel, deslocamentoAuxiliar);

        codigoIntermediario.add(new Instrucao(areaInstrucoes.LC, "CRCT", "-", "1"));
        hipotetica.IncluirAI(areaInstrucoes, 3, 0, 1);

        codigoIntermediario.add(new Instrucao(areaInstrucoes.LC, "SOMA", "-", "-"));
        hipotetica.IncluirAI(areaInstrucoes, 5, 0, 0);

        codigoIntermediario.add(new Instrucao(areaInstrucoes.LC, "ARMZ", String.valueOf(nivel), String.valueOf(deslocamentoAuxiliar)));
        hipotetica.IncluirAI(areaInstrucoes, 4, nivel, deslocamentoAuxiliar);

        pilhaAuxiliar = pilhaFor.pop();
        hipotetica.AlterarAI(areaInstrucoes, pilhaAuxiliar - 1, -1, areaInstrucoes.LC + 1);

        desvioAux = codigoIntermediario.get(pilhaAuxiliar - 1);
        desvioAux.setOperador2(String.valueOf(areaInstrucoes.LC + 1));
        codigoIntermediario.set(pilhaAuxiliar - 1, desvioAux);

        pilhaAuxiliar = pilhaFor.pop();
        codigoIntermediario.add(new Instrucao(areaInstrucoes.LC, "DSVS", "-", String.valueOf(pilhaAuxiliar)));
        hipotetica.IncluirAI(areaInstrucoes, 19, 0, pilhaAuxiliar);

        codigoIntermediario.add(new Instrucao(areaInstrucoes.LC, "AMEM", "-", "-1"));
        hipotetica.IncluirAI(areaInstrucoes, 24, 0, -1);
    }

    @FuncaoSemantica(141)
    public static void f41() {

        codigoIntermediario.add(new Instrucao(areaInstrucoes.LC, "CMIG", "-", "-"));
        hipotetica.IncluirAI(areaInstrucoes, 15, 0, 0);
    }

    @FuncaoSemantica(142)
    public static void f42() {

        codigoIntermediario.add(new Instrucao(areaInstrucoes.LC, "CMME", "-", "-"));
        hipotetica.IncluirAI(areaInstrucoes, 13, 0, 0);
    }

    @FuncaoSemantica(143)
    public static void f43() {

        codigoIntermediario.add(new Instrucao(areaInstrucoes.LC, "CMMA", "-", "-"));
        hipotetica.IncluirAI(areaInstrucoes, 14, 0, 0);
    }

    @FuncaoSemantica(144)
    public static void f44() {

        codigoIntermediario.add(new Instrucao(areaInstrucoes.LC, "CMAI", "-", "-"));
        hipotetica.IncluirAI(areaInstrucoes, 18, 0, 0);
    }

    @FuncaoSemantica(145)
    public static void f45() {

        codigoIntermediario.add(new Instrucao(areaInstrucoes.LC, "CMEI", "-", "-"));
        hipotetica.IncluirAI(areaInstrucoes, 17, 0, 0);
    }

    @FuncaoSemantica(146)
    public static void f46() {

        codigoIntermediario.add(new Instrucao(areaInstrucoes.LC, "CMDF", "-", "-"));
        hipotetica.IncluirAI(areaInstrucoes, 16, 0, 0);
    }

    @FuncaoSemantica(147)
    public static void f47() {

        codigoIntermediario.add(new Instrucao(areaInstrucoes.LC, "INVR", "-", "-"));
        hipotetica.IncluirAI(areaInstrucoes, 9, 0, 0);
    }

    @FuncaoSemantica(148)
    public static void f48() {

        codigoIntermediario.add(new Instrucao(areaInstrucoes.LC, "SOMA", "-", "-"));
        hipotetica.IncluirAI(areaInstrucoes, 5, 0, 0);
    }

    @FuncaoSemantica(149)
    public static void f49() {

        codigoIntermediario.add(new Instrucao(areaInstrucoes.LC, "SUBT", "-", "-"));
        hipotetica.IncluirAI(areaInstrucoes, 6, 0, 0);
    }

    @FuncaoSemantica(150)
    public static void f50() {

        codigoIntermediario.add(new Instrucao(areaInstrucoes.LC, "DISJ", "-", "-"));
        hipotetica.IncluirAI(areaInstrucoes, 12, 0, 0);
    }

    @FuncaoSemantica(151)
    public static void f51() {

        codigoIntermediario.add(new Instrucao(areaInstrucoes.LC, "MULT", "-", "-"));
        hipotetica.IncluirAI(areaInstrucoes, 7, 0, 0);
    }

    @FuncaoSemantica(152)
    public static void f52() {

        codigoIntermediario.add(new Instrucao(areaInstrucoes.LC, "DIV", "-", "-"));
        hipotetica.IncluirAI(areaInstrucoes, 8, 0, 0);
    }

    @FuncaoSemantica(153)
    public static void f53() {

        codigoIntermediario.add(new Instrucao(areaInstrucoes.LC, "CONJ", "-", "-"));
        hipotetica.IncluirAI(areaInstrucoes, 11, 0, 0);
    }

    @FuncaoSemantica(154)
    public static void f54() {

        codigoIntermediario.add(new Instrucao(areaInstrucoes.LC, "CRCT", "-", token.getName()));
        hipotetica.IncluirAI(areaInstrucoes, 3, 0, token.getValue());
    }

    @FuncaoSemantica(155)
    public static void f55() {

        codigoIntermediario.add(new Instrucao(areaInstrucoes.LC, "NEGA", "-", "-"));
        hipotetica.IncluirAI(areaInstrucoes, 3, 0, 0);
    }

    @FuncaoSemantica(156)
    public static void f56() {

        acao = "EXPRESSAO";
    }

    private static void removeSimboloAuxiliar() {

        for (Simbolo s : pilhaSimbolosAux) {
            if (s.getNivel() == nivelAtual) {
                tabelaSimbolos.exclui(s);
            }
        }
    }

    private static boolean simboloExiste(Simbolo s) {

        if (Objects.nonNull(s) && s.getIndex() != -1) {
            indiceTabelaSimbolos = s.getIndex();
            return true;
        } else {
            return false;
        }
    }

    public static List<Instrucao> getCodigoIntermediario() {

        return codigoIntermediario;
    }
}
