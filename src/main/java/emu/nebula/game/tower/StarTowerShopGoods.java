package emu.nebula.game.tower;

import dev.morphia.annotations.Entity;
import lombok.Getter;

@Getter
@Entity(useDiscriminator = false)
public class StarTowerShopGoods {
    private int type;
    private int goodsId;
    private int price;
    private boolean sold;
    
    public StarTowerShopGoods(int type, int goodsId, int price) {
        this.type = type;
        this.goodsId = goodsId;
        this.price = price;
    }
    
}
