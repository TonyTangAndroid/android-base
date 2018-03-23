package sdk.api;

import javax.inject.Inject;

public class LoginBadgeUseCase {

    private final BadgeRepo badgeRepo;

    @Inject
    public LoginBadgeUseCase(BadgeRepo badgeRepo) {
        System.out.println("creating LoginBadgeUseCase ");
        this.badgeRepo = badgeRepo;
    }

    public boolean login(String userName, String password) {
        return badgeRepo.login(userName, password);
    }
}
