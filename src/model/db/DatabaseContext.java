package model.db;

public class DatabaseContext {
    private DatabaseStrategy databaseStrategy;

    public DatabaseStrategy getDatabaseStrategy() {
        return databaseStrategy;
    }

    public void setDatabaseStrategy(DatabaseStrategy databaseStrategy) {
        this.databaseStrategy = databaseStrategy;
    }
}
