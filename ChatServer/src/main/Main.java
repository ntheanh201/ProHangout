package main;

import avatarserver.AvatarServer;
import chatserver.ChatServer;

public class Main {
    public static void main(String[] args) {
        String[] arguments = new String[] {};
        Thread chatThread = new Thread(() -> new ChatServer().main(arguments));
        Thread avatarThread = new Thread(() -> new AvatarServer().main(arguments));
        chatThread.start();
        avatarThread.start();
    }
}
