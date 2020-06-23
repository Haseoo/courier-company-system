package com.github.haseoo.courier;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Profile;

@SpringBootApplication
@Profile("prod")
public class SpringBootTomcatApplication extends SpringBootServletInitializer {
}
