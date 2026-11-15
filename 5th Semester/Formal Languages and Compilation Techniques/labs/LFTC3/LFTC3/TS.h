#pragma once
#include <string>
#include <iostream>
#include <iomanip>
#include <vector>

using namespace std;

class TS {
    static const int SIZE = 101;
    vector<pair<string, int>> table[SIZE];
    int nextCode = 1;

    int hashFunc(const string& s) const {
        unsigned int h = 0;
        for (char c : s)
            h = h * 31 + c; 
        return h % SIZE;
    }

public:
    int add(const string& simbol) {
        int idx = hashFunc(simbol);
        for (auto& p : table[idx])
            if (p.first == simbol)
                return p.second;

        table[idx].push_back({ simbol, nextCode });
        return nextCode++;
    }

    int getCodBySimbol(const string& simbol) const {
        int idx = hashFunc(simbol);
        for (auto& p : table[idx])
            if (p.first == simbol)
                return p.second;
        return -1;
    }

    void afisareTabela() const {
        cout << "Poz | Simbol\n";
        cout << "--------------\n";
        for (int i = 0; i < SIZE; ++i)
            for (auto& p : table[i])
                cout << setw(3) << p.second << " | " << p.first << "\n";
    }
    void scrieInFisier(ofstream& fout) const {
        fout << "Poz Simbol\n";
        for (int i = 0; i < SIZE; ++i) {
            for (auto& p : table[i]) {
                fout << setw(3) << p.second << " " << p.first << "\n";
            }
        }
    }
};
