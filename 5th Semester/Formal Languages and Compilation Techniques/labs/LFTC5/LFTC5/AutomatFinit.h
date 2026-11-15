#ifndef AUTOMATFINIT_H
#define AUTOMATFINIT_H

#include <string>
#include <set>
#include <map>
#include <vector>
#include <iostream>
using namespace std;

class AutomatFinit {
private:
    set<string> states;
    set<string> alphabet;
    map<pair<string, string>, vector<string>> transitions;
    string initialState;
    set<string> finalStates;

public:
    void loadFromFile(const string& filePath);
    void loadFromKeyboard();

    void displayElements() const;

    bool isSequenceAccepted(const string& sequence) const;
    string longestAcceptedPrefix(const string& sequence) const;

private:
    void addTransition(const string& from, const string& symbol, const string& to);
};

#endif