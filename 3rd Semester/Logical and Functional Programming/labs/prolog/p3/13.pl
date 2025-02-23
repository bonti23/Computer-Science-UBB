
% colorare_harta(+Tari, +Vecini, +Culori, -Colorare)
% Tari - lista tari
% Vecini - lista perechi tari vecine
% Culori - lista culori
% Colorare - rezultat => lista tara-culoare

colorare_harta(Tari, Vecini, Culori, Colorare) :-
    colorare(Tari, Culori, Colorare),
    verifica_vecini(Colorare, Vecini).

% colorare(+Tari, +Culori, -Colorare)
% Tari - lista tari
% Culori - lista culori
% Colorare - lista de asocieri tara-culoare

colorare([], _, []).
colorare([Tara|Tari], Culori, [Tara-Culoare|Colorare]) :-
    member(Culoare, Culori),
    colorare(Tari, Culori, Colorare).

% verifica_vecini(+Colorare, +Vecini)
% Colorare - lista de asocieri tara-culoare
% Vecini - lista perechi tari vecine

verifica_vecini(_, []).
verifica_vecini(Colorare, [Tara1-Tara2|RestVecini]) :-
    member(Tara1-Culoare1, Colorare),
    member(Tara2-Culoare2, Colorare),
    Culoare1 \= Culoare2,
    verifica_vecini(Colorare, RestVecini).

% colorare_harta([a,b,c,d,e],[(a-b),(a-c),(b-c),(b-d),(c-d),(c-e),(d-e)],[rosu,albstru,galben],
% Colorare).
