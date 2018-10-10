package by.rudko.dbping;

import by.rudko.dbping.conf.SqlServerConfig;
import org.apache.commons.lang.text.StrSubstitutor;
import picocli.CommandLine;

import java.sql.Connection;
import java.sql.DriverManager;
import java.util.HashMap;
import java.util.Map;

public class JdbcPing {
    public static void main(String[] args) {
        SqlServerConfig dbConf = new SqlServerConfig();
        Params params = CommandLine.populateCommand(new Params(), args);
        if (params.usageHelpRequested) {
            CommandLine.usage(new Params(), System.out);
            return;
        }

        String driverClass = dbConf.driver;
        String jdbcConnectionString = new StrSubstitutor(mapOf(
                Pair.of("host", params.host),
                Pair.of("port", params.port),
                Pair.of("database", params.database)
                )
        ).replace(dbConf.urlTemplate);
        String user = params.user;
        String password = params.password;

        System.out.println(driverClass);
        System.out.println(jdbcConnectionString);
        System.out.println(user);
        System.out.println(password);

        try {
            Class.forName(driverClass);
        } catch (ClassNotFoundException e) {
            System.out.println("Unable to load specified JDBC driver. Please check that driver is available on classpath.");
            e.printStackTrace();
        }

        try {
            Connection connection = DriverManager.getConnection(jdbcConnectionString, user, password);
            String dbProductName = connection.getMetaData().getDatabaseProductName();
            String dbProductVersion = connection.getMetaData().getDatabaseProductVersion();
            System.out.println("Successfully connected to " + dbProductName + " " + dbProductVersion);
        } catch (Exception e) {
            System.out.println("Failed to connect to database");
            e.printStackTrace();
        }
    }

    private static Map<String, String> mapOf(Pair ... args) {
        Map<String, String> map = new HashMap<>();
        for (Pair p : args) {
            map.put(p.a, p.b);
        }
        return map;
    }

    public static String interpolate(String text, Map<String, String> params) {
        for (Map.Entry<String, String> entry : params.entrySet()) {
            String key = entry.getKey();
            String value = entry.getValue();
            text = text.replaceAll("\\$\\{" + key + "\\}", value);
        }
        return text;
    }
}

class Pair {

    public final String a;
    public final String b;

    public Pair(String a, String b) {
        this.a = a;
        this.b = b;
    }

    public static Pair of(String a, String b) {
        return new Pair(a,b);
    }
}
