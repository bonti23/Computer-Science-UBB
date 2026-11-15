#include <iostream>
#include <fstream>
#include <vector>
#include <string>
#include <map>
#include <set>

using namespace std;

class AutomatFinit {
public:
    set<string> states;
    set<char> alphabet;
    map<pair<string, char>, string> transitions;
    string startState;
    set<string> finalStates;

    void citireFisier(const string& filename) {
        ifstream file(filename);
        if (!file.is_open()) {
            cerr << "Eroare la deschiderea fisierului: " << filename << endl;
            exit(1);
        }

        int nrStates, nrFinalStates, nrTransitions;
        string state;

        file >> nrStates;
        for (int i = 0; i < nrStates; ++i) {
            file >> state;
            states.insert(state);
        }

        int alphabetSize;
        file >> alphabetSize;
        for (int i = 0; i < alphabetSize; ++i) {
            char symbol;
            file >> symbol;
            alphabet.insert(symbol);
        }

        file >> nrTransitions;
        for (int i = 0; i < nrTransitions; ++i) {
            string currentState, nextState;
            char symbol;
            file >> currentState >> symbol >> nextState;

            if (transitions.find({ currentState, symbol }) != transitions.end()) {
                cerr << "Error: Automatul nu este determinist!\n";
                cerr << "Tranzitie nedeterminista detectata la starea " << currentState
                    << " cu simbolul " << symbol << "." << endl;
                file.close();
                exit(1);
            }

            transitions[{currentState, symbol}] = nextState;
        }

        file >> startState;

        file >> nrFinalStates;
        for (int i = 0; i < nrFinalStates; ++i) {
            file >> state;
            finalStates.insert(state);
        }

        file.close();
    }

    void afisareStari() {
        cout << "Starile sunt: ";
        for (const auto& state : states) cout << state << " ";
        cout << endl;
    }

    void afisareAlfabet() {
        cout << "Alfabetul este: ";
        for (const auto& symbol : alphabet) cout << symbol << " ";
        cout << endl;
    }

    void afisareTranzitii() {
        cout << "Tranzitiile sunt:" << endl;
        for (const auto& transition : transitions)
            cout << "(" << transition.first.first << ", " << transition.first.second << ") -> " << transition.second << endl;
    }

    void afisareStariFinale() {
        cout << "Starile finale sunt: ";
        for (const auto& state : finalStates) cout << state << " ";
        cout << endl;
    }

    bool verificaSecventa(const string& secventa) {
        string currentState = startState;
        cout << "Drum: " << currentState;
        for (const auto& symbol : secventa) {
            if (transitions.find({ currentState, symbol }) == transitions.end()) {
                cout << "\nNu exista tranzitie din " << currentState << " cu simbolul '" << symbol << "'." << endl;
                return false;
            }
            currentState = transitions[{currentState, symbol}];
            cout << " -> " << currentState;
        }
        cout << endl;
        return finalStates.find(currentState) != finalStates.end();
    }

    string celMaiLungPrefixAcceptat(const string& secventa) {
        string currentState = startState;
        string longestPrefix = "", currentPrefix = "";
        for (const auto& symbol : secventa) {
            if (transitions.find({ currentState, symbol }) == transitions.end()) break;
            currentState = transitions[{currentState, symbol}];
            currentPrefix += symbol;
            if (finalStates.find(currentState) != finalStates.end()) longestPrefix = currentPrefix;
        }
        return longestPrefix;
    }
};

void afisareMeniu() {
    cout << "\n=== MENIU ===\n";
    cout << "1. Afisare multimea starilor\n";
    cout << "2. Afisare alfabetul\n";
    cout << "3. Afisare tranzitiile\n";
    cout << "4. Afisare multimea starilor finale\n";
    cout << "5. Verifica secventa\n";
    cout << "6. Cel mai lung prefix acceptat\n";
    cout << "0. Iesire\n";
}

int main() {
    AutomatFinit af;
    string filename = "C:\\Users\\alexa\\source\\repos\\LFTC4\\LFTC4\\clasa.txt";
    af.citireFisier(filename);

    int optiune;
    do {
        afisareMeniu();
        cout << "Selectati o optiune: ";
        cin >> optiune;

        switch (optiune) {
        case 1: af.afisareStari(); break;
        case 2: af.afisareAlfabet(); break;
        case 3: af.afisareTranzitii(); break;
        case 4: af.afisareStariFinale(); break;
        case 5: {
            string secventa;
            cout << "Introduceti secventa de verificat: ";
            cin >> secventa;
            bool acceptata = af.verificaSecventa(secventa);
            if (acceptata) cout << "Secventa este acceptata.\n";
            else cout << "Secventa nu este acceptata.\n";
            break;
        }
        case 6: {
            string secventa;
            cout << "Introduceti secventa: ";
            cin >> secventa;
            string prefix = af.celMaiLungPrefixAcceptat(secventa);
            cout << "Cel mai lung prefix acceptat: " << prefix << endl;
            break;
        }
        }
    } while (optiune != 0);

    return 0;
}
