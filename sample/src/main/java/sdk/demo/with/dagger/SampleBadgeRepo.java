package sdk.demo.with.dagger;


import javax.inject.Inject;

import sdk.api.BadgeRepo;

public class SampleBadgeRepo {


    private final BadgeRepo badgeRepo;

    @Inject
    public SampleBadgeRepo(BadgeRepo badgeRepo) {
        System.out.println("creating SampleBadgeRepo");
        this.badgeRepo = badgeRepo;
    }

    public boolean login(String userName, String password) {
        System.out.println("login SampleBadgeRepo ");
        return badgeRepo.login(userName, password);
    }

}
