package com.checkers.network;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

/**
 * Class PlayerData holds connection information in remote game
 */
public class PlayerData {
    String name;
    public ObjectInputStream reader;
    public ObjectOutputStream writer;

    public PlayerData(String name, ObjectInputStream reader, ObjectOutputStream writer) {
        this.name = name;
        this.reader = reader;
        this.writer = writer;
    }

    public PlayerData(ObjectOutputStream writer, ObjectInputStream reader) {
        this.writer = writer;
        this.reader = reader;
    }
}
