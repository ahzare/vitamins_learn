package com.denbinger.vitamins.internal.search.spi.model.index.contributor;

import com.denbinger.vitamins.model.PersistedVitamin;
import com.denbinger.vitamins.service.PersistedVitaminLocalService;
import com.liferay.portal.kernel.search.Document;
import com.liferay.portal.search.batch.BatchIndexingActionable;
import com.liferay.portal.search.batch.DynamicQueryBatchIndexingActionableFactory;
import com.liferay.portal.search.spi.model.index.contributor.ModelIndexerWriterContributor;
import com.liferay.portal.search.spi.model.index.contributor.helper.IndexerWriterMode;
import com.liferay.portal.search.spi.model.index.contributor.helper.ModelIndexerWriterDocumentHelper;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

@Component(
        immediate = true,
        property
                = "indexer.class.name=com.dnebinger.vitamins.model.PersistedVitamin",
        service = ModelIndexerWriterContributor.class
)
public class PersistedVitaminModelIndexerWriterContributor
        implements ModelIndexerWriterContributor<PersistedVitamin> {
    @Override
    public void customize(BatchIndexingActionable batchIndexingActionable,
                          ModelIndexerWriterDocumentHelper modelIndexerWriterDocumentHelper) {
        batchIndexingActionable.setPerformActionMethod((PersistedVitamin vitamin) -> {
            Document document = modelIndexerWriterDocumentHelper.getDocument(vitamin);
            batchIndexingActionable.addDocuments(document);
        });
    }

    @Override
    public BatchIndexingActionable getBatchIndexingActionable() {
        return dynamicQueryBatchIndexingActionableFactory
                .getBatchIndexingActionable(persistedVitaminLocalService
                        .getIndexableActionableDynamicQuery());
    }

    @Override
    public long getCompanyId(PersistedVitamin baseModel) {
        return baseModel.getCompanyId();
    }

    @Override
    public IndexerWriterMode getIndexerWriterMode(PersistedVitamin baseModel) {
        return IndexerWriterMode.UPDATE;
    }

    @Reference
    private PersistedVitaminLocalService persistedVitaminLocalService;
    @Reference
    private DynamicQueryBatchIndexingActionableFactory dynamicQueryBatchIndexingActionableFactory;
}

