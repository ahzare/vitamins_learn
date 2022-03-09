package com.dnebinger.headless.vitamins.internal.odata.entity.v1_0;

import com.liferay.portal.kernel.search.Field;
import com.liferay.portal.odata.entity.*;

import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class VitaminEntityModel implements EntityModel {
    public VitaminEntityModel() {
        _entityFieldsMap = Stream.of(
                // chemicalNames is a string array of the chemical names of the vitamins/minerals
                new CollectionEntityField(
                        new StringEntityField(
                                "chemicalNames", locale -> Field.getSortableFieldName("chemicalNames"))),

                // we'll support filtering based upon user creator id.
                new IntegerEntityField("creatorId", locale -> Field.USER_ID),

                // sorting/filtering on name is okay too
                new StringEntityField(
                        "name", locale -> Field.getSortableFieldName(Field.NAME)),

                // as is sorting/filtering on the vitamin group
                new StringEntityField(
                        "group", locale -> Field.getSortableFieldName("vitaminGroup")),

                // and the type (vitamin, mineral, other).
                new StringEntityField(
                        "type", locale -> Field.getSortableFieldName("vType"))
        ).collect(
                Collectors.toMap(EntityField::getName, Function.identity())
        );
    }

    @Override
    public Map<String, EntityField> getEntityFieldsMap() {
        return _entityFieldsMap;
    }

    private final Map<String, EntityField> _entityFieldsMap;
}
