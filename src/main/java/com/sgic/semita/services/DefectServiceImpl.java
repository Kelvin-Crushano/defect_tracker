package com.sgic.semita.services;

import com.sgic.semita.dtos.DefectDto;
import com.sgic.semita.entities.*;
import com.sgic.semita.entities.Module;
import com.sgic.semita.repositories.*;
import com.sgic.semita.utils.ValidationMessages;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import com.sgic.semita.dtos.DefectGetDto;
import com.sgic.semita.entities.Defect;
import org.springframework.beans.BeanUtils;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import java.time.Instant;
import java.util.List;
import java.util.stream.Collectors;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Optional;


@Service
public class DefectServiceImpl  implements DefectService{
    @Autowired
    private DefectRepository defectRepository;
    @Autowired
    private PriorityRepository defectPriorityRepository;
    @Autowired
    private SeverityRepository defectSeverityRepository;
    @Autowired
    private DefectStatusRepository defectStatusRepository;
    @Autowired
    private DefectTypeRepository defectTypeRepository;
    @Autowired
    private ModuleRepository moduleRepository;
    @Autowired
    private ProjectRepository projectRepository;
    @Autowired
    private ReleaseRepository releaseRepository;
    @Autowired
    private SubModuleRepository subModuleRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ProjectAllocationsRepository projectAllocationsRepository;

    @Autowired
    private PriorityRepository priorityRepository;

    @Autowired
    SeverityRepository severityRepository;
   // private static final Logger logger = LoggerFactory.getLogger(DefectServiceImpl.class);
   @Override
   @Transactional(readOnly = true)
   public List<DefectGetDto> searchDefects(
           Long projectId,
           String stepToReCreate,
           String defectType,
           String defectStatus,
           String priority,
           String severity,
           String release,
           String subModule,
           String module,
           String assignBy,
           String assignTo,
           Pageable pageable) {

       if (projectId == null) {
           throw new RuntimeException(ValidationMessages.PROJECT_NOT_NULL_DEFECT);
       }
       if (!projectRepository.existsById(projectId)) {
           throw new RuntimeException(ValidationMessages.PROJECT_NOT_DEFECT);
       }
       try {
           Page<Defect> defectsPage = defectRepository.findDefectsByCriteria(
                   projectId, stepToReCreate, defectType, defectStatus,
                   priority, severity, release, subModule,
                   module, assignTo, assignBy, pageable
           );

           return defectsPage.stream()
                   .map(defect -> {
                       DefectGetDto defectGetDto = new DefectGetDto();
                       BeanUtils.copyProperties(defect, defectGetDto);
                       setRelatedEntitiesDto(defect, defectGetDto);
                       return defectGetDto;
                   })
                   .collect(Collectors.toList());

       } catch (Exception e) {
           throw new RuntimeException(e);
       }
   }

    @Override
    @Transactional(readOnly = false)
    public List<Defect> uploadDefects(Long projectId, MultipartFile file) {
        List<Defect> defects = new ArrayList<>();
        List<String> errorMessages = new ArrayList<>();
        CSVFormat format = CSVFormat.DEFAULT.builder()
                .setHeader()
                .build();
        Project project = projectRepository.findById(projectId)
                .orElseThrow(() -> new RuntimeException("Project not found for ID: " + projectId));

        try (BufferedReader reader = new BufferedReader(new InputStreamReader(file.getInputStream()));
             CSVParser csvParser = new CSVParser(reader, format)) {

            int rowNumber = 0;
            for (CSVRecord csvRecord : csvParser) {
                rowNumber++;
                Defect defect = new Defect();
                defect.setStepToReCreate(csvRecord.get("StepToReCreate"));
                defect.setComments(csvRecord.get("Comments"));

                int finalRowNumber = rowNumber;

                Optional.ofNullable(csvRecord.get("DefectType")).flatMap(name ->
                                defectTypeRepository.findByName(name))
                        .ifPresentOrElse(defect::setDefectType, () -> {
                            errorMessages.add("Row " + finalRowNumber + ": DefectType not found.");
                        });

                Optional.ofNullable(csvRecord.get("DefectStatus")).flatMap(name ->
                                defectStatusRepository.findByName(name))
                        .ifPresentOrElse(defect::setDefectStatus, () -> {
                            errorMessages.add("Row " + finalRowNumber + ": DefectStatus not found.");
                        });

                Optional.ofNullable(csvRecord.get("DefectPriority")).flatMap(name ->
                                defectPriorityRepository.findByName(name))
                        .ifPresentOrElse(defect::setPriority, () -> {
                            errorMessages.add("Row " + finalRowNumber + ": DefectPriority not found.");
                        });

                Optional.ofNullable(csvRecord.get("DefectSeverity")).flatMap(name ->
                                defectSeverityRepository.findByName(name))
                        .ifPresentOrElse(defect::setSeverity, () -> {
                            errorMessages.add("Row " + finalRowNumber + ": DefectSeverity not found.");
                        });

                Optional<Release> releaseOpt = Optional.ofNullable(csvRecord.get("Release"))
                        .flatMap(name -> releaseRepository.findByNameAndProjectId(name, projectId));
                releaseOpt.ifPresentOrElse(defect::setRelease, () -> {
                    errorMessages.add("Row " + finalRowNumber + ": Release not found.");
                });

                Optional<Module> moduleOpt = Optional.ofNullable(csvRecord.get("Module"))
                        .flatMap(name -> moduleRepository.findByNameAndProjectId(name, projectId));
                moduleOpt.ifPresentOrElse(defect::setModule, () -> {
                    errorMessages.add("Row " + finalRowNumber + ": Module not found.");
                });

                if (moduleOpt.isPresent()) {
                    Optional<SubModule> subModuleOpt = Optional.ofNullable(csvRecord.get("SubModule"))
                            .flatMap(name -> subModuleRepository.findByNameAndModuleId(name, moduleOpt.get().getId()));
                    subModuleOpt.ifPresentOrElse(defect::setSubModule, () -> {
                        errorMessages.add("Row " + finalRowNumber + ": SubModule not found.");
                    });
                } else {
                    errorMessages.add("Row " + finalRowNumber + ValidationMessages.SUBMODULE_SKIPPED);
                }


                Optional<String> assignByEmail = Optional.ofNullable(csvRecord.get("AssignBy"));
                Optional<User> userOpt = assignByEmail.flatMap(userRepository::findByEmail);

                userOpt.flatMap(user -> projectAllocationsRepository.findByUserAndProject(user, project))
                        .ifPresentOrElse(
                                defect::setAssignBy,
                                () -> errorMessages.add("Row " + finalRowNumber + ValidationMessages.PROJECT_ALLOCATION_NOT_FOUND_FOR_USER)
                        );

                Optional<String> assignToEmail = Optional.ofNullable(csvRecord.get("AssignTo"));
                Optional<User> userOpTo = assignToEmail.flatMap(userRepository::findByEmail);

                userOpTo.flatMap(user -> projectAllocationsRepository.findByUserAndProject(user, project))
                        .ifPresentOrElse(
                                defect::setAssignTo,
                                () -> errorMessages.add("Row " + finalRowNumber + ValidationMessages.PROJECT_ALLOCATION_NOT_FOUND_FOR_USER)
                        );

                Instant now = Instant.now();
                defect.setCreatedAt(now);
                defect.setUpdatedAt(now);

                    defects.add(defect);
            }

            if (!errorMessages.isEmpty()) {
                throw new RuntimeException(ValidationMessages.ERROR_CSV + String.join(" ", errorMessages));
            }

            if (defects.isEmpty()) {
                throw new RuntimeException(ValidationMessages.NO_DEFECT_IN_CSV);
            } else {

                return defectRepository.saveAll(defects);
            }
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage(), e);
        }
    }


    @Override
    public Defect createDefect(DefectDto defectDto) {
        Defect defect = new Defect();
        BeanUtils.copyProperties(defectDto, defect, "id", "createdAt", "updatedAt");
        setAndValidateForeignKeys(defect, defectDto);
        defect.setCreatedAt(Instant.now());
        defect.setUpdatedAt(Instant.now());
        return defectRepository.save(defect);
    }

    @Override
    public List<DefectGetDto> getAllDefectsByProjectId(Long projectId, Pageable pageable) {
        Page<Defect> defectPage = defectRepository.findByModule_Project_Id(projectId, pageable);

        if (defectPage.isEmpty()) {
            throw new ResourceNotFoundException(ValidationMessages.NO_RECORDS_FOUND);
        }

        return defectPage.getContent().stream()
                .map(defect -> {
                    DefectGetDto defectGetDto=new DefectGetDto();
                    BeanUtils.copyProperties(defect, defectGetDto);
                    setRelatedEntitiesDto(defect, defectGetDto); // Populate related entity IDs
                    return defectGetDto;
                })
                .collect(Collectors.toList());
    }

    @Override
    public Defect updateDefect(Long id, DefectDto defectDto) {

        Defect defect = defectRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException(ValidationMessages.INVALID_ID));

        BeanUtils.copyProperties(defectDto, defect, "id", "createdAt", "updatedAt");
        setAndValidateForeignKeys(defect, defectDto);
        defect.setUpdatedAt(Instant.now());
        return defectRepository.save(defect);
    }

    @Override
    public DefectGetDto getDefectById(Long id) {
        Defect defect = defectRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(ValidationMessages.INVALID_ID));

        DefectGetDto defectGetDto=new DefectGetDto();
        BeanUtils.copyProperties(defect, defectGetDto);
        setRelatedEntitiesDto(defect, defectGetDto);
        return defectGetDto;

    }

    @Override
    public boolean deleteDefect(Long id) {
        if(defectRepository.existsById(id)){
            defectRepository.deleteById(id);
            return true;
        }
        return false;
    }


    private void setAndValidateForeignKeys(Defect defect, DefectDto defectDto) {
        defect.setDefectType(defectTypeRepository.findById(defectDto.getDefect_type_id())
                .orElseThrow(() -> new ResourceNotFoundException("Defect Type Id " + defectDto.getDefect_type_id() + " not found")));
        defect.setDefectStatus(defectStatusRepository.findById(defectDto.getDefect_status_id())
                .orElseThrow(() -> new ResourceNotFoundException("Defect Status Id "+defectDto.getDefect_status_id()+" not found")));
        defect.setPriority(priorityRepository.findById(defectDto.getPriority_id())
                .orElseThrow(() -> new ResourceNotFoundException("Priority Id "+defectDto.getPriority_id()+" not found")));
        defect.setSeverity(severityRepository.findById(defectDto.getSeverity_id())
                .orElseThrow(() -> new ResourceNotFoundException("Severity Id "+defectDto.getSeverity_id()+" not found")));
        defect.setRelease(releaseRepository.findById(defectDto.getRelease_id())
                .orElseThrow(() -> new ResourceNotFoundException("Release Id "+defectDto.getRelease_id()+" not found")));
        defect.setSubModule(subModuleRepository.findById(defectDto.getSub_module_id())
                .orElseThrow(() -> new ResourceNotFoundException("SubModule Id "+defectDto.getSub_module_id()+" not found")));
        defect.setModule(moduleRepository.findById(defectDto.getModule_id())
                .orElseThrow(() -> new ResourceNotFoundException("Module Id "+defectDto.getModule_id()+" not found")));
        defect.setAssignTo(projectAllocationsRepository.findById(defectDto.getAssign_to())
                .orElseThrow(() -> new ResourceNotFoundException("Assign To Id "+defectDto.getAssign_to()+" not found")));
        defect.setAssignBy(projectAllocationsRepository.findById(defectDto.getAssign_by())
                .orElseThrow(() -> new ResourceNotFoundException("Assign By Id "+defectDto.getAssign_by()+" not found")));

        if (defectDto.getAssign_by().equals(defectDto.getAssign_to())) {
            throw new RuntimeException("Assign_by cannot be the same as Assign_to.");
        }
    }

    private void setRelatedEntitiesDto(Defect defect, DefectGetDto defectGetDto) {
        defectGetDto.setDefectTypeName(defect.getDefectType().getName());
        defectGetDto.setDefectStatusColorCode(defect.getDefectStatus().getColorCode());
        defectGetDto.setDefectStatusName(defect.getDefectStatus().getName());
        defectGetDto.setPriorityColorCode(defect.getPriority().getColorCode());
        defectGetDto.setPriorityStatusName(defect.getPriority().getName());
        defectGetDto.setSeverityColorCode(defect.getSeverity().getColorCode());
        defectGetDto.setSeverityStatusName(defect.getSeverity().getName());
        defectGetDto.setReleaseName(defect.getRelease().getName());
        defectGetDto.setSubModuleName(defect.getSubModule().getName());
        defectGetDto.setModuleName(defect.getModule().getName());
        defectGetDto.setAssignToName(defect.getAssignTo().getUser().getName());
        defectGetDto.setAssignByName(defect.getAssignBy().getUser().getName());

        // Set formatted defect ID
        String projectPrefix = defect.getModule().getProject().getPrefix();
        String formattedDefectId = String.format("%s-%03d", projectPrefix, defect.getId());
        defectGetDto.setFormattedDefectId(formattedDefectId);
    }
    // Convert To CSV
    @Override
    public String convertToCsv(List<DefectGetDto> defectDtos) {
        StringBuilder csvBuilder = new StringBuilder();
        csvBuilder.append("ID,StepToReCreate,Comments,DefectType,DefectStatus,Priority,Severity,Release,SubModule,Module,AssignTo,AssignBy\n");

        for (DefectGetDto defectDto : defectDtos) {
            csvBuilder.append(defectDto.getFormattedDefectId()).append(",");
            csvBuilder.append(defectDto.getStepToReCreate()).append(",");
            csvBuilder.append(defectDto.getComments()).append(",");
            csvBuilder.append(defectDto.getDefectTypeName()).append(",");
            csvBuilder.append(defectDto.getDefectStatusName()).append(",");
            csvBuilder.append(defectDto.getPriorityStatusName()).append(",");
            csvBuilder.append(defectDto.getSeverityStatusName()).append(",");
            csvBuilder.append(defectDto.getReleaseName()).append(",");
            csvBuilder.append(defectDto.getSubModuleName()).append(",");
            csvBuilder.append(defectDto.getModuleName()).append(",");
            csvBuilder.append(defectDto.getAssignToName()).append(",");
            csvBuilder.append(defectDto.getAssignByName()).append("\n");
        }

        return csvBuilder.toString();
    }
}
