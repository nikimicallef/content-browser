package com.rbmh.contentbrowser.services;

import java.util.List;
import java.util.stream.Collectors;

import org.openapitools.model.Content;
import org.springframework.stereotype.Service;

import com.rbmh.contentbrowser.mapper.ContentModelMapper;
import com.rbmh.contentbrowser.repositories.ContentRepository;


@Service
public class ContentService {

    private final ContentRepository repository;

    public ContentService(ContentRepository repository) {
        this.repository = repository;
    }

    public List<Content> getAllContent() {
        return repository.findAll().stream().map(ContentModelMapper::mapFromDbModelToApiModel).collect(Collectors.toList());
    }
}
