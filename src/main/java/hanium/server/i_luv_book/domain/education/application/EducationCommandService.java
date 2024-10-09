package hanium.server.i_luv_book.domain.education.application;

import hanium.server.i_luv_book.domain.education.application.dto.request.ChildAnswerCommand;
import hanium.server.i_luv_book.domain.education.application.dto.request.EducationContentsCreateCommand;
import hanium.server.i_luv_book.domain.education.application.dto.request.QuizSolveCommand;
import hanium.server.i_luv_book.domain.education.domain.EducationContentsGenerator;
import hanium.server.i_luv_book.domain.education.domain.EducationRepository;
import hanium.server.i_luv_book.domain.education.domain.Quiz;
import hanium.server.i_luv_book.domain.fairytale.domain.Fairytale;
import hanium.server.i_luv_book.global.exception.BusinessException;
import hanium.server.i_luv_book.global.exception.NotFoundException;
import hanium.server.i_luv_book.global.exception.code.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
@Transactional
public class EducationCommandService {

    private final EducationRepository educationRepository;
    private final EducationContentsGenerator educationContentsGenerator;

    private final EducationQueryService educationQueryService;

    // 퀴즈 생성
    public void makeQuizzes(EducationContentsCreateCommand command, Long fairytaleId) {
        Fairytale fairytale = findFairytale(fairytaleId);
        generateQuizzes(command, fairytaleId, fairytale);
    }

    // 퀴즈 풀기
    public void solveQuizzes(QuizSolveCommand command) {
        Map<Long, Quiz> quizzes = findQuizzes(command);
        for (ChildAnswerCommand childAnswer : command.childAnswerCommands()) {
            solveQuiz(childAnswer, quizzes);
        }
    }

    private void solveQuiz(ChildAnswerCommand childAnswer, Map<Long, Quiz> quizzes) {
        Quiz quiz = quizzes.get(childAnswer.quizId());
        checkQuizExist(quiz);
        quiz.solveQuiz(childAnswer.childAnswer(), childAnswer.correct());
    }

    private void checkQuizExist(Quiz quiz) {
        if (quiz == null) {
            throw new NotFoundException(ErrorCode.QUIZ_NOT_FOUND);
        }
    }

    private Map<Long, Quiz> findQuizzes(QuizSolveCommand command) {
        return educationQueryService.getQuizzesInfos(command.fairytaleId());
    }

    private void generateQuizzes(EducationContentsCreateCommand command, Long fairytaleId, Fairytale fairytale) {
        checkQuizzesAlreadyExist(fairytale);
        educationContentsGenerator.generateQuizzes(command, fairytaleId);
    }

    private void checkQuizzesAlreadyExist(Fairytale fairytale) {
        if (fairytale.isQuizzesExistence()) {
            throw new BusinessException(ErrorCode.QUIZ_ALREADY_EXIST);
        }
    }

    private Fairytale findFairytale(Long fairytaleId) {
        return educationRepository.findFairytaleById(fairytaleId)
                .orElseThrow(() -> new BusinessException(ErrorCode.FAIRYTALE_NOT_FOUND));
    }
}
