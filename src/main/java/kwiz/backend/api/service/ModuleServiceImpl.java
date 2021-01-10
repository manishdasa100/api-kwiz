package kwiz.backend.api.service;

import java.util.List;

import kwiz.backend.api.dto.ModuleDto;
import kwiz.backend.api.exceptions.ResourceNotFoundException;
import kwiz.backend.api.exchanges.GetModuleResponse;
import kwiz.backend.api.repositoryService.ModuleRepositoryService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ModuleServiceImpl implements ModuleService {

    @Autowired
    private ModuleRepositoryService moduleRepositoryService;

    @Autowired
    private QuestionService questionService;

    @Override
    public GetModuleResponse getAllModules() {
        List<ModuleDto> modules = moduleRepositoryService.getAll();
        GetModuleResponse response = new GetModuleResponse();
        response.setModules(modules);
        return response;
    }

    @Override
    public GetModuleResponse getRelevantModules(String pattern) {
        List<ModuleDto> modules = moduleRepositoryService.getMatching(pattern);
        GetModuleResponse response = new GetModuleResponse();
        response.setModules(modules);
        return response;
    }

    @Override
    public void addModule(ModuleDto moduleDto) {
        moduleRepositoryService.add(moduleDto);        
    }

    @Override
    public void updateModule(String moduleId, ModuleDto moduleDto) {
        moduleRepositoryService.update(moduleId, moduleDto);
    }

    @Override
    public long removeModule(String moduleId) {
    
        // This function returns the number of entities removed from the repository. Ideally it should be one.
        // If noOfEntitiesRemoved = 0 then there is no such entity present in the database having the provided moduleId. 
        // In such case it will throw a resourceNotFoundException.
        
        long noOfEntitiesRemoved = moduleRepositoryService.remove(moduleId);

        if (noOfEntitiesRemoved == 1) {
            questionService.removeAllQuestionsInModule(moduleId);
        } else {
            throw new ResourceNotFoundException();
        }

        return noOfEntitiesRemoved;
    }

    @Override
    public boolean isModuleExist(String moduleId) {
        return moduleRepositoryService.isModuleExist(moduleId);
    }

    @Override
    public void updateQuestionsCountBy(String moduleId, int num) {
        moduleRepositoryService.updateNumberOfQuestionsInModule(moduleId, num);
    }

    @Override
    public void updateAvgTimeToComplete(String moduleId, int addedTime) {
        
        moduleRepositoryService.updateAvgTimeToCompleteModule(moduleId, addedTime);
    }

    @Override
    public void deleteAll() {
        moduleRepositoryService.deleteAllModules();
        questionService.deleteAll();
    } 
    
}
