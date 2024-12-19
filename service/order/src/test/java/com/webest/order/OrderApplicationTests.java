package com.webest.order;

import com.webest.order.application.config.TestContainerConfig;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;

@ExtendWith(TestContainerConfig.class)
@SpringBootTest
class OrderApplicationTests {

    @Test
    void contextLoads() {
    }

}
