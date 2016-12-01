package cz.codecamp.lunchbitch.services.authorizationService.crypto;

import java.security.SecureRandom;
import java.util.Random;

/**
 * Generator of securely random alphanumeric strings.
 * <p>
 * Not thread-safe.
 *
 * @link http//www.stackoverflow.com/questions/7111651/how-to-generate-a-secure-random-alphanumeric-string-in-java
 * -efficiently
 */
public class AuthKeyGenerator {

    private static final int AUTH_KEY_LENGTH = 16;
    private static final char[] ALPHANUMERIC_CHARACTERS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456879".toCharArray();
    private static final int NUMBER_OF_ALPHANUMERIC_CHARACTERS = ALPHANUMERIC_CHARACTERS.length;
    private static final float NUMBER_ENTROPY_BITS_NEEDED_FOR_ONE_RANDOM_ALPHANUMERIC_CHARACTER = (float) Math.log
            (NUMBER_OF_ALPHANUMERIC_CHARACTERS);
    private static final float NUMBER_OF_ENTROPY_BITS_GENERATED_BY_SECURE_NEXT_LONG = 64;

    private SecureRandom secureRandom = new SecureRandom();
    private Random random = new Random();
    private float secureEntropyInBits = 0;

    public AuthKeyGenerator() {
    }

    public String generateNextRandomAuthKey() {
        char[] buffer = new char[AUTH_KEY_LENGTH];
        for (int i = 0; i < AUTH_KEY_LENGTH; i++) {
            buffer[i] = getSecureRandomCharacter();
        }
        return new String(buffer);
    }

    private char getSecureRandomCharacter() {
        if (notEnoughSecureEntropyLeft()) {
            addSecureEntropy();
        }
        subtractUsedSecureEntropy();
        return getRandomAlphanumericCharacter();
    }

    private boolean notEnoughSecureEntropyLeft() {
        return secureEntropyInBits < NUMBER_ENTROPY_BITS_NEEDED_FOR_ONE_RANDOM_ALPHANUMERIC_CHARACTER;
    }

    private void addSecureEntropy() {
        random.setSeed(secureRandom.nextLong());
        secureEntropyInBits = NUMBER_OF_ENTROPY_BITS_GENERATED_BY_SECURE_NEXT_LONG;
    }

    private void subtractUsedSecureEntropy() {
        secureEntropyInBits -= NUMBER_ENTROPY_BITS_NEEDED_FOR_ONE_RANDOM_ALPHANUMERIC_CHARACTER;
    }

    private char getRandomAlphanumericCharacter() {
        return ALPHANUMERIC_CHARACTERS[getRandomAlphanumericIndex()];
    }

    private int getRandomAlphanumericIndex() {
        return random.nextInt(NUMBER_OF_ALPHANUMERIC_CHARACTERS);
    }

}
