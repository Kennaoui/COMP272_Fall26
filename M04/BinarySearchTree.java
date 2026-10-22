public class BinarySearchTree {

    protected Node root;

    public static class Node {
        protected int value;
        protected Node left;
        protected Node right;
        protected Node parent;

        // Empty subtree: height -1. Leaf: height 0.
        protected int height = 0;

        protected Node(int value, Node parent) {
            this.value = value;
            this.parent = parent;
        }
    }

    /**
     * Insert value using ordinary BST insertion.
     *
     * Return the newly inserted node, or null for a duplicate.
     * Set its parent pointer and update root when necessary.
     */
    public Node add(int value) {
        // TODO: Students implement this method.
        throw new UnsupportedOperationException("Implement BST add");
    }

    /**
     * Remove value if present.
     * Return true if a node was removed, otherwise false.
     * Maintain parent pointers and root when necessary.
     */
    public boolean delete(int value) {
        // TODO: Students implement this method.
        throw new UnsupportedOperationException("Implement BST delete");
    }

    /**
     * Return whether value occurs in the tree.
     */
    public boolean search(int value) {
        // TODO: Students implement this method.
        throw new UnsupportedOperationException("Implement BST search");
    }

    /**
     * Read the stored height in O(1).
     * This method does not recursively traverse the subtree.
     */
    protected int height(Node node) {
        return node == null ? -1 : node.height;
    }

    /**
     * Update one node's height in O(1).
     * Its children's stored heights must already be correct.
     */
    protected void updateHeight(Node node) {
        node.height = 1 + Math.max(
            height(node.left),
            height(node.right)
        );
    }
}
