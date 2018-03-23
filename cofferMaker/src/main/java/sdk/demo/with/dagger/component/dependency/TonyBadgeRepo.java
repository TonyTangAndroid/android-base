package sdk.demo.with.dagger.component.dependency;


import javax.inject.Inject;

import sdk.api.BadgeRepo;

public class TonyBadgeRepo {


    private final BadgeRepo badgeRepo;

    @Inject
    public TonyBadgeRepo(BadgeRepo badgeRepo) {
        System.out.println("creating TonyBadgeRepo");
        this.badgeRepo = badgeRepo;
    }

    public boolean login(String userName, String password) {
        System.out.println("login TonyBadgeRepo ");
        return badgeRepo.login(userName, password);
    }

}
