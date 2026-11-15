#ifndef TEMA_4_CALCULATENOTESPARALLEL_H
#define TEMA_4_CALCULATENOTESPARALLEL_H

#include <string>
#include "QueueContainer.h"
#include "LinkedList.h"

class CalculateNotesParallel {
private:
    int readerThreads;
    int maxSize;
    int P;

public:
    CalculateNotesParallel(int readerT, int P, int maxSize)
        : readerThreads(readerT), maxSize(maxSize), P(P) {}

    void run();

    void consumerThread(QueueContainer& queue, LinkedList& list);

    void readNodesFromFile(const std::string& fileName, QueueContainer& queue);
};

#endif
