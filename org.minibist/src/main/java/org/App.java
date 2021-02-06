package org;

/**
 * Hello world!
 */
public final class App {
    private App() {
    }

    /**
     * Says hello to the world.
     * 
     * @param args The arguments of the program.
     */
    public static void main(String args[]) throws Exception {
        System.out.println("Hello, World!");

        startConnection();
    }

    public static void startConnection() {
        ServerInitializer greetServer = new ServerInitializer(6666);
        Thread thread = new Thread(greetServer);
        thread.start();
    }
}
