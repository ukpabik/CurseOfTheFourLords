package ai;

public class Node
{
    Node root;
    public int col,row;
    int gValue,fValue,hValue;
    boolean solidTile,openSpace,checkedSpace;
    
    public Node(int col, int row) {
        this.col = col;
        this.row = row;
    }
}
