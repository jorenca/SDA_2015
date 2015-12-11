import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

public class MyMap<TKey, TValue> {
	private static final int DEFAULT_CAPACITY = 100; // capital snake case
	private final int capacity; // thisIsSparta - camelCase
	private List<KVP>[] m;

	private class KVP {
		public TKey key;
		public TValue value;

		public KVP(TKey key, TValue value) {
			this.key = key;
			this.value = value;
		}
	}

	public MyMap(int capacity) {
		m = new List[capacity];
		for (int i = 0; i < capacity; i++)
			m[i] = new LinkedList<KVP>();
		this.capacity = capacity;
	}

	public MyMap() {
		this(DEFAULT_CAPACITY);
	}

	public void put(TKey key, TValue value) {
		int pos = Math.abs(key.hashCode() % capacity); // -64
		m[pos].add(new KVP(key, value));
	}

	public TValue get(TKey key) {
		int pos = Math.abs(key.hashCode() % capacity);
		List<KVP> slot = m[pos];
		for (KVP kvp : slot) {
			if (kvp.key.equals(key)) {
				return kvp.value;
			}
		}
		return null;
	}

	public void remove(TKey key) {
		int pos = Math.abs(key.hashCode() % capacity);
		List<KVP> slot = m[pos];

		for (Iterator<KVP> it = slot.iterator(); it.hasNext();) {
			KVP next = it.next();
			if (next.key.equals(key)) {
				it.remove();
				break;
			}
		}
	}
}
