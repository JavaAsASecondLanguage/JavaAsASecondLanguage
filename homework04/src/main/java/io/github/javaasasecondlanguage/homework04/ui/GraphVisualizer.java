package io.github.javaasasecondlanguage.homework04.ui;

import io.github.javaasasecondlanguage.homework04.ProcGraph;
import io.github.javaasasecondlanguage.homework04.nodes.JoinerNode;
import io.github.javaasasecondlanguage.homework04.nodes.MapperNode;
import io.github.javaasasecondlanguage.homework04.nodes.ProcNode;
import io.github.javaasasecondlanguage.homework04.nodes.ReducerNode;
import io.github.javaasasecondlanguage.homework04.nodes.SorterNode;
import org.graphstream.graph.Graph;
import org.graphstream.graph.Node;
import org.graphstream.graph.implementations.SingleGraph;
import org.graphstream.ui.swing.SwingGraphRenderer;
import org.graphstream.ui.swing_viewer.SwingViewer;
import org.graphstream.ui.view.GraphRenderer;
import org.graphstream.ui.view.Viewer.ThreadingModel;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.UUID;


public class GraphVisualizer {

    public static void visualizeGraph(ProcGraph procGraph) {
        System.setProperty("gs.ui.renderer", "org.graphstream.ui.j2dviewer.J2DGraphRenderer");
        System.setProperty("org.graphstream.ui", "swing");

        Graph visualGraph = new SingleGraph("Main");
//        visualGraph.setAttribute("ui.stylesheet", "url('/Users/ivan.azanov/Documents/Spark/JavaAsASecondLanguage/homework04/src/main/resources/style.css')");
        visualGraph.setAttribute("ui.stylesheet", STYLESHEET);

        visualGraph.setAttribute("layout.force", 1.5);
        visualGraph.setAttribute("layout.quality", 1);

        Map<ProcNode, Node> procVisualNodeMapping = new LinkedHashMap<>();
        for (ProcNode procNode : procGraph.getInputNodes()) {
            visit(procNode, visualGraph, null, procVisualNodeMapping);
        }

        SwingViewer viewer = new SwingViewer(visualGraph, ThreadingModel.GRAPH_IN_ANOTHER_THREAD);
        viewer.enableAutoLayout();
        GraphRenderer renderer = new SwingGraphRenderer();
        viewer.addView(SwingViewer.DEFAULT_VIEW_ID, renderer);
    }

    private static void visit(ProcNode currentProcNode,
                              Graph visualGraph,
                              Node previousVisualNode,
                              Map<ProcNode, Node> procVisualNodeMapping) {
        if (procVisualNodeMapping.containsKey(currentProcNode)) {
            Node currentVisualNode = procVisualNodeMapping.get(currentProcNode);
            createEdge(visualGraph, previousVisualNode, currentVisualNode);
            return;
        }

        Node currentVisualNode = createVisualNode(currentProcNode, visualGraph);
        procVisualNodeMapping.put(currentProcNode, currentVisualNode);

        createMockInputNode(visualGraph, previousVisualNode, currentVisualNode);

        createMockOutputNode(visualGraph, currentProcNode, currentVisualNode);

        createEdge(visualGraph, previousVisualNode, currentVisualNode);


        for (var connection : currentProcNode.getCollector().getConnections()) {
            visit(connection.getNode(), visualGraph, currentVisualNode, procVisualNodeMapping);
        }
    }

    private static Node createVisualNode(ProcNode currentProcNode, Graph visualGraph) {
        String nodeLabel = createNodeLabel(currentProcNode);

        UUID nodeId = UUID.randomUUID();
        Node currentVisualNode = visualGraph.addNode(nodeLabel + nodeId);
        addCssClasses(currentProcNode, currentVisualNode);
        currentVisualNode.setAttribute("ui.label", nodeLabel);

        return currentVisualNode;
    }

    private static String createNodeLabel(ProcNode procNode) {
        String nodeLabel = "";
        Object operator = null;

        if (procNode instanceof ReducerNode) {
            operator = ((ReducerNode) procNode).getReducer();
        } else if (procNode instanceof MapperNode) {
            operator = ((MapperNode) procNode).getMapper();
        }

        if (operator != null) {
            nodeLabel = operator.getClass().getSimpleName();
        } else {
            nodeLabel = procNode.getClass().getSimpleName();
        }
        return nodeLabel;
    }

    private static void addCssClasses(ProcNode currentProcNode, Node currentVisualNode) {
        if (currentProcNode instanceof ReducerNode) {
            currentVisualNode.setAttribute("ui.class", "reducer");
        } else if (currentProcNode instanceof SorterNode) {
            currentVisualNode.setAttribute("ui.class", "sorter");
        } else if (currentProcNode instanceof JoinerNode) {
            currentVisualNode.setAttribute("ui.class", "joiner");
        }
    }

    private static void createMockInputNode(Graph visualGraph, Node previousVisualNode, Node currentVisualNode) {
        if (previousVisualNode != null) {
            return;
        }

        var inputNode = visualGraph.addNode("input" + UUID.randomUUID());
        inputNode.setAttribute("ui.label", "Input");
        inputNode.setAttribute("ui.class", "input");
        inputNode.setAttribute("y", 1000);
        createEdge(visualGraph, inputNode, currentVisualNode);
    }

    private static void createMockOutputNode(Graph visualGraph, ProcNode currentProcNode, Node currentVisualNode) {
        if (!currentProcNode.getCollector().getConnections().isEmpty()) {
            return;
        }

        var outputNode = visualGraph.addNode("input" + UUID.randomUUID());
        outputNode.setAttribute("ui.label", "Output");
        outputNode.setAttribute("ui.class", "output");
        createEdge(visualGraph, currentVisualNode, outputNode);
    }

    private static void createEdge(Graph visualGraph, Node fromVisualNode, Node toVisualNode) {
        if (fromVisualNode == null) {
            return;
        }

        var edgeId = UUID.randomUUID().toString();
        visualGraph.addEdge(edgeId, fromVisualNode, toVisualNode, true);
    }

    public static final String STYLESHEET = "/*\n" +
            "*/\n" +
            "graph {\n" +
            "    padding: 100px, 100px;\n" +
            "}\n" +
            "\n" +
            "node {\n" +
            "    text-size: 15;\n" +
            "    text-alignment: at-right;\n" +
            "    text-offset: 0, 0;\n" +
            "    text-background-mode: plain;\n" +
            "\n" +
            "    size: 15px;\n" +
            "    fill-color: #fefeff;\n" +
            "    stroke-color: #3c3c3b;\n" +
            "    stroke-mode: plain;\n" +
            "    stroke-width: 3;\n" +
            "}\n" +
            "\n" +
            "\n" +
            "node.input {\n" +
            "    text-style: bold;\n" +
            "    fill-color: #d1e651;\n" +
            "}\n" +
            "\n" +
            "node.output {\n" +
            "    text-style: bold;\n" +
            "    fill-color: #fd2a38;\n" +
            "}\n" +
            "\n" +
            "node.reducer {\n" +
            "    fill-color: #27ace4;\n" +
            "}\n" +
            "\n" +
            "node.sorter {\n" +
            "    fill-color: #FF3D7F;\n" +
            "}\n" +
            "\n" +
            "node.joiner {\n" +
            "    fill-color: #ffc453;\n" +
            "}\n" +
            "\n" +
            "edge {\n" +
            "    arrow-shape: arrow;\n" +
            "    arrow-size: 10px, 4px;\n" +
            "}";

}
