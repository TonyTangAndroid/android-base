package sdk.demo.with.dagger;

import javax.inject.Inject;

public class SampleLoginBadgeUseCase {

    private final SampleBadgeRepo badgeRepo;

    @Inject
    public SampleLoginBadgeUseCase(SampleBadgeRepo badgeRepo) {
        System.out.println("creating SampleLoginBadgeUseCase.");
        this.badgeRepo = badgeRepo;
    }

    public boolean login(String userName, String password) {
        System.out.println("login SampleLoginBadgeUseCase.");
        return badgeRepo.login(userName, password);
    }
}
