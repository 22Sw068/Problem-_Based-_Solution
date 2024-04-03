
import java.util.*;
import java.io.*;

public class Main {
    public static void Sorting_data(ArrayList<Country> countries) {
        for (int i = 0; i < countries.size() - 1; i++) {
            for (int j = i + 1; j < countries.size(); j++) {
                if (countries.get(i).life_Expectancy > countries.get(j).life_Expectancy) {
                    Country temp = countries.get(i);
                    countries.set(i, countries.get(j));
                    countries.set(j, temp);
                }
            }
        }
    }
    public static void LifeExpectancy_ByYear(String countryCode, HashTable[] yearlyExpectancy) {
        for (int i = 0; i < yearlyExpectancy.length; i++) {
            Country country = (Country) yearlyExpectancy[i].get(countryCode);
            if (country != null) {
                System.out.println("Year " + (i + 1960) + ": " + country.getLife_Expectancy());
            } else {
                System.out.println("Year " + (i + 1960) + ": Data not available for country code " + countryCode);
            }
        }
    }
    public static void BestLifeExpectancy(HashTable[] yearlyExpectancy, int year) {
        ArrayList<Country> countries = new ArrayList<>();
        HashTable yearHashTable = yearlyExpectancy[year - 1960];

        for (Object value : yearHashTable.getValues()) {
            countries.add((Country) value);
        }

        Sorting_data(countries);

        System.out.println("Year " + year + ":");
        int startIdx = Math.max(0, countries.size() - 3); // Start index for loop
        for (int i = countries.size() - 1; i >= startIdx; i--) {
            Country country = countries.get(i);
            System.out.println(country.getCountry_name() + ": " + country.getLife_Expectancy());
        }
    }


    public static boolean countriesStartWithA(ArrayList<Country> countries) {
        double totalLifeExpA = 0.0;
        double countA = 0.0;
        double totalLifeExpOther = 0.0;
        double countOther = 0.0;

        for (Country country : countries) {
            if (country.getCountry_name().toUpperCase().startsWith("A")) {
                totalLifeExpA += country.getLife_Expectancy();
                countA++;
            } else {
                totalLifeExpOther += country.getLife_Expectancy();
                countOther++;
            }
        }

        double averageLifeExpA = (countA != 0.0) ? totalLifeExpA / countA : 0.0;
        double averageLifeExpOther = (countOther != 0.0) ? totalLifeExpOther / countOther : 0.0;

        return averageLifeExpA > averageLifeExpOther;
    }


    public static void main(String[] args) {

        String path = "D:\\global life expectancy dataset.csv";
        ArrayList<String[]> countries_data = new ArrayList<>();
        ArrayList<Country> country_data= new ArrayList<>();
        HashTable[] yearly_Expectancy = null; // Array of HashTable
        try (BufferedReader buff_reader = new BufferedReader(new FileReader(path))) {
            String line = buff_reader.readLine();
            String[] headers = line.split(",", -1);
            int numYears = headers.length - 2;
            yearly_Expectancy = new HashTable[numYears]; // Initialize array

            for (int i = 0; i < yearly_Expectancy.length; i++) {
                yearly_Expectancy[i] = new HashTable();
            }

            while ((line = buff_reader.readLine()) != null) {
                String[] data = line.split(",", -1);
                countries_data.add(data);
            }

            for (String[] countryData : countries_data) {
                String country_code = countryData[1];
                String country_name = countryData[0];
                for (int year = 0; year < numYears; year++) {
                    double expectancy;
                    try {
                        expectancy = Double.parseDouble(countryData[year + 2]);
                    } catch (NumberFormatException e) {
                        expectancy = 0.0;
                    }
                    Country country = new Country(country_name, country_code, expectancy);
                    yearly_Expectancy[year].put(country_code, country);
                 country_data.add(country);
                }
            }

            Queue[] country_queue = new Queue[numYears];
            for (int year = 0; year < numYears; year++) {
                ArrayList<Country> country_list = new ArrayList<>();
                ArrayList<String> keys = yearly_Expectancy[year].getKeys();
                for (String key : keys) {
                    Country country = (Country) yearly_Expectancy[year].get(key);
                    country_list.add(country);
                }

                Sorting_data(country_list);

                country_queue[year] = new Queue(country_list.size());
                for (Country country : country_list) {
                    country_queue[year].add(country.getCountry_name() + ": " + country.getLife_Expectancy());
                }

               System.out.print("Year " + (year + 1960) + ": ");
                country_queue[year].display();

            }

            Scanner scanner = new Scanner(System.in);
            System.out.print("Enter the country code: ");
            String input_code = scanner.nextLine().toUpperCase();
            System.out.print("Enter the year (starting from 1960): ");
            int input_year;
            try {
                input_year = Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Invalid year format.");
                return;
            }
            Country country = (Country) yearly_Expectancy[input_year - 1960].get(input_code);
            if (country != null) {
                System.out.println("Country: " + country.getCountry_name() + ", Code: " + country.getCountry_code() +
                        ", Life Expectancy: " + country.getLife_Expectancy() + " in year " + input_year);
            } else {
                System.out.println("Country with code " + input_code + " not found.");
            }
            // Problem 2
            System.out.println("______________problem 2 __________________________");
            System.out.println("Country name :   "+ country.getCountry_name()+" County Code : "+country.getCountry_code() );
            System.out.println(" Life Expectancies for All years");
            LifeExpectancy_ByYear(input_code,yearly_Expectancy);
            System.out.println("____________________problem 1_______________________");
            System.out.println("Countries with the best life expectancy in 1962:");
            BestLifeExpectancy(yearly_Expectancy, 1962);

            System.out.println("\nCountries with the best life expectancy in 1964:");
            BestLifeExpectancy(yearly_Expectancy, 1964);

            System.out.println("_____________________problem 4_______________________________");
            boolean isStartWithABetter = countriesStartWithA(country_data);
            if (isStartWithABetter) {
                System.out.println("True that  country Start with A  provide the better life expectancy.");
            } else {
                System.out.println("False  country provide the better life expectancy.");
            }

        } catch (FileNotFoundException e) {
            System.out.println("Error: File not found - " + path);
        } catch (IOException e) {
            System.out.println("Error reading file: " + e.getMessage());
        }
    }
}