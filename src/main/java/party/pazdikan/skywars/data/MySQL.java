package party.pazdikan.skywars.data;

import party.pazdikan.skywars.Skywars;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;
import java.util.UUID;

public class MySQL extends Database {
    private String host = Skywars.getInstance().getConfig().getString("database.host");
    private String port = Skywars.getInstance().getConfig().getString("database.port");
    private String database = Skywars.getInstance().getConfig().getString("database.database");
    private String username = Skywars.getInstance().getConfig().getString("database.username");
    private String password = Skywars.getInstance().getConfig().getString("database.password");
    private String useSSL = Skywars.getInstance().getConfig().getString("database.useSSL");
    private String table = Skywars.getInstance().getConfig().getString("database.table");

    private Connection connection;

    public boolean isConnected() { return connection != null; }

    public void connect() {
        try {
            if (!isConnected())
                connection = DriverManager.getConnection("jdbc:mysql://" + host + ":" + port + "/" + database + "?useSSL=" + useSSL, username, password);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void disconnect() {
        if (isConnected())
            try {
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
    }

    public Connection getConnection() {
        return connection;
    }

    String[] SQLVariables = {
            "UUID VARCHAR(100)",
            "GOLD DOUBLE DEFAULT 0",
            "SOULS INT DEFAULT 0",
            "KILLS INT DEFAULT 0",
            "DEATHS INT DEFAULT 0",
            "WINS INT DEFAULT 0",
            "LOSES INT DEFAULT 0",
            "WINSTREAK INT DEFAULT 0",
            "XP DOUBLE DEFAULT 0",
            "XP_TOTAL DOUBLE DEFAULT 0",
            "LEVEL INT DEFAULT 1",
            "PRESTIGE INT DEFAULT 0",
            "CURRENTKIT TEXT DEFAULT 'Rookie'",
            "KILLEFFECT TEXT DEFAULT 'None'",
            "KILLMESSAGE TEXT DEFAULT 'Default'",
            "UNLOCKEDKITS TEXT DEFAULT 'Rookie,'",
            "UNLOCKEDKILLEFFECTS TEXT DEFAULT 'None,'",
            "UNLOCKEDKILLMESSAGES TEXT DEFAULT 'Default,'",

            "PRIMARY KEY (UUID)"
    };

    public void createTable() {
        Skywars.getInstance().getLogger().info("CREATE TABLE IF NOT EXISTS " + table +
                " (" + String.join(", ", SQLVariables) + ")");
        try {
            PreparedStatement ps = getConnection().prepareStatement("CREATE TABLE IF NOT EXISTS " + table +
                    " (" + String.join(", ", SQLVariables) + ")");
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void createPlayer(UUID uuid) {

    }

    @Override
    public boolean exists(UUID uuid) {
        return false;
    }

    @Override
    public void addInt(UUID uuid, String name, int value) {

    }

    @Override
    public void subtractInt(UUID uuid, String name, int value) {

    }

    @Override
    public void setInt(UUID uuid, String name, int value) {

    }

    @Override
    public int getInt(UUID uuid, String name) {
        return 0;
    }

    @Override
    public void addDouble(UUID uuid, String name, double value) {

    }

    @Override
    public void subtractDouble(UUID uuid, String name, double value) {

    }

    @Override
    public void setDouble(UUID uuid, String name, double value) {

    }

    @Override
    public double getDouble(UUID uuid, String name) {
        return 0;
    }

    @Override
    public void setString(UUID uuid, String name, String value) {

    }

    @Override
    public String getString(UUID uuid, String name) {
        return null;
    }

    @Override
    public void addToArray(UUID uuid, String name, String value) {

    }

    @Override
    public void removeFromArray(UUID uuid, String name, String value) {

    }

    @Override
    public void setArray(UUID uuid, String name, List<String> value) {

    }

    @Override
    public List<String> getArray(UUID uuid, String name) {
        return null;
    }
}
