package hanium.server.i_luv_book.domain.education.presentation.dto;

import hanium.server.i_luv_book.domain.education.application.dto.request.ChildAnswerCommand;
import hanium.server.i_luv_book.domain.education.application.dto.request.EducationContentsCreateCommand;
import hanium.server.i_luv_book.domain.education.application.dto.request.QuizSolveCommand;
import hanium.server.i_luv_book.domain.education.presentation.dto.request.ChildAnswerDto;
import hanium.server.i_luv_book.domain.education.presentation.dto.request.EducationContentsCreateDto;
import hanium.server.i_luv_book.domain.education.presentation.dto.request.QuizSolveDto;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2024-10-11T00:16:03+0900",
    comments = "version: 1.4.2.Final, compiler: javac, environment: Java 17.0.11 (Amazon.com Inc.)"
)
@Component
public class EducationDtoMapperImpl implements EducationDtoMapper {

    @Override
    public EducationContentsCreateCommand toCommand(EducationContentsCreateDto dto) {
        if ( dto == null ) {
            return null;
        }

        String title = null;
        String content = null;

        title = dto.getTitle();
        content = dto.getContent();

        String level = dto.getLevel().getValue();

        EducationContentsCreateCommand educationContentsCreateCommand = new EducationContentsCreateCommand( level, title, content );

        return educationContentsCreateCommand;
    }

    @Override
    public QuizSolveCommand toCommand(QuizSolveDto dto) {
        if ( dto == null ) {
            return null;
        }

        List<ChildAnswerCommand> childAnswerCommands = null;
        Long fairytaleId = null;

        childAnswerCommands = childAnswerDtoListToChildAnswerCommandList( dto.getChildAnswerDtos() );
        fairytaleId = dto.getFairytaleId();

        QuizSolveCommand quizSolveCommand = new QuizSolveCommand( fairytaleId, childAnswerCommands );

        return quizSolveCommand;
    }

    protected ChildAnswerCommand childAnswerDtoToChildAnswerCommand(ChildAnswerDto childAnswerDto) {
        if ( childAnswerDto == null ) {
            return null;
        }

        Long quizId = null;
        String childAnswer = null;
        boolean correct = false;

        quizId = childAnswerDto.getQuizId();
        childAnswer = childAnswerDto.getChildAnswer();
        correct = childAnswerDto.isCorrect();

        ChildAnswerCommand childAnswerCommand = new ChildAnswerCommand( quizId, childAnswer, correct );

        return childAnswerCommand;
    }

    protected List<ChildAnswerCommand> childAnswerDtoListToChildAnswerCommandList(List<ChildAnswerDto> list) {
        if ( list == null ) {
            return null;
        }

        List<ChildAnswerCommand> list1 = new ArrayList<ChildAnswerCommand>( list.size() );
        for ( ChildAnswerDto childAnswerDto : list ) {
            list1.add( childAnswerDtoToChildAnswerCommand( childAnswerDto ) );
        }

        return list1;
    }
}
