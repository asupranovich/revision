package com.asupranovich.revision.sandbox;

import java.util.List;

public class Sandbox {


    public static void main(String[] args) {
    }

    static class Solution {

        public int minExtraChar(String s, List<String> words) {
            TrieNode dictionary = getDictionary(words);

            int wordsCount = words.size();
            while (wordsCount > 0) {
                char[] sChars = s.toCharArray();
                for (int i = 0; i < sChars.length; i++) {
                    char c = sChars[i];
                    TrieNode node = dictionary.children[c - 'a'];
                    if (node == null) {
                        continue;
                    }
                    int j = i + 1;
                    for (; j < sChars.length; j++) {
                        c = sChars[j];
                        node = node.children[c - 'a'];
                        if (node == null || node.isEnd) {
                            break;
                        }
                    }
                    if (node != null && node.isEnd) {
                        System.out.println("i:" + i + ", j:" + j);
                        StringBuilder sBuilder = new StringBuilder(s);
                        s = sBuilder.delete(i, j + 1).toString();
                        System.out.println(s);
                        wordsCount--;
                        break;
                    }
                }
            }

            return s.length();
        }

        private TrieNode getDictionary(List<String> words) {
            TrieNode root = new TrieNode();
            for (String word : words) {
                System.out.println(word);
                TrieNode node = root;
                for (char c : word.toCharArray()) {
                    if (node.children[c - 'a'] == null) {
                        node.children[c - 'a'] = new TrieNode();
                    }
                    node = node.children[c - 'a'];
                }
                node.isEnd = true;
            }
            return root;
        }
    }

    static class TrieNode {
        TrieNode[] children = new TrieNode[26];
        boolean isEnd = false;
    }
}
