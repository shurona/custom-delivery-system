package com.webest.store.product.application;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.webest.store.application.config.MongoContainerConfig;
import com.webest.store.application.config.TestContainerConfig;
import com.webest.store.product.domain.model.Cart;
import com.webest.store.product.domain.model.Product;
import com.webest.store.product.domain.repository.ProductRepository;
import com.webest.store.product.presentation.dto.CartResponseDto;
import com.webest.store.store.domain.model.Store;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.data.mongodb.core.MongoTemplate;

@SpringBootTest
@ExtendWith({TestContainerConfig.class, MongoContainerConfig.class, MockitoExtension.class})
class CartServiceTest {

    @Autowired
    private CartService cartService;

    @SpyBean
    private MongoTemplate mongoTemplate;

    @MockBean
    private ProductRepository productRepository;


    @DisplayName("목록 조회 테스트")
    @Test
    public void 세팅_확인_테스트() {

        // given
        // when
        List<Cart> allCart = cartService.findAllCart();

        //then (로직이 실행되는지만 확인)
        assertThat(allCart.size()).isGreaterThanOrEqualTo(0);
    }

    @DisplayName("카트 생성")
    @Test
    public void 카트_생성() {
        // given
        String name = "name";
        Store store = mock(Store.class);
        Long productId = 1L;
        Product product = Product.of(store, name, 1200.0, "description");
        String userId = "userOne";

        // when
        when(productRepository.findById(anyLong())).thenReturn(Optional.of(product));

        // then
        CartResponseDto cartResponseDto = cartService.createCart(productId, userId);

        verify(productRepository, times(1)).findById(anyLong());
        assertThat(cartResponseDto.name()).isEqualTo(name);

    }

    @DisplayName("캐시 적용 유무 확인")
    @Test
    public void 캐시_적용확인() {
        // given
        String name = "name";
        String userId = "userId";
        Long productId = 1L;
        Store store = mock(Store.class);
        Product product = Product.of(store, name, 1200.0, "description");

        // productDB는 mocking한다.
        when(productRepository.findById(anyLong())).thenReturn(Optional.of(product));

        // 카트 생성
        cartService.createCart(productId, userId);

        reset(mongoTemplate);

        // when
        CartResponseDto cartById = cartService.findCartById(userId);

        // then
        // find에서는 호출이 안되므로 create 할 때만 호출되어야 한다.
        verify(mongoTemplate, times(0)).query(Cart.class);
        assertThat(cartById.name()).isEqualTo(name);

    }

    @DisplayName("캐시가 없는 경우 조회")
    @Test
    public void 캐시_없을_시_조회() {
        // given
        String name = "name";
        String userId = "userId";
        Long productId = 1L;
        Store store = mock(Store.class);
        Product product = Product.of(store, name, 1200.0, "description");

        // productDB는 mocking한다.
        when(productRepository.findById(anyLong())).thenReturn(Optional.of(product));

        // when
        CartResponseDto cartById = cartService.findCartById(userId);

        // then
        assertThat(cartById).isNull();
    }

}