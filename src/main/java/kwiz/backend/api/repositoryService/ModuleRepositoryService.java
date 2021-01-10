package kwiz.backend.api.repositoryService;

import java.util.List;

import kwiz.backend.api.dto.ModuleDto;

public interface ModuleRepositoryService {
    List<ModuleDto> getAll();
    boolean isModuleExist(String moduleId);
    void add(ModuleDto moduleDto);
    void update(String moduleId, ModuleDto moduleDto);
    void updateNumberOfQuestionsInModule(String moduleId, int num);
    void updateAvgTimeToCompleteModule(String moduleId, int addedTime);
    long remove(String moduleId);
    List<ModuleDto> getMatching(String pattern);
    void deleteAllModules();
}
