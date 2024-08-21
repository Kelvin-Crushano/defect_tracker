package com.sgic.semita.controllers;


import com.sgic.semita.dtos.DashboardDto;
import com.sgic.semita.dtos.ProjectDto;
import com.sgic.semita.enums.RestApiResponseStatusCodes;
import com.sgic.semita.services.DashboardService;
import com.sgic.semita.utils.EndpointBundle;
import com.sgic.semita.utils.ResponseWrapper;
import com.sgic.semita.utils.ValidationMessages;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(EndpointBundle.DASHBOARD)
public class DashboardController {

    @Autowired
    private DashboardService dashboardService;

    @GetMapping
    public ResponseEntity<ResponseWrapper<List<ProjectDto>>> getAllProjects() {
        List<ProjectDto> projects = dashboardService.getAllProjects();

        if (projects.isEmpty()) {
            return ResponseEntity.status(HttpStatus.OK).body(new ResponseWrapper<>(
                    RestApiResponseStatusCodes.NOT_FOUND.getCode(),
                    ValidationMessages.RETRIEVED_FAILED,
                    null
            ));
        }

        return ResponseEntity.status(HttpStatus.OK).body(new ResponseWrapper<>(
                RestApiResponseStatusCodes.SUCCESS.getCode(),
                ValidationMessages.RETRIEVED,
                projects
        ));
    }
    @GetMapping(EndpointBundle.GET_BY_PROJECT)
    public ResponseEntity<ResponseWrapper<DashboardDto.DashboardData>> getDashboardDetailsByProjectId(
            @PathVariable Long projectId) {

        DashboardDto.DashboardData dashboardData = dashboardService.getDashboardDetailsByProjectId(projectId);

        if (dashboardData == null) {
            ResponseWrapper<DashboardDto.DashboardData> response = new ResponseWrapper<>(
                    HttpStatus.NOT_FOUND.value(),
                    ValidationMessages.PROJECT_NOT_DEFECT + projectId.toString(),
                    null
            );
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        } else {
            ResponseWrapper<DashboardDto.DashboardData> response = new ResponseWrapper<>(
                    HttpStatus.OK.value(),
                    ValidationMessages.RETRIEVED,
                    dashboardData
            );
            return new ResponseEntity<>(response, HttpStatus.OK);
        }
    }


}
