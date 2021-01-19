public class App {
    
    public static void main(String args[]) throws Exception {
        System.out.println("Hello, World!");

        startConnection();
    }

    public static void startConnection(){
        GreetServer greetServer = new GreetServer(6666);
        Thread thread = new Thread(greetServer);
        thread.start();
    }
}
