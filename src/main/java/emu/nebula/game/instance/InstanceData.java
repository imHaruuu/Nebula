package emu.nebula.game.instance;

import java.util.List;

import emu.nebula.game.inventory.ItemParamMap;
import emu.nebula.game.player.Player;

public interface InstanceData {
    
    public int getId();
    
    public int getNeedWorldClass();
    
    public int getEnergyConsume();
    
    // Handle reward generation
    
    public List<InstanceRewardParam> getFirstRewards();
    
    public default List<InstanceRewardParam> getFirstRewards(int rewardType) {
        return getFirstRewards();
    }
    
    public default ItemParamMap generateFirstRewards(int rewardType) {
        return this.generateRewards(this.getFirstRewards());
    }
    
    public List<InstanceRewardParam> getRewards();
    
    public default List<InstanceRewardParam> getRewards(int rewardType) {
        return getRewards();
    }
    
    public default ItemParamMap generateRewards(int rewardType) {
        return this.generateRewards(this.getRewards());
    }
    
    public default ItemParamMap generateRewards(List<InstanceRewardParam> params) {
        var map = new ItemParamMap();
        
        for (var param : params) {
            map.add(param.getId(), param.getRandomCount());
        }
        
        return map;
    }
    
    /**
     * Checks if the player has enough energy to complete this instance
     * @return true if the player has enough energy
     */
    public default boolean hasEnergy(Player player) {
        return this.hasEnergy(player, 1);
    }
    
    /**
     * Checks if the player has enough energy to complete this instance
     * @return true if the player has enough energy
     */
    public default boolean hasEnergy(Player player, int count) {
        return (this.getEnergyConsume() * count) <= player.getEnergy();
    }
    
}
