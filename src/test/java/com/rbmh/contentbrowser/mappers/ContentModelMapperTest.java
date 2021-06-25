package com.rbmh.contentbrowser.mappers;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;
import org.openapitools.model.Content;

import com.rbmh.contentbrowser.repositories.models.ContentDbModel;
import com.rbmh.contentbrowser.repositories.models.MediaTypeDbEnum;

import uk.co.jemos.podam.api.PodamFactory;
import uk.co.jemos.podam.api.PodamFactoryImpl;


public class ContentModelMapperTest {

    private static final PodamFactory PODAM_FACTORY = new PodamFactoryImpl();

    @Test
    public void mapFromDbModelToApiModel_randomDbModel_correctMapping() {
        final ContentDbModel dbModel = PODAM_FACTORY.manufacturePojo(ContentDbModel.class);

        final Content apiModel = ContentModelMapper.mapFromDbModelToApiModel(dbModel);

        assertEquals(dbModel.getId(), apiModel.getId(), "ID does not match");
        final Content.MediaTypeEnum expectedMediaType = mapDbMediaTypeToApiMediaType(dbModel.getMediaType());
        assertEquals(expectedMediaType, apiModel.getMediaType(), "Media Type does not match");

        assertEquals(dbModel.getSource(), apiModel.getSource(), "Source does not match");
        assertEquals(dbModel.getTitle(), apiModel.getTitle(), "Title does not match");
        assertEquals(dbModel.getContentUrl(), apiModel.getContentUrl(), "Content URL does not match");
        assertEquals(dbModel.getPreviewUrl(), apiModel.getPreviewUrl(), "Preview URL does not match");
        assertEquals(dbModel.getVotes(), apiModel.getVotes(), "Votes does not match");
        assertEquals(dbModel.getDescription(), apiModel.getDescription(), "Description does not match");
        assertEquals(dbModel.getLengthSeconds(), apiModel.getLength(), "Length does not match");
        assertEquals(dbModel.getAspectRatio(), apiModel.getAspectRatio(), "Aspect Ratio does not match");
        assertEquals(dbModel.getTopic(), apiModel.getTopic(), "Topic does not match");
    }


    @ParameterizedTest
    @EnumSource(MediaTypeDbEnum.class)
    public void mapMediaTypeDbModelToApiModel_randomMediaType_correctMapping(final MediaTypeDbEnum dbEnum) {
        final Content.MediaTypeEnum returnedMediaTypeEnum = ContentModelMapper.mapMediaTypeDbModelToApiModel(dbEnum);

        assertEquals(mapDbMediaTypeToApiMediaType(dbEnum), returnedMediaTypeEnum, "Media type does not match");
    }

    private Content.MediaTypeEnum mapDbMediaTypeToApiMediaType(MediaTypeDbEnum dbEnum) {
        return dbEnum == MediaTypeDbEnum.IMAGE ? Content.MediaTypeEnum.IMAGE : Content.MediaTypeEnum.VIDEO;
    }
}
