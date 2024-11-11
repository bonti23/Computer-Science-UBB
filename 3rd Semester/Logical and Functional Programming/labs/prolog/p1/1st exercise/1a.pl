apartine_lista(_, []) :- false, !.
apartine_lista(X, [X|_]) :- !.
apartine_lista(X, [_|T]) :- apartine_lista(X, T).

diferenta([], _, []):-!.
diferenta([H|T], L2, R):- apartine_lista(H, L2), !,
                           diferenta(T, L2, R).
diferenta([H|T], L2, R):- diferenta(T, L2, R1),
                          R=[H|R1].
