package db;

import java.util.ArrayList;

public interface DbStrategy {
    ArrayList load();
    void save(ArrayList content);
}
