package com.hand.web;

import com.hand.dto.RequestBodyDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.io.UnsupportedEncodingException;

/**
 * Created by hand on 2017/4/11.
 */
@RestController
public class RedisResource {

    @Autowired
    private RedisTemplate redisTemplate ;

    @RequestMapping(value = "/setDemo", method = RequestMethod.GET)
    public String getFlybackReport(@RequestParam String key,
                                    @RequestParam String value) {

        redisTemplate.opsForValue().set(key,value);

        return "ok";
    }

    @RequestMapping(value = "/getDemo", method = RequestMethod.GET)
    public String getFlybackReport(@RequestParam String key) {

        Object execute = redisTemplate.execute(new RedisCallback() {
            @Override
            public Object doInRedis(RedisConnection redisConnection) throws DataAccessException {
                byte[] bytes = redisConnection.get(key.getBytes());
                String value = null;
                try {
                    value = new String(bytes,"utf-8");
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
                return value;
            }
        });
        return execute.toString();
    }



    @Autowired
    private StringRedisTemplate stringRedisTemplate ;

    @Resource(name ="redisTemplate")
    private ValueOperations<String,Object> valueOperations;

    public ValueOperations<String,Object> getValueOperations(RedisTemplate redisTemplate){
        redisTemplate.setKeySerializer(new StringRedisSerializer());
        ValueOperations valueOperations = redisTemplate.opsForValue();
        return valueOperations;

    }

    @RequestMapping("/getDTO")
    public ResponseEntity<RequestBodyDTO> getr(@RequestParam String timestamp ){
        ValueOperations<String, Object> valueOperations = getValueOperations(redisTemplate);
        Object value =valueOperations.get(timestamp);
        if (value == null){
            RequestBodyDTO requestBodyDTO = new RequestBodyDTO();
            requestBodyDTO.setSignature("sdfsadfsdafsd");
            requestBodyDTO.setMessage("sdfsdafsdafsadfds");
            requestBodyDTO.setTimestamp(timestamp);
            valueOperations.set(timestamp,requestBodyDTO);
            return ResponseEntity.ok(requestBodyDTO);
        }

        return ResponseEntity.ok((RequestBodyDTO)value);

    }


}
