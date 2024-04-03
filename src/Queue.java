class Queue   {

    private Object[] country_data;
    private int size;
    public Queue(int capacity) {
        country_data = new Object[capacity];
    }
    public void add(Object object) {
        country_data[size++] = object;

    }

    public Object[] getCountry_data() {
        return country_data;
    }



    public void display() {
        if (size == 0) {
            System.out.println("queue is empty");
            return;
        }
        System.out.print(": ");
        for (int i = 0; i < size; i++) {
            System.out.print(country_data[i] + " ");
        }
        System.out.println();
    }

}
