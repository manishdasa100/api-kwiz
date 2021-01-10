package kwiz.backend.api.controller;

import kwiz.backend.api.dto.ModuleDto;
import kwiz.backend.api.exchanges.GetModuleResponse;
import kwiz.backend.api.service.ModuleService;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ModuleController {

    public static final String BASE_URL = "/quiz/v2/api/modules";

    @Autowired
    private ModuleService moduleService;

    @GetMapping("/quiz/v2/api")
    public String greet() {
        return "<h1>Hello from the backend<h1>";
    }

    @GetMapping(BASE_URL)
    public GetModuleResponse getModules(@RequestParam(required = false) String pattern) {
        if (pattern == null) {
            GetModuleResponse response = moduleService.getAllModules();
            return response;
        }
        GetModuleResponse response = moduleService.getRelevantModules(pattern);
        return response;
    }

    @PostMapping(BASE_URL+"/add")
    public void addModule(@RequestBody List<ModuleDto> moduleDtos) {

        for (ModuleDto moduleDto: moduleDtos) {
            moduleService.addModule(moduleDto);
        } 
    }

    @PutMapping(BASE_URL+"/update")
    public void updateModule(@RequestParam String moduleId, @RequestBody ModuleDto moduleDto) {
        moduleService.updateModule(moduleId, moduleDto);
    }

    @DeleteMapping(BASE_URL+"/delete")
    public long deleteModule(@RequestParam String moduleId) {
        return moduleService.removeModule(moduleId);
    }

    @DeleteMapping(BASE_URL+"/delete_all")
    public void deleteAllModules() {
        moduleService.deleteAll();
    }
    
}
