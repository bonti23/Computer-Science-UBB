#pragma once
#ifndef TS_H
#define TS_H

#include <cstdio>

#define MAX_SYMBOLS 1000

struct AtomEntry {
    int position;
    char atom[50];
    int left;
    int right;
};

struct TS {
    AtomEntry nodes[MAX_SYMBOLS];
    int size;
    int nextPosition;
};

void initTS(TS* ts);
int findSymbol(TS* ts, const char* symbol);
int addSymbol(TS* ts, const char* symbol);
void saveTS(TS* ts, FILE* outFile);
void printTS(TS* ts);

#endif
