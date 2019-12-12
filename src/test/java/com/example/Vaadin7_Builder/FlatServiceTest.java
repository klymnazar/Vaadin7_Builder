package com.example.Vaadin7_Builder;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.junit.Test;

import com.example.Vaadin7_Builder.model.Flat;
import com.example.Vaadin7_Builder.service.FlatService;

public class FlatServiceTest {
	
	FlatService fs = new FlatService();
	
	@Test
	public void TestCreateFlat() throws SQLException {
		
		Flat flat = new Flat();
		
//		flat.setIdFlatTable(2);
		flat.setBuildingCorps("A");
		flat.setFlatArea(49.1);
		flat.setFlatFloor(2);
		flat.setFlatNumber(12);
		flat.setFlatRooms(1);
		
		fs.createFlat(flat);
		flat = fs.getFlatByFlatNumber(12);
		
		assertEquals("A", flat.getBuildingCorps());
		assertEquals(49.1, flat.getFlatArea());
		assertEquals(2, flat.getFlatFloor());
		assertEquals(12, flat.getFlatNumber());
		assertEquals(1, flat.getFlatRooms());
		
		fs.deleteFlatByFlatNumber(12);
	
	}

	@Test
	public void TestcountFlats() throws SQLException {

		int count;
		int countAfter;
		
		Flat flat = new Flat();
		
		flat.setBuildingCorps("A");
		flat.setFlatArea(49.1);
		flat.setFlatFloor(2);
		flat.setFlatNumber(12);
		flat.setFlatRooms(1);
		
		
		count = fs.countFlats();
		fs.createFlat(flat);
		countAfter = fs.countFlats();
		
		assertEquals(count+1, countAfter);
		
		fs.deleteFlatByFlatNumber(12);
	}
	
	
	
	
	
	
	
	
	
	@Test
	public void TestDeleteFlatById() throws SQLException {
		
		Flat flat = new Flat();
		
		flat.setIdFlatTable(1);
		flat.setBuildingCorps("A");
		flat.setFlatArea(49.1);
		flat.setFlatFloor(2);
		flat.setFlatNumber(12);
		flat.setFlatRooms(1);
		
		fs.createFlat(flat);
		
		


		
		assertEquals("A", flat.getBuildingCorps());
		assertEquals(49.1, flat.getFlatArea());
		assertEquals(2, flat.getFlatFloor());
		assertEquals(12, flat.getFlatNumber());
		assertEquals(1, flat.getFlatRooms());
		
	
		fs.deleteFlatByFlatNumber(12);

	}
	
	
	@Test
	public void TestGetFlats() throws SQLException {
		
//		List<Flat> flatList = new ArrayList<>();
//		
//		Flat flat = new Flat();
//		
////		flat.setIdFlatTable(2);
////		flat.setBuildingCorps("A");
////		flat.setFlatArea(49.1);
////		flat.setFlatFloor(2);
////		flat.setFlatNumber(12);
////		flat.setFlatRooms(1);
////		
////		fs.createFlat(flat);
//		flatList = fs.getFlats();
//		
//		Iterator<Flat> itr = flatList.iterator();
//		while (itr.hasNext()) {
//			Flat flatFromList = itr.next();
//			
//			assertEquals("A", flatFromList.getBuildingCorps());
//			assertEquals(49.1, flatFromList.getFlatArea());
//			assertEquals(2, flatFromList.getFlatFloor());
//			assertEquals(12, flatFromList.getFlatNumber());
//			assertEquals(1, flatFromList.getFlatRooms());
//		
//		}
//		
//		
//		
//		
//		
//		fs.deleteFlatByFlatNumber(12);
	
	}
	
	
	
	
	
	
	
	

}
