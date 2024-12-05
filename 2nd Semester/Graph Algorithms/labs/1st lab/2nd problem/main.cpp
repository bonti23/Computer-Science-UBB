#include <iostream>
#include <fstream>
#include <climits>
using namespace std;
ifstream fin("date.in");

const int MAX_N = 100;
int a[MAX_N][MAX_N];
int n;

void citireMatriceAdiacenta() {
    if (!fin.is_open()) {
        cerr<<"Eroare la deschiderea fisierului.\n";
        return;
    }
    fin>>n;
    for (int i = 0; i < n; ++i) {
        for (int j = 0; j < n; ++j) {
            fin>>a[i][j];
        }
    }
}
void gasesteNoduriIzolate() {
    cout << "Noduri izolate: ";
    for (int i = 0; i < n; ++i) {
        bool izolat = true;
        for (int j = 0; j < n; ++j) {
            if (a[i][j] != 0) {
                izolat = false;
                break;
            }
        }
        if (izolat) {
            cout << i + 1 << " ";
        }
    }
    cout << endl;
}
bool esteRegular() {
    int gradInitial = -1;
    for (int i = 0; i < n; ++i) {
        int grad = 0;
        for (int j = 0; j < n; ++j) {
            grad += a[i][j];
        }
        if (gradInitial == -1) {
            gradInitial = grad;
        } else if (grad != gradInitial) {
            return false;
        }
    }
    return true;
}
void matriceDistantelor() {
    int dist[MAX_N][MAX_N];
    for (int i = 0; i < n; ++i) {
        for (int j = 0; j < n; ++j) {
            if (i == j) {
                dist[i][j] = 0; 
            } else if (a[i][j] != 0) {
                dist[i][j] = a[i][j];
            } else {
                dist[i][j] = INT_MAX;
            }
        }
    }
    for (int k = 0; k < n; ++k) {
        for (int i = 0; i < n; ++i) {
            for (int j = 0; j < n; ++j) {
                if (dist[i][k] != INT_MAX && dist[k][j] != INT_MAX && dist[i][j] > dist[i][k] + dist[k][j]) {
                    dist[i][j] = dist[i][k] + dist[k][j];
                }
            }
        }
    }
    cout << "Matricea distantelor:\n";
    for (int i = 0; i < n; ++i) {
        for (int j = 0; j < n; ++j) {
            if (dist[i][j] == INT_MAX) {
                cout << "INF ";
            } else {
                cout << dist[i][j] << " ";
            }
        }
        cout << endl;
    }
}
void dfs(int nod, bool vizitat[]) {
    vizitat[nod] = true;
    for (int i = 0; i < n; ++i) {
        if (a[nod][i] != 0 && !vizitat[i]) {
            dfs(i, vizitat);
        }
    }
}

bool esteConex() {
    bool vizitat[MAX_N] = {false};
    dfs(0, vizitat);
    for (int i = 0; i < n; ++i) {
        if (!vizitat[i]) {
            return false;
        }
    }
    return true;
}

int main() {
    citireMatriceAdiacenta();
    gasesteNoduriIzolate();
    if (esteRegular()) {
        cout << "Graful este regular.\n";
    } else {
        cout << "Graful nu este regular.\n";
    }
    matriceDistantelor();
    if (esteConex()) {
        cout << "Graful este conex.\n";
    } else {
        cout << "Graful nu este conex.\n";
    }
    fin.close();
    return 0;
}
