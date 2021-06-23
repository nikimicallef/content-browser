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

    private ContentService service;

    public ContentRestController(NativeWebRequest request, final ContentService contentService) {
        super(request);
        this.service = contentService;
    }

    @Override
    public ResponseEntity<List<Content>> contentGet(Integer top) {
        if (top == null) {
            return ResponseEntity.ok(service.getAllContent());
        }

        return super.contentGet(top);
    }

    @Override
    public ResponseEntity<Content> contentIdActionsDislikePost(String id) {
        return super.contentIdActionsDislikePost(id);
    }

    @Override
    public ResponseEntity<Content> contentIdActionsLikePost(String id) {
        return super.contentIdActionsLikePost(id);
    }
}
