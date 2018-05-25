package hashmap;

import org.junit.Test;

import java.util.HashMap;
import java.util.Random;

import static org.junit.Assert.*;

public class OpenAddressingHashMapTest {

    OpenAddressingHashMap hashMap;

    @Test
    public void testPut() {
        hashMap = new OpenAddressingHashMap();

        hashMap.put(Integer.MAX_VALUE, Long.MAX_VALUE);
        assertEquals(Long.MAX_VALUE, hashMap.get(Integer.MAX_VALUE), 0);

        hashMap.put(Integer.MAX_VALUE, 500);
        assertEquals(Long.MAX_VALUE, hashMap.get(Integer.MAX_VALUE),0);

        hashMap.put(17, Long.MAX_VALUE);
        assertEquals(Long.MAX_VALUE, hashMap.get(17), 0);

        hashMap.put(8, 80);
        hashMap.put(13, 130);
        hashMap.put(41, 410);

        assertEquals(80, hashMap.get(8), 0);
        assertEquals(130, hashMap.get(13), 0);
        assertEquals(410, hashMap.get(41), 0);
        assertEquals(Long.MAX_VALUE, hashMap.get(25));
    }

    @Test
    public void testSize() {
        hashMap = new OpenAddressingHashMap();

        hashMap.put(Integer.MAX_VALUE, Long.MAX_VALUE);
        assertEquals(0, hashMap.size());

        hashMap.put(Integer.MAX_VALUE, 500);
        assertEquals(0, hashMap.size());

        hashMap.put(17, Long.MAX_VALUE);
        assertEquals(0, hashMap.size());

        hashMap.put(8, 80);
        hashMap.put(13, 130);
        assertEquals(2, hashMap.size());

        hashMap.put(51, 555);
        assertEquals(3, hashMap.size());

        hashMap.put(8, 800);
        hashMap.put(13, 131);
        assertEquals(3, hashMap.size());
    }

    @Test
    public void testRepeatedPut() {
        // To verify correctness of code execution for different values of HashMap capacity
        Random randomCapacity = new Random();
        hashMap = new OpenAddressingHashMap(randomCapacity.nextInt(1000));

        hashMap.put(8, 80);
        hashMap.put(13, 130);

        assertEquals(80, hashMap.get(8), 0);
        assertEquals(130, hashMap.get(13), 0);

        hashMap.put(8, 800);
        hashMap.put(13, 131);

        assertEquals(800, hashMap.get(8), 0);
        assertEquals(131, hashMap.get(13), 0);
    }

    @Test
    public void testRandom() {
        // To verify correctness of code execution for different values of HashMap capacity
        Random random = new Random();
        hashMap = new OpenAddressingHashMap(random.nextInt(1000));

        HashMap <Integer, Long> hashMapJdK = new HashMap<Integer, Long>();

        /*The execution of the loop is limited by the limit of the hash table capacity,
        because this realization doesn't provide expansion of capacity. In addition,
        there is a very small probability (for a given range of keys) of the fact that
        empty cells can remain in the hash table, because random values of keys are used,
        respectively, they are rewritable.*/
        for(int i=0; i < hashMap.getThreshold(); i++)
        {
            int k = random.nextInt(1000);
            long v = (long)random.nextInt(10000);
            hashMap.put(k,v);
            hashMapJdK.put(k,v);
            assertEquals(hashMap.get(k), hashMapJdK.get(k), 0);
        }
    }
}