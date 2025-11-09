package emu.nebula.data.resources;

import java.util.List;

import emu.nebula.data.BaseDef;
import emu.nebula.data.GameData;
import emu.nebula.data.ResourceType;
import emu.nebula.game.instance.InstanceData;
import emu.nebula.game.instance.InstanceRewardParam;

import lombok.Getter;

@Getter
@ResourceType(name = "DailyInstance.json")
public class DailyInstanceDef extends BaseDef implements InstanceData {
    private int Id;
    private int AwardDropId;
    private int PreLevelId;
    private int PreLevelStar;
    private int OneStarEnergyConsume;
    private int NeedWorldClass;
    
    @Override
    public int getId() {
        return Id;
    }

    @Override
    public int getEnergyConsume() {
        return OneStarEnergyConsume;
    }
    
    public DailyInstanceRewardGroupDef getRewardGroup(int rewardType) {
        int groupId = this.getAwardDropId() + rewardType;
        return GameData.getDailyInstanceRewardGroupDataTable().get(groupId);
    }
    
    @Override
    @Deprecated
    public List<InstanceRewardParam> getFirstRewards() {
        return null;
    }

    @Override
    @Deprecated
    public List<InstanceRewardParam> getRewards() {
        return null;
    }
    
    @Override
    public List<InstanceRewardParam> getFirstRewards(int rewardType) {
        var data = this.getRewardGroup(rewardType);
        
        if (data != null) {
            return data.getFirstRewards();
        } else {
            return null;
        }
    }
    
    @Override
    public List<InstanceRewardParam> getRewards(int rewardType) {
        var data = this.getRewardGroup(rewardType);
        
        if (data != null) {
            return data.getRewards();
        } else {
            return null;
        }
    }
}
