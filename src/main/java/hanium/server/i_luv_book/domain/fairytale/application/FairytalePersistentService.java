package hanium.server.i_luv_book.domain.fairytale.application;

import hanium.server.i_luv_book.domain.fairytale.dao.*;
import hanium.server.i_luv_book.domain.fairytale.domain.*;
import hanium.server.i_luv_book.domain.fairytale.domain.enums.KeywordCategory;
import hanium.server.i_luv_book.domain.fairytale.dto.request.GameFairyTaleRequestDTO;
import hanium.server.i_luv_book.domain.fairytale.dto.request.GeneralFairyTaleRequestDTO;
import hanium.server.i_luv_book.domain.fairytale.dto.request.KeywordsDTO;
import hanium.server.i_luv_book.domain.fairytale.dto.response.GameFairyTaleResponseDTO;
import hanium.server.i_luv_book.domain.fairytale.dto.response.GameFairyTaleSelectionResponseDTO;
import hanium.server.i_luv_book.domain.fairytale.dto.response.GeneralFairyTaleResponseDTO;
import hanium.server.i_luv_book.domain.user.domain.Child;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

import static hanium.server.i_luv_book.domain.fairytale.dto.response.GameFairyTaleSelectionResponseDTO.*;

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
    public Fairytale saveFairytaleWithPage(GameFairyTaleRequestDTO taleRequestDTO, GameFairyTaleSelectionResponseDTO taleResponseDTO, Long childId) {
        //아이 찾는 쿼리 수정 해야함.
        Child child = tmpChildRepository.findById(childId)
                .orElseThrow();

        //동화 저장
        Fairytale fairytale = Fairytale.builder()
                .difficulty(taleRequestDTO.getDifficulty())
                .title(taleResponseDTO.getTitle())
                .thumbnail(taleResponseDTO.getImgURL())
                .child(child)
                .build();
        fairytaleRepository.save(fairytale);

        //동화 페이지 저장
        FairytalePage fairytalePage = FairytalePage.builder()
                .imgUrl(taleResponseDTO.getImgURL())
                .content(taleResponseDTO.getContent())
                .fairytale(fairytale)
                .build();
        fairytalePageRepository.save(fairytalePage);

        //동화 선택지 저장
        Options  optionA = taleResponseDTO.getOptions().get(0);
        Options  optionB = taleResponseDTO.getOptions().get(1);
        Options  optionC = taleResponseDTO.getOptions().get(2);

        PageOptionInfo pageOptionInfo = PageOptionInfo.builder()
                .fairytalePage(fairytalePage)
                .contentA(optionA.getOptionContent())
                .contentB(optionB.getOptionContent())
                .contentC(optionC.getOptionContent())
                .titleA(optionA.getOptionTitle())
                .titleB(optionB.getOptionTitle())
                .titleC(optionC.getOptionTitle())
                .build();
        pageOptionInfoRepository.save(pageOptionInfo);
        return fairytale;
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
    public void saveFairytaleKeyword(Fairytale fairytale,KeywordsDTO keywordsDTO) {
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

    @Transactional
    public void deleteFairytale(Long fairytaleId) {
        fairytaleRepository.deleteById(fairytaleId);
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
