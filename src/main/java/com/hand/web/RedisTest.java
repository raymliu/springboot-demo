package com.hand.web;

import com.hand.service.RedisService;
import com.hand.dto.RequestBodyDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by hand on 2017/4/18.
 */
@RestController
public class RedisTest {

    @Autowired
    private RedisService redisService;

    @RequestMapping("/redis/get")
    public ResponseEntity<RequestBodyDTO> getDTO(@RequestParam String id){
        RequestBodyDTO dto = redisService.getDTO(id);
        return ResponseEntity.ok().body(dto);
    }
}
