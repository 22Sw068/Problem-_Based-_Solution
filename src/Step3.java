import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
public class Step3 {
    public static void main(String[] args) {
        String path = "D:\\global life expectancy dataset.csv";
        HashTable country_table = new HashTable();
        try (BufferedReader buff_reader = new BufferedReader(new FileReader(path))) {
            String line;
            buff_reader.readLine(); // Skip headers
            while ((line = buff_reader.readLine()) != null) {
                String[] data = line.split(",", -1);
                String country_name = data[0];
                String country_code = data[1];
                double totalLifeExpectancy = 0;
                int numYears = 0;

                for (int i = 2; i < data.length; i++) {
                    if (!data[i].isEmpty()) {
                        try {
                            totalLifeExpectancy += Double.parseDouble(data[i]);
                            numYears++;
                        } catch (NumberFormatException e) {
                            System.out.println(e.getMessage());
                        }
                    }
                }
                double averageLifeExpectancy = totalLifeExpectancy / numYears;

                Country country = new Country(country_name, country_code, averageLifeExpectancy);
                country_table.put(country_name, country);
            }
            ArrayList<String> countryNames = new ArrayList<>();
            ArrayList<Double> averageLifeExpectancies = new ArrayList<>();
            for (Object key : country_table.getKeys()) {
                String countryName = (String) key;
                Country country = (Country) country_table.get(countryName);
                countryNames.add(countryName);
                averageLifeExpectancies.add(country.getLife_Expectancy());
            }
            Sorting_data(averageLifeExpectancies,countryNames);

            Stack_Array stack = new Stack_Array();
            int count=1;
            for (int i = 0; i < countryNames.size(); i++) {
                String countryName = countryNames.get(i);
                Country country = (Country) country_table.get(countryName);
                stack.push(country);
            }

            stack.best_Average_Worst();
            System.out.println("_______________________________________");
            for(int i=0;i<countryNames.size();i++){
                System.out.println(count+" :"+stack.pop());
                count++;

            }

        } catch (IOException e) {
            System.out.println("Error reading file: " + e.getMessage());
        }
    }

    public static void Sorting_data(ArrayList<Double> lifeExpectancies, ArrayList<String> countryNames) {
        for (int i = 0; i < lifeExpectancies.size() - 1; i++) {
            for (int j = i + 1; j < lifeExpectancies.size(); j++) {
                if (lifeExpectancies.get(i) > lifeExpectancies.get(j)) {
                    // Swap average life expectancies
                    double tempLifeExpectancy = lifeExpectancies.get(i);
                    lifeExpectancies.set(i, lifeExpectancies.get(j));
                    lifeExpectancies.set(j, tempLifeExpectancy);
                    // Swap corresponding country names
                    String tempCountryName = countryNames.get(i);
                    countryNames.set(i, countryNames.get(j));
                    countryNames.set(j, tempCountryName);
                }
            }
        }
    }
}
