package hanium.server.i_luv_book.domain.education.infra.dto;

import hanium.server.i_luv_book.domain.education.application.dto.request.QuizCreateCommand;
import org.junit.jupiter.api.Test;

import java.util.List;

class OpenAiResponseProcessorTest {

    @Test
    void toQuizCreateCommands() {
        String value = """
                # Intro
                | Tag    | Content                                        |
                |--------|------------------------------------------------|
                | Title  | The Adventure of Starfish, Tubby, Clucky, Lucky, and T-Rex |
                | Level  | Low-Level                                      |
                                
                # Quiz
                                
                ## 1
                Type: Reading Comprehension Quiz
                Format: True/False
                Question: 이야기 속 Echo는 특별한 코끼리예요.
                Pronunciation or Voca: None
                Options: None
                Answer: True
                                
                ## 2
                Type: Reading Comprehension Quiz
                Format: True/False
                Question: Echo는 아기 Ely를 놔두고 다른 코끼리들과 떠났어요.
                Pronunciation or Voca: None
                Options: None
                Answer: False
                                
                ## 3
                Type: Voca Quiz
                Format: Multiple Choice
                Question: 다음 단어에 맞는 이모티콘을 고르세요.
                Pronunciation or Voca: Elephant
                Options: A. 🐘 | B. 🌸 | C. 🚗 | D. 🍎
                Answer: A
                                
                ## 4
                Type: Voca Quiz
                Format: Multiple Choice
                Question: 다음 단어에 맞는 이모티콘을 고르세요.
                Pronunciation or Voca: Happy
                Options: A. 😢 | B. 🎉 | C. 😊 | D. 🌧️
                Answer: C
                                
                ## 5
                Type: Voca Quiz
                Format: Multiple Choice
                Question: 다음 단어에 맞는 이모티콘을 고르세요.
                Pronunciation or Voca: Walk
                Options: A. 🚶 | B. 🌊 | C. 🚜 | D. 🍔
                Answer: A
                                
                ## 6
                Type: Listening Quiz
                Format: Multiple Choice
                Question: 들려주는 단어를 알맞게 고르세요.
                Pronunciation or Voca: Baby
                Options: A. Baby | B. Tree | C. Jump | D. Sun
                Answer: A
                                
                ## 7
                Type: Listening Quiz
                Format: Multiple Choice
                Question: 들려주는 단어를 알맞게 고르세요.
                Pronunciation or Voca: Stand
                Options: A. Run | B. Stand | C. Fly | D. Walk
                Answer: B
                                
                ## 8
                Type: Listening Quiz
                Format: Multiple Choice
                Question: 들려주는 단어를 알맞게 고르세요.
                Pronunciation or Voca: Leave
                Options: A. Leave | B. Sleep | C. Sit | D. Play
                Answer: A
                                
                ## 9
                Type: Creativity Quiz
                Format: Short Answer
                Question: 여러분이 Echo처럼 다른 사람을 도와준 경험이 있나요? 어떤 일이었나요?
                Pronunciation or Voca: None
                Options: None
                Answer: None
                                
                ## 10
                Type: Creativity Quiz
                Format: Short Answer
                Question: Ely와 함께하고 싶은 모험이 있나요? 어떤 모험인가요?
                Pronunciation or Voca: None
                Options: None
                Answer: None
                """;

        List<QuizCreateCommand> results = OpenAiResponseProcessor.toQuizCreateCommands(value);
        for (QuizCreateCommand quizCreateCommand : results) {
            System.out.println(quizCreateCommand.toString());
        }
    }
}
