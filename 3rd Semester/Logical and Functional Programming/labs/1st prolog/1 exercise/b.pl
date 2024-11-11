adauga_par([],[]).
adauga_par([H|T], [H,1|R]):-
    H mod 2 =:= 0,
    !,
    adauga_par(T, R).
adauga_par([H|T], [H|R]):-adauga_par(T, R).
