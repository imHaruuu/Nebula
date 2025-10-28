package emu.nebula.data.resources;

import emu.nebula.data.BaseDef;
import emu.nebula.data.GameData;
import emu.nebula.data.ResourceType;
import emu.nebula.data.ResourceType.LoadPriority;
import lombok.Getter;

@Getter
@ResourceType(name = "DictionaryEntry.json", loadPriority = LoadPriority.LOW)
public class DictionaryEntryDef extends BaseDef {
    private int Id;
    private int Tab;
    private int Index;
    
    @Override
    public int getId() {
        return Id;
    }
    
    @Override
    public void onLoad() {
        var dictionary = GameData.getDictionaryTabDataTable().get(this.getTab());
        if (dictionary != null) {
            dictionary.getEntries().add(this);
        }
    }
}
