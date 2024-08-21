package com.sgic.semita.utils;



public class ValidationMessages {
    public static final String SAVED_SUCCESSFULL = "Saved successfully.";
    public static final String SAVE_FAILED = "Save Failed.";
    public static final String UPDATE_SUCCESS="Update successfully";
    public static final String UPDATE_FAILED="Update failed";
    public static final String DELETE_SUCCESS = "Delete successfully";
    public static final String DELETE_FAILED = "Delete Failed";
    public static final String RETRIEVED = "Retrieved Successfully.";
    public static final String RETRIEVED_FAILED = "Retrieve Failed.";
    public static final String EMAIL_NOT_EMPTY = "please enter email address";
    public static final String INVALID_NAME = "Firstname can contain only A-Z or a-z characters only.";
    public static final String INVALID_EMAIL = "An email should be in the form of: your-email@example.com";
    public static final String NAME = "Name must not be null";
    public static final String COLOR_CODE = "Color Code must not be null";

    public static final String NO_RECORDS_FOUND="No records found";
    // Exception Handler
    public static final String FOREIGN_KEY_CONSTRIN = "Unable to Delete: This Record Linked to Another Record.";
    public static final String DUPLICATE_ENTRY = "Duplicate Entry: You are try to insert a data that already in the database.";
    public static final String WRONG_API_CALL = "Wrong API method or path: Please ensure that your API path and method are correct.";
    public static final String WRONG_JSON = "JSON is not in a valid format.";
    public static final String MIN_REQUIREMENT = "At least one attribute must be present";



    // Login
    public static final String INVALID_CREDENTIAL = "Invalid email or password";

    // Invalid Fields
    public static final String INVALID_ID = "Invalid ID: No Value Present.";

    //Others
    public static final String MISMATCH_INPUT = "Input is not in a valid format.";
    public static final String COLOR_CODE_NOT_EMPTY = "ColorCode must not be null" ;
    public static final String NAME_NOT_EMPTY ="Name must not be null" ;
    public static final String NOT_EMPTY ="must not be null" ;


    //Defect status
    public static final String ROLE_NOT_FOUND = "Role not found";
    public static final String DEFECT_STATUS_NOT_FOUND = "DefectStatus not found";
    public static final String COLOR_CODE_ALREADY_EXISTS = "Color code already exists";
    public static final String DEFECT_STATUS_ALREADY_EXISTS="Defect status already exists";

    public static final String NO_Record="No Records Found !";
    public static final String INTERNAL_SERVER_ERROR = "Internal server Error";
    public static final String INVALID_PREFIX = "Invalid prefix";
    public static final String START_DATE_NOT_NULL = "Start date cannot be null";
    public static final String INVALID_START_DATE = "Start date must be in the present or future";
    public static final String END_DATE_NOT_NULL = "End date cannot be null";
    public static final String INVALID_END_DATE = "End date must be in the present or future";
    public static final String INVALID_KLOC = "KLOC must be zero or positive";
    public static final String INVALID_CLIENT_NAME = "Invalid client name";
    public static final String INVALID_CONTACT_NAME = "Invalid contact name";
    public static final String INVALID_MOBILE_NO = "Invalid mobile number";
    public static final String PROJECT_STATUS_NOT_NULL = "Project status cannot be null";
    public static final String PROJECT_TYPE_NOT_NULL = "Project type cannot be null";



    //public static final String NO_RECORDS_FOUND="No records found";

    public static final String NAME_REQUEST = "Name must not be null";
    public static final String NO_SUCH_FILE = "Project Status Not found ";


    //Email Recipients
    public static final String EMAIL_RECIPIENT_NOT = "Email Recipients Not Found";

    // project_type
    public static final String PROJECT_TYPE_NOT_FOUND = "Project type not found";
    public static final String PROJECT_TYPE_ALREADY_EXISTS = "This project type already exists";
    public static final String COLOUR_CODE ="ColorCode must not be null" ;
    public static final String USER_ALLOCATE="Users allocated successfully";
    public static final String ALLOCATE_FAIL="Failed to allocate users";


    // release
    public static final String PROJECT_NOT_FOUND = "Project not found.";
    public static final String RELEASE_NOT_FOUND = "Release not found.";


    //Defect
    public static final String DEFECT_NOT_FOUND = "Defect not found";
    public static final String DEFECT_FOUND = "Defect found";
    public static final String ERROR_OCCURRED_WHEN_SEARCHING = "An error occurred while searching for defects.";
    public static final String ERROR_CSV = "Errors found in the CSV file: ";
    public static final String NO_DEFECT_IN_CSV = "No defects found in the CSV file.";
    public static final String PROJECT_NOT_DEFECT = "Project not found.";
    public static final String PROJECT_NOT_NULL_DEFECT = "Project ID cannot be null.";
    public static final String MODULE_NOT_SET = ": Module not set, cannot determine project ";
    public static final String PROJECT_ALLOCATION_NOT_FOUND_FOR_USER = ":project allocation not found for user and project.";
    public static final String PROJECT_ID_NOT_MATCH = ": Project IDs do not match for Release, SubModule, and Module.";
    public static final String RELEASE_NOT_FOUND_PROJECT_ID =": Release not found for the provided project ID.";
    public static final String PROJECT_ID_MISMATCH = ": Project ID mismatch.";
    public static final String SUBMODULE_SKIPPED = ": Module is not set, so SubModule lookup is skipped.";
    // sub module
    public static final String MODULE_NOT_FOUND = "Module not found with ID : ";
    public static final String MODULE_ALLOCATION_NOT_FOUND_PROJECT="No module allocations found for the given project ID:";
    public static final String MODULE_ALLOCATION_NOT_FOUND="Module Allocation not found";
    public static final String SUBMODULE_NOT_FOUND="Submodule not found with ID :";
    public static final String USER_NOT_FOUND="User not found";

    // Status Workflow
    public static final String CREATED_UPDATED = "Transitions created/updated successfully.";
    public static final String CREATE_INVALID = "Invalid status IDs or project ID";
    public static final String DUPLICATE_TRANSITION = "Duplicate transition";
    public static final String INVALID_TRANSITION_SAME_STATUS = "Invalid Transition same status";
    public static final String NO_TRANSITION = "No Transition Found";




    // project allocation
    public static final String PROJECT_ALLOCATION_NOT_FOUND = "Project Allocation not found with ID : ";
    //
    public static final String WORKFLOW_EMPTY = "Workflow list cannot be empty";
    private ValidationMessages() {

    }

}
