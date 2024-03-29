package uk.ac.ebi.ddi.task.ddiannotationlink;


import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.ConfigFileApplicationContextInitializer;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import sun.awt.image.ImageWatched;
import uk.ac.ebi.ddi.ddidomaindb.dataset.DSField;
import uk.ac.ebi.ddi.service.db.model.dataset.Dataset;
import uk.ac.ebi.ddi.service.db.service.dataset.DatasetService;
import uk.ac.ebi.ddi.task.ddiannotationlink.configuration.DatasetAnnotationTaskProperties;
import uk.ac.ebi.ddi.task.ddiannotationlink.service.LinkAnnotationService;
import uk.ac.ebi.ddi.task.ddiannotationlink.utils.Constants;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

@RunWith(SpringRunner.class)
@ContextConfiguration(classes = DdiAnnotationLinkApplication.class,
        initializers = ConfigFileApplicationContextInitializer.class)
@TestPropertySource(properties = {
        "linkannotation.database_name=EGA"
})
public class ITLinkAnnotationService {

    @Autowired
    private DatasetAnnotationTaskProperties datasetAnnotationTaskProperties;

    @Autowired
    private LinkAnnotationService linkAnnotationService;

    @Autowired
    private DdiAnnotationLinkApplication ddiAnnotationLinkApplication;

    @Autowired
    private DatasetService datasetService;

    @Before
    public void setUp() throws Exception {
        Dataset dataset = new Dataset();
        dataset.setAccession("EGAO00000000460");
        dataset.setDatabase(datasetAnnotationTaskProperties.getDatabaseName());
        dataset.setName("Transcription profiling of Arabidopsis wild type, FIE- and MIA- loss-of-function mutants");
        dataset.setDescription("Plants with genotypes wild type, FIE loss-of-function and MIA loss-of-function" +
                " were compared. Plants were grown in growth chambers at 70% humidity and daily cycles of 16 h" +
                " light and 8 h darkness at 21 C. Plant material used for the experiments was pooled from 10 plants." +
                " Siliques without withering flower organs were harvested. The experiment was performed twice" +
                " providing independent biological replicates. This for testing only:  Libraries were prepared as " +
                "described in DOI:10.1038/NMETH.1315. Each library");
        Map<String, Set<String>> dates = new HashMap<>();
        dates.put("publication", Collections.singleton("2007-01-18"));
        dates.put("updated", Collections.singleton("2014-05-01"));
        dataset.setDates(dates);

        Map<String, Set<String>> additional = new HashMap<>();
        additional.put("omics_type", Collections.singleton("Transcriptomics"));
        additional.put("submitter", Collections.singleton("Lars Hennig"));
        additional.put("instrument_platform", Collections.singleton("418 [Affymetrix]"));
        additional.put("software", Collections.singleton("MicroArraySuite 5.0"));
        additional.put("species", Collections.singleton("Arabidopsis Thaliana"));
        additional.put("submitter_keywords", Collections.singleton("transcription profiling by array"));
        additional.put("full_study_link",
                Collections.singleton("https://www.ebi.ac.uk/arrayexpress/experiments/E-ATMX-10"));
        additional.put("submitter_email", Collections.singleton("lhennig@ethz.ch"));
        additional.put("repository", Collections.singleton("ArrayExpress"));
        additional.put("sample_protocol", Collections.singleton("Growth Protocol - Plants were grown in growth " +
                "chambers at 70% humidity and daily cycles of 16 h light and 8 h darkness at 21 C." +
                " Plant material used for the experiments was pooled from 10 plants. Siliques without withering " +
                "flower organs were harvested.\n" +
                " Hybridization - Affymetrix Generic Hybridization\n" +
                " Labeling - Fifteen micrograms of total RNA were used to prepare cDNA with the Superscript " +
                "Double-Stranded cDNA Synthesis Kit (Invitrogen) according to manufacturers instructions using " +
                "oligodT-T7 oligonucleotides (GGCCAGTGAATTGTAATACGACTCACTATAGGGAGGCGG(dT)24). The cDNA was " +
                "subjected to in vitro transcription in the presence of 2 mM each biotin-11-CTP and biotin-16-UTP " +
                "(ENZO Life Sciences, Farmingdale, NY) using the MegaScript High Yield Transcription Kit " +
                "(Ambion, Austin, TX). After purification of the cRNA on RNeasy columns (Quiagen, Hilden, Germany)," +
                " fifteen micrograms cRNA were fragmented in a volume of 40 Ýl as recommended by Affymetrix.\n" +
                " Nucleic Acid Extraction - Total RNA was prepared from frozen tissue using Trizol and purified with " +
                "RNeasy columns (Quiagen, Hilden, Germany).\n" +
                " Scaning - P-AFFY-6 Affymetrix CEL analysis"));
        additional.put("data_protocol",
                Collections.singleton("Assay Data Transformation - Affymetrix CHP Analysis (ExpressionStat)."));
        dataset.setAdditional(additional);

        Map<String, Set<String>> crossReference = new HashMap<>();
        crossReference.put("pubmed", Collections.singleton("12815071"));
        dataset.setCrossReferences(crossReference);
        dataset.setCurrentStatus("Inserted");

        datasetService.save(dataset);

    }

    @Test
    public void contextLoads() throws Exception {
        ddiAnnotationLinkApplication.run();
        Dataset dataset = datasetService.read("EGAO00000000460", datasetAnnotationTaskProperties.getDatabaseName());
        Assert.assertTrue(dataset.getAdditional().containsKey(DSField.Additional.LINK.getName()));
    }
}

