package by.rudko.dbping.conf;

public class SqlServerConfig {
    public String driver = "com.microsoft.sqlserver.jdbc.SQLServerDriver";
    public String urlTemplate = "jdbc:sqlserver://${host}:${port};database=${database};";
}
