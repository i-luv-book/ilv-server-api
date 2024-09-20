package hanium.server.i_luv_book.domain.fairytale.api;

import hanium.server.i_luv_book.domain.fairytale.application.FairytalePersistentService;
import hanium.server.i_luv_book.domain.fairytale.application.FairytaleService;
import hanium.server.i_luv_book.domain.fairytale.dto.request.GameFairyTaleRequestDTO;
import hanium.server.i_luv_book.domain.fairytale.dto.request.GeneralFairyTaleRequestDTO;
import hanium.server.i_luv_book.domain.fairytale.dto.response.GameFairyTaleResponseDTO;
import hanium.server.i_luv_book.domain.fairytale.dto.response.GeneralFairyTaleResponseDTO;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/api/v1/fairytale")
/**
 * @author Young9
 */
public class FairytaleController {

    private final FairytaleService fairytaleService;
    private final FairytalePersistentService fairytalePersistentService;

    @PostMapping("/general/{childId}")
    public GeneralFairyTaleResponseDTO createGenrealFairyTale(@Valid @RequestBody GeneralFairyTaleRequestDTO taleRequestDTO, @PathVariable Long childId) {
        return fairytaleService.createAndSaveGeneralTale(taleRequestDTO,childId);
    }

    @PostMapping("/game/{childId}")
    public GameFairyTaleResponseDTO createGenrealFairyTale(@Valid @RequestBody GameFairyTaleRequestDTO taleRequestDTO, @PathVariable Long childId) {
        return fairytaleService.createAndSaveGameTale(taleRequestDTO,childId);
    }

    @DeleteMapping("/{childId}/{fairytaleId}")
    public void deleteFairyTale(@PathVariable Long childId,@PathVariable Long fairytaleId) {
        fairytalePersistentService.deleteFairytale(fairytaleId);
    }



}
