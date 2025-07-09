// Name: Jason Holmes
// Project: CMSC 315 - Project 4
// Date: 7/9/2025
// Description: This class contains the main method and builds the JavaFX GUI,
// tying together graph logic, visual pane, and button event handling.

import java.util.List;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;

public class MainApp extends Application {

    private Graph graph;
    private GraphPane graphPane;
    private TextField vertexField1;
    private TextField vertexField2;
    private Label statusLabel;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        graph = new Graph();
        graphPane = new GraphPane(graph);

        vertexField1 = new TextField();
        vertexField1.setPromptText("Vertex 1");
        vertexField1.setPrefWidth(80);

        vertexField2 = new TextField();
        vertexField2.setPromptText("Vertex 2");
        vertexField2.setPrefWidth(80);

        Button addEdgeBtn = new Button("Add Edge");
        Button isConnectedBtn = new Button("Is Connected");
        Button hasCyclesBtn = new Button("Has Cycles");
        Button dfsBtn = new Button("Depth First Search");
        Button bfsBtn = new Button("Breadth First Search");

        statusLabel = new Label("Click to add vertices. Then connect them.");
        statusLabel.setStyle("-fx-text-fill: green;");

        HBox inputBox = new HBox(10, vertexField1, vertexField2, addEdgeBtn);
        HBox buttonBox = new HBox(10, isConnectedBtn, hasCyclesBtn, dfsBtn, bfsBtn);
        VBox controlBox = new VBox(10, inputBox, buttonBox, statusLabel);
        VBox layout = new VBox(10, graphPane, controlBox);
        layout.setPrefSize(800, 600);

        // Event Handlers
        addEdgeBtn.setOnAction(e -> handleAddEdge());
        isConnectedBtn.setOnAction(e -> handleIsConnected());
        hasCyclesBtn.setOnAction(e -> handleHasCycles());
        dfsBtn.setOnAction(e -> handleDFS());
        bfsBtn.setOnAction(e -> handleBFS());

        Scene scene = new Scene(layout);
        primaryStage.setTitle("Graph Visualizer - Jason Holmes");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private void handleAddEdge() {
        String v1 = vertexField1.getText().trim().toUpperCase();
        String v2 = vertexField2.getText().trim().toUpperCase();

        if (!graph.getVertexNames().contains(v1) || !graph.getVertexNames().contains(v2)) {
            statusLabel.setText("Error: One or both vertices not found.");
            statusLabel.setStyle("-fx-text-fill: red;");
            return;
        }

        boolean success = graph.addEdge(v1, v2);
        if (success) {
            graphPane.drawEdge(v1, v2);
            statusLabel.setText("Edge added between " + v1 + " and " + v2);
            statusLabel.setStyle("-fx-text-fill: green;");
        } else {
            statusLabel.setText("Edge already exists.");
            statusLabel.setStyle("-fx-text-fill: orange;");
        }
    }

    private void handleIsConnected() {
        boolean connected = graph.isConnected();
        statusLabel.setText(connected ? "Graph is connected." : "Graph is not connected.");
        statusLabel.setStyle("-fx-text-fill: " + (connected ? "green;" : "red;"));
    }

    private void handleHasCycles() {
        boolean cycles = graph.hasCycles();
        statusLabel.setText(cycles ? "Graph has cycles." : "Graph has no cycles.");
        statusLabel.setStyle("-fx-text-fill: " + (cycles ? "orange;" : "green;"));
    }

    private void handleDFS() {
        List<String> result = graph.depthFirstSearch();
        // Use "->" if the UTF-8 arrow character causes issues in your environment.
        statusLabel.setText("DFS: " + String.join(" → ", result));
        statusLabel.setStyle("-fx-text-fill: blue;");
    }

    private void handleBFS() {
        List<String> result = graph.breadthFirstSearch();
        statusLabel.setText("BFS: " + String.join(" → ", result));
        statusLabel.setStyle("-fx-text-fill: blue;");
    }
}
