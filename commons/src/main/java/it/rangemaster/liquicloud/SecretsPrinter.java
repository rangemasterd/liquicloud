package it.rangemaster.liquicloud;

import java.io.IOException;
import java.util.logging.Logger;

/**
 * The SecretsPrinter class is responsible for printing secrets to standard output based on specified paths.
 */
public class SecretsPrinter {

    private static final Logger logger;

    static {
        System.setProperty("java.util.logging.SimpleFormatter.format","[%1$tF %1$tT] %5$s %n");
        logger = Logger.getLogger(SecretsPrinter.class.getName());
    }

    private final SecretsAccessor secretsAccessor;

    public SecretsPrinter(SecretsAccessor secretsAccessor) {
        this.secretsAccessor = secretsAccessor;
    }

    /**
     * Prints secrets to standard output based on the provided command-line arguments.
     *
     * @param args The command-line arguments containing paths to secrets (URL, username, and password).
     * @throws IOException If an I/O error occurs while retrieving or printing the secrets.
     * @throws IllegalArgumentException If the number of command-line arguments is not equal to 3.
     */
    public void toStandardOutput(String[] args) throws IOException {
        if (args.length != 3) {
            throw new IllegalArgumentException("Required 3 arguments, but found " + args.length);
        }

        SecretPath dbUrlPath = new SecretPath(args[0]);
        SecretPath dbUsernamePath = new SecretPath(args[1]);
        SecretPath dbPasswordPath = new SecretPath(args[2]);

        logger.info("Retrieving secrets by " + secretsAccessor.getClass().getSimpleName());

        Secrets secrets = secretsAccessor.retrieve(dbUrlPath, dbUsernamePath, dbPasswordPath);

        System.out.printf("%s %s %s", secrets.url(), secrets.username(), secrets.password());
    }
}
