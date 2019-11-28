package br.com.unisul.analisador.motor;

import br.com.unisul.analisador.annotations.FuncaoSemantica;
import br.com.unisul.analisador.dto.Category;
import br.com.unisul.analisador.dto.Instruction;
import br.com.unisul.analisador.dto.Symbol;
import br.com.unisul.analisador.dto.SymbolTable;
import br.com.unisul.analisador.dto.Token;
import br.com.unisul.analisador.exception.SemanticoException;
import br.com.unisul.analisador.util.SemanticoUtils;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

public class AnalisadorSemantico {

    private static final List<Instruction> intermediateCode = new ArrayList<>();
    private static List<Integer> stackIf;
    private static List<Integer> stackWhile;
    private static List<Integer> stackRepeat;
    private static List<Integer> stackCase;
    private static List<Integer> stackFor;
    private static List<Integer> stackParameters;
    private static List<Integer> stackProcedure;
    private static List<Symbol> stackSymbol;
    private static List<Symbol> stackSymbolAux;
    private static Map<Integer, String> literalArea;

    private static SymbolTable symbolTable;
    private static Symbol constant;
    private static Symbol procedure;
    private static Symbol symbolTmp;

    private static Hipotetica virtualMachine;
    private static AreaInstrucoes ia;
    private static AreaLiterais la;
    private static Integer currentLevel;
    private static Integer level;
    private static Integer variables;
    private static Integer parameters;
    private static Integer displacement;
    private static Integer levelVariable, displacementVariable;
    private static Integer indexSymbolTable;
    private static Integer stackAux;
    private static Instruction stackDetour;
    private static Boolean containsParameters;
    private static String identifierType;
    private static String context;
    private static String callAux;

    private static Token token;

    public static void setToken(Token token) {
        AnalisadorSemantico.token = token;
    }

    public static void setCall(String call) {
        System.out.println("Setando call: " + call);
        AnalisadorSemantico.callAux = call;
    }

    public static void executeAction(Integer idCode, Token token) throws SemanticoException {
        SemanticoUtils.executeAnalisadorSemantico(idCode, token);
        System.out.println(intermediateCode.stream().map(code -> code.toString()).collect(Collectors.joining("\n")));
    }

    public static String getIntermediateCode() {

        StringBuilder tmp = new StringBuilder();
        tmp.append("------------------------------------------------------\n");
        tmp.append("CÓDIGO INTERMEDIÁRIO\n");
        tmp.append("#\t" + "INST.\t" + "ARG.1\t" + "ARG.2\t\n");
        tmp.append("------------------------------------------------------\n");

        for (int i = 0; i < intermediateCode.size(); i++) {

            tmp.append(i + 1 + "\t");
            tmp.append(intermediateCode.get(i).getName() + "\t");
            tmp.append(intermediateCode.get(i).getOperator1() + "\t");
            tmp.append(intermediateCode.get(i).getOperator2() + "\t\n");
        }

        if (Objects.nonNull(literalArea) && !literalArea.isEmpty()) {

            tmp.append("------------------------------------------------------\n");
            tmp.append("ÁREA DE LITERAIS \n");
            tmp.append("#\t" + "LITERAL\n");

            for (int i = 0; i < literalArea.size(); i++) {
                tmp.append(i + "\t" + literalArea.get(i + 1) + "\n");
            }
        }

        return tmp.toString();
    }
    
    public static List<Instruction> getIntermediateCodel() {
    	
    	List<Instruction> list = new ArrayList<>();

        for (int i = 0; i < intermediateCode.size(); i++) {
        	Instruction instruction = new Instruction(i + 1, intermediateCode.get(i).getName(), intermediateCode.get(i).getOperator1(), intermediateCode.get(i).getOperator2());
        
        	list.add(instruction);
        }

        return list;
    }
    
    public static void interpretar() {
   	 virtualMachine.Interpreta(ia, la);
   }

    @FuncaoSemantica(100)
    public static void inicializacao() {

        stackIf = new ArrayList<>();
        stackWhile = new ArrayList<>();
        stackRepeat = new ArrayList<>();
        stackParameters = new ArrayList<>();
        stackProcedure = new ArrayList<>();
        stackCase = new ArrayList<>();
        stackFor = new ArrayList<>();
        stackSymbol = new ArrayList<>();
        stackSymbolAux = new ArrayList<>();
        literalArea = new HashMap<>();
        Integer tableSize = 8;
        symbolTable = new SymbolTable(tableSize);

        virtualMachine = new Hipotetica();
        ia = new AreaInstrucoes();
        la = new AreaLiterais();
        Hipotetica.InicializaAI(ia);
        Hipotetica.InicializaAL(la);

        currentLevel = 0;
        Integer nextFreePosition = 1;
        variables = 0;
        displacement = 3;
        ia.LC = 1;
        Integer indexLiteralArea = 1;
        levelVariable = currentLevel;
        displacementVariable = displacement;
        Boolean isProcedure = false;
    }

    @FuncaoSemantica(101)
    public static void para() {
        intermediateCode.add(new Instruction(ia.LC, "PARA", "-", "-"));
        virtualMachine.IncluirAI(ia, 26, 0, 0);
    }

    @FuncaoSemantica(102)
    public static void amem() {
        displacement = 3;

        intermediateCode.add(new Instruction(ia.LC, "AMEM", "-", (displacement + variables) + ""));
        virtualMachine.IncluirAI(ia, 24, 0, (displacement + variables));
    }

    @FuncaoSemantica(104)
    public static void f4() {
        if (Category.VAR.getLabel().equals(identifierType)) {

            symbolTmp = symbolTable.search(token.getName(), currentLevel);

            if (isValidSymbol(symbolTmp)) {
                throw new SemanticoException(getMsgError104(token));
            } else {
                symbolTable.insert(new Symbol(token.getName(), Category.VAR, currentLevel, displacement, 0));

                stackSymbol.add(new Symbol(token.getName(), Category.VAR, currentLevel, displacement, 0));
                stackSymbolAux.add(new Symbol(token.getName(), Category.VAR, currentLevel, displacement, 0));

                displacement++;
                variables++;
            }

        } else if (Category.PARAMETER.getLabel().equals(identifierType)) {

            symbolTmp = symbolTable.search(token.getName(), currentLevel);

            if (isValidSymbol(symbolTmp)) {
                throw new SemanticoException(getMsgError104(token));
            } else {
                Symbol domain = new Symbol(token.getName(), Category.PARAMETER, currentLevel, 0, 0);
                symbolTable.insert(domain);

                stackSymbol.add(domain);
                stackSymbolAux.add(domain);
                parameters++;
            }
        }
    }

    @FuncaoSemantica(105)
    public static void f5() {
        symbolTmp = symbolTable.search(token.getName(), currentLevel);

        if (isValidSymbol(symbolTmp)) {
            throw new SemanticoException(getMsgError104(token));
        } else {
            constant = new Symbol(token.getName(), Category.CONSTANT, currentLevel, 0, 0);
            symbolTable.insert(constant);
            stackSymbolAux.add(constant);
        }
    }

    @FuncaoSemantica(106)
    public static void f6() {
        constant.setGeneralA(token.getValue());
    }

    @FuncaoSemantica(107)
    public static void f7() {
        identifierType = Category.VAR.getLabel();
    }

    @FuncaoSemantica(108)
    public static void f8() {
        procedure = new Symbol(token.getName(), Category.PROCEDURE, currentLevel, ia.LC + 1, 0);
        symbolTable.insert(procedure);
        stackSymbolAux.add(procedure);

        parameters = 0;
        containsParameters = false;

        currentLevel++;

        displacement = 3;
        variables = 0;
    }

    @FuncaoSemantica(109)
    public static void f9() {
        stackProcedure.add(ia.LC);

        if (containsParameters) {

            procedure.setGeneralB(parameters);

            for (int i = 0; i < parameters; i++) {
                Symbol p = returnSymbol(stackSymbol);
                p.setGeneralA(-(parameters - i));
            }
        }

        intermediateCode.add(new Instruction(ia.LC, "DSVS", "-", "?"));
        virtualMachine.IncluirAI(ia, 19, 0, 0);
        stackParameters.add(ia.LC - 1);
        stackParameters.add(parameters);

        parameters = 0;
    }

    @FuncaoSemantica(110)
    public static void f10() {
        Integer p = returnStack(stackParameters);

        
        intermediateCode.add(new Instruction(ia.LC, "RETU", "-", String.valueOf(p)));
        virtualMachine.IncluirAI(ia, 1, 0, p);
        intermediateCode.get(stackProcedure.get(stackProcedure.size() -1) -1).setOperator2(ia.LC + "");
        Hipotetica.AlterarAI(ia, stackProcedure.get(stackProcedure.size() - 1), 0, ia.LC);
        
        removeSymbol();

        currentLevel--;
    }

    @FuncaoSemantica(111)
    public static void f11() {
        identifierType = Category.PARAMETER.getLabel();
        containsParameters = true;
    }

    @FuncaoSemantica(114)
    public static void f14() {
        symbolTmp = symbolTable.search(token.getName(), currentLevel);

        int oldLevel = currentLevel - 1;
        while (symbolTmp == null && oldLevel >= 0) {
            symbolTmp = symbolTable.search(token.getName(), oldLevel);
            oldLevel = oldLevel - 1;
        }

        if (isValidSymbol(symbolTmp)) {

            if (!symbolTmp.getCategory().getLabel().equals(Category.VAR.getLabel())) {

                throw new SemanticoException("Não é uma variável.");

            } else {
                levelVariable = currentLevel - symbolTmp.getLevel();
                displacementVariable = symbolTmp.getGeneralA();
            }

        } else {
            throw new SemanticoException("Identificador \"" + token.getName() + "\" não declarado.");
        }
    }

    @FuncaoSemantica(115)
    public static void f15() {
        intermediateCode.add(new Instruction(ia.LC, "ARMZ", String.valueOf(levelVariable), String.valueOf(displacementVariable)));
        virtualMachine.IncluirAI(ia, 4, levelVariable, displacementVariable);
    }

    @FuncaoSemantica(116)
    public static void f16() {
        symbolTmp = symbolTable.search(token.getName());

        if (isValidSymbol(symbolTmp)) {

            Category c = symbolTable.getTable().get(indexSymbolTable).getCategory();

            if (c.equals(Category.PROCEDURE)) {

                stackAux = returnStack(stackProcedure);
                stackDetour = intermediateCode.get(stackAux - 1);
                stackDetour.setOperator2(String.valueOf(ia.LC));

                intermediateCode.set(stackAux - 1, stackDetour);
                Hipotetica.AlterarAI(ia, 21, 0, stackDetour.getOp2());

            } else {
                throw new SemanticoException("Não é uma procedure.");
            }

        } else {
            throw new SemanticoException("Procedure não declarada.");
        }

        parameters = 0;
    }

    @FuncaoSemantica(117)
    public static void f17() {
        symbolTmp = symbolTable.search(callAux);

        if (symbolTmp.getGeneralB() != parameters)
            throw new SemanticoException("Número de parâmetros inválido.");

        intermediateCode.add(new Instruction(ia.LC, "CALL", String.valueOf(currentLevel), String.valueOf(displacementVariable)));
        virtualMachine.IncluirAI(ia, 4, levelVariable, displacementVariable);
    }

    @FuncaoSemantica(118)
    public static void f18() {
        parameters++;
    }

    @FuncaoSemantica(120)
    public static void f20() {
        stackIf.add(ia.LC);
        intermediateCode.add(new Instruction(ia.LC, "DVSF", "-", "?"));
        virtualMachine.IncluirAI(ia, 20, -1, -1);
    }

    @FuncaoSemantica(121)
    public static void f21() {
        stackAux = returnStack(stackIf);
        stackDetour = intermediateCode.get(stackAux - 1);
        stackDetour.setOperator2(String.valueOf(ia.LC));

        intermediateCode.set(stackAux - 1, stackDetour);
        Hipotetica.AlterarAI(ia, 20, 0, stackDetour.getOp2());
    }

    @FuncaoSemantica(122)
    public static void f22() {
        stackAux = returnStack(stackIf);
        stackDetour = intermediateCode.get(stackAux - 1);
        stackDetour.setOperator2(String.valueOf(ia.LC + 1));

        intermediateCode.set(stackAux - 1, stackDetour);
        Hipotetica.AlterarAI(ia, 20, 0, stackDetour.getOp2());

        stackIf.add(ia.LC);
        intermediateCode.add(new Instruction(ia.LC, "DSVS", "-", "?"));
        virtualMachine.IncluirAI(ia, 19, 0, 0);
    }

    @FuncaoSemantica(123)
    public static void f23() {
        stackWhile.add(ia.LC);
    }

    @FuncaoSemantica(124)
    public static void f24() {
        intermediateCode.add(new Instruction(ia.LC, "DVSF", "-", "?"));
        stackWhile.add(ia.LC);
        virtualMachine.IncluirAI(ia, 20, 0, 0);
    }

    @FuncaoSemantica(125)
    public static void f25() {
        stackAux = returnStack(stackWhile);
        Hipotetica.AlterarAI(ia, stackAux, 0, ia.LC + 1);

        stackDetour = intermediateCode.get(stackAux - 1);
        stackDetour.setOperator2(String.valueOf(ia.LC + 1));

        intermediateCode.set(stackAux - 1, stackDetour);

        stackAux = returnStack(stackWhile);
        intermediateCode.add(new Instruction(ia.LC, "DSVS", "-", String.valueOf(stackAux)));
        virtualMachine.IncluirAI(ia, 19, 0, stackAux);
    }

    @FuncaoSemantica(126)
    public static void f26() {
        stackRepeat.add(ia.LC);
    }

    @FuncaoSemantica(127)
    public static void f27() {
        stackAux = returnStack(stackRepeat);

        intermediateCode.add(new Instruction(ia.LC, "DVSF", "-", String.valueOf(stackAux)));
        virtualMachine.IncluirAI(ia, 20, 0, stackAux);
    }

    @FuncaoSemantica(128)
    public static void f28() {
        context = "readln";
    }

    @FuncaoSemantica(129)
    public static void f29() {
        symbolTmp = symbolTable.search(token.getName());

        if (isValidSymbol(symbolTmp)) {

            if (context.equals("readln")) {

                if (symbolTable.getTable().get(indexSymbolTable).getCategory().equals(Category.VAR)) {

                    intermediateCode.add(new Instruction(ia.LC, "LEIT", "-", "-"));
                    virtualMachine.IncluirAI(ia, 21, 0, 0);

                    Integer localLevel = symbolTable.getTable().get(indexSymbolTable).getLevel() - currentLevel;
                    Integer localDisplacement = symbolTable.getTable().get(indexSymbolTable).getGeneralA();

                    intermediateCode.add(new Instruction(ia.LC, "ARMZ", String.valueOf(localLevel), String.valueOf(localDisplacement)));
                    virtualMachine.IncluirAI(ia, 4, localLevel, localDisplacement);

                } else {
                    throw new SemanticoException("Não é uma variável.");
                }

            } else if (context.equals("EXPRESSAO")) {

                Category c = symbolTable.getTable().get(indexSymbolTable).getCategory();

                if (c.equals(Category.PROCEDURE)) {

                    throw new SemanticoException("Não é possível ler uma procedure!");

                } else if (c.equals(Category.CONSTANT)) {

                    Integer i = symbolTable.getTable().get(indexSymbolTable).getGeneralA();

                    intermediateCode.add(new Instruction(ia.LC, "CRCT", "-", String.valueOf(i)));
                    virtualMachine.IncluirAI(ia, 3, 0, i);

                } else {

                    Integer levelDifference = currentLevel
                            - symbolTable.getTable().get(indexSymbolTable).getLevel();
                    Integer localDisplacement = symbolTable.getTable().get(indexSymbolTable).getGeneralA();

                    intermediateCode.add(new Instruction(ia.LC, "CRVL", String.valueOf(levelDifference),
                            String.valueOf(localDisplacement)));
                    virtualMachine.IncluirAI(ia, 2, levelDifference, localDisplacement);

                }
            }
        }
    }

    @FuncaoSemantica(130)
    public static void f30() {
        intermediateCode.add(new Instruction(ia.LC, "IMPL", "-", String.valueOf(la.LIT)));
        Hipotetica.IncluirAL(la, token.getName());
        virtualMachine.IncluirAI(ia, 23, 0, la.LIT - 1);

        literalArea.put(la.LIT, token.getName());
    }

    @FuncaoSemantica(131)
    public static void f31() {
        intermediateCode.add(new Instruction(ia.LC, "IMPR", "-", "-"));
        virtualMachine.IncluirAI(ia, 22, 0, 0);
    }

    @FuncaoSemantica(132)
    public static void f32() {
    }

    @FuncaoSemantica(133)
    public static void f33() {
        while (!stackCase.isEmpty()) {
            stackAux = returnStack(stackCase);
            String op2 = String.valueOf(ia.LC + 1);
            intermediateCode.get(stackAux).setOperator2(op2);
        }
        intermediateCode.add(new Instruction(ia.LC, "AMEM", "-", "-1"));
        virtualMachine.IncluirAI(ia, 24, 0, -1);
    }

    @FuncaoSemantica(134)
    public static void f34() {
        intermediateCode.add(new Instruction(ia.LC, "COPI", "-", "-"));
        virtualMachine.IncluirAI(ia, 28, 0, 0);

        intermediateCode.add(new Instruction(ia.LC, "CRCT", "-", token.getName()));// TODO VERIFICAR OP2
        virtualMachine.IncluirAI(ia, 3, 0, 1);

        intermediateCode.add(new Instruction(ia.LC, "CMIG", "-", "-"));
        virtualMachine.IncluirAI(ia, 15, 0, 0);

        while (!stackCase.isEmpty()) {
            stackAux = returnStack(stackCase);
            if (intermediateCode.get(stackAux).getName().equals("DSVS"))
                break;

            String op2 = String.valueOf(ia.LC + 1);
            intermediateCode.get(stackAux).setOperator2(op2);
        }

        stackCase.add(ia.LC);
        intermediateCode.add(new Instruction(ia.LC, "DVSF", "-", "?"));
        virtualMachine.IncluirAI(ia, 20, 0, 0);
    }

    @FuncaoSemantica(135)
    public static void f35() {
        intermediateCode.add(new Instruction(ia.LC, "DSVS", "-", "?"));
        virtualMachine.IncluirAI(ia, 19, 0, 0);

        stackAux = returnStack(stackCase);
        String op2 = String.valueOf(ia.LC + 1);
        intermediateCode.get(stackAux).setOperator2(op2);

        stackCase.add(ia.LC - 1);
    }

    @FuncaoSemantica(136)
    public static void f36() {

        intermediateCode.add(new Instruction(ia.LC, "COPI", "-", "-"));
        virtualMachine.IncluirAI(ia, 28, 0, 0);

        intermediateCode.add(new Instruction(ia.LC, "CRCT", "-", token.getName()));
        virtualMachine.IncluirAI(ia, 3, 0, 1);

        intermediateCode.add(new Instruction(ia.LC, "CMIG", "-", "-"));
        virtualMachine.IncluirAI(ia, 15, 0, 0);

        stackCase.add(ia.LC);

        intermediateCode.add(new Instruction(ia.LC, "DSVT", "-", "?"));
        virtualMachine.IncluirAI(ia, 19, 0, 0);
    }

    @FuncaoSemantica(137)
    public static void f37() {
        symbolTmp = symbolTable.search(token.getName());

        if (Objects.isNull(symbolTmp) || symbolTmp.getIndex() == -1)
            throw new SemanticoException(token.getName() + " não declarado.");
        else
            indexSymbolTable = symbolTmp.getIndex();

        levelVariable = symbolTable.getTable().get(indexSymbolTable).getLevel();
    }

    @FuncaoSemantica(138)
    public static void f38() {
        displacementVariable = symbolTable.getTable().get(indexSymbolTable).getGeneralA();
        intermediateCode.add(new Instruction(ia.LC, "ARMZ", String.valueOf(levelVariable), String.valueOf(displacementVariable)));
        virtualMachine.IncluirAI(ia, 4, levelVariable, displacementVariable);
    }

    @FuncaoSemantica(139)
    public static void f39() {
        stackFor.add(ia.LC);

        intermediateCode.add(new Instruction(ia.LC, "COPI", "-", "-"));
        virtualMachine.IncluirAI(ia, 28, levelVariable, displacementVariable);

        level = symbolTable.getTable().get(indexSymbolTable).getLevel() - currentLevel;
        displacementVariable = symbolTable.getTable().get(indexSymbolTable).getGeneralA();

        intermediateCode.add(new Instruction(ia.LC, "CRVL", String.valueOf(level), String.valueOf(displacementVariable)));
        virtualMachine.IncluirAI(ia, 2, level, displacementVariable);

        intermediateCode.add(new Instruction(ia.LC, "CMAI", "-", "-"));
        virtualMachine.IncluirAI(ia, 18, 0, 0);

        intermediateCode.add(new Instruction(ia.LC, "DSVF", "-", "?"));
        stackFor.add(ia.LC);
        virtualMachine.IncluirAI(ia, 20, 0, 0);
        stackFor.add(indexSymbolTable);
    }

    @FuncaoSemantica(140)
    public static void f40() {
        indexSymbolTable = returnStack(stackFor);
        level = symbolTable.getTable().get(indexSymbolTable).getLevel() - currentLevel;
        displacementVariable = symbolTable.getTable().get(indexSymbolTable).getGeneralA();

        intermediateCode.add(new Instruction(ia.LC, "CRVL", String.valueOf(level), String.valueOf(displacementVariable)));
        virtualMachine.IncluirAI(ia, 2, level, displacementVariable);

        intermediateCode.add(new Instruction(ia.LC, "CRCT", "-", "1"));
        virtualMachine.IncluirAI(ia, 3, 0, 1);

        intermediateCode.add(new Instruction(ia.LC, "SOMA", "-", "-"));
        virtualMachine.IncluirAI(ia, 5, 0, 0);

        intermediateCode.add(new Instruction(ia.LC, "ARMZ", String.valueOf(level), String.valueOf(displacementVariable)));
        virtualMachine.IncluirAI(ia, 4, level, displacementVariable);

        stackAux = returnStack(stackFor);
        Hipotetica.AlterarAI(ia, stackAux - 1, -1, ia.LC + 1);

        stackDetour = intermediateCode.get(stackAux - 1);
        stackDetour.setOperator2(String.valueOf(ia.LC + 1));
        intermediateCode.set(stackAux - 1, stackDetour);

        stackAux = returnStack(stackFor);
        intermediateCode.add(new Instruction(ia.LC, "DSVS", "-", String.valueOf(stackAux)));
        virtualMachine.IncluirAI(ia, 19, 0, stackAux);

        intermediateCode.add(new Instruction(ia.LC, "AMEM", "-", "-1"));
        virtualMachine.IncluirAI(ia, 24, 0, -1);
    }

    @FuncaoSemantica(141)
    public static void f41() {
        intermediateCode.add(new Instruction(ia.LC, "CMIG", "-", "-"));
        virtualMachine.IncluirAI(ia, 15, 0, 0);
    }

    @FuncaoSemantica(142)
    public static void f42() {
        intermediateCode.add(new Instruction(ia.LC, "CMME", "-", "-"));
        virtualMachine.IncluirAI(ia, 13, 0, 0);
    }

    @FuncaoSemantica(143)
    public static void f43() {
        intermediateCode.add(new Instruction(ia.LC, "CMMA", "-", "-"));
        virtualMachine.IncluirAI(ia, 14, 0, 0);
    }

    @FuncaoSemantica(144)
    public static void f44() {
        intermediateCode.add(new Instruction(ia.LC, "CMAI", "-", "-"));
        virtualMachine.IncluirAI(ia, 18, 0, 0);
    }

    @FuncaoSemantica(145)
    public static void f45() {
        intermediateCode.add(new Instruction(ia.LC, "CMEI", "-", "-"));
        virtualMachine.IncluirAI(ia, 17, 0, 0);
    }

    @FuncaoSemantica(146)
    public static void f46() {
        intermediateCode.add(new Instruction(ia.LC, "CMDF", "-", "-"));
        virtualMachine.IncluirAI(ia, 16, 0, 0);
    }

    @FuncaoSemantica(147)
    public static void f47() {
        intermediateCode.add(new Instruction(ia.LC, "INVR", "-", "-"));
        virtualMachine.IncluirAI(ia, 9, 0, 0);
    }

    @FuncaoSemantica(148)
    public static void f48() {
        intermediateCode.add(new Instruction(ia.LC, "SOMA", "-", "-"));
        virtualMachine.IncluirAI(ia, 5, 0, 0);
    }

    @FuncaoSemantica(149)
    public static void f49() {
        intermediateCode.add(new Instruction(ia.LC, "SUBT", "-", "-"));
        virtualMachine.IncluirAI(ia, 6, 0, 0);
    }

    @FuncaoSemantica(150)
    public static void f50() {
        intermediateCode.add(new Instruction(ia.LC, "DISJ", "-", "-"));
        virtualMachine.IncluirAI(ia, 12, 0, 0);
    }

    @FuncaoSemantica(151)
    public static void f51() {
        intermediateCode.add(new Instruction(ia.LC, "MULT", "-", "-"));
        virtualMachine.IncluirAI(ia, 7, 0, 0);
    }

    @FuncaoSemantica(152)
    public static void f52() {
        intermediateCode.add(new Instruction(ia.LC, "DIV", "-", "-"));
        virtualMachine.IncluirAI(ia, 8, 0, 0);
    }

    @FuncaoSemantica(153)
    public static void f53() {
        intermediateCode.add(new Instruction(ia.LC, "CONJ", "-", "-"));
        virtualMachine.IncluirAI(ia, 11, 0, 0);
    }

    @FuncaoSemantica(154)
    public static void f54() {
        intermediateCode.add(new Instruction(ia.LC, "CRCT", "-", token.getName()));
        virtualMachine.IncluirAI(ia, 3, 0, token.getValue());
    }

    @FuncaoSemantica(155)
    public static void f55() {
        intermediateCode.add(new Instruction(ia.LC, "NEGA", "-", "-"));
        virtualMachine.IncluirAI(ia, 3, 0, 0);
    }

    @FuncaoSemantica(156)
    public static void f56() {
        context = "EXPRESSAO";
    }

    private static void removeSymbol() {

        for (Symbol s : stackSymbolAux) {

            if (s.getLevel() == currentLevel)
                symbolTable.delete(s);

        }
    }

    private static String getMsgError104(Token token) {
        StringBuilder sb = new StringBuilder();
        sb.append("Variável ").append(token.getName()).append(" do nível ").append(currentLevel).append(" ja existe!");

        return sb.toString();
    }

    private static boolean isValidSymbol(Symbol s) {

        if (Objects.nonNull(s) && s.getIndex() != -1) {

            indexSymbolTable = s.getIndex();
            return true;

        } else {
            return false;
        }
    }

    private static Integer returnStack(List<Integer> stack) {

        Integer tmp = stack.get(stack.size() - 1);
        stack.remove(stack.size() - 1);

        return tmp;
    }

    private static Symbol returnSymbol(List<Symbol> stack) {

        Symbol tmp = stack.get(stack.size() - 1);
        stack.remove(stack.size() - 1);

        return tmp;
    }
}
