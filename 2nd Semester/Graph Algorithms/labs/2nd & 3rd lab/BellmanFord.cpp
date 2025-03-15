#include <iostream>
#include <vector>
#include <limits>

using namespace std;

struct Pair{
    int src, dest, weight;
};

class Graph {
public:
    int V, E;
    vector<Pair> pairs;  // List of all pairs

    Graph(int vertices, int edges) {
        V=vertices;
        E=edges;
    }

    void addEdge(int u, int v, int w) {
        pairs.push_back({u, v, w});
    }

    void bellmanFord(int source) {
        vector<int> dist(V, numeric_limits<int>::max());
        dist[source] = 0;  // Distance from source to itself is 0

        for (int i = 0; i < V - 1; i++) {
            for (auto pair : pairs) {
                int u = pair.src;
                int v = pair.dest;
                int w = pair.weight;

                if (dist[u] != numeric_limits<int>::max() && dist[u] + w < dist[v]) {
                    dist[v] = dist[u] + w;
                }
            }
        }

        // Step 3: Check for negative weight cycles
        for (auto pair : pairs) {
            int u = pair.src;
            int v = pair.dest;
            int w = pair.weight;

            if (dist[u] != numeric_limits<int>::max() && dist[u] + w < dist[v]) {
                cout << "Graph contains a negative weight cycle!" << endl;
                return;
            }
        }

        cout << "Node Distance from Source " << source << ":\n";
        for (int i = 0; i < V; i++) {
            cout << "Node " << i << " -> Distance: ";
            if (dist[i] == numeric_limits<int>::max()) {
                cout << "INF" << endl;
            } else {
                cout << dist[i] << endl;
            }
        }
    }
};

// Driver code
int main() {
    int V = 5;  // Number of nodes
    int E = 8;  // Number of edges
    Graph g(V, E);

    g.addEdge(0, 1, -1);
    g.addEdge(0, 2, 4);
    g.addEdge(1, 2, 3);
    g.addEdge(1, 3, 2);
    g.addEdge(1, 4, 2);
    g.addEdge(3, 2, 5);
    g.addEdge(3, 1, 1);
    g.addEdge(4, 3, -3);

    g.bellmanFord(0);
    return 0;
}
