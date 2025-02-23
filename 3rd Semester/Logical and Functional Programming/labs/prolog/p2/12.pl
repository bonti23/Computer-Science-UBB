% a)
% inlocuire(L, E, V, R)
% L - lista în care înlocuim
% E - elementul pe care îl înlocuim
% V - valoarea cu care înlocuim
% R - rezultat
% (i, i, o)

inlocuire([], _, _, []).
inlocuire([H|T], E, V, [V|R]) :-
    H =:= E,  % daca l1=E
    !,
    inlocuire(T, E, V, R).
inlocuire([H|T], E, V, [H|R]) :-
    H \= E,  % il pastram
    !,
    inlocuire(T, E, V, R).  % continuare

% b)
% cautareMaxim(L, M, R)
% L - lista în care căutăm
% M - elementul maxim curent
% R - rezultat
% (i, i, o)

cautareMaxim2([], M, M).
cautareMaxim2([H|T], M, R) :-
    not(is_list(H)),  % daca e nr
    H > M,  %  >M
    !,
    cautareMaxim2(T, H, R).  % actualizam maximul
cautareMaxim2([_|T], M, R) :-  % ignoram listele
    cautareMaxim2(T, M, R).  % Continuam fara a schimba maximul

% inlocuire2Lista(L, R).
% L - lista în care înlocuim
% R - rezultat
% (i, o)

% Funcția auxiliară care înlocuiește aparițiile maximului în subliste
inlocuire2ListaAux([], _, []).
inlocuire2ListaAux([H|T], M, [H1|R]) :-
    is_list(H),  % verificam daca e nr
    !,
    cautareMaxim2(H, 0, E),  % cautam max in sublista
    inlocuire(H, M, E, H1),  % il inlocuim
    inlocuire2ListaAux(T, M, R).  % continuam
inlocuire2ListaAux([H|T], M, [H|R]) :-
    not(is_list(H)),  % daca nu e lista
    !,
    inlocuire2ListaAux(T, M, R).  % continuam

% Funcția principală care caută maximul global și apelează funcția auxiliară
inlocuire2Lista(L, R) :-
    cautareMaxim2(L, 0, M),  % cautam max global in lista
    inlocuire2ListaAux(L, M, R).  % functia auxiliara

% Exemplu de testare
% ?- inlocuire2Lista([1, [2, 5, 7], 4, 5, [1, 4], 3, [1, 3, 5, 8, 5, 4], 5, [5, 9, 1], 2], R).
% R = [1, [2, 7, 7], 4, 5, [1, 4], 3, [1, 3, 8, 8, 8, 4], 5, [9, 9, 1], 2].
