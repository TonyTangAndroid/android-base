package sdk.api.interval;


public class Badge {


    private Badge() {
        throw new RuntimeException("No instance");
    }


    public static boolean login(String userName, String password) {
        return Usher.get().badgeRepo().login(userName, password);
    }


}
