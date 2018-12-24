package model.db;

public class BadDb {
    private static DatabaseContext instance;

    // TODO: REFACTOR
    public static DatabaseStrategy getInstance() {
        if (instance == null) {
            instance = new DatabaseContext();
            instance.setDatabaseStrategy(new TxtDatabaseStrategy());
            instance.getDatabaseStrategy().load();
        }

        return instance.getDatabaseStrategy();
    }
}
