#include "TS.h"
#include <iostream>
#include <cstring>
#include <iomanip> // pentru std::setw

void initTS(TS* ts) {
    ts->size = 0;
    ts->nextPosition = 0;
    for (int i = 0; i < MAX_SYMBOLS; i++) {
        ts->nodes[i].left = -1;
        ts->nodes[i].right = -1;
    }
}

int findSymbol(TS* ts, const char* symbol) {
    int index = 0;

    while (index != -1) {
        int cmp = strcmp(symbol, ts->nodes[index].atom);

        if (cmp == 0) {
            return ts->nodes[index].position;
        }
        else if (cmp < 0) {
            index = ts->nodes[index].left;
        }
        else {
            index = ts->nodes[index].right;
        }
    }

    return -1;
}

int addSymbol(TS* ts, const char* symbol) {
    if (ts->size >= MAX_SYMBOLS) {
        std::cout << "Error: TS is full.\n";
        return -1;
    }

    int index = 0;

    if (ts->size == 0) {
        strcpy(ts->nodes[0].atom, symbol);
        ts->nodes[0].position = ts->nextPosition++;
        ts->size++;
        return ts->nodes[0].position;
    }

    while (index != -1) {
        int cmp = strcmp(symbol, ts->nodes[index].atom);

        if (cmp == 0) {
            return ts->nodes[index].position;
        }
        else if (cmp < 0) {
            if (ts->nodes[index].left == -1) {
                ts->nodes[index].left = ts->size;
                break;
            }
            else {
                index = ts->nodes[index].left;
            }
        }
        else {
            if (ts->nodes[index].right == -1) {
                ts->nodes[index].right = ts->size;
                break;
            }
            else {
                index = ts->nodes[index].right;
            }
        }
    }

    strcpy(ts->nodes[ts->size].atom, symbol);
    ts->nodes[ts->size].position = ts->nextPosition++;
    ts->size++;

    return ts->nodes[ts->size - 1].position;
}

void saveTS(TS* ts, FILE* outFile) {
    fprintf(outFile, "| Position | Atom         | Left Link | Right Link |\n");
    fprintf(outFile, "|--------------------------------------------------|\n");

    for (int i = 0; i < ts->size; i++) {
        char leftStr[10];
        char rightStr[10];

        if (ts->nodes[i].left == -1)
            sprintf(leftStr, "-");
        else
            sprintf(leftStr, "%d", ts->nodes[i].left);

        if (ts->nodes[i].right == -1)
            sprintf(rightStr, "-");
        else
            sprintf(rightStr, "%d", ts->nodes[i].right);

        fprintf(outFile, "| %8d | %-12s | %9s | %10s |\n",
            ts->nodes[i].position,
            ts->nodes[i].atom,
            leftStr,
            rightStr);
    }
}

void printTS(TS* ts) {
    std::cout << "| Position | Atom         | Left Link | Right Link |\n";
    std::cout << "|--------------------------------------------------|\n";

    for (int i = 0; i < ts->size; i++) {
        std::cout << "| " << std::setw(8) << ts->nodes[i].position
            << " | " << std::setw(12) << ts->nodes[i].atom
            << " | " << std::setw(9) << ts->nodes[i].left
            << " | " << std::setw(10) << ts->nodes[i].right
            << " |\n";
    }
}
