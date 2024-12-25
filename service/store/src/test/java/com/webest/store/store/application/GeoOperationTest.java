package com.webest.store.store.application;

import static org.assertj.core.api.Assertions.assertThat;

import com.webest.store.application.config.MongoContainerConfig;
import com.webest.store.application.config.TestContainerConfig;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@ExtendWith({TestContainerConfig.class, MongoContainerConfig.class})
class GeoOperationTest {

    @Autowired
    private GeoOperation geoOperation;

    @DisplayName("Geo 기본 추가 테스트")
    @Test
    public void Geo_추가_및_조회() {

        //given
        double x = 12.22;
        double y = 13.41;
        geoOperation.add(x, y, "멤버");

        //when
        List<String> nearByStores = geoOperation.findNearByStores(x, y, 1.1);

        //then
        assertThat(nearByStores.size()).isEqualTo(1);

    }


}