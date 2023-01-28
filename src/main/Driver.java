package main;

class Driver {
    public static void main(String[] args) {
        Foo foo = new Foo();
        while (true) {
            System.out.println("Sending request...");
            String response = foo.sendRequest("dummy request");
            System.out.println("Response: " + response);
            System.out.println("main.Foo state: " + foo.getState());
            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}