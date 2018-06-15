/**
 * Copyright (c) 2017. Hand Enterprise Solution Company. All right reserved. Project
 * Name:springboot-demo Package Name:com.hand Date:2017/4/6 0006 Create
 * By:zongyun.zhou@hand-china.com
 *
 */
package com.hand;

import java.net.InetAddress;
import java.net.UnknownHostException;

import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.core.env.Environment;
import org.springframework.core.env.SimpleCommandLinePropertySource;

@SpringBootApplication
public class Application {
  private static final Logger log = LoggerFactory.getLogger(Application.class);
  @Inject
  private Environment env;

  public static void main(String[] args) throws UnknownHostException {
    SpringApplication app = new SpringApplication(Application.class);
    SimpleCommandLinePropertySource source = new SimpleCommandLinePropertySource(args);

    Environment env = app.run(args).getEnvironment();
    log.info(
        "Access URLs:\n----------------------------------------------------------\n\t"
            + "Local: \t\thttp://127.0.0.1:{}\n\t"
            + "External: \thttp://{}:{}\n----------------------------------------------------------",
        env.getProperty("server.port"), InetAddress.getLocalHost().getHostAddress(),
        env.getProperty("server.port"));
  }
}
