package party.pazdikan.skywars.data;

import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

public abstract class Database {
    public abstract void connect();

    public abstract void disconnect();

    public abstract boolean isConnected();

    public abstract Object getConnection();

    public abstract void createTable();

    public abstract void createPlayer(UUID uuid);

    public abstract boolean exists(UUID uuid);

    public abstract void addInt(UUID uuid, String name, int value);

    public abstract void subtractInt(UUID uuid, String name, int value);

    public abstract void setInt(UUID uuid, String name, int value);

    public abstract int getInt(UUID uuid, String name);

    public abstract void addDouble(UUID uuid, String name, double value);

    public abstract void subtractDouble(UUID uuid, String name, double value);

    public abstract void setDouble(UUID uuid, String name, double value);

    public abstract double getDouble(UUID uuid, String name);

    public abstract void setString(UUID uuid, String name, String value);

    public abstract String getString(UUID uuid, String name);

    public abstract void addToArray(UUID uuid, String name, String value);

    public abstract void removeFromArray(UUID uuid, String name, String value);

    public abstract void setArray(UUID uuid, String name, List<String> value);

    public abstract List<String> getArray(UUID uuid, String name);
}
