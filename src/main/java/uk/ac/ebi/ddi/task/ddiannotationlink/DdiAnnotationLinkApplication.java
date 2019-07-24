package uk.ac.ebi.ddi.task.ddiannotationlink;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import uk.ac.ebi.ddi.task.ddiannotationlink.configuration.DatasetAnnotationTaskProperties;
import uk.ac.ebi.ddi.task.ddiannotationlink.service.LinkAnnotationService;

@SpringBootApplication
public class DdiAnnotationLinkApplication implements CommandLineRunner {

    @Autowired
    private LinkAnnotationService linkAnnotationService;

    @Autowired
    private DatasetAnnotationTaskProperties datasetAnnotationTaskProperties;

	public static void main(String[] args) {
		SpringApplication.run(DdiAnnotationLinkApplication.class, args);
	}

    @Override
    public void run(String... args) throws Exception {
        linkAnnotationService.annotateDatasets(datasetAnnotationTaskProperties.getDatabaseName());
    }

}
