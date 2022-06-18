package com.checkers.network;

/**
 * Class JoinGame represents a join remote game action
 */
public class JoinGame extends Action {
    public String name;

    public JoinGame(String name) {
        this.name = name;
    }
}
