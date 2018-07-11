package project;

import com.SpringBootWebApplication;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

//import org.springframework.data.redis.core.StringRedisTemplate;

/**
 * redis读写测试
 * @author z77z
 *
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = SpringBootWebApplication.class)
public class RedisCacheTest {
//    @Autowired
//    StringRedisTemplate stringRedisTemplate;

    @Test
    public void redisTest() throws Exception {

//        //保存字符串
//        stringRedisTemplate.opsForValue().set("aaa", "111");
//        //读取字符串
//        String aaa = stringRedisTemplate.opsForValue().get("aaa");
//        System.out.println(aaa);
    }
}
