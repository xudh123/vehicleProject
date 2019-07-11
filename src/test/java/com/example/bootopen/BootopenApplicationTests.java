package com.example.bootopen;

import com.example.bootopen.pojo.User;
import com.example.bootopen.redis.RedisService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

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
		User user = new User("xu", 22, "学生");
		redisService.set("student", user);
		User user1 = redisService.get("student", User.class);
		System.out.println(user1.toString());

		String name = "yu";
		redisService.set("name", name);
		String result_name = redisService.get("name");
		System.out.println(result_name);


	}*/

}
