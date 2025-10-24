package com.asupranovich.revision.sandbox.graph;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class SpringDependencyCycleCheck {

    public static void main(String[] args) {
        Node graph = new Node("A",
            List.of(
                new Node("B",
                    List.of(
                        new Node("C"),
                        new Node("D")
                    )
                ),
                new Node("C"),
                new Node("D")
            )
        );
        try {
            checkCycle(graph, new HashSet<>());
            System.out.println("No cycle detected");
        } catch (Exception e) {
            System.out.println("Cycle detected");
        }
    }

    private static void checkCycle(Node node, Set<String> path) {

        if (path.contains(node.val)) {
            throw new IllegalStateException("Cycle!");
        }

        if (!node.neighbors.isEmpty()) {
            path.add(node.val);
            node.neighbors.forEach(n -> checkCycle(n, path));
            path.remove(node.val);
        }
    }

    static class Node {

        public String val;
        public List<Node> neighbors;
        public Node() {
            this.neighbors = new ArrayList<>();
        }
        public Node(String val) {
            this.val = val;
            this.neighbors = new ArrayList<>();
        }
        public Node(String val, List<Node> neighbors) {
            this.val = val;
            this.neighbors = neighbors;
        }
    }

}
