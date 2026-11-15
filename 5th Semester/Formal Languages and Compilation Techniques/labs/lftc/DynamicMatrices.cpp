#include "DynamicMatrices.h"

void DynamicMatrices::allocateMatrices() {
    matrix = new int*[N];
    for (int i = 0; i < N; i++)
        matrix[i] = new int[M];

    convolutionMatrix = new int*[3];
    for (int i = 0; i < 3; i++)
        convolutionMatrix[i] = new int[3];
}

void DynamicMatrices::deallocateMatrices() {
    if (matrix) {
        for (int i = 0; i < N; i++) delete[] matrix[i];
        delete[] matrix;
        matrix = nullptr;
    }
    if (convolutionMatrix) {
        for (int i = 0; i < 3; i++) delete[] convolutionMatrix[i];
        delete[] convolutionMatrix;
        convolutionMatrix = nullptr;
    }
}

void DynamicMatrices::readOriginalMatrix(ifstream& data) {
    for (int row = 0; row < N; row++)
        for (int col = 0; col < M; col++)
            data >> matrix[row][col];
}

void DynamicMatrices::readConvolutionMatrix(ifstream& data) {
    for (int row = 0; row < 3; row++)
        for (int col = 0; col < 3; col++)
            data >> convolutionMatrix[row][col];
}

void DynamicMatrices::writeToFile(const string& filename) {
    ofstream outputFile(filename);
    if (!outputFile.is_open()) {
        cerr << "Could not open the file for writing!\n";
        return;
    }
    for (int row = 0; row < N; row++) {
        for (int col = 0; col < M; col++)
            outputFile << matrix[row][col] << " ";
        outputFile << "\n";
    }
    outputFile.close();
    cout << "Matrix successfully written to " << filename << endl;
}

bool DynamicMatrices::areEqual(const string& file1, const string& file2) {
    ifstream f1(file1), f2(file2);
    if (!f1.is_open() || !f2.is_open()) {
        cerr << "Could not open one or both files!\n";
        return false;
    }

    string l1, l2;
    while (getline(f1, l1) && getline(f2, l2)) {
        if (l1 != l2) {
            cout << "Matrices are NOT equal.\n";
            return false;
        }
    }

    if (f1.eof() != f2.eof()) {
        cout << "Matrices are NOT equal.\n";
        return false;
    }

    cout << "Matrices are equal.\n";
    return true;
}
void DynamicMatrices::computeSequential() {
    int* prevRow = new int[M];
    int* nextRow = new int[M];
    int* resultRow = new int[M];

    // Initialize prevRow
    copy(matrix[0], matrix[0] + M, prevRow);

    for (int row = 0; row < N; row++) {
        if (row < N - 1)
            copy(matrix[row + 1], matrix[row + 1] + M, nextRow);
        else
            copy(matrix[row], matrix[row] + M, nextRow);

        for (int col = 0; col < M; col++) {
            int sum = 0;
            for (int kr = 0; kr < n; kr++) {
                for (int kc = 0; kc < n; kc++) {
                    int r = row - n/2 + kr;
                    int c = col - n/2 + kc;
                    if (r < 0) r = 0;
                    if (r >= N) r = N - 1;
                    if (c < 0) c = 0;
                    if (c >= M) c = M - 1;

                    int val;
                    if (r == row - 1) val = prevRow[c];
                    else if (r == row + 1) val = nextRow[c];
                    else val = matrix[r][c];

                    sum += val * convolutionMatrix[kr][kc];
                }
            }
            resultRow[col] = sum;
        }
        copy(matrix[row], matrix[row] + M, prevRow);
        copy(resultRow, resultRow + M, matrix[row]);
    }

    delete[] prevRow;
    delete[] nextRow;
    delete[] resultRow;
}
void DynamicMatrices::computeForRange(int startRow, int endRow) {
    int* prevRow = new int[M];
    int* nextRow = new int[M];
    int* resultRow = new int[M];

    if (startRow > 0)
        copy(matrix[startRow - 1], matrix[startRow - 1] + M, prevRow);
    else
        copy(matrix[startRow], matrix[startRow] + M, prevRow);

    if (endRow < N)
        copy(matrix[endRow], matrix[endRow] + M, nextRow);
    else
        copy(matrix[endRow - 1], matrix[endRow - 1] + M, nextRow);

    barrier.wait(); // synchronize threads

    for (int row = startRow; row < endRow; row++) {
        for (int col = 0; col < M; col++) {
            int sum = 0;
            for (int kr = 0; kr < n; kr++) {
                for (int kc = 0; kc < n; kc++) {
                    int r = row - n/2 + kr;
                    int c = col - n/2 + kc;
                    if (r < 0) r = 0;
                    if (r >= N) r = N - 1;
                    if (c < 0) c = 0;
                    if (c >= M) c = M - 1;

                    int val;
                    if (r == row - 1) val = prevRow[c];
                    else if (r == row + 1) val = nextRow[c];
                    else val = matrix[r][c];
                    sum += val * convolutionMatrix[kr][kc];
                }
            }
            resultRow[col] = sum;
        }

        if (row < N - 1)
            copy(matrix[row], matrix[row] + M, prevRow);
        copy(resultRow, resultRow + M, matrix[row]);
    }

    delete[] prevRow;
    delete[] nextRow;
    delete[] resultRow;
}

void DynamicMatrices::computeParallelHorizontal(int noOfThreads) {
    vector<thread> threads(noOfThreads);
    int rowsPerThread = N / noOfThreads;
    int remaining = N % noOfThreads;
    int start = 0;

    for (int t = 0; t < noOfThreads; t++) {
        int end = start + rowsPerThread + (remaining > 0 ? 1 : 0);
        if (remaining > 0) remaining--;
        threads[t] = thread(&DynamicMatrices::computeForRange, this, start, end);
        start = end;
    }

    for (auto& th : threads) th.join();
}

void DynamicMatrices::run() {
    ifstream data("N10M10n3.txt");
    if (!data.is_open()) {
        cerr << "Could not open input file!\n";
        return;
    }

    data >> N >> M;
    allocateMatrices();
    readOriginalMatrix(data);
    readConvolutionMatrix(data);
    data.close();

    // --- Sequential run for reference ---
    auto startSeq = chrono::high_resolution_clock::now();
    computeSequential();
    auto endSeq = chrono::high_resolution_clock::now();
    double msSeq = chrono::duration<double, milli>(endSeq - startSeq).count();
    writeToFile("output_seq.txt");
    cout << "Sequential done in " << msSeq << " ms\n";

    // --- Parallel run ---
    ifstream reload("N10M10n3.txt");
    reload >> N >> M;
    for (int i = 0; i < N; i++)
        for (int j = 0; j < M; j++)
            reload >> matrix[i][j];
    readConvolutionMatrix(reload);
    reload.close();

    auto startPar = chrono::high_resolution_clock::now();
    computeParallelHorizontal(noOfThreads);
    auto endPar = chrono::high_resolution_clock::now();
    double msPar = chrono::duration<double, milli>(endPar - startPar).count();
    writeToFile("output_par.txt");
    cout << "Parallel (" << noOfThreads << " threads) done in " << msPar << " ms\n";

    // --- Compare results ---
    areEqual("output_seq.txt", "output_par.txt");

    deallocateMatrices();
}
