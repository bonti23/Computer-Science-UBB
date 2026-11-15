#ifndef PPD2
#define PPD2

#include <iostream>
#include <fstream>
#include <functional>
#include <algorithm>
#include <chrono>
#include <thread>
#include <vector>
#include <mutex>
#include <condition_variable>
#include <cstring>

using namespace std;

//==========================//
// Custom barrier (C++11)  //
//==========================//
class my_barrier {
public:
    explicit my_barrier(int count) : thread_count(count), counter(0), waiting(0) {}

    void wait() {
        unique_lock<mutex> lock(mutex_);
        int local_counter = counter;
        ++waiting;

        if (waiting == thread_count) {
            counter++;
            waiting = 0;
            condition_.notify_all();
        } else {
            condition_.wait(lock, [&] { return counter != local_counter; });
        }
    }

private:
    mutex mutex_;
    condition_variable condition_;
    int thread_count;
    int counter;
    int waiting;
};

//==========================//
// DynamicMatrices class   //
//==========================//
class DynamicMatrices {
private:
    int N;
    int M;
    static const int n = 3;
    int noOfThreads;

    int** matrix;
    int** convolutionMatrix;
    my_barrier barrier;

    // helper methods
    void allocateMatrices();
    void deallocateMatrices();
    void readOriginalMatrix(ifstream& data);
    void readConvolutionMatrix(ifstream& data);
    void writeToFile(const string& filename);
    bool areEqual(const string& filename1, const string& filename2);

    // sequential + parallel computation
    void computeSequential();
    void computeForRange(int startRow, int endRow);
    void computeParallelHorizontal(int noOfThreads);

public:
    explicit DynamicMatrices(int noOfThreads) : barrier(noOfThreads) {
        this->noOfThreads = noOfThreads;
        matrix = nullptr;
        convolutionMatrix = nullptr;
        N = M = 0;
    }

    ~DynamicMatrices() {
        deallocateMatrices();
    }

    void run();
};

#endif //PPD2
