package hanium.server.i_luv_book.domain.education.application.dto.response;

import lombok.Getter;

import java.util.Map;

import static hanium.server.i_luv_book.domain.education.domain.QuizInfo.*;

@Getter
public class SolvedQuizzesTypesInfo {

    private long reading;
    private long voca;
    private long listening;
    private long creativity;
    private long grammar;

    public SolvedQuizzesTypesInfo(Map<QuizType, Long> quizTypeCounts) {
        this.reading = quizTypeCounts.get(QuizType.READING);
        this.voca = quizTypeCounts.get(QuizType.VOCA);
        this.listening = quizTypeCounts.get(QuizType.LISTENING);
        this.creativity = quizTypeCounts.get(QuizType.CREATIVITY);
        this.grammar = quizTypeCounts.get(QuizType.GRAMMAR);
    }
}
