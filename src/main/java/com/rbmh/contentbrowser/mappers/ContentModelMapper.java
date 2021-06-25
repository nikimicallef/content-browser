package com.rbmh.contentbrowser.mappers;

import org.openapitools.model.Content;

import com.rbmh.contentbrowser.repositories.models.ContentDbModel;
import com.rbmh.contentbrowser.repositories.models.MediaTypeDbEnum;


public class ContentModelMapper {

    /**
     * Maps an {@link ContentDbModel} to a {@link Content}
     *
     * @param dbModel to map
     * @return mapped object
     */
    public static Content mapFromDbModelToApiModel(final ContentDbModel dbModel) {
        final Content content = new Content();
        content.setId(dbModel.getId());
        content.setMediaType(mapMediaTypeDbModelToApiModel(dbModel.getMediaType()));
        content.setSource(dbModel.getSource());
        content.setTitle(dbModel.getTitle());
        content.setContentUrl(dbModel.getContentUrl());
        content.setPreviewUrl(dbModel.getPreviewUrl());
        content.setVotes(dbModel.getVotes());
        content.setDescription(dbModel.getDescription());
        content.setLength(dbModel.getLengthSeconds());
        content.setAspectRatio(dbModel.getAspectRatio());
        content.setTopic(dbModel.getTopic());

        return content;
    }

    /**
     * Maps a {@link MediaTypeDbEnum} to a {@link Content.MediaTypeEnum}
     *
     * @param dbEnum to map
     * @return mapped result
     */
    public static Content.MediaTypeEnum mapMediaTypeDbModelToApiModel(final MediaTypeDbEnum dbEnum) {
        switch (dbEnum) {

            case VIDEO:
                return Content.MediaTypeEnum.VIDEO;
            case IMAGE:
                return Content.MediaTypeEnum.IMAGE;
            default:
                throw new IllegalArgumentException("DB media type enum specified not supported.");
        }
    }
}
