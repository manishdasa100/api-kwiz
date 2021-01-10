package kwiz.backend.api.repositoryService;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import kwiz.backend.api.dto.ModuleDto;
import kwiz.backend.api.entity.ModuleEntity;
import kwiz.backend.api.repositoryService.ModuleRepositoryService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Query;

public class ModuleRepositoryServiceTest {
    
    @Mock
    private MongoTemplate mongoTemplate;

    @InjectMocks
    private ModuleRepositoryServiceImpl service;

    private static List<ModuleEntity> allModules;

    private List<ModuleEntity> loadDummyEntities() {
        return Arrays.asList(
            new ModuleEntity("1234", "Java Beginner", "Beginner practise questions for java", 0, 30),
            new ModuleEntity("1235", "Python Beginner", "Beginner practise questions for pyhton", 0, 35),
            new ModuleEntity("1236", "Javascript Beginner", "Beginner practise questions for javascript", 0, 25)
        );
    }

    @BeforeEach
    private void setup(){
        allModules = loadDummyEntities();
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void getAllModulesTest() {

        when(mongoTemplate.findAll(ModuleEntity.class, ModuleRepositoryServiceImpl.COLLECTION)).thenReturn(Collections.EMPTY_LIST);

        List<ModuleDto> moduleDtos = service.getAll();

        assertEquals(0, moduleDtos.size());

        when(mongoTemplate.findAll(ModuleEntity.class, ModuleRepositoryServiceImpl.COLLECTION)).thenReturn(allModules);

        moduleDtos = service.getAll();
       
        assertEquals("1234", moduleDtos.get(0).getModuleId());
        assertEquals("1236", moduleDtos.get(2).getModuleId());
    }

    @Test
    public void isModuleExistTest() {
        service.isModuleExist("moduleId");

        verify(mongoTemplate, times(1)).exists(any(Query.class), eq(ModuleEntity.class), anyString());
    }

    @Test
    public void addModuleTest() {
        service.add(new ModuleDto());

        verify(mongoTemplate, times(1)).save(any(ModuleEntity.class), anyString());
    }

    // @Test
    // public void updateModuleTest() {
    //     when(mongoTemplate.findOne(any(Query.class), eq(ModuleEntity.class), anyString())).thenReturn(null, new ModuleEntity());

    //     service.update("moduleId", new ModuleDto());

    //     verify(mongoTemplate, times(0)).save(any(ModuleEntity.class), anyString());

    //     service.update("moduleId", new ModuleDto());

    //     verify(mongoTemplate, times(1)).save(any(ModuleEntity.class), anyString());

    // }

}
