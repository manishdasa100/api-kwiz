package kwiz.backend.api.exchanges;

import java.util.List;

import kwiz.backend.api.dto.ModuleDto;

import lombok.Data;

@Data
public class GetModuleResponse {
    
    private List<ModuleDto> modules;

}
