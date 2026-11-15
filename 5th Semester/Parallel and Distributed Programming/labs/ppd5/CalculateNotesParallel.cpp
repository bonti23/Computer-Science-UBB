#include "CalculateNotesParallel.h"
#include "ThreadPool.h"

#include <iostream>
#include <fstream>
#include <vector>
#include <thread>

using namespace std;

constexpr int numberOfFiles = 10;

void CalculateNotesParallel::run() {
    QueueContainer queue(readerThreads, maxSize);
    LinkedList list;

    const int consumerThreads = P - readerThreads;
    vector<thread> consumers;
    consumers.reserve(consumerThreads);

    for (int i = 0; i < consumerThreads; ++i) {
        consumers.emplace_back(
            &CalculateNotesParallel::consumerThread,
            this,
            ref(queue),
            ref(list)
        );
    }

    {
        ThreadPool readPool(readerThreads);

        for (int i = 1; i <= numberOfFiles; ++i) {
            string fileName = "Input/project" + to_string(i) + ".txt";

            readPool.enqueue([this, fileName, &queue]() {
                readNodesFromFile(fileName, queue);
            });
        }
    }
    for (int i = 0; i < readerThreads; ++i) {
        queue.doneProducing();
    }

    // Așteptăm consumatorii
    for (auto& t : consumers) {
        t.join();
    }

    // ================= SORTARE PARALELĂ =================

    LinkedList sortedList;
    vector<thread> sortWorkers;

    int numSortWorkers = 4;

    sortWorkers.reserve(numSortWorkers);

    for (int i = 0; i < numSortWorkers; ++i) {
        sortWorkers.emplace_back([&]() {
            while (true) {
                Node* node = list.extractFirstNode();
                if (node == nullptr) {
                    break;
                }
                sortedList.addInOrder(node);
            }
        });
    }

    for (auto& t : sortWorkers) {
        t.join();
    }

    sortedList.writeToFile("Results/resultT.txt");
}

void CalculateNotesParallel::consumerThread(QueueContainer& queue, LinkedList& list) {
    while (true) {
        Node* node = queue.removeNode();
        if (node == nullptr) {
            break;
        }
        list.addOrUpdateNode(node);
    }
}

void CalculateNotesParallel::readNodesFromFile(const string& fileName, QueueContainer& queue) {
    ifstream in(fileName);

    if (!in.is_open()) {
        cerr << "Cannot open file: " << fileName << endl;
        return;
    }

    int id, nota;
    while (in >> id >> nota) {
        queue.addNode(new Node(id, nota));
    }

    in.close();
}
