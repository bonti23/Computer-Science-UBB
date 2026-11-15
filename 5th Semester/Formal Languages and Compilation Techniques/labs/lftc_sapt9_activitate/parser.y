%{
#include <stdio.h>
#include <stdlib.h>

void yyerror(const char *s);
int yylex(void);
%}

%token NUMBER
%token LPAR RPAR PLUS
%left PLUS

%start expr

%%

expr:
      operand
    | LPAR expr RPAR
    | expr PLUS expr
;

operand:
      NUMBER
;

%%

void yyerror(const char *s) {
    fprintf(stderr, "Eroare sintactică: %s\n", s);
}

int main() {
    printf("Introduceti expresia:\n");
    int ret = yyparse();
    if (ret == 0) {
        printf("se inchid corect\n");
    } else {
        printf("NU se inchid corect\n");
    }
    return ret;
}
