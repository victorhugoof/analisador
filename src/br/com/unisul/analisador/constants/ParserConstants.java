package br.com.unisul.analisador.constants;

public interface ParserConstants {
    int START_SYMBOL = 46;

    int FIRST_NON_TERMINAL    = 46;
    int FIRST_SEMANTIC_ACTION = 77;

    int[][] PARSER_TABLE =
    {
        { -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1,  0, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1 },
        { -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1,  1,  1,  1,  1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1 },
        { -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1,  2, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1 },
        { -1, -1, -1, -1, -1, -1, -1, -1,  3, -1, -1, -1, -1, -1, -1,  4, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1 },
        { -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1,  5,  8,  8,  8, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1 },
        { -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1,  7, -1, -1, -1, -1,  6,  6,  6, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1 },
        { -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1,  9, 12, 12, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1 },
        { -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, 11, -1, -1, -1, -1, -1, 10, 10, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1 },
        { -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, 13, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1 },
        { -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, 14, 15, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1 },
        { -1, -1, -1, -1, -1, 17, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, 16, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1 },
        { -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, 18, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1 },
        { -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, 20, -1, -1, -1, -1, -1, -1, -1, -1, -1, 19, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1 },
        { -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, 23, -1, 21, -1, -1, -1, -1, -1, -1, 22, 23, -1, 24, 29, -1, 23, 32, -1, 33, 23, 34, 38, -1, -1, -1, 49, -1, 43, -1 },
        { -1, -1, -1, -1, -1, 26, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, 25, -1, -1, -1, -1, -1, -1, -1, -1, -1, 25, -1, -1, -1, -1, 25, -1, -1, -1, 25, -1, -1, -1, -1, -1, -1, -1, -1, -1 },
        { -1, -1, -1, -1, -1, -1, 27, -1, -1, -1, -1, -1, -1, -1, -1, 28, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1 },
        { -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, 30, -1, -1, -1, -1, -1, -1, -1, -1, -1, 30, -1, -1, -1, -1, 31, -1, -1, -1, 30, -1, -1, -1, -1, -1, -1, -1, -1, -1 },
        { -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, 35, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1 },
        { -1, -1, -1, -1, -1, -1, 36, -1, -1, -1, -1, -1, -1, -1, -1, 37, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1 },
        { -1, 40, 40, -1, -1, 40, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, 40, 40, 39, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, 40, -1, -1, -1, -1 },
        { -1, -1, -1, -1, -1, -1, 41, -1, -1, -1, -1, -1, -1, -1, -1, 42, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1 },
        { -1, 50, 50, -1, -1, 50, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, 50, 50, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, 50, -1, -1, -1, -1 },
        { -1, -1, -1, -1, -1, -1, 51, -1, -1, 52, 54, 53, 56, 55, 57, 51, 51, -1, -1, -1, -1, -1, -1, -1, -1, -1, 51, -1, -1, -1, 51, 51, -1, 51, -1, 51, -1, -1, -1, -1, -1, -1, 51, -1, 51 },
        { -1, 58, 59, -1, -1, 60, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, 60, 60, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, 60, -1, -1, -1, -1 },
        { -1, 61, 62, -1, -1, -1, 64, -1, -1, 64, 64, 64, 64, 64, 64, 64, 64, -1, -1, -1, -1, -1, -1, -1, -1, -1, 64, -1, -1, -1, 64, 64, -1, 64, -1, 64, -1, -1, 63, -1, -1, -1, 64, -1, 64 },
        { -1, -1, -1, -1, -1, 65, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, 65, 65, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, 65, -1, -1, -1, -1 },
        { -1, 66, 66, 67, 68, -1, 66, -1, -1, 66, 66, 66, 66, 66, 66, 66, 66, -1, -1, -1, -1, -1, -1, -1, -1, -1, 66, -1, -1, -1, 66, 66, -1, 66, -1, 66, -1, -1, 66, 69, -1, -1, 66, -1, 66 },
        { -1, -1, -1, -1, -1, 71, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, 73, 70, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, 72, -1, -1, -1, -1 },
        { -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, 44, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1 },
        { -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, 48, -1, -1, -1, -1, -1, -1, -1, -1, -1, 47, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1 },
        { -1, -1, -1, -1, -1, -1, -1, -1, 46, -1, -1, -1, -1, -1, -1, 45, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1 }
    };

    int[][] PRODUCTIONS = {
        { 22, 19, 17, 47, 18 },
        { 50, 52, 55, 57 },
        { 19, 49 },
        {  0 },
        { 16, 19, 49 },
        { 23, 19, 10, 20, 17, 51 },
        {  0 },
        { 19, 10, 20, 17, 51 },
        {  0 },
        { 24, 48,  9, 54, 17, 53 },
        {  0 },
        { 48,  9, 54, 17, 53 },
        {  0 },
        { 28 },
        { 25, 19, 56, 17, 47, 17, 55 },
        {  0 },
        {  0 },
        {  6, 48,  9, 28,  7 },
        { 26, 59, 58, 27 },
        {  0 },
        { 17, 59, 58 },
        { 19,  8, 67 },
        { 57 },
        {  0 },
        { 29, 19, 60 },
        {  0 },
        {  6, 67, 61,  7 },
        {  0 },
        { 16, 67, 61 },
        { 30, 67, 31, 59, 62 },
        {  0 },
        { 32, 59 },
        { 33, 67, 34, 59 },
        { 35, 59, 36, 67 },
        { 37,  6, 63, 64,  7 },
        { 19 },
        {  0 },
        { 16, 63, 64 },
        { 38,  6, 65, 66,  7 },
        { 21 },
        { 67 },
        {  0 },
        { 16, 65, 66 },
        { 44, 67, 45, 74, 27 },
        { 20, 76,  9, 59, 75 },
        { 16, 20, 76 },
        {  0 },
        {  0 },
        { 17, 74 },
        { 42, 19,  8, 67, 43, 67, 34, 59 },
        { 69, 68 },
        {  0 },
        { 10, 69 },
        { 12, 69 },
        { 11, 69 },
        { 14, 69 },
        { 13, 69 },
        { 15, 69 },
        {  2, 71, 70 },
        {  3, 71, 70 },
        { 71, 70 },
        {  2, 71, 70 },
        {  3, 71, 70 },
        { 39, 71, 70 },
        {  0 },
        { 73, 72 },
        {  0 },
        {  4, 73, 72 },
        {  5, 73, 72 },
        { 40, 73, 72 },
        { 20 },
        {  6, 67,  7 },
        { 41, 73 },
        { 63 }
    };

    String[] PARSER_ERROR = {
        "",
        "Era esperado fim de programa.txt",
        "Era esperado \"+\"",
        "Era esperado \"-\"",
        "Era esperado \"*\"",
        "Era esperado \"/\"",
        "Era esperado \"(\"",
        "Era esperado \")\"",
        "Era esperado \":=\"",
        "Era esperado \":\"",
        "Era esperado \"=\"",
        "Era esperado \">\"",
        "Era esperado \"<\"",
        "Era esperado \"<=\"",
        "Era esperado \">=\"",
        "Era esperado \"<>\"",
        "Era esperado \",\"",
        "Era esperado \";\"",
        "Era esperado \".\"",
        "Era esperado IDENT",
        "Era esperado INTEIRO",
        "Era esperado LITERAL",
        "Era esperado PROGRAM",
        "Era esperado CONST",
        "Era esperado VAR",
        "Era esperado PROCEDURE",
        "Era esperado BEGIN",
        "Era esperado END",
        "Era esperado INTEGER",
        "Era esperado CALL",
        "Era esperado IF",
        "Era esperado THEN",
        "Era esperado ELSE",
        "Era esperado WHILE",
        "Era esperado DO",
        "Era esperado REPEAT",
        "Era esperado UNTIL",
        "Era esperado READLN",
        "Era esperado WRITELN",
        "Era esperado OR",
        "Era esperado AND",
        "Era esperado NOT",
        "Era esperado FOR",
        "Era esperado TO",
        "Era esperado CASE",
        "Era esperado OF",
        "<PROGRAMA> inv�lido",
        "<BLOCO> inv�lido",
        "<LID> inv�lido",
        "<REPIDENT> inv�lido",
        "<DCLCONST> inv�lido",
        "<LDCONST> inv�lido",
        "<DCLVAR> inv�lido",
        "<LDVAR> inv�lido",
        "<TIPO> inv�lido",
        "<DCLPROC> inv�lido",
        "<DEFPAR> inv�lido",
        "<CORPO> inv�lido",
        "<REPCOMANDO> inv�lido",
        "<COMANDO> inv�lido",
        "<PARAMETROS> inv�lido",
        "<REPPAR> inv�lido",
        "<ELSEPARTE> inv�lido",
        "<VARIAVEL> inv�lido",
        "<REPVARIAVEL> inv�lido",
        "<ITEMSAIDA> inv�lido",
        "<REPITEM> inv�lido",
        "<EXPRESSAO> inv�lido",
        "<REPEXPSIMP> inv�lido",
        "<EXPSIMP> inv�lido",
        "<REPEXP> inv�lido",
        "<TERMO> inv�lido",
        "<REPTERMO> inv�lido",
        "<FATOR> inv�lido",
        "<CONDCASE> inv�lido",
        "<CONTCASE> inv�lido",
        "<RPINTEIRO> inv�lido"
    };
}
