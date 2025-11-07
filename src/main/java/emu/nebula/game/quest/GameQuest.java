package emu.nebula.game.quest;

import dev.morphia.annotations.Entity;

import emu.nebula.data.resources.DailyQuestDef;
import emu.nebula.proto.Public.Quest;
import emu.nebula.proto.Public.QuestProgress;
import lombok.Getter;

@Getter
@Entity(useDiscriminator = false)
public class GameQuest {
    private int id;
    private int type;
    private int curProgress;
    private int maxProgress;
    
    @Deprecated
    public GameQuest() {
        
    }
    
    public GameQuest(DailyQuestDef data) {
        this.id = data.getId();
        this.type = QuestType.Daily;
        this.maxProgress = data.getCompleteCondParams()[0];
    }

    public void resetProgress() {
        this.curProgress = 0;
    }
    
    // Proto

    public Quest toProto() {
        var progress = QuestProgress.newInstance()
                .setCur(this.getCurProgress())
                .setMax(this.getMaxProgress());
        
        var proto = Quest.newInstance()
                .setId(this.getId())
                .setTypeValue(this.getType())
                .addProgress(progress);
        
        return proto;
    }
}
