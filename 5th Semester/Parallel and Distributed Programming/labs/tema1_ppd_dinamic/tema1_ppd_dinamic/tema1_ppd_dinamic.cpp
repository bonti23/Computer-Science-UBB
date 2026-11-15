#include <iostream>
#include <fstream>
#include <chrono>
#include <thread>
#include <vector>

using namespace std;
using namespace std::chrono;

int N, M, n, m;
int lineOffset, columnOffset;

int computeKernel(int x, int y, int** A, int** Kernel) {
    int result = 0;

    for (int i_kernel = 0; i_kernel < n; i_kernel++) {
        for (int j_kernel = 0; j_kernel < m; j_kernel++) {

            int i_matr = x - lineOffset + i_kernel;
            int j_matr = y - columnOffset + j_kernel;

            if (i_matr < 0)
                i_matr = 0;
            else if (i_matr >= N)
                i_matr = N - 1;

            if (j_matr < 0)
                j_matr = 0;
            else if (j_matr >= M)
                j_matr = M - 1;

            result += A[i_matr][j_matr] * Kernel[i_kernel][j_kernel];
        }
    }

    return result;
}

void runLine(int start, int end, int** A, int** K, int** R) {
    for (int i = start; i < end; i++)
        for (int j = 0; j < M; j++)
            R[i][j] = computeKernel(i, j, A, K);
}

void runColumn(int start, int end, int** A, int** K, int** R) {
    for (int j = start; j < end; j++)
        for (int i = 0; i < N; i++)
            R[i][j] = computeKernel(i, j, A, K);
}


bool citire(const string& path, int**& A, int**& Kernel) {
    ifstream file(path);
    if (!file.is_open()) {
        cerr << "Eroare la deschiderea fisierului " << path << endl;
        return false;
    }

    file >> N >> M;
    A = new int* [N];
    for (int i = 0; i < N; i++)
        A[i] = new int[M];
    for (int i = 0; i < N; i++)
        for (int j = 0; j < M; j++)
            file >> A[i][j];
    file >> n >> m;
    Kernel = new int* [n];
    for (int i = 0; i < n; i++)
        Kernel[i] = new int[m];
    for (int i = 0; i < n; i++)
        for (int j = 0; j < m; j++)
            file >> Kernel[i][j];
    lineOffset = (n - 1) / 2;
    columnOffset = (m - 1) / 2;
    file.close();
    return true;
}

void afisare(const string& path, int** R) {
    ofstream file(path);
    if (!file.is_open()) {
        cerr << "Eroare la scriere in fisierul " << path << endl;
        return;
    }

    for (int i = 0; i < N; i++) {
        for (int j = 0; j < M; j++)
            file << R[i][j] << " ";
        file << endl;
    }

    file.close();
}

double resolve(int p, bool secv, bool peLinii) {
    int** A = nullptr;
    int** K = nullptr;

    if (!citire("date5.txt", A, K))
        return -1;

    int** R = new int* [N];
    for (int i = 0; i < N; i++)
        R[i] = new int[M];

    auto t_start = high_resolution_clock::now();

    if (secv) {
        for (int i = 0; i < N; i++)
            for (int j = 0; j < M; j++)
                R[i][j] = computeKernel(i, j, A, K);
    }
    
    else {
        vector<thread> threads;
        int size = peLinii ? N : M;
        int base = size / p;
        int extra = size % p;
        int start = 0, end = 0;

        for (int i = 0; i < p; i++) {
            start = end;
            end = start + base + (extra > 0 ? 1 : 0);
            if (extra > 0) extra--;

            if (peLinii)
                threads.emplace_back(runLine, start, end, A, K, R);
            else
                threads.emplace_back(runColumn, start, end, A, K, R);
        }

        for (auto& t : threads)
            t.join();
    }

    auto t_end = high_resolution_clock::now();
    double elapsed_ms = duration<double, std::milli>(t_end - t_start).count();

    afisare("output.txt", R);
    for (int i = 0; i < N; i++) delete[] A[i];
    for (int i = 0; i < n; i++) delete[] K[i];
    for (int i = 0; i < N; i++) delete[] R[i];
    delete[] A;
    delete[] K;
    delete[] R;

    return elapsed_ms;
}

int main() {
    int p;
    int modRulare;
    int modSplit;

    cout << "Introdu numarul de threaduri: ";
    cin >> p;

    cout << "Rulare secventiala (1) sau paralela (0): ";
    cin >> modRulare;

    cout << "Impartire pe linii (1) sau pe coloane (0): ";
    cin >> modSplit;

    bool secv = (modRulare == 1);
    bool peLinii = (modSplit == 1);

    double timp = resolve(p, secv, peLinii);

    cout << "----------------------------------\n";
    cout << "Dimensiune matrice: " << N << " x " << M << endl;
    cout << "Dimensiune kernel: " << n << " x " << m << endl;
    cout << "Threaduri: " << p << endl;
    cout << "Mod rulare: " << (secv ? "Secvential" : "Paralel") << endl;
    cout << "Impartire: " << (peLinii ? "Linii" : "Coloane") << endl;
    cout << "Timp executie: " << timp << " ms" << endl;
    cout << "----------------------------------\n";

    return 0;
}
