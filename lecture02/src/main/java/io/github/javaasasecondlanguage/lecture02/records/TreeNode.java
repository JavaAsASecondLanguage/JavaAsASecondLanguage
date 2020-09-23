package io.github.javaasasecondlanguage.lecture02.records;

public record TreeNode(TreeNode left, TreeNode right, int value) {
    public static void main(String[] args) {
        System.out.println(new TreeNode(new TreeNode(null, null, 5), null, 42));
    }
}
