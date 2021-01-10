package kwiz.backend.api.service;

import kwiz.backend.api.dto.ModuleDto;
import kwiz.backend.api.exchanges.GetModuleResponse;

public interface ModuleService {
    GetModuleResponse getAllModules();
    boolean isModuleExist(String moduleId);
    GetModuleResponse getRelevantModules(String pattern);
    void addModule(ModuleDto moduleDto);
    void updateModule(String moduleId, ModuleDto moduleDto);
    void updateQuestionsCountBy(String moduleId, int num);
    void updateAvgTimeToComplete(String moduleId, int addedTime);
    long removeModule(String moduleId);
    void deleteAll();
}
