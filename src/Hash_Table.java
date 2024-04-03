import java.util.*;

interface Map {
    Object get(Object key);
    Object put(Object key, Object value);
    Object remove(Object key);
    int size();
}

class Country {
    String country_code;
    double life_Expectancy;
 String country_name;
    public Country(String country_name,String country_code, double life_Expectancy) {
        this.country_code = country_code;
        this.life_Expectancy = life_Expectancy;
        this.country_name= country_name;
    }

    public String getCountry_name(){
        return country_name;
    }
     public String getCountry_code(){
        return country_code;
    }

    public double getLife_Expectancy() {
        return life_Expectancy;
    }
@Override
    public String toString(){
        return  "Country code: "+country_code+":"+life_Expectancy +", ";
    }


}

class HashTable implements Map {
    private Entry[] entries;
    private int size;
    private float load_Factor;

    public HashTable(int capacity, float load_Factor) {
        entries = new Entry[capacity];
        this.load_Factor = load_Factor;
    }

    public HashTable(int capacity) {
        this(capacity, 0.75F);
    }

    public HashTable() {
        this(101);
    }

    public Object get(Object key) {
        int h = hash(key);
        for (Entry e = entries[h]; e != null; e = e.next) {
            if (e.key.equals(key)) {
                return e.value;
            }
        }
        return null;
    }
    public ArrayList<Object> getValues() {
        ArrayList<Object> values = new ArrayList<>();

        for (Entry entry : entries) {
            for (Entry e = entry; e != null; e = e.next) {
                values.add(e.value);
            }
        }

        return values;
    }
    public ArrayList<String> getKeys() {
        ArrayList<String> keys = new ArrayList<>();
        for (Entry entry : entries) {
            for (Entry e = entry; e != null; e = e.next) {
                keys.add((String) e.key);
            }
        }
        return keys;
    }


    public Object put(Object key, Object value) {
        int h = hash(key);
        for (Entry e = entries[h]; e != null; e = e.next) {
            if (e.key.equals(key)) {
                Object oldValue = e.value;
                e.value = value;
                return oldValue;
            }
        }
        entries[h] = new Entry(key, value, entries[h]);
        ++size;
        if (size > load_Factor * entries.length)
            rehash();
        return null;
    }

    public Object remove(Object key) {
        int h = hash(key);
        for (Entry e = entries[h], prev = null; e != null; prev = e, e = e.next) {
            if (e.key.equals(key)) {
                Object oldValue = e.value;
                if (prev == null)
                    entries[h] = e.next;
                else prev.next = e.next;
                --size;
                return oldValue;
            }
        }
        return null;
    }

    public int size() {
        return size;
    }

    private class Entry {
        Object key, value;
        Entry next;

        Entry(Object key, Object value, Entry next) {
            this.key = key;
            this.value = value;
            this.next = next;
        }
    }

    private int hash(Object key) {
        if (key == null) throw new IllegalArgumentException();
        return (key.hashCode() & 0x7FFFFFFF) % entries.length;
    }

    private void rehash() {
        Entry[] oldEntries = entries;
        entries = new Entry[2 * oldEntries.length + 1];
        for (int k = 0; k < oldEntries.length; k++) {
            for (Entry old = oldEntries[k]; old != null;) {
                Entry e = old;
                old = old.next;
                int h = hash(e.key);
                e.next = entries[h];
                entries[h] = e;
            }
        }
    }
}