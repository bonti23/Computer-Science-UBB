%a. Sa se scrie un predicat care determina cel mai mic multiplu comun
% al elementelor unei liste formate din numere intregi.
%


%cmmdc(A:integer, B:integer, C: integer)// returneaza in C cel mai mare
% divizor comun al nr A si B
% A - primul nr, B - al doilea nr, C - cmmdc intre A si B
% (i,i,o)
% cmmdc(A,B) = { 0, if A=B=0,
%                  A, if (A=N && B=0)
%                  B, if (A=0 && B=N)
%                  A, if (A!=0 && B!=0 && A==B)
%                  cmmdc(A-B,B), if A>B && B>0
%                  cmmdc(A,B-A), if B>A && A>0
%                  }

cmmdc(0,0,0).
cmmdc(0,N,N).
cmmdc(N,0,N).
cmmdc(E,E,E).

cmmdc(A,B,C):- B>0,
    A>B,
    A1 is A-B,
    cmmdc(A1,B,C).
cmmdc(A,B,C):- A>0,
    A<B,
    B1 is B-A,
    cmmdc(A,B1,C).


% cmmmc(A:integer, B:integer, C: integer) // returneaza in C cel mai mic
% multiplu comun al nr A si B
% A - primul nr, B - al doilea nr, C - cmmmc intre A si B
% (i,i,o)
% % cmmmc(A,B) = { 0, if A=B=0 || (A=0 && B!=0) || (A!=0 && B=0)
%                   N, if (A=B=N)
%                   A*B/cmmdc(A,B), if A!=B && A!=0 &&
%                   B!=0 }


cmmmc(0,0,0).
cmmmc(0,N,0):- N\=0.
cmmmc(N,0,0):- N\=0.
cmmmc(N,N,N):- N\=0.

cmmmc(A,B,C):- A\=B, A\=0, B\=0,
    Produs is A*B,
    cmmdc(A,B,C1),
    C is Produs/C1.

%general(C: integer, L: list) // C este cmmmc al elementelor din L
%C - nr in care se va returna cmmmc pentru toate el din L
%L - lista cu numere
%(o,i)
% general(l1...ln) = { -1, if vida(L)
%                        l1, if L = l1
%                        general ([cmmmc(l1,l2) U l3...ln])
%                        , altfel
%                        }

general(_C,[]).
general(C,[C]).
general(C,[H1,H2|T]):-
    cmmmc(H1,H2,C1),
    general(C,[C1|T]).

/*Cazuri de testare:
 * general(C,[]). % TRUE
 * general(C,[5]). % C=5
 * general(C,[-4]). % C=-4
 * general(C,[1,2,3,4]). % C=12
 * general(C,[7,7,7]). % C=7
 * general(C,[7,2,3]). % C=42
 * general(C,[7,2,-3]). % FALSE, pt ca cmmdc se face doar intre nr
 * pozitive

    */
