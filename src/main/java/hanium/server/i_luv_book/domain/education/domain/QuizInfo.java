package hanium.server.i_luv_book.domain.education.domain;

import hanium.server.i_luv_book.domain.education.application.dto.request.QuizCreateCommand;
import hanium.server.i_luv_book.global.exception.OpenAiException;
import hanium.server.i_luv_book.global.exception.code.ErrorCode;
import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Embeddable
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class QuizInfo {
    @Enumerated(EnumType.STRING)
    @Column(name = "quiz_type")
    private QuizType quizType;
    @Enumerated(EnumType.STRING)
    @Column(name = "format")
    private Format format;
    @Column(name = "question")
    private String question;
    @Column(name = "pronoun_or_word")
    private String pronounOrWord;

    @Builder
    public QuizInfo(QuizCreateCommand command) {
        this.quizType = command.getQuizType();
        this.format = command.getFormat();
        this.question = command.getQuestion();
        this.pronounOrWord = command.getPronounOrWord();
    }

    @Getter
    public enum QuizType {
        READING, VOCA, LISTENING, GRAMMAR, CREATIVITY;

        public static QuizType toQuizType(String value) {
            switch (value) {
                case "Reading Comprehension Quiz" -> {
                    return READING;
                }
                case "Voca Quiz" -> {
                    return VOCA;
                }
                case "Listening Quiz" -> {
                    return LISTENING;
                }
                case "Creativity Quiz" -> {
                    return CREATIVITY;
                }
                case "Grammar Quiz" -> {
                    return GRAMMAR;
                }
            }
            throw new OpenAiException(ErrorCode.UNSUITABLE_OPENAI_RESPONSE);
        }
    }

    @Getter
    public enum Format {
        OX, MULTIPLE_CHOICE, SHORT_ANSWER;

        public static Format toFormat(String value) {
            switch (value) {
                case "True/False" -> {
                    return OX;
                }
                case "Multiple Choice" -> {
                    return MULTIPLE_CHOICE;
                }
                case "Short Answer" -> {
                    return SHORT_ANSWER;
                }
            }
            throw new OpenAiException(ErrorCode.UNSUITABLE_OPENAI_RESPONSE);
        }
    }
}
