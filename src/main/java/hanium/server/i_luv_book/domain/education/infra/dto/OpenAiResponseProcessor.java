package hanium.server.i_luv_book.domain.education.infra.dto;

import hanium.server.i_luv_book.domain.education.application.dto.request.QuizCreateCommand;
import hanium.server.i_luv_book.domain.education.domain.QuizInfo;
import lombok.experimental.UtilityClass;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static hanium.server.i_luv_book.domain.education.application.dto.request.QuizCreateCommand.*;
import static hanium.server.i_luv_book.domain.education.domain.QuizInfo.*;

@UtilityClass
public class OpenAiResponseProcessor {

    private static final Pattern QUIZ_PATTERN = Pattern.compile(
            "## (\\d+)\\s*" +
                    "Type: ([^\\n]+)\\s*" +
                    "Format: ([^\\n]+)\\s*" +
                    "Question: ([^\\n]+)\\s*" +
                    "Pronunciation or Voca: ([^\\n]+)\\s*" +
                    "Options: ([^\\n]+)\\s*" +
                    "Answer: ([^\\n]+)"
    );

    public static List<QuizCreateCommand> toQuizCreateCommands(String openAiResponse) {
        List<QuizCreateCommand> quizCreateCommands = new ArrayList<>();
        Matcher matcher = QUIZ_PATTERN.matcher(openAiResponse);
        while (matcher.find()) {
            int number = Integer.parseInt(matcher.group(1));
            String type = matcher.group(2);
            String format = matcher.group(3);
            String question = matcher.group(4);
            String pronounOrWord = matcher.group(5);
            String options = matcher.group(6);
            String answer = matcher.group(7);

            QuizCreateCommand quizCreateCommand = new QuizCreateCommand(
                    QuizType.toQuizType(type), Format.toFormat(format),
                    verifyNoneValue(question), verifyNoneValue(pronounOrWord), verifyNoneValue(answer), toQuizOptionsCreateCommand(options));
            quizCreateCommands.add(quizCreateCommand);
        }
        return quizCreateCommands;
    }

    private QuizOptionsCreateCommand toQuizOptionsCreateCommand (String openAiResponse) {
        if (openAiResponse.equals("None")) {
            return null;
        } else {
            String[] splitOptions = openAiResponse.split("\\|");
            Queue<String> options = new LinkedList<>();

            for (String option : splitOptions) {
                String trimmedOption = option.trim().substring(3).trim();
                options.add(trimmedOption);
            }

            return new QuizOptionsCreateCommand(options);
        }
    }

    private String verifyNoneValue(String openAiResponse) {
        return Objects.equals(openAiResponse, "None") ? null : openAiResponse;
    }
}
