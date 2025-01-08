%cmmdc(a, b)={a, if a=b
%            {cmmdc(a, b-a), if a<b
%            {cmmdc(a-b, b), if a>b

cmmdc(A, A, A).
cmmdc(A, B, C):- A<B,
                 B1 is B-A,
                 cmmdc(A, B1, C).
cmmdc(A, B, C):-A>B,
                 A1 is A-B,
                 cmmdc(A1, B, C).

%cmmmc(a, b)= a*b/cmmdc(a, b)

cmmmc(A, B, D):-cmmdc(A, B, C),
                D is A*B//C.

%lista(l1...ln)={0, if l is vida
%               {l1, if n=1
%               {cmmmc(l1, l2) U lista(l3...ln)
lista([], 0):-!.
lista([H], H).
lista([H1, H2|T], C):- cmmmc(H1, H2, R),
                       lista([R|T], C).

