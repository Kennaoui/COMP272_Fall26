/**
 * COMP 272 Assignment 4 — Exercise 1.
 *
 * This is the AVLTree class from Module 4, unchanged except for delete().
 * Implement delete(). Do not change any other method, name or signature.
 * You may add private helper methods.
 */
public class AVLTree extends BinarySearchTree {

    @Override
    public Node add(int value) {
        // First perform ordinary BST insertion.
        Node inserted = super.add(value);

        if (inserted != null) {
            // The new leaf already has height 0.
            rebalanceUpward(inserted.parent);
        }

        return inserted;
    }

    /**
     * Positive: left subtree is taller.
     * Negative: right subtree is taller.
     */
    private int balanceFactor(Node node) {
        return height(node.left) - height(node.right);
    }

    /**
     * Update heights and restore balance from bottom to top.
     */
    private void rebalanceUpward(Node node) {
        while (node != null) {
            updateHeight(node);

            Node subtreeRoot = node;
            int balance = balanceFactor(node);

            if (balance > 1) {
                // Left-right case.
                if (balanceFactor(node.left) < 0) {
                    rotateLeft(node.left);
                }

                // Left-left case, or second left-right rotation.
                subtreeRoot = rotateRight(node);

            } else if (balance < -1) {
                // Right-left case.
                if (balanceFactor(node.right) > 0) {
                    rotateRight(node.right);
                }

                // Right-right case, or second right-left rotation.
                subtreeRoot = rotateLeft(node);
            }

            node = subtreeRoot.parent;
        }
    }

    /**
     * Rotate a subtree left and return its new root.
     */
    private Node rotateLeft(Node oldRoot) {
        Node newRoot = oldRoot.right;
        Node middle = newRoot.left;

        replaceAtParent(oldRoot, newRoot);

        newRoot.left = oldRoot;
        oldRoot.parent = newRoot;

        oldRoot.right = middle;
        if (middle != null) {
            middle.parent = oldRoot;
        }

        // Order matters: update the lower node first.
        updateHeight(oldRoot);
        updateHeight(newRoot);

        return newRoot;
    }

    /**
     * Rotate a subtree right and return its new root.
     */
    private Node rotateRight(Node oldRoot) {
        Node newRoot = oldRoot.left;
        Node middle = newRoot.right;

        replaceAtParent(oldRoot, newRoot);

        newRoot.right = oldRoot;
        oldRoot.parent = newRoot;

        oldRoot.left = middle;
        if (middle != null) {
            middle.parent = oldRoot;
        }

        // Order matters: update the lower node first.
        updateHeight(oldRoot);
        updateHeight(newRoot);

        return newRoot;
    }

    /**
     * Connect the new subtree root to the original parent.
     */
    private void replaceAtParent(Node oldRoot, Node newRoot) {
        Node parent = oldRoot.parent;
        newRoot.parent = parent;

        if (parent == null) {
            root = newRoot;
        } else if (parent.left == oldRoot) {
            parent.left = newRoot;
        } else {
            parent.right = newRoot;
        }
    }

    @Override
    public boolean delete(int value) {
        Node target = findNode(value);
        if (target == null) {
            return false;
        }

        // Two children: copy the successor's value, then remove the successor.
        if (target.left != null && target.right != null) {
            Node successor = minimum(target.right);
            target.value = successor.value;
            target = successor;
        }

        // Now target has at most one child.
        Node child = (target.left != null) ? target.left : target.right;
        Node parentOfRemoved = target.parent;

        transplant(target, child);
        size--;
        rebalanceUpward(parentOfRemoved);
        return true;
    }

    /*
     * ======================== Exercise 1, Part B — Analysis ========================
     * Question 1: The worst-case running is O(log n). This is because an AVL tree has n nodes
     * and height h, and every node is balanced within 1 of the next one. It'll cost O(h), or O(log n) 
     * for a step.
     * - Finding the node, is O(h), finding the successor is also only O(h). - Removing is just copying values and changing pointers, so only O(1)
     * - Rebalancing costs O(h) because it starts at the parent of the remove node, which can only have a max of h moves.
     * O(h) + O(h) + O(1) + O(h) = O(log n).
     * 
     * Question 2. One delete can perform O(log n) rotations in the worst case, if there is a rotation at every level.
     * An insertion only needs only one restructure. This is because if a subtree has a height of h, an insertion would make it h+2.
     * Having either rotation type would bring it to h+1, which is the height before the insertion. 
     * 
     * ===============================================================================
     */

}
