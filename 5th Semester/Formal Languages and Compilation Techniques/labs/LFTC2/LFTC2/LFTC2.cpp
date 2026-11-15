#include <vector>
#include <iostream>
#include <fstream>
#include <iomanip>
#include <algorithm>
#include <cctype>
#include <string>

using namespace std;

bool isKeyword(const string& s) {
    static const vector<string> keywords = {
        "int", "double", "if", "else", "while", "cin", "cout",
        "return", "struct", "using", "namespace", "main"
    };
    return find(keywords.begin(), keywords.end(), s) != keywords.end();
}

bool isOperator(const string& s) {
    static const vector<string> ops = {
        "+", "-", "*", "/", "=", "==", "!=", "<", ">", "<=", ">=", ">>", "<<"
    };
    return find(ops.begin(), ops.end(), s) != ops.end();
}

bool isSeparator(const string& s) {
    static const vector<string> seps = {
        "(", ")", "{", "}", ";", ","
    };
    return find(seps.begin(), seps.end(), s) != seps.end();
}

bool isNumber(const string& s) {
    bool dot = false;
    if (s.empty()) return false;
    for (char c : s) {
        if (!isdigit(c)) {
            if (c == '.' && !dot) dot = true;
            else return false;
        }
    }
    return true;
}

bool isIdentifier(const string& s) {
    if (s.empty() || !isalpha(s[0])) return false;
    if (s.size() > 20) return false;
    for (char c : s)
        if (!isalnum(c)) return false;
    return true;
}


bool isPreprocessorDirective(const string& s) {
    return !s.empty() && s[0] == '#';
}

// --- clasifica un token ---
string getTokenType(const string& token) {
    if (isPreprocessorDirective(token)) return "preprocessor";
    if (isKeyword(token)) return "keyword";
    if (isOperator(token)) return "operator";
    if (isSeparator(token)) return "separator";
    if (isNumber(token)) return "const";
    if (isIdentifier(token)) return "id";
    return "eroare lex";
}

int main() {
    ifstream fin("program.txt");
    if (!fin.is_open()) {
        cerr << "Eroare: nu s-a putut deschide fisierul 'program.txt'!\n";
        return 1;
    }

    vector<pair<string, string>> atoms;
    string line;
    string delimiters = "();{}<>=+-*/,'";

    while (getline(fin, line)) {
        if (!line.empty() && line.back() == '\r') // windows CRLF
            line.pop_back();

        string token = "";

        for (size_t i = 0; i < line.size(); i++) {
            char c = line[i];

            // spatiu=> finalizeaza tokenul
            if (isspace(static_cast<unsigned char>(c))) {
                if (!token.empty()) {
                    atoms.push_back({ token, getTokenType(token) });
                    token.clear();
                }
            }

            // string
            else if (c == '"') {
                token = "\"";
                i++;
                while (i < line.size() && line[i] != '"') {
                    token += line[i];
                    i++;
                }
                if (i < line.size()) token += '"';
                atoms.push_back({ token, "string" });
                token.clear();
            }

            // char literal
            else if (c == '\'') {
                token = "\'";
                i++;
                while (i < line.size() && line[i] != '\'') {
                    token += line[i];
                    i++;
                }
                if (i < line.size()) token += '\'';
                atoms.push_back({ token, "char" });
                token.clear();
            }

            // delimitator/operator
            else if (delimiters.find(c) != string::npos) {
                if (!token.empty()) {
                    atoms.push_back({ token, getTokenType(token) });
                    token.clear();
                }

                string op(1, c);
                if (i + 1 < line.size()) {
                    string combined = op + line[i + 1];
                    if (isOperator(combined)) {
                        op = combined;
                        i++;
                    }
                }
                atoms.push_back({ op, getTokenType(op) });
            }
            else {
                token += c;
            }
        }

        if (!token.empty()) {
            atoms.push_back({ token, getTokenType(token) });
            token.clear();
        }
    }

    fin.close();

    cout << "=== Atomi identificati ===\n";
    for (auto& p : atoms)
        cout << setw(12) << left << p.first << " -> " << p.second << "\n";

    return 0;
}
