package cz.codecamp.lunchbitch.services.authorizationService.crypto;

import org.springframework.stereotype.Component;

@Component
public class AuthKeyProvider {

    public String generateNextRandomAuthKey() {
        return new AuthKeyGenerator().generateNextRandomAuthKey();
    }
}
