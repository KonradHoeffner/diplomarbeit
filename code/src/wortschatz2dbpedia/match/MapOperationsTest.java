package wortschatz2dbpedia.match;

import static org.junit.Assert.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.apache.commons.collections15.MultiMap;
import org.junit.Before;
import org.junit.Test;

public class MapOperationsTest {

	@Before
	public void setUp() throws Exception {
	}

	@Test
	public void testReverseMap() {
		Map<String,String> map = new HashMap<String,String>();
		map.put("brot","1 euro");
		map.put("broetchen","20 cent");
		map.put("butter","1 euro");
		MultiMap<String,String> revertedMap = new MapOperations<String>().reverseMap(map);
		//System.out.println(revertedMap);
		assertTrue(	revertedMap.toString().equals("{1 euro=[butter, brot], 20 cent=[broetchen]}")||
					revertedMap.toString().equals("{1 euro=[brot, butter], 20 cent=[broetchen]}")||
					revertedMap.toString().equals("{20 cent=[broetchen], 1 euro=[butter, brot]}")||
					revertedMap.toString().equals("{20 cent=[broetchen], 1 euro=[brot, butter]}"));
	}

}