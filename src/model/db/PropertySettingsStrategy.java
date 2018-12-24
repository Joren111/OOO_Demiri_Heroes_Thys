package model.db;

import java.io.*;
import java.util.ArrayList;
import java.util.Properties;

public class PropertySettingsStrategy implements SettingsStrategy {
    private Properties properties = new Properties();
    InputStream inputStream = null;
    OutputStream outputStream;
    ArrayList<String> content = new ArrayList<>();

    @Override
    public ArrayList load() {
        try {
            InputStream i = this.getClass().getResourceAsStream("/evaluation.properties");
            properties.load(i);
            content.add(properties.getProperty("evaluation.mode"));
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return content;
    }

    @Override
    public void save(ArrayList content) {
        ArrayList<String> strategies = (ArrayList<String>) content;
        String strategy = strategies.get(0);
        try {
            OutputStream outputStream = new FileOutputStream(new File(this.getClass().getResource("/evaluation.properties").getPath()));
            properties.setProperty("evaluation.mode", strategy);
            properties.store(outputStream, null);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (outputStream != null) {
                try {
                    outputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
