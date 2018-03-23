package dream;

import javax.inject.Inject;

public class AppStateDataRepository {


    @Inject
    public AppStateDataRepository() {
        System.out.println("AppRepo created");
    }


    public boolean appOnForeground() {
        return true;
    }
}
