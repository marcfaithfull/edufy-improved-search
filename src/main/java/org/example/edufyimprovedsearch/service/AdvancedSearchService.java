package org.example.edufyimprovedsearch.service;

import org.example.edufyimprovedsearch.model.dto.MediaSearchResponseDto;
import org.example.edufyimprovedsearch.model.dto.SearchDto;

public interface AdvancedSearchService {

    MediaSearchResponseDto getEverything(SearchDto search);
}
