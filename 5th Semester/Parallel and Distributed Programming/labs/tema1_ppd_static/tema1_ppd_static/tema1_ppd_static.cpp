#include <iostream>
#include <fstream>
#include <chrono>
#include <thread>
#include <vector>

#define N 10
#define M 10
#define n 3
#define m 3
#define lineOffset  (n - 1) / 2
#define columnOffset  (m - 1) / 2

using namespace std;
using namespace std::chrono;

int A[N][M], Kernel[n][m], R[N][M];

void citire(const string& path) {
    ifstream file(path);
    if (!file.is_open()) {
        cerr << "Eroare la citire din fisier!" << endl;
        return;
    }
    int nFisier, mFisier;
    file >> nFisier >> mFisier;
    for (int i = 0; i < N; i++)
        for (int j = 0; j < M; j++)
            file >> A[i][j];
    int nk, mk;
    file >> nk >> mk;
    for (int i = 0; i < n; i++)
        for (int j = 0; j < m; j++)
            file >> Kernel[i][j];

    file.close();
}


void afisare(const string& path) {
    ofstream file(path);
    if (file.is_open()) {
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < M; j++)
                file << R[i][j] << " ";
            file << endl;
        }
        file.close();
    }
    else cerr << "Eroare la scriere in " << path << endl;
}

int computeKernel(int x, int y) {
    int result = 0;
    for (int i_kernel = 0; i_kernel < n; i_kernel++) {
        for (int j_kernel = 0; j_kernel < m; j_kernel++) {
            int i_matr = x - lineOffset + i_kernel;
            int j_matr = y - columnOffset + j_kernel;

            if (i_matr < 0) i_matr = 0;
            else if (i_matr >= N) i_matr = N - 1;
            if (j_matr < 0) j_matr = 0;
            else if (j_matr >= M) j_matr = M - 1;

            result += A[i_matr][j_matr] * Kernel[i_kernel][j_kernel];
        }
    }
    return result;
}

void runLine(int start, int end) {
    for (int i = start; i < end; i++)
        for (int j = 0; j < M; j++)
            R[i][j] = computeKernel(i, j);
}

void runColumn(int start, int end) {
    for (int j = start; j < end; j++)
        for (int i = 0; i < N; i++)
            R[i][j] = computeKernel(i, j);
}

double resolve(int p, bool secv, bool peLinii) {
    citire("date.txt");

    auto t_start = high_resolution_clock::now();

    if (secv) {
        for (int i = 0; i < N; i++)
            for (int j = 0; j < M; j++)
                R[i][j] = computeKernel(i, j);
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
                threads.emplace_back(runLine, start, end);
            else
                threads.emplace_back(runColumn, start, end);
        }

        for (auto& t : threads)
            t.join();
    }

    auto t_end = high_resolution_clock::now();
    double elapsed_ms = duration<double, std::milli>(t_end - t_start).count();

    afisare("output.txt");
    return elapsed_ms;
}
void test(int p, bool secv, bool peLinii) {
    double t = resolve(p, secv, peLinii);
    int R_ref[N][M];
    for (int i = 0; i < N; i++)
        for (int j = 0; j < M; j++)
            R_ref[i][j] = computeKernel(i, j);
    bool ok = true;
    for (int i = 0; i < N && ok; i++)
        for (int j = 0; j < M; j++)
            if (R_ref[i][j] != R[i][j]) {
                cout << "Diferenta la (" << i << ", " << j << "): "
                    << R_ref[i][j] << " vs " << R[i][j] << endl;
                ok = false;
                break;
            }
    if (ok) cout << "corect!\n";
    else cout << "incorect!\n";
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

    cout << "N = " << N << " M = " << M << " Threads = " << p
        << " | Mode: " << (secv ? "Secvential" : "Paralel")
        << " | Split: " << (peLinii ? "Linii" : "Coloane") << endl;

    double t = resolve(p, secv, peLinii);
    cout << "Timp executie: " << t << " ms" << endl;
    test(p, secv, peLinii);


    return 0;
}
