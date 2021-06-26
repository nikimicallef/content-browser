package com.rbmh.contentbrowser.services;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.openapitools.model.Content;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

import com.rbmh.contentbrowser.exceptions.EntityNotFoundException;
import com.rbmh.contentbrowser.repositories.ContentRepository;
import com.rbmh.contentbrowser.repositories.VotesRepository;
import com.rbmh.contentbrowser.repositories.models.ContentDbModel;
import com.rbmh.contentbrowser.repositories.models.VoteDbModel;
import com.rbmh.contentbrowser.repositories.models.VoteTypeDbEnum;

import uk.co.jemos.podam.api.PodamFactory;
import uk.co.jemos.podam.api.PodamFactoryImpl;


public class ContentServiceTest {

    private static final PodamFactory PODAM_FACTORY = new PodamFactoryImpl();

    private ContentRepository contentRepository;
    private VotesRepository votesRepository;
    private ContentService contentService;

    @BeforeEach
    public void setUp() {
        this.contentRepository = mock(ContentRepository.class);
        this.votesRepository = mock(VotesRepository.class);
        this.contentService = new ContentService(contentRepository, votesRepository);
    }

    @AfterEach
    private void afterEach() {
        verifyNoMoreInteractions(contentRepository, votesRepository);
    }

    @Test
    public void getContent_getAllContent_allContentReturned() {
        final List<ContentDbModel> contentReturnedFromDb = PODAM_FACTORY.manufacturePojo(List.class, ContentDbModel.class);

        when(contentRepository.findAll()).thenReturn(contentReturnedFromDb);

        final List<Content> content = contentService.getContent(false, 0, null);

        assertEquals(contentReturnedFromDb.size(), content.size(), "Number of content returned is not as expected.");

        verify(contentRepository).findAll();
    }

    @Test
    public void getContent_getPagedContent_subsetOfContentReturned() {
        final List<ContentDbModel> contentReturnedFromDb = PODAM_FACTORY.manufacturePojo(List.class, ContentDbModel.class);
        final int pageSize = contentReturnedFromDb.size();
        final Page<ContentDbModel> contentPage = new PageImpl<>(contentReturnedFromDb);

        when(contentRepository.findAll(any(PageRequest.class))).thenReturn(contentPage);

        final List<Content> content = contentService.getContent(false, 0, pageSize);

        assertEquals(contentReturnedFromDb.size(), content.size(), "Number of content returned is not as expected.");

        final ArgumentCaptor<PageRequest> argumentCaptor = ArgumentCaptor.forClass(PageRequest.class);
        verify(contentRepository).findAll(argumentCaptor.capture());
        assertEquals(0, argumentCaptor.getValue().getPageNumber(), "Page number does not match");
        assertEquals(pageSize, argumentCaptor.getValue().getPageSize(), "Page size does not match");
    }

    @Test
    public void getContent_getAllContentSortedByVotesDesc_allContentReturnedSortedByVotesDesc() {
        final List<ContentDbModel> contentReturnedFromDb = PODAM_FACTORY.manufacturePojo(List.class, ContentDbModel.class);

        when(contentRepository.findAll(any(Sort.class))).thenReturn(contentReturnedFromDb);

        final List<Content> content = contentService.getContent(true, 0, null);

        assertEquals(contentReturnedFromDb.size(), content.size(), "Number of content returned is not as expected.");

        final ArgumentCaptor<Sort> argumentCaptor = ArgumentCaptor.forClass(Sort.class);
        verify(contentRepository).findAll(argumentCaptor.capture());
        assertEquals("votes", argumentCaptor.getValue().getOrderFor("votes").getProperty(), "Order property name is not correct");
        assertEquals(Sort.Direction.DESC, argumentCaptor.getValue().getOrderFor("votes").getDirection(), "Order direction is not correct");
    }

    @Test
    public void getContent_getPagedContentSortedByVotesDesc_subsetOfContentReturnedInDescCotes() {
        final List<ContentDbModel> contentReturnedFromDb = PODAM_FACTORY.manufacturePojo(List.class, ContentDbModel.class);
        final int pageSize = contentReturnedFromDb.size();
        final Page<ContentDbModel> contentPage = new PageImpl<>(contentReturnedFromDb);

        when(contentRepository.findAll(any(PageRequest.class))).thenReturn(contentPage);

        final List<Content> content = contentService.getContent(true, 0, pageSize);

        assertEquals(contentReturnedFromDb.size(), content.size(), "Number of content returned is not as expected.");

        final ArgumentCaptor<PageRequest> argumentCaptor = ArgumentCaptor.forClass(PageRequest.class);
        verify(contentRepository).findAll(argumentCaptor.capture());
        assertEquals(0, argumentCaptor.getValue().getPageNumber(), "Page number does not match");
        assertEquals(pageSize, argumentCaptor.getValue().getPageSize(), "Page size does not match");
        assertEquals(Sort.by("votes").descending(), argumentCaptor.getValue().getSort(), "Sorting does not match");
    }

    @Test
    public void voteContent_contentUpvoted_contentUpvoted() {
        final String contentId = PODAM_FACTORY.manufacturePojo(String.class);

        final ContentDbModel content = PODAM_FACTORY.manufacturePojo(ContentDbModel.class);
        content.setId(contentId);
        final Integer contentVotes = content.getVotes();

        when(contentRepository.findById(contentId)).thenReturn(Optional.of(content));
        when(votesRepository.save(any(VoteDbModel.class))).thenReturn(PODAM_FACTORY.manufacturePojo(VoteDbModel.class));
        when(contentRepository.save(any(ContentDbModel.class))).thenReturn(PODAM_FACTORY.manufacturePojo(ContentDbModel.class));

        contentService.voteContent(contentId, true);

        verify(contentRepository).findById(contentId);

        final ArgumentCaptor<VoteDbModel> voteDbModelAc = ArgumentCaptor.forClass(VoteDbModel.class);
        verify(votesRepository).save(voteDbModelAc.capture());
        assertEquals(VoteTypeDbEnum.UPVOTE, voteDbModelAc.getValue().getVoteType(), "Vote type does not match");
        assertEquals(contentId, voteDbModelAc.getValue().getContentId(), "Content ID does not match.");

        final ArgumentCaptor<ContentDbModel> contentDbModelAc = ArgumentCaptor.forClass(ContentDbModel.class);
        verify(contentRepository).save(contentDbModelAc.capture());
        assertEquals(contentVotes+1, contentDbModelAc.getValue().getVotes(), "Votes do not match");
    }

    @Test
    public void voteContent_contentDownvoted_contentDownvoted() {
        final String contentId = PODAM_FACTORY.manufacturePojo(String.class);

        final ContentDbModel content = PODAM_FACTORY.manufacturePojo(ContentDbModel.class);
        content.setId(contentId);
        final Integer contentVotes = content.getVotes();

        when(contentRepository.findById(contentId)).thenReturn(Optional.of(content));
        when(votesRepository.save(any(VoteDbModel.class))).thenReturn(PODAM_FACTORY.manufacturePojo(VoteDbModel.class));
        when(contentRepository.save(any(ContentDbModel.class))).thenReturn(PODAM_FACTORY.manufacturePojo(ContentDbModel.class));

        contentService.voteContent(contentId, false);

        verify(contentRepository).findById(contentId);

        final ArgumentCaptor<VoteDbModel> voteDbModelAc = ArgumentCaptor.forClass(VoteDbModel.class);
        verify(votesRepository).save(voteDbModelAc.capture());
        assertEquals(VoteTypeDbEnum.DOWNVOTE, voteDbModelAc.getValue().getVoteType(), "Vote type does not match");
        assertEquals(contentId, voteDbModelAc.getValue().getContentId(), "Content ID does not match.");

        final ArgumentCaptor<ContentDbModel> contentDbModelAc = ArgumentCaptor.forClass(ContentDbModel.class);
        verify(contentRepository).save(contentDbModelAc.capture());
        assertEquals(contentVotes-1, contentDbModelAc.getValue().getVotes(), "Votes do not match");
    }

    @Test
    public void voteContent_contentIdInvalid_entityNotFoundException() {
        final String contentId = PODAM_FACTORY.manufacturePojo(String.class);

        when(contentRepository.findById(contentId)).thenThrow(new EntityNotFoundException());

        assertThrows(EntityNotFoundException.class, () -> contentService.voteContent(contentId, true));

        verify(contentRepository).findById(contentId);
        verify(votesRepository, never()).save(any(VoteDbModel.class));
    }
}
