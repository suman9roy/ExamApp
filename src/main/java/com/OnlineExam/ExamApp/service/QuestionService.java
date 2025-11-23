package com.OnlineExam.ExamApp.service;

import com.OnlineExam.ExamApp.Dto.QuestionGetDto;
import com.OnlineExam.ExamApp.Dto.QuestionPostDto;
import com.OnlineExam.ExamApp.Entity.Question;
import com.OnlineExam.ExamApp.Repo.QuestionRepo;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class QuestionService {
    private final QuestionRepo questionRepo;

    public QuestionService(QuestionRepo questionRepo) {
        this.questionRepo = questionRepo;
    }

    public ResponseEntity<?> getAllQuestion() {
        List<Question> questionListFromRepo = questionRepo.findAll();
        if (questionListFromRepo.isEmpty()) {
            return new ResponseEntity<>("No question is there in Db", HttpStatus.NOT_FOUND);
        } else {
            return new ResponseEntity<>(questionListFromRepo.
                    stream().
                    map(question->new QuestionGetDto(question.getId(),
                            question.getQuestionName(),
                            question.getOptionA(),
                            question.getOptionB(),
                            question.getOptionC(),
                            question.getOptionD()
                            ))
                    ,HttpStatus.OK);
        }
    }

    public ResponseEntity<?> addQuestion(QuestionPostDto questionPostDto) {
        Question question=new Question(questionPostDto.getId(),
                questionPostDto.getQuestionName(),
                questionPostDto.getOptionA(),
                questionPostDto.getOptionB(),
                questionPostDto.getOptionC(),
                questionPostDto.getOptionD(),
                questionPostDto.getCorrectAnswer(),
                null);
        //System.out.println(questionRepo.findByQuestionName(questionPostDto.getQuestionName()).isEmpty());
        if(questionPostDto.getId()!=null
                && questionPostDto.getQuestionName()!=null
                && (questionRepo.findById(questionPostDto.getId()).isPresent()
                || !questionRepo.findByQuestionName(questionPostDto.getQuestionName()).isEmpty()
                )){
            return new ResponseEntity<>("question  is already exist",HttpStatus.BAD_REQUEST);
        }
        else {
            questionRepo.save(question);
            return new ResponseEntity<>(questionPostDto,HttpStatus.CREATED);
        }

    }

    public ResponseEntity<?> getQuestionById(long id) {
        Optional<Question> optionalQuestion = questionRepo.findById(id);
        if (optionalQuestion.isPresent()) {
            QuestionGetDto questionGetDto = new QuestionGetDto(
                    optionalQuestion.get().getId(),
                    optionalQuestion.get().getQuestionName(),
                    optionalQuestion.get().getOptionA(),
                    optionalQuestion.get().getOptionB(),
                    optionalQuestion.get().getOptionC(),
                    optionalQuestion.get().getOptionD()
            );

            return new ResponseEntity<>(questionGetDto, HttpStatus.OK);
        } else
            return new ResponseEntity<>("bookId does not exist",HttpStatus.NOT_FOUND);
    }

    public ResponseEntity<?> updateQuestionById(long id, QuestionPostDto questionPostDto) {
        Optional<Question> optionalQuestion=questionRepo.findById(id);
        if(optionalQuestion.isPresent()){
            Question question=optionalQuestion.get();
            question.setQuestionName(questionPostDto.getQuestionName());
            question.setOptionA(questionPostDto.getOptionA());
            question.setOptionB(questionPostDto.getOptionB());
            question.setOptionC(questionPostDto.getOptionC());
            question.setOptionD(questionPostDto.getOptionD());
            question.setCorrectOption(questionPostDto.getCorrectAnswer());
           return new ResponseEntity<>(questionRepo.save(question),HttpStatus.OK);
        }
        else {
            return  new ResponseEntity<>("questionId does not exist",HttpStatus.NOT_FOUND);
        }
    }

    public ResponseEntity<?> deleteBookById(long id) {
        Optional<Question> optionalQuestion=questionRepo.findById(id);
        if(optionalQuestion.isPresent()){
            questionRepo.deleteById(id);
            return new ResponseEntity<>("question is deleted",HttpStatus.OK);
        }
        else {
            return  new ResponseEntity<>("questionId does not exist",HttpStatus.NOT_FOUND);
        }
    }
}
