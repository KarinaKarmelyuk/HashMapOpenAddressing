package hashmap;

/**
 * Realization of custom HashMap with open addressing.
 * Using double hashing algorithm to resolve the problem
 * of primary and secondary grouping.
 */

public class OpenAddressingHashMap {
    /* Can't use null for primitive types, so use max values of the types for marking empty cells.
     * That's why you can't use these max values for your key-value mappings */
    private static final long NO_VALUE = Long.MAX_VALUE;
    private static final int NO_KEY = Integer.MAX_VALUE;

    // default size of HashMap (fixed size)
    private final int DEFAULT_CAPACITY = 41; // Must be a prime number
    // size of HashMap (using size)
    private int CAPACITY;
    // size of key-value mappings in this map
    private int size = 0;
    // limit of hash table filling
    private int threshold;

    /*Prime size for HashMap is not divisible by any number (except for itself and a unit),
    so the sequence of samples will sooner or later check each cell.*/

    // References to arrays (arrays are used for keeping key-value mappings)
    private int[] keys;
    private long[] values;

    public OpenAddressingHashMap() { // Default constructor
        CAPACITY = DEFAULT_CAPACITY;
        keys = new int[CAPACITY];
        values = new long[CAPACITY];
        for(int i=0; i<CAPACITY; i++)
        {
            keys[i] = NO_KEY;
            values[i] = NO_VALUE;
        }

        threshold = (int)(CAPACITY * 0.75);
    }

    public OpenAddressingHashMap(int customCapacity) { // Custom constructor
        if(customCapacity > DEFAULT_CAPACITY) {
            if(isPrime(customCapacity)) // Check whether a prime number is given
                CAPACITY = customCapacity;
            else                        // If given number isn't prime, get the next prime number
                CAPACITY = getPrime(customCapacity);
        } else
            CAPACITY = DEFAULT_CAPACITY;

        keys = new int[CAPACITY];
        values = new long[CAPACITY];
        for(int i=0; i<CAPACITY; i++)
        {
            keys[i] = NO_KEY;
            values[i] = NO_VALUE;
        }

        threshold = (int)(CAPACITY * 0.75);
    }

    private boolean isPrime(int number) {
        for (int i = 2; (i * i <= number); i++)
            if (number % i == 0)
                return false;
        return true;
    }

    private int getPrime(int number) {
        for (int i = number+1; true; i++)
            if (isPrime(i))
                return i;
    }

    public int getThreshold() {
        return threshold;
    }

    // Methods for using double-hash algorithm
    private int hashFunc1(int key) {
        return (key % CAPACITY);
    }

    private int hashFunc2(int key) { // To avoid equals values with hashFunc1 and zero result
        return 5 - (key % 5); // Use const 5 or another prime number which is less than capacity
    }

    public void put(int key, long value) {
        if (size != threshold) { // Add new key-value, if the limit is not reached

            /* Check whether the given key and value equals to the specified constants.
             * Thus, it will be possible to avoid incorrect increasing of size */
            if(key == NO_KEY)
                return;
            if(value == NO_VALUE) // Use the condition, because this realization doesn't provide
                return;          // rewriting value with an "empty value" (removing value)

            int hashKey = hashFunc1(key);
            int stepSize = hashFunc2(key);
            boolean notExistingKey = true;

            while(keys[hashKey] != NO_KEY) // At first, check whether the key already exists
            {
                if(keys[hashKey] == key) { // If the key exists, rewrite the value
                    notExistingKey = false;
                    values[hashKey] = value;
                    break;
                }
                hashKey += stepSize; // Increase offset
                hashKey %= CAPACITY; // Back to start
            }
            if(notExistingKey) { // Otherwise, search the first empty cell to put key-value
                while(keys[hashKey] != NO_KEY)
                {
                    ++hashKey;
                    hashKey %= CAPACITY; // Back to start (if hashKey == CAPACITY)
                }
                keys[hashKey] = key;
                values[hashKey] = value;
                size++;
            }
        } else {
            //When a hash table is filled with more than 75%, elements are not added to it
            System.out.println("Hash table is filled!");
            // Although it would be better to complete the program with expanding capacity method
        }
    }

    // Method returns value by the given key or NO_VALUE if there is no cell with this key
    public long get(int key) {
        int hashKey = hashFunc1(key);
        int stepSize = hashFunc2(key);

        while(keys[hashKey] != NO_KEY)
        {// Compare keys of not empty cells with the given key
            if (keys[hashKey] == key)
                return values[hashKey];
            hashKey += stepSize;
            hashKey %= CAPACITY;
        }
        return NO_VALUE; // If equal key is not found
    }

    public int size() { // Method returns number of added key-value mappings in this map
        return this.size;
    }
}