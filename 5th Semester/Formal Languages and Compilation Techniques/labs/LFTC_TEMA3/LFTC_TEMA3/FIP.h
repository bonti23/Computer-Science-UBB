#pragma once
#ifndef FIP_H
#define FIP_H

#include <vector>
#include <string>

struct FIPEntry {
    int codAtom;
    int pozitieTS;
};

class FIP {
public:
    void init();
    void addEntry(int codAtom, int pozitieTS);
    void save(const std::string& fileName) const;

private:
    std::vector<FIPEntry> entries;
};

#endif
