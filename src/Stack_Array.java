import java.util.ArrayList;
public class Stack_Array {
    private ArrayList<Country> countries;

    public Stack_Array() {
        countries = new ArrayList<>();
    }

    // Method to push a country onto the stack
    public void push(Country country) {
        countries.add(country);
    }

    // Method to remove and return the top country from the stack
    public Country pop() {
        if (isEmpty()) {
            System.out.println("Stack is empty");
            return null;
        }
        return countries.remove(countries.size() - 1);
    }

    // Method to return the top country from the stack without removing it
    public Country peek() {
        if (isEmpty()) {
            System.out.println("Stack is empty");
            return null;
        }
        return countries.get(countries.size() - 1);
    }

    // Method to check if the stack is empty
    public boolean isEmpty() {
        return countries.isEmpty();
    }
// problem 3
    public void best_Average_Worst() {
        if (isEmpty()) {
            System.out.println("Stack is empty");
            return;
        }

        Country best = countries.get(0);
        Country worst = countries.get(0);
        double total = 0;

        for (Country c : countries) {
            total += c.getLife_Expectancy();
            if (c.getLife_Expectancy() > best.getLife_Expectancy()) {
                best = c;
            }
            if (c.getLife_Expectancy() < worst.getLife_Expectancy()) {
                worst = c;
            }
        }
        double average = total / countries.size();
        System.out.println("The Best life Expectancy : \t " + best);
        System.out.println("The Average life Expectancy : \t " + average);
        System.out.println("The Worst life Expectancy : \t " + worst);
    }
}
