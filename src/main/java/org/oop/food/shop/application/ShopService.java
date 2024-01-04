package org.oop.food.shop.application;

import org.oop.food.shop.domain.Menu;
import org.oop.food.shop.domain.MenuRepository;
import org.oop.food.shop.domain.Shop;
import org.oop.food.shop.domain.ShopRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 1단계에서는 누락되어있던 클래스
 * 1단계에서는 shop을 통해 menu를 찾아 MenuBoard를 반환한다.
 */
@Service
public class ShopService {
    private final ShopRepository shopRepository;
    private final MenuRepository menuRepository;

    public ShopService(ShopRepository shopRepository, MenuRepository menuRepository) {
        this.shopRepository = shopRepository;
        this.menuRepository = menuRepository;
    }

    @Transactional(readOnly = true)
    public MenuBoard getMenuBoard(Long shopId) {
        Shop shop = shopRepository.findById(shopId).orElseThrow(IllegalArgumentException::new);
        List<Menu> menus = menuRepository.findByShopId(shopId);
        return new MenuBoard(shop, menus);
    }
}
