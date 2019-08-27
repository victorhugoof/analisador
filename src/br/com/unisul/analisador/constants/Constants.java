package br.com.unisul.analisador.constants;

public interface Constants extends ScannerConstants, ParserConstants {
    int EPSILON  = 0;
    int DOLLAR   = 1;

    int t_TOKEN_2 = 2; //"+"
    int t_TOKEN_3 = 3; //"-"
    int t_TOKEN_4 = 4; //"*"
    int t_TOKEN_5 = 5; //"/"
    int t_TOKEN_6 = 6; //"("
    int t_TOKEN_7 = 7; //")"
    int t_TOKEN_8 = 8; //":="
    int t_TOKEN_9 = 9; //":"
    int t_TOKEN_10 = 10; //"="
    int t_TOKEN_11 = 11; //">"
    int t_TOKEN_12 = 12; //"<"
    int t_TOKEN_13 = 13; //"<="
    int t_TOKEN_14 = 14; //">="
    int t_TOKEN_15 = 15; //"<>"
    int t_TOKEN_16 = 16; //","
    int t_TOKEN_17 = 17; //";"
    int t_TOKEN_18 = 18; //"."
    int t_IDENT = 19;
    int t_INTEIRO = 20;
    int t_LITERAL = 21;
    int t_PROGRAM = 22;
    int t_CONST = 23;
    int t_VAR = 24;
    int t_PROCEDURE = 25;
    int t_BEGIN = 26;
    int t_END = 27;
    int t_INTEGER = 28;
    int t_CALL = 29;
    int t_IF = 30;
    int t_THEN = 31;
    int t_ELSE = 32;
    int t_WHILE = 33;
    int t_DO = 34;
    int t_REPEAT = 35;
    int t_UNTIL = 36;
    int t_READLN = 37;
    int t_WRITELN = 38;
    int t_OR = 39;
    int t_AND = 40;
    int t_NOT = 41;
    int t_FOR = 42;
    int t_TO = 43;
    int t_CASE = 44;
    int t_OF = 45;

}
