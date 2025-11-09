package emu.nebula.game.instance;

import emu.nebula.util.Utils;
import lombok.Getter;

@Getter
public class InstanceRewardParam {
    public int id;
    public int min;
    public int max;
    
    public InstanceRewardParam(int id, int min, int max) {
        this.id = id;
        this.min = min;
        this.max = max;
    }
    
    public int getRandomCount() {
        if (this.min == this.max) {
            return this.min;
        }
        
        return Utils.randomRange(this.min, this.max);
    }
}