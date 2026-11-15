#include "FIP.h"
#include <iostream>
#include <fstream>
#include <iomanip>

void FIP::init() {
    entries.clear();
}

void FIP::addEntry(int codAtom, int pozitieTS) {
    for (const auto& e : entries) {
        if (e.codAtom == codAtom && e.pozitieTS == pozitieTS)
            return;
    }
    if (entries.size() < 1000)
        entries.push_back({ codAtom, pozitieTS });
}

void FIP::save(const std::string& fileName) const {
    std::ofstream out(fileName);
    out << "Cod Atom  | Pozitie in TS\n"
        << "--------------------------\n";

    for (const auto& e : entries) {
        std::string pozStr = (e.pozitieTS == -1 ? "-" : std::to_string(e.pozitieTS));
        out << std::setw(9) << e.codAtom << " | " << std::setw(13) << pozStr << "\n";
    }
}
