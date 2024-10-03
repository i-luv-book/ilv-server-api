package hanium.server.i_luv_book.domain.education.application;

import hanium.server.i_luv_book.domain.education.application.dto.EducationCommandMapper;
import hanium.server.i_luv_book.domain.education.application.dto.request.QuizCreateCommand;
import hanium.server.i_luv_book.domain.education.domain.EducationRepository;
import hanium.server.i_luv_book.domain.education.domain.Quiz;
import hanium.server.i_luv_book.domain.fairytale.domain.Fairytale;
import hanium.server.i_luv_book.global.exception.NotFoundException;
import hanium.server.i_luv_book.global.exception.code.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class AsyncEducationCommandService {

    private final EducationRepository educationRepository;
    private final EducationCommandMapper mapper;

    // 퀴즈 저장
    @Async("educationTaskExecutor")
    public void saveQuizzes(List<QuizCreateCommand> quizCreateCommands, Long fairytaleId) {
        Fairytale fairytale = findFairytale(fairytaleId);
        List<Quiz> quizzes = toQuizzes(quizCreateCommands, fairytale);
        addAndSaveQuizzes(fairytale, quizzes);
    }

    private void addAndSaveQuizzes(Fairytale fairytale, List<Quiz> quizzes) {
        fairytale.addQuizzes(quizzes);
        quizzes.forEach(educationRepository::save);
    }

    private List<Quiz> toQuizzes(List<QuizCreateCommand> quizCreateCommands, Fairytale fairytale) {
        return mapper.toQuizzes(quizCreateCommands, fairytale);
    }

    private Fairytale findFairytale(Long fairytaleId) {
        return educationRepository.findFairytaleById(fairytaleId)
                .orElseThrow(() -> new NotFoundException(ErrorCode.FAIRYTALE_NOT_FOUND));
    }
}
