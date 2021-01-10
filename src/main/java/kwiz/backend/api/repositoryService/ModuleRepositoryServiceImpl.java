package kwiz.backend.api.repositoryService;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

import kwiz.backend.api.dto.ModuleDto;
import kwiz.backend.api.entity.ModuleEntity;
import kwiz.backend.api.exceptions.ResourceNotFoundException;
import com.mongodb.client.result.DeleteResult;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

@Service
public class ModuleRepositoryServiceImpl implements ModuleRepositoryService {

    @Autowired
    private MongoTemplate mongoTemplate;

    private ModelMapper mapper = new ModelMapper();

    public static final String COLLECTION = "modules";

    @Override
    public List<ModuleDto> getAll() {
        List<ModuleEntity> modules = mongoTemplate.findAll(ModuleEntity.class, COLLECTION);
        
        List<ModuleDto> moduleDtos = new ArrayList<ModuleDto>();

        for (ModuleEntity module:modules) {
            moduleDtos.add(mapper.map(module, ModuleDto.class));
        }

        return moduleDtos;
    }

    @Override
    public boolean isModuleExist(String moduleId) {
        return mongoTemplate.exists(new Query(Criteria.where("moduleId").is(moduleId)), ModuleEntity.class, COLLECTION);
    }

    @Override
    public void add(ModuleDto moduleDto) {
        String moduleTitle = moduleDto.getModuleTitle();
        String moduleDescription = moduleDto.getModuleDescription();
        // int avgDuration = moduleDto.getAvgDuration(); 

        ModuleEntity entity = ModuleEntity.builder()
                                        .moduleTitle(moduleTitle)
                                        .moduleDescription(moduleDescription)
                                        .totalQuestions(0)
                                        .avgDuration(0)
                                        .build();

        mongoTemplate.save(entity, "modules");
    }


    /**
     * This function takes as arguments a moduleId and a moduleDto. It searches the database for an entity having the provided
     * moduleId and updates it with the provided moduleDto. If no such module entity is found then it throws a resource not 
     * found exception.
     */
    public void update(String moduleId, ModuleDto moduleDto) {

        String moduleTitle = moduleDto.getModuleTitle();
        String moduleDescription = moduleDto.getModuleDescription();

        ModuleEntity entity = mongoTemplate.findOne(new Query(Criteria.where("moduleId").is(moduleId)), ModuleEntity.class, COLLECTION);
    
        if (entity == null) throw new ResourceNotFoundException();
        
        entity.setModuleTitle(moduleTitle);
        entity.setModuleDescription(moduleDescription);

        mongoTemplate.save(entity, "modules");

    }

    @Override
    public void updateNumberOfQuestionsInModule(String moduleId, int num) {
        
        ModuleEntity entity = mongoTemplate.findOne(new Query(Criteria.where("moduleId").is(moduleId)), ModuleEntity.class, COLLECTION);
        entity.setTotalQuestions(entity.getTotalQuestions()+num);

        mongoTemplate.save(entity);

    }

    @Override
    public void updateAvgTimeToCompleteModule(String moduleId, int addedTime) {
        
        ModuleEntity entity = mongoTemplate.findOne(new Query(Criteria.where("moduleId").is(moduleId)), ModuleEntity.class, COLLECTION);
        entity.setAvgDuration(entity.getAvgDuration()+addedTime);

        mongoTemplate.save(entity);
    }

    @Override
    public long remove(String moduleId) {
        DeleteResult result = mongoTemplate.remove(new Query(Criteria.where("moduleId").is(moduleId)), ModuleEntity.class, COLLECTION);
        return result.getDeletedCount();
    }

    @Override
    public List<ModuleDto> getMatching(String pattern) {
        Pattern p = Pattern.compile("(\\b"+pattern+"+\\b)", Pattern.CASE_INSENSITIVE);
        Query query = new Query(Criteria.where("moduleTitle").regex(p));
        List<ModuleEntity> modulesEntities = mongoTemplate.find(query, ModuleEntity.class, COLLECTION);
        List<ModuleDto> modules = new ArrayList<ModuleDto>();

        for (ModuleEntity moduleEntity:modulesEntities) {
            modules.add(mapper.map(moduleEntity, ModuleDto.class));
        }

        return modules;
    }

    public void deleteAllModules() {
        
        mongoTemplate.dropCollection("modules");
    }

}
