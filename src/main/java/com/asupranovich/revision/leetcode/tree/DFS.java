package com.asupranovich.revision.leetcode.tree;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class DFS {

    public static void main(String[] args) {
        final DFS traversing = new DFS();
        TreeNode tree = new TreeNode(1,
            new TreeNode(2,
                new TreeNode(4),
                new TreeNode(5,
                    new TreeNode(6),
                    new TreeNode(7))),
            new TreeNode(3,
                null,
                new TreeNode(8,
                    new TreeNode(9),
                    null)));
        System.out.println(traversing.inorderTraversal(tree));
    }

    public List<Integer> inorderTraversal(TreeNode node) {
        if (node == null) {
            return Collections.emptyList();
        }
        List<Integer> values = new ArrayList<>();
        values.addAll(inorderTraversal(node.left));
        values.add(node.val);
        values.addAll(inorderTraversal(node.right));
        return values;
    }

    public List<Integer> preorderTraversal(TreeNode node) {
        if (node == null) {
            return Collections.emptyList();
        }
        List<Integer> values = new ArrayList<>();
        values.add(node.val);
        values.addAll(inorderTraversal(node.left));
        values.addAll(inorderTraversal(node.right));
        return values;
    }

    public List<Integer> postorderTraversal(TreeNode node) {
        if (node == null) {
            return Collections.emptyList();
        }
        List<Integer> values = new ArrayList<>();
        values.addAll(inorderTraversal(node.left));
        values.addAll(inorderTraversal(node.right));
        values.add(node.val);
        return values;
    }
}