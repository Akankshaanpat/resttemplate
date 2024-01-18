package com.demo.rest.template.api.config;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import com.demo.rest.template.entity.User;

import lombok.extern.slf4j.Slf4j;
@Slf4j
public class RestClient {

	public static final String CREATE_USER_API = "http://localhost:8080/users/add";
	public static final String GET_ALL_USERS_API = "http://localhost:8080/users/getAll";
	public static final String GET_USER_BY_ID_API = "http://localhost:8080/users/get/{id}";
	public static final String UPDATE_USER_BY_ID_API = "http://localhost:8080/users/update/{id}";
	public static final String DELETE_USER_BY_ID_API = "http://localhost:8080/users/delete/{id}";

	private static RestTemplate restTemplate = new RestTemplate();

	public static void main(String[] args) {
		callCreateUserAPI();
		callGetAllUsersAPI();
		callGetUserByIdAPI();
		callUpdateUserAPI();
		callDeleteUserAPI();
	}

	private static void callCreateUserAPI() {
		
		User user = new User("Sachin", "sachin@gmail.com");
		
		ResponseEntity<User> user2 = restTemplate.postForEntity(CREATE_USER_API, user, User.class);
		System.out.println(user2.getBody());
		log.info("Create UserAPI method executed");
	}

	private static void callGetAllUsersAPI() {

		HttpHeaders headers = new HttpHeaders();
		headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
		
		HttpEntity<String> entity = new HttpEntity<>("parameters", headers);
		ResponseEntity<String> result = restTemplate.exchange(GET_ALL_USERS_API, HttpMethod.GET, entity, String.class);
		System.out.println(result);
		log.info("Get AllUserAPI method executed");		
	}

	private static void callGetUserByIdAPI() {

		Map<String, Integer> param = new HashMap<>();
		param.put("id", 2);
				User user = restTemplate.getForObject(GET_USER_BY_ID_API, User.class, param);
		System.out.println(user.getName());
		System.out.println(user.getEmailId());
		log.info("UserByIdAPI method executed");
		
	}

	private static void callUpdateUserAPI() {

		Map<String, Integer> params = new HashMap<String, Integer>();
		params.put("id", 52);
		
		User updatedUser = new User("Obama", "obm123@gmail.com");
		restTemplate.put(UPDATE_USER_BY_ID_API, updatedUser, params);
		log.info("Update UserAPI method executed");
		
	}

	private static void callDeleteUserAPI() {
		Map<String, Integer> params = new HashMap<String, Integer>();
		params.put("id", 152);
		try {
		restTemplate.delete(DELETE_USER_BY_ID_API, params);
		log.info("Delete UserAPI method executed");
		}
		catch (Exception e) {
		System.out.println(e);
		}
	}
}
