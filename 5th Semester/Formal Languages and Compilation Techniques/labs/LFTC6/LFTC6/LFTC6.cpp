#include <iostream>
#include <fstream>
#include <sstream>
#include <vector>
#include <string>
#include <map>
#include <algorithm>

#include "AF.h"
#include "Atom.h"
#include "TS.h"

using namespace std;

// Funcție auxiliară pentru a scrie listele în fișier
static void writeListToFile(ofstream& out, const string& name, const vector<string>& v) {
    out << name << ":\n";
    for (const auto& s : v) {
        out << s << "\n";
    }
    out << "\n";
}

int main() {
    ios::sync_with_stdio(false);
    cin.tie(nullptr);

    // === CONFIGURARE ===
    const string numeFisier =
        R"(program_3.txt)";

    // === CREARE AUTOMATE FINITE ===
    AF AF_Constante_Intregi;
    AF_Constante_Intregi.citireFisierAF(
        R"(AF_CT_INT.txt)");

    AF AF_Constante_Reale;
    AF_Constante_Reale.citireFisierAF(
        R"(AF_CT_RL.txt)");

    AF AF_Identificatori;
    AF_Identificatori.citireFisierAF(
        R"(AF_IDENTIFICATORI.txt)");

    // === COLECȚII DE TOKENI ===
    vector<string> IDs, CONSTs, OPERATORI, SEPARATORI, CUVINTE_CHEIE;

    const vector<string> OPERATORI_LIST{
        "<", ">", "<=", ">=", "+", "-", "/", "*", "%", ">>", "<<", "==", "!=", "=", ";"
    };

    TS TS_IDENDTIFIERS;
    TS TS_CONSTANTS;

    int eroare = 0;

    // === FUNCȚIE: verifică dacă un string începe cu un operator ===
    auto startsWithAny = [&](const string& s, const vector<string>& ops, string& matched) -> bool {
        vector<string> sorted = ops;
        sort(sorted.begin(), sorted.end(), [](const string& a, const string& b) {
            return a.size() > b.size();
            });
        for (const auto& op : sorted) {
            if (s.rfind(op, 0) == 0) {
                matched = op;
                return true;
            }
        }
        return false;
        };

    // === FUNCȚIE: procesează un token (similar cu Java) ===
    auto processToken = [&](string token) -> bool {
        while (!token.empty()) {
            Atom atom(token);

            if (atom.isCUVANT_CHEIE()) {
                CUVINTE_CHEIE.push_back(token);
                token.clear();
                continue;
            }

            if (atom.isOPERATOR()) {
                OPERATORI.push_back(token);
                token.clear();
                continue;
            }

            {
                string matched;
                if (startsWithAny(token, OPERATORI_LIST, matched)) {
                    OPERATORI.push_back(matched);
                    token.erase(0, matched.size());
                    continue;
                }
            }

            if (atom.isSEPARATOR()) {
                SEPARATORI.push_back(token);
                token.clear();
                continue;
            }

            if (AF_Constante_Intregi.verifica_secventa(token) || AF_Constante_Reale.verifica_secventa(token)) {
                CONSTs.push_back(token);
                TS_CONSTANTS.add(token);
                token.clear();
                continue;
            }

            if (AF_Identificatori.verifica_secventa(token)) {
                IDs.push_back(token);
                TS_IDENDTIFIERS.add(token);
                token.clear();
                continue;
            }

            {
                auto pInt = AF_Constante_Intregi.celMaiLungPrefix(token);
                auto pReal = AF_Constante_Reale.celMaiLungPrefix(token);
                if (!pInt.empty() || !pReal.empty()) {
                    if (!pReal.empty()) {
                        CONSTs.push_back(pReal);
                        TS_CONSTANTS.add(pReal);
                        token.erase(0, pReal.size());
                        continue;
                    }
                    if (!pInt.empty()) {
                        CONSTs.push_back(pInt);
                        TS_CONSTANTS.add(pInt);
                        token.erase(0, pInt.size());
                        continue;
                    }
                }
            }

            {
                auto pid = AF_Identificatori.celMaiLungPrefix(token);
                if (!pid.empty()) {
                    IDs.push_back(pid);
                    TS_IDENDTIFIERS.add(pid);
                    token.erase(0, pid.size());
                    continue;
                }
            }

            cout << "Eroare! -" << token << "-\n";
            return false;
        }
        return true;
        };

    // === CITIRE FIȘIER INTRARE ===
    ifstream fin(numeFisier);
    if (!fin) {
        cerr << "Eroare: nu se poate deschide fișierul sursă.\n";
        return 1;
    }

    string linie;
    while (getline(fin, linie) && eroare == 0) {
        string token;
        for (char c : linie) {
            if (isspace(static_cast<unsigned char>(c))) {
                if (!token.empty()) {
                    if (!processToken(token)) { eroare = 1; break; }
                    token.clear();
                }
            }
            else {
                token += c;
            }
        }
        if (eroare) break;
        if (!token.empty()) {
            if (!processToken(token)) { eroare = 1; break; }
        }
    }

    // === SCRIERE OUTPUT ===
    ofstream out("output.txt");
    if (!out) {
        cerr << "Eroare la scrierea în fișierul de ieșire.\n";
    }
    else {
        writeListToFile(out, "IDs", IDs);
        writeListToFile(out, "CONSTs", CONSTs);
        writeListToFile(out, "OPERATORI", OPERATORI);
        writeListToFile(out, "SEPARATORI", SEPARATORI);
        writeListToFile(out, "CUVINTE_CHEIE", CUVINTE_CHEIE);
    }

    // === AFIȘARE TABEL SIMBOLURI ===
    cout << "   TS IDENTIFIERS\n";
    cout << "-------------------\n";
    TS_IDENDTIFIERS.afisare_tabela();
    cout << "\n\n";

    cout << "   TS CONSTANTS\n";
    cout << "-------------------\n";
    TS_CONSTANTS.afisare_tabela();
    cout << "\n\n";

    // === AFIȘARE FIP ===
    cout << "         FIP\n";
    cout << "---------------------------\n";
    cout << " COD_ATOM | COD_TS\n";
    cout << "---------------------------\n";

    map<int, pair<string, string>> cod_FIP;
    int cod = 2;

    fin.clear();
    fin.seekg(0);
    int ok = 1;

    while (ok && getline(fin, linie)) {
        stringstream ss(linie);
        string e;
        while (ok && ss >> e) {
            Atom atom(e);
            if (e.empty()) continue;

            if (atom.isCONST() && !atom.isCUVANT_CHEIE()) {
                cout << "0 | " << TS_CONSTANTS.getCodBySimbol(e) << "\n";
            }
            else if (atom.isID() && !atom.isCUVANT_CHEIE()) {
                cout << "1 | " << TS_IDENDTIFIERS.getCodBySimbol(e) << "\n";
            }
            else if (atom.isSEPARATOR() || atom.isCUVANT_CHEIE() || atom.isOPERATOR()) {
                bool exista = false;
                for (const auto& kv : cod_FIP) {
                    if (kv.second.first == e) {
                        cout << kv.first << " | " << kv.second.second << "\n";
                        exista = true;
                        break;
                    }
                }
                if (!exista) {
                    cod_FIP[cod] = { e, "-" };
                    cout << cod << " | -\n";
                    ++cod;
                }
            }
            else {
                ok = 0;
                break;
            }
        }
    }

    return 0;
}
