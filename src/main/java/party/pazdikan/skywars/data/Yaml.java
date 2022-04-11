package party.pazdikan.skywars.data;

import java.util.List;
import java.util.UUID;

public class Yaml extends Database {
    public void connect() {

    }

    public void disconnect() {

    }

    @Override
    public boolean isConnected() {
        return false;
    }

    public Object getConnection() {
        return null;
    }

    public void createTable() {

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
