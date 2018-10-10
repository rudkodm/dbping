package by.rudko.dbping;
import picocli.CommandLine.Option;

public class Params {

    @Option(names = {"--help"}, usageHelp = true, description = "display this help message")
    boolean usageHelpRequested;



    @Option(names = {"-u", "--user"}, required = true, description = "User name")
    String user;

    @Option(names = {"-p", "--password"}, required = true, description = "User password")
    String password;

    @Option(names = {"--host"}, description = "Server host")
    String host;

    @Option(names = {"--port"}, description = "Server port")
    public String port;

    @Option(names = {"-t", "--type"}, description = "Server type: <sqlserver>")
    String type;

    @Option(names = {"-d", "--database"}, description = "Database")
    String database;
}
