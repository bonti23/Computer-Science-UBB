#include <iostream>
#include <fstream>
#include <sstream>
#include <vector>
#include <map>
#include <iomanip>
#include "Atom.h"
#include "TS.h"

using namespace std;

struct FIPEntry {
    int codAtom;
    int pozTS;
    string explicatie;
};

int main() {
    string fisier = "program.txt";
    ifstream fin(fisier);
    if (!fin.is_open()) {
        cerr << "Eroare la deschiderea fișierului!\n";
        return 1;
    }

    vector<string> listaID, listaCONST, listaOP, listaSEP, listaKW;
    TS tabela;
    vector<FIPEntry> FIP;
    bool eroare = false;

    map<string, int> codAtom = {
    {"ID", 0}, {"CONST", 1},

    // cuvinte cheie / directive
    {"#include", 2}, {"<iostream>", 3},
    {"using", 4}, {"namespace", 5}, {"std", 6},
    {"int", 7}, {"main", 8},
    {"cin", 9}, {"cout", 10},

    // operatori
    {">>", 11}, {"<<", 12},
    {"+", 13}, {"-", 14}, {"*", 15}, {"/", 16},
    {"=", 17}, {"<", 18}, {">", 19}, {"<=", 20}, {">=", 21},
    {"==", 22}, {"!=", 23},

    // separatori
    {";", 24}, {"(", 25}, {")", 26},
    {"{", 27}, {"}", 28}
    };


    string linie;
    while (getline(fin, linie)) {
        stringstream ss(linie);
        string e;
        while (ss >> e) {
            Atom atom(e);
            if (atom.is_cuv_cheie()) {
                listaKW.push_back(e);
                FIP.push_back({ codAtom[e], -1, e });
            }
            else if (atom.is_operator()) {
                listaOP.push_back(e);
                FIP.push_back({ codAtom[e], -1, e });
            }
            else if (atom.is_separator()) {
                listaSEP.push_back(e);
                FIP.push_back({ codAtom[e], -1, e });
            }
            else if (atom.is_const()) {
                listaCONST.push_back(e);
                int poz = tabela.add(e);
                FIP.push_back({ 1, poz, e }); // 1 = CONST
            }
            else if (atom.is_id()) {
                listaID.push_back(e);
                int poz = tabela.add(e);
                FIP.push_back({ 0, poz, e }); // 0 = ID
            }
            else {
                cout << "Eroare lexicala: " << e << "\n";
                eroare = true;
            }

        }
    }
    fin.close();

    if (eroare) return 1;

    cout << "\nFIP\n";
    cout << left << setw(9) << "CodAtom"
        << "| " << setw(10) << "PozitieTS"
        << "| " << "Explicatie" << "\n";
    for (auto& f : FIP) {
        string pozText = (f.pozTS == -1) ? "-" : to_string(f.pozTS);
        cout << left << setw(9) << f.codAtom
            << "| " << setw(10) << pozText
            << "| " << f.explicatie << "\n";
    }


    cout << "\nTS\n";
    tabela.afisareTabela();


    ofstream foutFIP("FIP.txt"), foutTS("TS.txt");
    foutFIP << "CodAtom PozitieTS\n";
    for (auto& f : FIP)
        foutFIP << f.codAtom << " " << f.pozTS << "\n";
    tabela.scrieInFisier(foutTS);
    foutTS.close();

    return 0;
}
