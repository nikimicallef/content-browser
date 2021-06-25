package com.rbmh.contentbrowser.controllers;

import java.util.List;

import org.openapitools.api.ContentApiController;
import org.openapitools.model.Content;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.NativeWebRequest;

import com.rbmh.contentbrowser.services.ContentService;


@Component
public class ContentRestController
        extends ContentApiController {

    private final ContentService service;

    public ContentRestController(NativeWebRequest request, final ContentService contentService) {
        super(request);
        this.service = contentService;
    }

    @Override public ResponseEntity<List<Content>> contentGet(final Boolean orderByVotesDesc, final Integer page, final Integer pageSize) {
        return ResponseEntity.ok(service.getContent(orderByVotesDesc, page, pageSize));
    }

    @Override public ResponseEntity<Content> contentIdActionsDownvotePost(String id) {
        return ResponseEntity.ok(service.voteContent(id, false));
    }

    @Override public ResponseEntity<Content> contentIdActionsUpvotePost(String id) {
        return ResponseEntity.ok(service.voteContent(id, true));
    }
}
