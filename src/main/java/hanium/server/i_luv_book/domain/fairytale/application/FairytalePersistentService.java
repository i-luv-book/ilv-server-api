package hanium.server.i_luv_book.domain.fairytale.application;

import hanium.server.i_luv_book.domain.fairytale.dao.*;
import hanium.server.i_luv_book.domain.fairytale.domain.*;
import hanium.server.i_luv_book.domain.fairytale.domain.enums.KeywordCategory;
import hanium.server.i_luv_book.domain.fairytale.dto.request.GeneralFairyTaleRequestDTO;
import hanium.server.i_luv_book.domain.fairytale.dto.request.KeywordsDTO;
import hanium.server.i_luv_book.domain.fairytale.dto.response.GeneralFairyTaleResponseDTO;
import hanium.server.i_luv_book.domain.user.domain.Child;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
/**
 * @author Young9
 */
@Service
@Slf4j
@RequiredArgsConstructor
public class FairytalePersistentService {

    private final FairytaleRepository fairytaleRepository;
    private final FairytalePageRepository fairytalePageRepository;
    private final FairytaleKeywordRepository fairytaleKeywordRepository;
    private final KeywordRepository keywordRepository;
    private final PageOptionInfoRepository pageOptionInfoRepository;
    private final TMPChildRepository tmpChildRepository;

    //동화관련 엔티티 save 로직
    @Transactional
    public Fairytale saveFairytaleWithAllPage(GeneralFairyTaleRequestDTO requestDTO, GeneralFairyTaleResponseDTO responseDTO, Long childId) {

        Child child = tmpChildRepository.findById(childId)
                .orElseThrow();

        Fairytale fairytale = Fairytale.builder()
                .difficulty(requestDTO.getDifficulty())
                .title(responseDTO.getTitle())
                .summary(responseDTO.getSummary())
                .thumbnail(responseDTO.getPages().get(0).getImgURL())
                .build();

        child.addFairytale(fairytale);

        for (GeneralFairyTaleResponseDTO.PagesDTO page : responseDTO.getPages()) {
            fairytale.addPage(page);
        }

        return fairytaleRepository.save(fairytale);
    }

    @Transactional
    public FairytalePage saveFairytalePage(FairytalePage fairytalePage) {
        return fairytalePageRepository.save(fairytalePage);
    }

    @Transactional
    public PageOptionInfo savePageOptionInfo(PageOptionInfo pageOptionInfo) {
        return pageOptionInfoRepository.save(pageOptionInfo);
    }

    @Transactional
    public void saveFairytaleKeyword(Fairytale fairytale,GeneralFairyTaleRequestDTO taleRequestDTO) {
        KeywordsDTO keywordsDTO = taleRequestDTO.getKeywords();
        List<Keyword> keywordList = new ArrayList<>();

        addKeyword(keywordList,keywordsDTO.getCharacters(),KeywordCategory.CHARACTERS);
        addKeyword(keywordList,keywordsDTO.getTraits(),KeywordCategory.TRAITS);
        addKeyword(keywordList,keywordsDTO.getSettings(),KeywordCategory.SETTINGS);
        addKeyword(keywordList,keywordsDTO.getGenre(),KeywordCategory.GENRES);

        for (Keyword keyword : keywordList) {
            FairytaleKeyword fairytaleKeyword = FairytaleKeyword.builder()
                    .fairytale(fairytale)
                    .childId(fairytale.getChild().getId())
                    .keyword(keyword)
                    .build();

            fairytaleKeywordRepository.save(fairytaleKeyword);
        }

    }


    private Keyword saveKeyword(Keyword keyword) {
        return keywordRepository.save(keyword);
    }

    private void addKeyword(List<Keyword> keywordList,List<String> keywords,KeywordCategory keywordCategory) {
        for (String content  : keywords) {
            Keyword keyword = keywordRepository.findByContentAndKeywordCategory(content,keywordCategory)
                    .orElseGet(() -> saveKeyword(Keyword.builder()
                            .content(content)
                            .keywordCategory(KeywordCategory.GENRES)
                            .build()));

            keywordList.add(keyword);
        }


    }

}
