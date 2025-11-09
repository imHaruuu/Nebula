package emu.nebula.data.resources;

import java.util.ArrayList;
import java.util.List;

import emu.nebula.data.BaseDef;
import emu.nebula.data.ResourceType;
import emu.nebula.game.instance.InstanceRewardParam;
import emu.nebula.util.JsonUtils;
import lombok.Getter;

@Getter
@ResourceType(name = "DailyInstanceRewardGroup.json")
public class DailyInstanceRewardGroupDef extends BaseDef {
    private int GroupId;
    private int DailyRewardType;
    private String BaseAwardPreview;
    
    private transient List<InstanceRewardParam> firstRewards;
    private transient List<InstanceRewardParam> rewards;
    
    @Override
    public int getId() {
        return GroupId + DailyRewardType;
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
