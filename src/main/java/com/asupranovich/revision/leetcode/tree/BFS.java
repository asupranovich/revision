package com.asupranovich.revision.leetcode.tree;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class BFS {

    public static void main(String[] args) {
        final BFS traversing = new BFS();
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
        System.out.println(traversing.traverse(tree));
    }

    public List<Integer> traverse(TreeNode node) {
        if (node == null) {
            return Collections.emptyList();
        }
        List<Integer> values = new ArrayList<>();
        Queue<TreeNode> queue = new LinkedList<>();
        queue.add(node);

        while (!queue.isEmpty()) {
            final TreeNode treeNode = queue.poll();
            values.add(treeNode.val);
            if (treeNode.left != null) {
                queue.add(treeNode.left);
            }
            if (treeNode.right != null) {
                queue.add(treeNode.right);
            }
        }
        return values;
    }
}
