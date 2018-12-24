package model.db;

import java.util.ArrayList;

public interface SettingsStrategy {
    ArrayList load();

    void save(ArrayList content);
}
