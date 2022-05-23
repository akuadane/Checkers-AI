package com.company.models.players.mcts;

import java.util.ArrayList;


public class MCTSNode {

    private NodeState state;
    private MCTSNode parent;
    private ArrayList<MCTSNode> children;

    public MCTSNode(NodeState state){
        this.state = state;
        this.parent = null;
        this.children = new ArrayList<>();
    }
    public MCTSNode(NodeState state, MCTSNode parent){
        this(state);
        this.parent = parent;
    }

    public MCTSNode(MCTSNode node){
        this.state = new NodeState(node.state);
        this.parent = new MCTSNode(node.parent);
        this.children = (ArrayList<MCTSNode>) node.children.clone();
    }

    public NodeState getState() {
        return this.state;
    }

    public MCTSNode getParent() {
        return parent;
    }

    public ArrayList<MCTSNode> getChildren() {
        return children;
    }
    public void addChild(MCTSNode child){
        this.children.add(child);
    }
}
