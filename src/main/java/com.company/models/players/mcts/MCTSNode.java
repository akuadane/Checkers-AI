package com.company.models.players.mcts;

import java.util.List;

public class MCTSNode {

    private NodeState state;
    private MCTSNode parent;
    private List<MCTSNode> children;


    public NodeState getState() {
        return state;
    }

    public MCTSNode getParent() {
        return parent;
    }

    public List<MCTSNode> getChildren() {
        return children;
    }
}
