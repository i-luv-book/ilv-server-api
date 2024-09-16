package hanium.server.i_luv_book.domain.fairytale.api;

import hanium.server.i_luv_book.domain.fairytale.dto.request.GameFairyTaleRequestDTO;
import hanium.server.i_luv_book.domain.fairytale.dto.request.GeneralFairyTaleRequestDTO;
import hanium.server.i_luv_book.domain.fairytale.dto.response.GeneralFairyTaleResponseDTO;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/api/v1/fairytale")
public class FairyTaleController {

//    @PostMapping("/general")
//    public GeneralFairyTaleResponseDTO createGenrealFairyTale(@Valid @RequestBody GeneralFairyTaleRequestDTO taleRequestDTO) {
//
//
//    }
//
//    @PostMapping("/game")
//    public GeneralFairyTaleResponseDTO createGenrealFairyTale(@Valid @RequestBody GameFairyTaleRequestDTO taleRequestDTO) {
//
//    }
//


}
