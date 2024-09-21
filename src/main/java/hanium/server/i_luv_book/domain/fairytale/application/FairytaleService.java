package hanium.server.i_luv_book.domain.fairytale.application;


import hanium.server.i_luv_book.domain.fairytale.domain.Fairytale;
import hanium.server.i_luv_book.domain.fairytale.dto.request.GameFairyTaleRequestDTO;
import hanium.server.i_luv_book.domain.fairytale.dto.request.GeneralFairyTaleRequestDTO;
import hanium.server.i_luv_book.domain.fairytale.dto.response.GameFairyTaleResponseDTO;
import hanium.server.i_luv_book.domain.fairytale.dto.response.GameFairyTaleSelectionResponseDTO;
import hanium.server.i_luv_book.domain.fairytale.dto.response.GeneralFairyTaleResponseDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import static hanium.server.i_luv_book.domain.fairytale.application.FairytaleWebClientService.*;

@Service
@Slf4j
@RequiredArgsConstructor
/**
 * @author Young9
 */
public class FairytaleService {

    private final FairytaleWebClientService fairytaleWebClientService;
    private final FairytalePersistentService fairytalePersistentService;

    public GeneralFairyTaleResponseDTO createAndSaveGeneralTale(GeneralFairyTaleRequestDTO taleRequestDTO,Long childId) {
        GeneralFairyTaleBody body = new GeneralFairyTaleBody(taleRequestDTO.getKeywords());
        GeneralFairyTaleResponseDTO taleResponseDTO = fairytaleWebClientService.createTale(GeneralFairyTaleResponseDTO.class,body,taleRequestDTO.getDifficulty().getGeneralUrl()).block();
        Fairytale fairytale = fairytalePersistentService.saveFairytaleWithAllPage(taleRequestDTO,taleResponseDTO,childId);
        fairytalePersistentService.saveFairytaleKeyword(fairytale,taleRequestDTO.getKeywords());
        return taleResponseDTO;
    }

    public GameFairyTaleResponseDTO createAndSaveGameTale(GameFairyTaleRequestDTO taleRequestDTO, Long childId) {
        //선택지가 있다면 기존의 동화가 있다는 것.
        if (StringUtils.hasText(taleRequestDTO.getSelection())) {
            return null;
        } else {
            // 기존의 동화가 없는 경우
            GameFairyTaleBody body = new GameFairyTaleBody(taleRequestDTO.getKeywords(), "");
            GameFairyTaleSelectionResponseDTO taleResponseDTO = fairytaleWebClientService.createTale(GameFairyTaleSelectionResponseDTO.class,body,taleRequestDTO.getDifficulty().getGameUrl()).block();
            Fairytale fairytale = fairytalePersistentService.saveFairytaleWithPage(taleRequestDTO, taleResponseDTO, childId);
            fairytalePersistentService.saveFairytaleKeyword(fairytale,taleRequestDTO.getKeywords());

            return taleResponseDTO;
        }
    }

}
