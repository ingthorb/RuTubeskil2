package server;

public class Server {


    public static void main(String[] args ) throws Exception {


        //Initialize the server.
        final App app = new App();
        System.out.println("App starting!");

        //Start the server.
        app.start();

        //Incase something happens, gracefully shutdown.
        Runtime.getRuntime().addShutdownHook(new Thread(){
            public void run(){
                app.close();
                System.out.println("App exiting!");

            }
        });
        try {
            Thread.currentThread().join();
        } catch(InterruptedException e){
            //TODO: add error logging here to know what happened.
            System.out.println("Interrupted when waiting in Server");
        }
    }
}
