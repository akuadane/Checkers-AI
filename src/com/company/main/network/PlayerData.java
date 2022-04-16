package com.company.main.network;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class PlayerData {
    String name;
    public ObjectInputStream reader;
    public ObjectOutputStream writer;

    public PlayerData(String name, ObjectInputStream reader, ObjectOutputStream writer) {
        this.name = name;
        this.reader = reader;
        this.writer = writer;
    }
}
