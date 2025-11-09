package emu.nebula.data.resources;

import java.util.ArrayList;
import java.util.List;

import emu.nebula.data.BaseDef;
import emu.nebula.data.ResourceType;
import emu.nebula.game.instance.InstanceData;
import emu.nebula.game.instance.InstanceRewardParam;
import emu.nebula.util.JsonUtils;
import lombok.Getter;

@Getter
@ResourceType(name = "SkillInstance.json")
public class SkillInstanceDef extends BaseDef implements InstanceData {
    private int Id;
    private int PreLevelId;
    private int PreLevelStar;
    private int NeedWorldClass;
    private int EnergyConsume;
    private String BaseAwardPreview;
    
    private transient List<InstanceRewardParam> firstRewards;
    private transient List<InstanceRewardParam> rewards;
    
    @Override
    public int getId() {
        return Id;
    }
    
    @Override
    public void onLoad() {
        // Init reward lists
        this.firstRewards = new ArrayList<>();
        this.rewards = new ArrayList<>();
        
        // Parse rewards
        var awards = JsonUtils.decodeList(this.BaseAwardPreview, int[].class);
        if (awards == null) {
            return;
        }
        
        for (int[] award : awards) {
            int itemId = award[0];
            int min = award[1];
            int max = award.length >= 4 ? award[2] : min;
            boolean isFirst = award[award.length - 1] == 1;
            
            if (min == -1) {
                min = 0;
                max = 1;
            }
            
            var reward = new InstanceRewardParam(itemId, min, max);
            
            if (isFirst) {
                this.firstRewards.add(reward);
            } else {
                this.rewards.add(reward);
            }
        }
    }
}
