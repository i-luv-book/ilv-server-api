package hanium.server.i_luv_book.domain.education.application.dto.response;

import lombok.Getter;

@Getter
public class SolvedQuizzesCountsInfo {
    private long correctQuizzes;
    private long wrongQuizzes;

    public SolvedQuizzesCountsInfo(long correctQuizzes, long wrongQuizzes) {
        this.correctQuizzes = correctQuizzes;
        this.wrongQuizzes = wrongQuizzes;
    }
}
