// Name: Jason Holmes
// Project: CMSC 315 - Project 4
// Date: 7/9/2025
// Description: This class represents an undirected graph using an adjacency list.
// It supports vertex and edge insertion and provides methods for connectivity, cycle detection, DFS, and BFS.

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Set;

public class Graph {
    private final Map<String, Vertex> vertexMap; // name -> Vertex
    private final Map<Vertex, List<Vertex>> adjList;

    public Graph() {
        vertexMap = new HashMap<>();
        adjList = new HashMap<>();
    }

    public boolean addVertex(Vertex v) {
        if (vertexMap.containsKey(v.getName())) {
            return false; // already exists
        }
        vertexMap.put(v.getName(), v);
        adjList.put(v, new ArrayList<>());
        return true;
    }

    public boolean addEdge(String name1, String name2) {
        Vertex v1 = vertexMap.get(name1);
        Vertex v2 = vertexMap.get(name2);

        if (v1 == null || v2 == null) return false;

        if (!adjList.get(v1).contains(v2)) {
            adjList.get(v1).add(v2);
            adjList.get(v2).add(v1); // undirected
        }

        return true;
    }

    public Vertex getVertex(String name) {
        return vertexMap.get(name);
    }

    public Set<String> getVertexNames() {
        return vertexMap.keySet();
    }

    public Map<Vertex, List<Vertex>> getAdjList() {
        return adjList;
    }

    public boolean isConnected() {
        if (vertexMap.isEmpty()) return true;

        Set<Vertex> visited = new HashSet<>();
        Vertex start = vertexMap.get("A"); // Start from vertex A
        if (start == null) return false;

        dfs(start, visited);
        return visited.size() == vertexMap.size();
    }

    private void dfs(Vertex current, Set<Vertex> visited) {
        visited.add(current);
        for (Vertex neighbor : adjList.get(current)) {
            if (!visited.contains(neighbor)) {
                dfs(neighbor, visited);
            }
        }
    }

    public boolean hasCycles() {
        Set<Vertex> visited = new HashSet<>();

        for (Vertex v : adjList.keySet()) {
            if (!visited.contains(v)) {
                if (dfsCycle(v, null, visited)) {
                    return true;
                }
            }
        }
        return false;
    }

    private boolean dfsCycle(Vertex current, Vertex parent, Set<Vertex> visited) {
        visited.add(current);

        for (Vertex neighbor : adjList.get(current)) {
            if (!visited.contains(neighbor)) {
                if (dfsCycle(neighbor, current, visited)) {
                    return true;
                }
            } else if (!neighbor.equals(parent)) {
                return true; // Found a back edge
            }
        }

        return false;
    }

    public List<String> depthFirstSearch() {
        List<String> result = new ArrayList<>();
        Set<Vertex> visited = new HashSet<>();
        Vertex start = vertexMap.get("A");
        if (start != null) dfsTraversal(start, visited, result);
        return result;
    }

    private void dfsTraversal(Vertex current, Set<Vertex> visited, List<String> result) {
        visited.add(current);
        result.add(current.getName());
        for (Vertex neighbor : adjList.get(current)) {
            if (!visited.contains(neighbor)) {
                dfsTraversal(neighbor, visited, result);
            }
        }
    }

    public List<String> breadthFirstSearch() {
        List<String> result = new ArrayList<>();
        Set<Vertex> visited = new HashSet<>();
        Queue<Vertex> queue = new LinkedList<>();
        Vertex start = vertexMap.get("A");

        if (start == null) return result;

        queue.offer(start);
        visited.add(start);

        while (!queue.isEmpty()) {
            Vertex current = queue.poll();
            result.add(current.getName());

            for (Vertex neighbor : adjList.get(current)) {
                if (!visited.contains(neighbor)) {
                    queue.offer(neighbor);
                    visited.add(neighbor);
                }
            }
        }

        return result;
    }
}
