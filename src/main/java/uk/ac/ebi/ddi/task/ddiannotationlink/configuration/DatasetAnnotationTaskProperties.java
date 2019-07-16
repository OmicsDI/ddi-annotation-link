package uk.ac.ebi.ddi.task.ddiannotationlink.configuration;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties("linkannotation")
public class DatasetAnnotationTaskProperties {

    private String databaseName;

    public String getDatabaseName() {
        return databaseName;
    }

    public void setDatabaseName(String databaseName) {
        this.databaseName = databaseName;
    }

}
