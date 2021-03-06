package ru.itis.inform;


public interface DirectedGraph {
    void addVertex();

    void addEdgeDirection(int vertexA, int vertexB, int weightAB);

    int getDegreeVertex(int numberVertex);
    void showGraph();
    void showGraph(int[][] matrix);

    int[][] runFloyd();
}
