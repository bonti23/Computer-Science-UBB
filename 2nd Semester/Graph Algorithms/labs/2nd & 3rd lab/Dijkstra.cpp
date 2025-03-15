#include <iostream>
#include <vector>
#include <queue>
#include <limits>

using namespace std;

const int INF = numeric_limits<int>::max();  // Infinity value

class Graph {
public:
    int V;  // Number of nodes
    vector<vector<pair<int, int>>> adj;  // Adjacency list (node, weight)

    Graph(int vertices) {
        V = vertices;
        adj.resize(V);
    }

    void addEdge(int u, int v, int w) {
        adj[u].push_back({v, w});
        adj[v].push_back({u, w});
    }

    void dijkstra(int source) {
        vector<int> dist(V, INF);
        priority_queue<pair<int, int>, vector<pair<int, int>>, greater<>> pq;

        dist[source] = 0;
        pq.push({0, source});

        while (!pq.empty()) {
            int u = pq.top().second;
            int d = pq.top().first;
            pq.pop();

            if (d > dist[u]) continue;

            for (auto [v, weight] : adj[u]) {
                if (dist[u] + weight < dist[v]) {
                    dist[v] = dist[u] + weight;
                    pq.push({dist[v], v});
                }
            }
        }

        cout << "Node Distance from Source " << source << ":\n";
        for (int i = 0; i < V; i++) {
            cout << "Node " << i << " -> Distance: ";
            cout << (dist[i] == INF ? "INF" : to_string(dist[i])) << endl;
        }
    }
};

int main() {
    int V = 5;  // Number of nodes
    Graph g(V);

    // Adding edges (u, v, w)
    g.addEdge(0, 1, 10);
    g.addEdge(0, 4, 3);
    g.addEdge(1, 2, 2);
    g.addEdge(1, 4, 4);
    g.addEdge(2, 3, 9);
    g.addEdge(3, 2, 7);
    g.addEdge(4, 1, 1);
    g.addEdge(4, 2, 8);
    g.addEdge(4, 3, 2);

    g.dijkstra(0);

    return 0;
}
