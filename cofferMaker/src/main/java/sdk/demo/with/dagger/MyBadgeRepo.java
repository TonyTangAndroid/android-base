package sdk.demo.with.dagger;


import javax.inject.Inject;

import sdk.api.BadgeRepo;

public class MyBadgeRepo {


    private final BadgeRepo badgeRepo;

    @Inject
    public MyBadgeRepo(BadgeRepo badgeRepo) {
        System.out.println("creating MyBadgeRepo");
        this.badgeRepo = badgeRepo;
    }

    public boolean login(String userName, String password) {
        System.out.println("login MyBadgeRepo ");
        return badgeRepo.login(userName, password);
    }

}
