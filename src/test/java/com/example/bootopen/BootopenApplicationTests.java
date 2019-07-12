package com.example.bootopen;

import com.example.bootopen.pojo.User;
import com.example.bootopen.redis.RedisService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.data.redis.RedisProperties;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class BootopenApplicationTests {

	/*private MockMvc mvc;*/

	/*@Before
	public void setup()throws  Exception{
		mvc = MockMvcBuilders.standaloneSetup(new HelloSpringBootController()).build();
	}

	@Test
	public void getHello() throws Exception{
		mvc.perform(MockMvcRequestBuilders.get("/hello").accept(MediaType.APPLICATION_JSON))
			.andExpect(status().isOk())
			.andExpect(content().string(equalTo("hello spring boot")));
	}*/
	/*@Autowired
	private UserMapper userMapper;

	@Test
	public void testSelect(){
		System.out.println("select all");
		List<User> userList = userMapper.selectList(null);
		Assert.assertEquals(1, userList.size());
		userList.forEach(System.out::println);
	}*/
	@Autowired
	private RedisService redisService;

	/*@Test
	public void testRedisService(){
*//*		User user = new User("xu", "password");
		redisService.set("student", user);
		User user1 = redisService.get("student", User.class);
		System.out.println(user1.toString());

		String name = "yu";
		redisService.set("name", name);*//*
		String result_name = redisService.get("name");
		System.out.println(result_name);
	}*/
	@Test
	public void testListFromRedis(){
		List<User> userList = new ArrayList<>();
		User user1 = new User("1","1");
		User user2 = new User("2", "2");
		User user3 = new User("3", "3");
		userList.add(user1);
		userList.add(user2);
		userList.add(user3);

		redisService.addList("userList", userList);

	}

}
