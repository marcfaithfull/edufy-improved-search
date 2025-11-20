package org.example.edufyimprovedsearch.service;

import org.example.edufyimprovedsearch.dto.MediaSearchResponseDto;
import org.example.edufyimprovedsearch.dto.SearchDto;

public interface AdvancedSearchService {

    MediaSearchResponseDto getEverything(SearchDto search);
}
