package com.denbinger.vitamins.internal.search.spi.model.index.contributor;

import com.denbinger.vitamins.constants.PersistedVitaminType;
import com.denbinger.vitamins.model.PersistedVitamin;
import com.denbinger.vitamins.model.VitaminDetail;
import com.denbinger.vitamins.model.VitaminDetailModel;
import com.liferay.portal.kernel.search.Document;
import com.liferay.portal.kernel.search.Field;
import com.liferay.portal.search.spi.model.index.contributor.ModelDocumentContributor;
import org.osgi.service.component.annotations.Component;

import java.util.List;

@Component(immediate = true,
        property
                = "indexer.class.name=com.denbinger.vitamins.model.PersistedVitamin",
        service = ModelDocumentContributor.class)
public class PersistedVitaminModelDocumentContributor
        implements ModelDocumentContributor<PersistedVitamin> {
    @Override
    public void contribute(Document document, PersistedVitamin baseModel) {
        document.addText(Field.DESCRIPTION, baseModel.getDescription());
        document.addText(Field.NAME, baseModel.getName());
        String vType = "other";
        switch (baseModel.getType()) {
            case PersistedVitaminType
                    .MINERAL:
                vType = "mineral";
                break;
            case PersistedVitaminType.VITAMIN:
                vType = "vitamin";
                break;
            default:
                vType = "other";
        }
        document.addKeyword("vType", vType);
        document.addKeyword("vitaminGroup", baseModel.getGroupName());
//support for chemical names
        List<VitaminDetail> details = baseModel.getChemicalNames();
        if (details != null && !details.isEmpty()) {
            String[] chemicalNames = details
                    .stream()
                    .map(VitaminDetailModel::getValue)
                    .toArray(String[]::new);
            document.addKeywordSortable("chemicalName", chemicalNames);
        }
    }
}

