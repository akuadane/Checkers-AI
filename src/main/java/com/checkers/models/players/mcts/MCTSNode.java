package com.checkers.models.players.mcts;

import java.util.ArrayList;
import java.util.Random;


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
    public MCTSNode getRandomChild(){
        if(this.children.size()==0)
            return null;
        Random random = new Random();
        return this.children.get(random.nextInt(0, this.children.size()));
    }
    public void addChild(MCTSNode child){
        this.children.add(child);
    }
    public void incrementPlays(){this.state.incrementPlays();}
    public void incrementWins(){this.state.incrementWins();}
}
