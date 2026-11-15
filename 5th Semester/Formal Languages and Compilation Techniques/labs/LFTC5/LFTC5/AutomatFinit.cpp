#include "AutomatFinit.h"
#include <fstream>
#include <sstream>
using namespace std;

void AutomatFinit::addTransition(const string& from, const string& symbol, const string& to) {
    transitions[{from, symbol}].push_back(to);
}

void AutomatFinit::loadFromFile(const string& filePath) {
    ifstream file(filePath);
    if (!file.is_open()) {
        cerr << "Error: cannot open file " << filePath << "\n";
        return;
    }

    string line;
    while (getline(file, line)) {
        if (line.empty()) continue;
        istringstream iss(line);
        string token, rest;
        iss >> token;
        if (token == "state:") {
            iss >> rest;
            states.insert(rest);
        }
        else if (token == "symbol:") {
            iss >> rest;
            alphabet.insert(rest);
        }
        else if (token == "transition:") {
            string data;
            iss >> data;
            size_t comma = data.find(',');
            size_t arrow = data.find("=>");
            string first = data.substr(0, comma);
            string symbol = data.substr(comma + 1, arrow - comma - 1);
            string second = data.substr(arrow + 2);
            addTransition(first, symbol, second);
        }
        else if (token == "initial:") {
            iss >> initialState;
        }
        else if (token == "final:") {
            iss >> rest;
            finalStates.insert(rest);
        }
    }

    file.close();
}

void AutomatFinit::loadFromKeyboard() {
    int numStates, numSymbols, numTransitions, numFinals;
    cout << "Enter number of states: ";
    cin >> numStates;
    cout << "Enter states:\n";
    for (int i = 0; i < numStates; ++i) {
        string s;
        cin >> s;
        states.insert(s);
    }

    cout << "Enter number of symbols: ";
    cin >> numSymbols;
    cout << "Enter symbols:\n";
    for (int i = 0; i < numSymbols; ++i) {
        string sym;
        cin >> sym;
        alphabet.insert(sym);
    }

    cout << "Enter initial state: ";
    cin >> initialState;

    cout << "Enter number of final states: ";
    cin >> numFinals;
    cout << "Enter final states:\n";
    for (int i = 0; i < numFinals; ++i) {
        string f;
        cin >> f;
        finalStates.insert(f);
    }

    cout << "Enter number of transitions: ";
    cin >> numTransitions;
    cout << "Enter transitions (format: from symbol to):\n";
    for (int i = 0; i < numTransitions; ++i) {
        string from, symbol, to;
        cin >> from >> symbol >> to;
        addTransition(from, symbol, to);
    }
}

void AutomatFinit::displayElements() const {
    cout << "States: ";
    for (const auto& s : states) cout << s << " ";
    cout << "\nAlphabet: ";
    for (const auto& a : alphabet) cout << a << " ";
    cout << "\nInitial State: " << initialState << "\nFinal States: ";
    for (const auto& f : finalStates) cout << f << " ";
    cout << "\nTransitions:\n";
    for (const auto& t : transitions) {
        cout << t.first.first << " --" << t.first.second << "--> ";
        for (const auto& dest : t.second)
            cout << dest << " ";
        cout << "\n";
    }
    cout << endl;
}

bool AutomatFinit::isSequenceAccepted(const string& sequence) const {
    string current = initialState;
    for (char c : sequence) {
        string symbol(1, c);
        auto it = transitions.find({ current, symbol });
        if (it != transitions.end()) {
            current = it->second[0];
        }
        else {
            return false;
        }
    }
    return finalStates.count(current) > 0;
}

string AutomatFinit::longestAcceptedPrefix(const string& sequence) const {
    string longest = "";
    string currentPrefix = "";
    string current = initialState;

    for (char c : sequence) {
        string symbol(1, c);
        auto it = transitions.find({ current, symbol });
        if (it != transitions.end()) {
            current = it->second[0];
            currentPrefix += c;
            if (finalStates.count(current)) {
                longest = currentPrefix;
            }
        }
        else break;
    }
    return longest;
}