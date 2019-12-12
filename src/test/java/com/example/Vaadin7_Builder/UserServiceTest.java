package com.example.Vaadin7_Builder;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.sql.SQLException;
import java.util.List;

import org.junit.jupiter.api.Test;

import com.example.Vaadin7_Builder.model.User;
import com.example.Vaadin7_Builder.service.UserService;

public class UserServiceTest {
	
	UserService us = new UserService();
	
	@Test
	public void TestCreateUser() throws SQLException {

		User user = new User();
		
		user.setUserFirstName("Nazar");
		user.setUserLastName("Klym");
		user.setUserName("Nazar1");
		user.setUserPassword("user13");
		user.setUserMail("nazar@gmail.com");
//		user.setUserPhone("979766297");
				
		us.createUser(user);
		user = us.getUserByUserName("Nazar1");
		
		assertEquals("Nazar", user.getUserFirstName());
		assertEquals("Klym", user.getUserLastName());
		assertEquals("Nazar1", user.getUserName());
		assertEquals("user13", user.getUserPassword());
		assertEquals("nazar@gmail.com", user.getUserMail());
//		assertEquals("979766297", user.getUserPhone());
		
		us.deleteUser(user);
	}
	
	@Test
	public void TestCountCreateUser() throws SQLException {
		
		int count = us.countUser();
		int newCount;

		User user = new User();
		
		user.setUserFirstName("Nazar");
		user.setUserLastName("Klym");
		user.setUserName("Nazar1");
		user.setUserPassword("user13");
		user.setUserMail("nazar@gmail.com");
		user.setUserPhone("979766297");
				
		us.createUser(user);
		
		newCount = us.countUser();
		
		assertEquals(count+1, newCount);
		
		us.deleteUser(user);
	}
	
	@Test
	public void TesrGetUserByUserName() throws SQLException {
		
		User user = new User();
		
		user.setUserFirstName("userNazar1");
		user.setUserLastName("user12");
		user.setUserName("userNazar");
		user.setUserPassword("user13");
		user.setUserMail("user1@gmail.com");
		user.setUserPhone("979766295");
		
		us.createUser(user);
		user = us.getUserByUserName("userNazar");
		
		assertEquals("userNazar1", user.getUserFirstName());
		assertEquals("user12", user.getUserLastName());
		assertEquals("userNazar", user.getUserName());
		assertEquals("user13", user.getUserPassword());
		assertEquals("user1@gmail.com", user.getUserMail());
		assertEquals("979766295", user.getUserPhone());
		
		us.deleteUser(user);
	}
	
	@Test
	public void TestGetUsers() throws SQLException {
		
		int elementNumber = 3;
		
		int idUser;
		
		User user = new User();
		User userFromList = new User();
		
		List<User> userList = us.getUsers();
		userFromList = userList.get(elementNumber);
		
		List<Integer> idUserList = us.getIdUserTable();
		idUser = idUserList.get(elementNumber);
		
		user = us.getUserById(idUser);
		
		assertEquals(user.getIduserTable(), userFromList.getIduserTable());
		assertEquals(user.getUserFirstName(), userFromList.getUserFirstName());
		assertEquals(user.getUserLastName(), userFromList.getUserLastName());
		assertEquals(user.getUserName(), userFromList.getUserName());
		assertEquals(user.getUserPassword(), userFromList.getUserPassword());
		assertEquals(user.getUserMail(), userFromList.getUserMail());
		assertEquals(user.getUserPhone(), userFromList.getUserPhone());		
	}
	
	@Test
	public void TestCountGetUsers() throws SQLException {
		
		int count = us.countUser();
		
		List<User> userList = us.getUsers();
		int listCount =	userList.size();
		
		assertEquals(count, listCount);
	}
	
	@Test
	public void TestGetUserById() throws SQLException {
		
		User user = new User();
		
		user.setUserFirstName("user1");
		user.setUserLastName("user12");
		user.setUserName("user11");
		user.setUserPassword("user13");
		user.setUserMail("user1@gmail.com");
		user.setUserPhone("979766295");
		
		us.createUser(user);
		
		User userDB = new User();
		int idUser; 
		List<Integer> idUserList = us.getIdUserTable();
		int elementNumber = idUserList.size()-1;
		idUser = idUserList.get(elementNumber);
		
		userDB = us.getUserById(idUser);

		assertEquals(user.getUserFirstName(), userDB.getUserFirstName());
		assertEquals(user.getUserLastName(), userDB.getUserLastName());
		assertEquals(user.getUserName(), userDB.getUserName());
		assertEquals(user.getUserPassword(), userDB.getUserPassword());
		assertEquals(user.getUserMail(), userDB.getUserMail());
		assertEquals(user.getUserPhone(), userDB.getUserPhone());
		
		us.deleteUser(user);
	}
	
	@Test
	public void TestUpdateUser() throws SQLException {
		
		User user = new User();
		
		user.setUserFirstName("userNazar1");
		user.setUserLastName("user12");
		user.setUserName("userNazar");
		user.setUserPassword("user13");
		user.setUserMail("user1@gmail.com");
		user.setUserPhone("979766295");
		
		us.createUser(user);
		user = us.getUserByUserName("userNazar");
		
		user.setUserFirstName("Nazar");
		user.setUserLastName("user12Nazar");
		user.setUserPassword("user131");
		user.setUserMail("1@gmail.com");
		user.setUserPhone("979766291");
				
		us.updateUser(user);
		user = us.getUserByUserName("userNazar");

		assertEquals("Nazar", user.getUserFirstName());
		assertEquals("user12Nazar", user.getUserLastName());
		assertEquals("userNazar", user.getUserName());
		assertEquals("user131", user.getUserPassword());
		assertEquals("1@gmail.com", user.getUserMail());
		assertEquals("979766291", user.getUserPhone());
		
		us.deleteUser(user);
	}
	
	@Test
	public void TestCountDeleteUser() throws SQLException {
		
		int count = us.countUser();
		int newCount;

		User user = new User();
		
		user.setUserFirstName("Nazar");
		user.setUserLastName("Klym");
		user.setUserName("Nazar1");
		user.setUserPassword("user13");
		user.setUserMail("nazar@gmail.com");
		user.setUserPhone("979766297");
				
		us.createUser(user);
		user = us.getUserByUserName("Nazar1");
		
		newCount = us.countUser();
		
		assertEquals(count+1, newCount);
		
		us.deleteUser(user);
	}
	
}
