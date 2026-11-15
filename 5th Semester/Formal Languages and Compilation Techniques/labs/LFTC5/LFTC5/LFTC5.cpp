#include <iostream>
#include "AutomatFinit.h"
using namespace  std;

int main() {
    AutomatFinit af;
    //af.loadFromFile("constante_intregi.txt");
    //af.loadFromFile("Activitate.txt");
    af.loadFromKeyboard();
    af.displayElements();
    cout << boolalpha;

    cout << af.isSequenceAccepted("abb") << "\n";
    cout << af.isSequenceAccepted("ab") << "\n";

    cout << af.longestAcceptedPrefix("abb2") << "\n";
    //cout << "cccc accepted: " << af.isSequenceAccepted("cccc") << "\n";
    //cout << "Longest accepted prefix of aabc: " << af.longestAcceptedPrefix("aabc") << "\n";
    //cout << "a accepted: " << af.isSequenceAccepted("a") << "\n";

    //Teste tema
    // cout << "012347 accepted: " << af.isSequenceAccepted("012347") << "\n";
    // cout << "Longest accepted prefix of 0123478: " << af.longestAcceptedPrefix("0123478") << "\n";
    // cout << "0X123a accepted: " << af.isSequenceAccepted("0X123a") << "\n";
    // cout << "Longest accepted prefix of 0xff3ag: " << af.longestAcceptedPrefix("0xff3ag") << "\n";
    // cout << "Longest accepted prefix of 360qb: " << af.longestAcceptedPrefix("360qb") << "\n";
    // cout << "0X123ag accepted: " << af.isSequenceAccepted("0X123ag") << "\n";

    return 0;
}