package sdk.demo.with.dagger.component.dependency;

import javax.inject.Inject;

import sdk.api.BadgeRepo;

public class TonyBadgeUseCase {

    private final TonyBadgeRepo tonyBadgeRepo;
    private final BadgeRepo badgeRepo;

    @Inject
    public TonyBadgeUseCase(TonyBadgeRepo tonyBadgeRepo, BadgeRepo badgeRepo) {
        this.badgeRepo = badgeRepo;
        System.out.println("creating TonyBadgeUseCase ");
        this.tonyBadgeRepo = tonyBadgeRepo;
    }

    public boolean login(String userName, String password) {
        System.out.println("login TonyBadgeUseCase using badgeRepo");
        return badgeRepo.login(userName, password);
    }

    public boolean loginTony(String userName, String password) {
        System.out.println("login TonyBadgeUseCase using tonyBadgeRepo");
        return tonyBadgeRepo.login(userName, password);
    }
}
