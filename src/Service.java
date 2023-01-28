public class Service {
    private static boolean healthy;

    public Service() {
        healthy = true;
    }

    public void setHealthy(boolean healthy) {
        this.healthy = healthy;
    }

    public boolean isHealthy() {
        return healthy;
    }

    public static String send(String request) throws Exception {
        if (healthy) {
            return "Request Successful";
        } else {
            throw new Exception("Request Failed");
        }
    }
}
