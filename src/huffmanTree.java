public class huffmanTree {
    char character;
    int frequency;
    huffmanTree left;
    huffmanTree right;

    huffmanTree(char character, int frequency, huffmanTree left, huffmanTree right)
    {
        this.character = character;
        this.frequency = frequency;
        this.left = left;
        this.right = right;
    }

    /*--Checking if it is the last value.--*/
    boolean isLeaf() {
        return left == null && right == null;
    }
}