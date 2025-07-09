// Name: Jason Holmes
// Project: CMSC 315 - Project 4
// Date: 7/9/2025
// Description: This class extends Pane and displays the graph visually.
// It handles mouse clicks for adding vertices and methods for drawing edges.

import java.util.HashMap;
import java.util.Map;

import javax.sound.sampled.Line;

import org.w3c.dom.Text;
import org.w3c.dom.events.MouseEvent;

import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

public class GraphPane extends Pane {
    private final Graph graph;
    private final Map<String, Vertex> vertices;  // Name → Vertex
    private char nextLabel = 'A';
    private final int radius = 15;

    public GraphPane(Graph graph) {
        this.graph = graph;
        this.vertices = new HashMap<>();

        setStyle("-fx-background-color: white;");
        setOnMouseClicked(this::handleMouseClick);
    }

    private void handleMouseClick(MouseEvent e) {
        if (nextLabel > 'Z') return; // Limit to 26 vertices
        int x = (int) e.getX();
        int y = (int) e.getY();
        String name = String.valueOf(nextLabel);

        Vertex v = new Vertex(name, x, y);
        if (graph.addVertex(v)) {
            vertices.put(name, v);
            drawVertex(v);
            nextLabel++;
        }
    }

    public void drawVertex(Vertex v) {
        Circle circle = new Circle(v.getX(), v.getY(), radius);
        circle.setFill(Color.LIGHTBLUE);
        circle.setStroke(Color.BLACK);

        Text label = new Text(v.getX() - 5, v.getY() + 5, v.getName());

        getChildren().addAll(circle, label);
    }

    public void drawEdge(String name1, String name2) {
        Vertex v1 = graph.getVertex(name1);
        Vertex v2 = graph.getVertex(name2);

        if (v1 == null || v2 == null) return;

        Line edge = new Line(v1.getX(), v1.getY(), v2.getX(), v2.getY());
        edge.setStroke(Color.BLACK);
        edge.setStrokeWidth(2);

        getChildren().add(edge);
        // Send edge behind nodes
        edge.toBack();
    }

    public Map<String, Vertex> getVertices() {
        return vertices;
    }

    public void resetGraph() {
        getChildren().clear();
        vertices.clear();
        nextLabel = 'A';
    }
