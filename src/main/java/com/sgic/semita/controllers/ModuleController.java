package com.sgic.semita.controllers;

import com.sgic.semita.dtos.ModuleDto;
import com.sgic.semita.entities.Module;
import com.sgic.semita.enums.RestApiResponseStatusCodes;
import com.sgic.semita.services.ModuleService;
import com.sgic.semita.utils.EndpointBundle;
import com.sgic.semita.utils.ResponseWrapper;
import com.sgic.semita.utils.ValidationMessages;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(EndpointBundle.MODULE)
public class ModuleController {

    @Autowired
    private ModuleService moduleService;

    @PostMapping
    public ResponseEntity<ResponseWrapper<Module>> createModule(@Valid @RequestBody ModuleDto moduleDto) {
        Module createdModule = moduleService.createModule(moduleDto);
        if (createdModule != null) {
            return ResponseEntity.status(HttpStatus.CREATED).body(new ResponseWrapper<>(
                    RestApiResponseStatusCodes.CREATED.getCode(),
                    ValidationMessages.SAVED_SUCCESSFULL,
                    null
            ));
        } else {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body(new ResponseWrapper<>(
                    RestApiResponseStatusCodes.NO_CONTENT.getCode(),
                    ValidationMessages.SAVE_FAILED,
                    null
            ));
        }
    }

    @GetMapping
    public ResponseEntity<ResponseWrapper<List<Module>>> getAllModules(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "10") int size) {
        Pageable pageable = PageRequest.of(page, size);
        List<Module> moduleList = moduleService.getAllModules(pageable).getContent();
        if (!moduleList.isEmpty()) {
            return ResponseEntity.status(HttpStatus.OK).body(new ResponseWrapper<>(
                    RestApiResponseStatusCodes.SUCCESS.getCode(),
                    ValidationMessages.RETRIEVED,
                    moduleList
            ));
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ResponseWrapper<>(
                    RestApiResponseStatusCodes.NOT_FOUND.getCode(),
                    ValidationMessages.RETRIEVED_FAILED,
                    null
            ));
        }
    }

    @GetMapping(EndpointBundle.MODULE_BY_PROJECT)
    public ResponseEntity<ResponseWrapper<List<Module>>> getModulesByProjectId(@PathVariable("projectId") Long projectId) {

        List<Module> moduleList = moduleService.getModulesByProjectId(projectId);
        if (!moduleList.isEmpty()) {
            return ResponseEntity.status(HttpStatus.OK).body(new ResponseWrapper<>(
                    RestApiResponseStatusCodes.SUCCESS.getCode(),
                    ValidationMessages.RETRIEVED,
                    moduleList
            ));
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ResponseWrapper<>(
                    RestApiResponseStatusCodes.NOT_FOUND.getCode(),
                    ValidationMessages.RETRIEVED_FAILED,
                    null
            ));
        }
    }

    @PutMapping(EndpointBundle.ID)
    public ResponseEntity<ResponseWrapper<Module>> updateModule(@PathVariable("id") Long id, @Valid @RequestBody ModuleDto moduleDto) {
        Module updatedModule = moduleService.updateModule(id, moduleDto);
        if (updatedModule != null) {
            return ResponseEntity.status(HttpStatus.OK).body(new ResponseWrapper<>(
                    RestApiResponseStatusCodes.SUCCESS.getCode(),
                    ValidationMessages.UPDATE_SUCCESS,
                    null
            ));
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ResponseWrapper<>(
                    RestApiResponseStatusCodes.NOT_FOUND.getCode(),
                    ValidationMessages.UPDATE_FAILED,
                    null
            ));
        }
    }


    @GetMapping(EndpointBundle.ID)
    public ResponseEntity<ResponseWrapper<ModuleDto>> getModuleNameById(@PathVariable("id") Long id) {
        ModuleDto moduleDto = moduleService.getModuleNameById(id);
        if (moduleDto != null) {
            return ResponseEntity.status(HttpStatus.OK).body(new ResponseWrapper<>(
                    RestApiResponseStatusCodes.SUCCESS.getCode(),
                    ValidationMessages.RETRIEVED,
                    moduleDto
            ));
        } else {
            return ResponseEntity.status(HttpStatus.OK).body(new ResponseWrapper<>(
                    RestApiResponseStatusCodes.NOT_FOUND.getCode(),
                    ValidationMessages.RETRIEVED_FAILED,
                    null
            ));
        }
    }

    @DeleteMapping(EndpointBundle.ID)
    public ResponseEntity<ResponseWrapper<Void>>deleteModule(@PathVariable Long id){
        boolean deleted=moduleService.deleteModule(id);
        if(deleted){
            return ResponseEntity.status(HttpStatus.OK).body(new ResponseWrapper<>(
                    RestApiResponseStatusCodes.SUCCESS.getCode(),
                    ValidationMessages.DELETE_SUCCESS,
                    null
            ));
        }else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponseWrapper<>(
                    RestApiResponseStatusCodes.BAD_REQUEST.getCode(),
                    ValidationMessages.DELETE_FAILED,
                    null
            ));
        }
    }
}
