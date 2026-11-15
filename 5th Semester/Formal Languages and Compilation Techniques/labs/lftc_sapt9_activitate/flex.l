%option noyywrap

%{
#include "parser.tab.h"
#include <stdio.h>
#include <stdlib.h>

extern int yylval;
%}

DIGIT   [0-9]+

%%

"("         { return LPAR; }
")"         { return RPAR; }
"+"         { return PLUS; }
{DIGIT}     { yylval = atoi(yytext); return NUMBER; }
[ \t\n]+    { /* skip whitespace */ }
.           { printf("Caractere invalide: %s\n", yytext); return -1; }

%%
