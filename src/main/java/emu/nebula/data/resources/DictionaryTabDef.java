package emu.nebula.data.resources;

import java.util.ArrayList;
import java.util.List;

import emu.nebula.data.BaseDef;
import emu.nebula.data.ResourceType;

import lombok.Getter;

@Getter
@ResourceType(name = "DictionaryTab.json")
public class DictionaryTabDef extends BaseDef {
    private int Id;
    
    private List<DictionaryEntryDef> entries;
    
    public DictionaryTabDef() {
        this.entries = new ArrayList<>();
    }
    
    @Override
    public int getId() {
        return Id;
    }
}
