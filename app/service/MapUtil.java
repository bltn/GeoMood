package service;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

public class MapUtil {
    public static <K, V extends Comparable<? super V>> Map<K, V> getTop15(Map<K, V> map) {
        List<Entry<K, V>> list = new ArrayList<>(map.entrySet());
        list.sort((o1, o2) -> o2.getValue().compareTo(o1.getValue()));

        int count = 0;
        Map<K, V> result = new LinkedHashMap<>();
        for (Entry<K, V> entry : list) {
            if (count >= 15) break;

            result.put(entry.getKey(), entry.getValue());
            count++;
        }

        return result;
    }
}
