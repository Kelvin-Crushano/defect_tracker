package com.sgic.semita.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.HashMap;
import java.util.Map;

@Getter
@Setter
public class DashboardDto {
    private DashboardData dashboardData;

    @Getter
    @Setter
    public static class DashboardData {
        private Long projectId;
        private Map<String, SeverityData> defectStatusCountBySeverityAndStatus;
        private double defectRemovalEfficiency;
        private double defectSeverityIndex;
        private double defectLeakage;
        private double defectDensity;
    }

    @Getter
    @Setter
    public static class SeverityData {
        private String severityColorCode;
        private Map<String, DefectStatusDetail> statuses = new HashMap<>();
    }

    @Getter
    @Setter
    @AllArgsConstructor
    public static class DefectStatusDetail {
        private long count;
        private String statusColorCode;
    }
}
