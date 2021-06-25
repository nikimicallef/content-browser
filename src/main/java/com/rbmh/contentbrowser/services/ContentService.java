package com.rbmh.contentbrowser.services;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.openapitools.model.Content;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.rbmh.contentbrowser.exceptions.EntityNotFoundException;
import com.rbmh.contentbrowser.mappers.ContentModelMapper;
import com.rbmh.contentbrowser.repositories.ContentRepository;
import com.rbmh.contentbrowser.repositories.VotesRepository;
import com.rbmh.contentbrowser.repositories.models.ContentDbModel;
import com.rbmh.contentbrowser.repositories.models.VoteDbModel;
import com.rbmh.contentbrowser.repositories.models.VoteTypeDbEnum;


@Service
public class ContentService {

    private final ContentRepository contentRepository;

    private final VotesRepository votesRepository;

    public ContentService(ContentRepository contentRepository, VotesRepository votesRepository) {
        this.contentRepository = contentRepository;
        this.votesRepository = votesRepository;
    }

    /**
     * Returns content saved within the DB. The data returned also depends on the parameters passed in.
     *
     * @param orderByVotesDesc If specified, the content will be returned in descending order of votes, with the highest rated content being returned first
     * @param page             Determines which page to retrieve when using paging
     * @param pageSize         Determines how many entries within a page
     * @return content retrieved from Database
     */
    public List<Content> getContent(final boolean orderByVotesDesc, final int page, final Integer pageSize) {
        final List<ContentDbModel> retrievedData = new ArrayList<>();

        if (!orderByVotesDesc && pageSize == null) {
            retrievedData.addAll(contentRepository.findAll());
        } else if (!orderByVotesDesc && pageSize != null) {
            final PageRequest getTopX = PageRequest.of(page, pageSize);
            retrievedData.addAll(contentRepository.findAll(getTopX).getContent());
        } else if (orderByVotesDesc && pageSize == null) {
            retrievedData.addAll(contentRepository.findAll(Sort.by("votes").descending()));
        } else if (orderByVotesDesc && pageSize != null) {
            final PageRequest getTopX = PageRequest.of(page, pageSize, Sort.by("votes").descending());
            retrievedData.addAll(contentRepository.findAll(getTopX).getContent());
        }

        return retrievedData.stream().map(ContentModelMapper::mapFromDbModelToApiModel).collect(Collectors.toList());
    }

    /**
     * Upvotes or downvotes a piece of content and stores the vote in the {@link VotesRepository}.
     * This method is in a synchronized block to avoid multiple threads updating the same entity at the same time.
     *
     * @param contentId to vote on
     * @param upvote    if true then the content has been upvoted, if false then the content has been downvoted
     * @return updated content entity
     */
    public synchronized Content voteContent(final String contentId, final boolean upvote) {
        final ContentDbModel content = contentRepository.findById(contentId).orElseThrow(() -> new EntityNotFoundException("Content with ID " + contentId + " not found."));

        if (upvote) {
            content.setVotes(content.getVotes() + 1);
            votesRepository.save(new VoteDbModel(null, VoteTypeDbEnum.UPVOTE, contentId, Instant.now()));
        } else {
            content.setVotes(content.getVotes() - 1);
            votesRepository.save(new VoteDbModel(null, VoteTypeDbEnum.DOWNVOTE, contentId, Instant.now()));
        }

        return ContentModelMapper.mapFromDbModelToApiModel(contentRepository.save(content));
    }
}
