package sdk.demo.with.dagger;

import javax.inject.Inject;

public class MyLoginBadgeUseCase {

    private final MyBadgeRepo badgeRepo;

    @Inject
    public MyLoginBadgeUseCase(MyBadgeRepo badgeRepo) {
        System.out.println("creating MyLoginBadgeUseCase ");
        this.badgeRepo = badgeRepo;
    }

    public boolean login(String userName, String password) {
        System.out.println("login MyLoginBadgeUseCase ");

        return badgeRepo.login(userName, password);
    }
}
