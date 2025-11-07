package emu.nebula.game.quest;

import java.util.HashMap;
import java.util.Map;

import dev.morphia.annotations.Entity;
import dev.morphia.annotations.Id;
import emu.nebula.data.GameData;
import emu.nebula.database.GameDatabaseObject;
import emu.nebula.game.player.Player;
import emu.nebula.game.player.PlayerManager;
import emu.nebula.net.NetMsgId;

import lombok.Getter;

@Getter
@Entity(value = "quests", useDiscriminator = false)
public class QuestManager extends PlayerManager implements GameDatabaseObject {
    @Id
    private int uid;
    
    private Map<Integer, GameQuest> quests;
    
    @Deprecated // Morphia only
    public QuestManager() {
        
    }
    
    public QuestManager(Player player) {
        super(player);
        this.uid = player.getUid();
        this.quests = new HashMap<>();
        
        this.resetDailyQuests();
        
        this.save();
    }
    
    public void resetDailyQuests() {
        // Reset daily quests
        for (var data : GameData.getDailyQuestDataTable()) {
            // Get quest
            var quest = getQuests().computeIfAbsent(data.getId(), i -> new GameQuest(data));
            
            // Reset progress
            quest.resetProgress();
            
            // Update to player
            if (getPlayer().hasSession()) {
                getPlayer().addNextPackage(
                        NetMsgId.quest_change_notify, 
                        quest.toProto()
                );
            }
        }
        
        // Persist to database
        this.save();
    }

}
