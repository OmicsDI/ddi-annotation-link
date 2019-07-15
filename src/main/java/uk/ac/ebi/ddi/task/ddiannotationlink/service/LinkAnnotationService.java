package uk.ac.ebi.ddi.task.ddiannotationlink.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uk.ac.ebi.ddi.ddidomaindb.dataset.DSField;
import uk.ac.ebi.ddi.service.db.model.dataset.Dataset;
import uk.ac.ebi.ddi.service.db.service.dataset.IDatasetService;

import java.util.List;
import java.util.Set;

@Service
public class LinkAnnotationService {

    @Autowired
    IDatasetService datasetService;

    public List<Dataset> getAllDatasetsByDatabase(String databaseName){
        return datasetService.readDatasetHashCode(databaseName);
    }

    public void annotateDatasets(String databaseName){
        getAllDatasetsByDatabase(databaseName).parallelStream().forEach(dt -> {
            annotateDatasetLink(dt);
                    datasetService.update(dt.getId(), dt);
        });
    }

    public Dataset annotateDatasetLink(Dataset dataset){
        if (dataset.getAdditional() != null
                && dataset.getAdditional().containsKey("full_study_link")) {
            Set<String> links = dataset.getAdditional().remove("full_study_link");
            dataset.getAdditional().put(DSField.Additional.LINK.getName(), links);
        }
        return dataset;
    }

}
