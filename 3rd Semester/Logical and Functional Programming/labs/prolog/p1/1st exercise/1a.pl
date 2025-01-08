%apartine_lista(X, l1...ln)={false, if l1...ln e vida
%                           {true, if l1=X
%                           {apartine_lista(l2...ln), altfel

apartine_lista(_, []):-false,
    !.
apartine_lista(X, [X|_]):-!.
apartine_lista(X, [_|T]):-apartine_lista(X, T).

% diferenta(l1...ln, m1...mn)={diferenta(l2...ln, m1...mn), if
% apartine_lista(l1, m1...mn)
%                             {l1 U diferenta(l2...ln, m1...mn), altfel

diferenta([], _, []):-!.
diferenta([H|T], L2, R):-apartine_lista(H, L2), !,
                         diferenta(T, L2, R).
diferenta([H|T], L2, [H|R]):-diferenta(T, L2, R).
