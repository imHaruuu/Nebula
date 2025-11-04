package emu.nebula.game.tower;

import java.util.HashMap;
import java.util.Map;

import emu.nebula.proto.PublicStarTower.HawkerGoods;
import emu.nebula.proto.PublicStarTower.StarTowerRoomCase;
import it.unimi.dsi.fastutil.ints.IntArrayList;
import it.unimi.dsi.fastutil.ints.IntList;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class StarTowerCase {
    private int id;
    
    @Setter(AccessLevel.NONE)
    private CaseType type;
    
    // Extra data
    private int teamLevel;
    private int subNoteSkillNum;

    private int floorId;
    private int roomType;
    
    private int eventId;
    private int npcId;
    
    // Selector
    private IntList ids;
    
    // Hawker
    private Map<Integer, StarTowerShopGoods> goodsList;
    
    public StarTowerCase(CaseType type) {
        this.type = type;
    }
    
    public void addId(int id) {
        if (this.ids == null) {
            this.ids = new IntArrayList();
        }
        
        this.ids.add(id);
    }

    public int selectId(int index) {
        if (this.getIds() == null) {
            return 0;
        }
        
        if (index < 0 || index >= this.getIds().size()) {
            return 0;
        }
        
        return this.getIds().getInt(index);
    }
    
    public void addGoods(StarTowerShopGoods goods) {
        if (this.goodsList == null) {
            this.goodsList = new HashMap<>();
        }
        
        this.getGoodsList().put(getGoodsList().size() + 1, goods);
    }
    
    // Proto
    
    public StarTowerRoomCase toProto() {
        var proto = StarTowerRoomCase.newInstance()
                .setId(this.getId());
        
        switch (this.type) {
            case Battle -> {
                proto.getMutableBattleCase()
                    .setSubNoteSkillNum(this.getSubNoteSkillNum());
            }
            case OpenDoor -> {
                proto.getMutableDoorCase()
                    .setFloor(this.getFloorId())
                    .setType(this.getRoomType());
            }
            case SyncHP, RecoveryHP -> {
                proto.getMutableSyncHPCase();
            }
            case SelectSpecialPotential -> {
                proto.getMutableSelectSpecialPotentialCase()
                    .setTeamLevel(this.getTeamLevel())
                    .addAllIds(this.getIds().toIntArray());
            }
            case PotentialSelect -> {
                proto.getMutableSelectPotentialCase();
            }
            case NpcEvent -> {
                proto.getMutableSelectOptionsEventCase()
                    .setEvtId(this.getEventId())
                    .setNPCId(this.getNpcId())
                    .addAllOptions(this.getIds().toIntArray());
            }
            case Hawker -> {
                var hawker = proto.getMutableHawkerCase();
                
                for (var entry : getGoodsList().entrySet()) {
                    var id = entry.getKey();
                    var goods = entry.getValue();
                    
                    var info = HawkerGoods.newInstance()
                            .setIdx(goods.getGoodsId())
                            .setSid(id)
                            .setType(goods.getType())
                            .setGoodsId(102) // ?
                            .setPrice(goods.getPrice())
                            .setTag(1);
                    
                    hawker.addList(info);
                }
            }
            default -> {
                
            }
        }
        
        return proto;
    }
}
