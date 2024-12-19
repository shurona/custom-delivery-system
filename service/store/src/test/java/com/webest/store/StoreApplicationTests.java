package com.webest.store;

import com.webest.store.application.config.TestContainerConfig;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;

@ExtendWith(TestContainerConfig.class)
@SpringBootTest
class StoreApplicationTests {

    @Test
    void contextLoads() {
    }

}
