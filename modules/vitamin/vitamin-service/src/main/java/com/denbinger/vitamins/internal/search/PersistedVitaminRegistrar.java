package com.denbinger.vitamins.internal.search;

import com.denbinger.vitamins.model.PersistedVitamin;
import com.liferay.portal.kernel.search.Field;
import com.liferay.portal.search.spi.model.index.contributor.ModelIndexerWriterContributor;
import com.liferay.portal.search.spi.model.registrar.ModelSearchRegistrarHelper;
import com.liferay.portal.search.spi.model.result.contributor.ModelSummaryContributor;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceRegistration;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;
import org.osgi.service.component.annotations.Reference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Component(immediate = true, service = PersistedVitaminRegistrar.class)
public class PersistedVitaminRegistrar {
    @Reference(target
            = "(indexer.class.name=com.denbinger.vitamins.model.PersistedVitamin)")
    protected ModelSummaryContributor modelSummaryContributor;
    @Reference(target
            = "(indexer.class.name=com.denbinger.vitamins.model.PersistedVitamin)")
    protected ModelIndexerWriterContributor<PersistedVitamin> persistedVitaminModelIndexerWriterContributor;
    @Reference
    protected ModelSearchRegistrarHelper modelSearchRegistrarHelper;
    private ServiceRegistration<?> serviceRegistration;

    @Activate
    public void activate(BundleContext bundleContext) {
        serviceRegistration = modelSearchRegistrarHelper.register(
                PersistedVitamin.class, bundleContext,
                modelSearchDefinition -> {
                    modelSearchDefinition.setDefaultSelectedFieldNames(
                            Field.COMPANY_ID,
                            Field.ENTRY_CLASS_NAME,
                            Field.ENTRY_CLASS_PK,
                            Field.GROUP_ID,
                            Field.SCOPE_GROUP_ID,
                            Field.UID,
                            Field.NAME,
                            Field.DESCRIPTION,
                            "chemicalNames"
                    );

                    modelSearchDefinition.setModelIndexWriteContributor(persistedVitaminModelIndexerWriterContributor);

                    modelSearchDefinition.setModelSummaryContributor(modelSummaryContributor);
                }
        );
        logger.error("persisted vitamin registrar for search");
    }

    Logger logger = LoggerFactory.getLogger(PersistedVitaminRegistrar.class);

    @Deactivate
    public void deactivate() {
        serviceRegistration.unregister();
    }
}

