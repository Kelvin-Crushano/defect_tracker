package com.sgic.semita.utils;

public class EndpointBundle {
    public static final String BASE_URL = "api/v1";
    public static final String USER = BASE_URL + "/users";
    public static final String ID = "/{id}";
    public static final String EMAIL_ENDPOINTS = BASE_URL + "/email-endpoints";
    // Email Recipients
    public static final String EMAIL_RECIPIENTS = BASE_URL + "/email-recipients";
    public static final String ENDPOINTS_ID = "/{emailEndpointId}";
    public static final String STATUS = "/{id}/status";
    public static final String _USER = "/user/{emailEndpointId}";
    public static final String PROJECT = BASE_URL + "/project";
    public static final String EMAIL_Config = BASE_URL + "/email-config";
    public static final String PRIORITY = BASE_URL + "/priority";
    public static final String SEVERITY = BASE_URL + "/severities";
	public static final String DENSITY_RANGE = BASE_URL + "/density-range";

    public static final String PROJECT_ROLE =BASE_URL+"/project-role";

    public static final String SEARCH  ="/search";


    public static final String DEFECT_STATUS = BASE_URL + "/defect-status";
	public static final String PROJECT_ID = "/{projectId}";
	public static final String DEFECT_TYPE = BASE_URL + "/defect-types";

    public static final String DEFECT = BASE_URL + "/defect";
    public static final String _PROJECT_ID = "project/{projectId}";


    public static final String RELEASE_MODULE = BASE_URL + "/release-module";


    // project_type
    public static final String PROJECT_TYPE = BASE_URL + "/project-type";
    // Project Status

	// Project Status
	public static final String PROJECT_STATUS = BASE_URL + "/project-status";
    public static final String ROLE = BASE_URL+"/role";

    public static final String SUBMODULE =BASE_URL + "/sub-module" ;
    public static final String SUBMODULE_PROJECT="/project/{projectId}";

    public static final String USER_ROLE = BASE_URL+"/user-role";
    public static final String PROJECT_ALLOCATION=BASE_URL+ "/project-allocations";
    public static final String BENCH="/bench-resources";
    public static final String ALLOCATE="/allocate";


    // Module
    public static final String MODULE = BASE_URL + "/modules";
    public static final String MODULE_BY_PROJECT =  "/project-module/{projectId}";
    public static final String UPDATE_SUBMODULE = BASE_URL + "/modules/{moduleId}/submodules/{subModuleId}";


    public static final String SUB_MODULE_BY_PROJECT = "/project/{projectId}/modules/{moduleId}";


    public static final String PROJECT_KLOC= "/kloc/{projectId}";

    // Defect
    public static final String DEFECT_URL = "/api/v1/defects";
    public static final String GET_BY_PROJECT = "/{projectId}";
    //Search Defect
    public static final String SEARCH_DEFECT= "/search";
    public static final String STEP_TO_RECREATE ="stepToReCreate";
    public static final String DEFECT_TYPE_SEARCH = "defectType";
    public static final String DEFECT_STATUS_SEARCH = "defectStatus";
    public static final String DEFECT_PRIORITY = "priority";
    public static final String DEFECT_SEVERITY = "severity";
    public static final String RELEASE_NAME = "release";
    public static final String SUBMODULE_NAME = "subModule";
    public static final String MODULE_NAME = "module";
    public static final String ASSIGN_TO = "assignBy";
    public static final String  ASSIGN_BY = "assignTo";
    public static final String PROJECT_ID_DE = "projectId";
    public static final String UPLOAD = "/upload";
    public static final String _DELETE = "/{defectId}";
    public static final String DELETE = "defectId";
    public static final String EXPORT = "/export";
    // Release
    public static final String RELEASE = BASE_URL + "/release";
    public static final String RELEASE_PROJECT = "/project/{projectId}";
    //Dashboard
    public static final String DASHBOARD = BASE_URL + "/dashboard/project";

    public static final String MODULE_ALLOCATION=BASE_URL+"/module-allocations";
    public static final String MODULE_ALLOCATION_GET="/Get/{id}";

    // Status workflow
    public static final String DELETE_ALL_TRANSITIONS_PROJECT_BY_ID = "/project/{projectId}";

    //Status Workflow
    public static final String STATUS_WORKFLOW = BASE_URL + "/status-workflow";
    public static final String PROJECT_ID_STATUS_ID = "/project/{projectId}/status/{statusId}";
    private EndpointBundle() {

	}
}
