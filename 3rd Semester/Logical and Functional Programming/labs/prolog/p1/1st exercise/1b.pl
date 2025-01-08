%adauga_par(l1...ln)={[], if l1...ln is vida
%                    {l1 U 1 U adauga_par(l2...ln), if l1 e par
%                    {l1 U adauga_par(l2...ln), altfel

adauga_par([], []):-!.
adauga_par([H|T], [H, 1|R]):-H mod 2 =:= 0,
                            !,
                            adauga_par(T, R).
adauga_par([H|T], [H|R]):- adauga_par(T, R).
