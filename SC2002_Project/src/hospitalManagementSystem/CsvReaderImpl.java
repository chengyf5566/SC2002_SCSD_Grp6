package hospitalManagementSystem;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class CsvReaderImpl implements CsvReader {

    @Override
    public void readCsv(String filePath) {
        String line;
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            while ((line = br.readLine()) != null) {
                String[] values = line.split(","); // Split by comma
                for (String value : values) {
                    System.out.print(value + " ");
                }
                System.out.println();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}