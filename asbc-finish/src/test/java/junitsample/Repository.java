package junitsample;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class Repository<K,T> {
	private Map<K,T> map =new HashMap<>();
	public int seq = 1;
	
	public void save(K k,T t){
		map.put(k, t);
	}

	public T find(K k){
		return map.get(k);
	}

	public List<T> findAll(){
		return new ArrayList<>(map.values());
	}
}
