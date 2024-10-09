package hanium.server.i_luv_book.domain.education.domain;

import hanium.server.i_luv_book.domain.education.application.dto.request.EducationContentsCreateCommand;

public interface EducationContentsGenerator {
    // 퀴즈 생성
    void generateQuizzes(EducationContentsCreateCommand command, Long fairytaleId);

    // 단어 생성
    void generateWords(EducationContentsCreateCommand command, Long fairytaleId);
}
