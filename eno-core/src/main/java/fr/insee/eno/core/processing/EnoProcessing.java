package fr.insee.eno.core.processing;

import fr.insee.eno.core.model.*;
import fr.insee.eno.core.model.question.Question;
import fr.insee.eno.core.model.question.TextQuestion;
import fr.insee.eno.core.utils.RomanNumber;
import lombok.extern.slf4j.Slf4j;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
public class EnoProcessing {

    public static final String COMMENT_VARIABLE_NAME = "COMMENT_QE";
    public static final String COMMENT_SEQUENCE_ID = "COMMENT-SEQ";
    public static final String COMMENT_SEQUENCE_LABEL = "Commentaire";
    public static final String COMMENT_QUESTION_ID = "COMMENT-QUESTION";
    public static final String COMMENT_QUESTION_LABEL =
            "Avez-vous des remarques concernant l'enquête ou des commentaires\u00a0?";

    public enum QuestionNumberingMode {NONE, SEQUENCE, ALL}

    public static final String SEQUENCE_NUMBERING_SEPARATOR = " -";
    public static final String QUESTION_NUMBERING_SEPARATOR = ".";
    public static final String QUESTION_ARROW_CHAR = "➡";

    private boolean commentSection = true; //TODO: parametrize
    private boolean sequenceNumbering = true; //TODO: parametrize
    private boolean arrowCharInQuestions = true; //TODO: parametrize

    Map<String, Sequence> sequenceMap = new HashMap<>();
    Map<String, Subsequence> subsequenceMap = new HashMap<>();
    Map<String, Question> questionMap = new HashMap<>();

    public void applyProcessing(EnoQuestionnaire enoQuestionnaire) {
        //
        if (commentSection) addCommentSection(enoQuestionnaire);
        //
        enoQuestionnaire.getSequences().forEach(sequence -> sequenceMap.put(sequence.getId(), sequence));
        enoQuestionnaire.getSubsequences().forEach(subsequence -> subsequenceMap.put(subsequence.getId(), subsequence));
        enoQuestionnaire.getSingleResponseQuestions().forEach(question -> questionMap.put(question.getId(), question));
        enoQuestionnaire.getMultipleResponseQuestions().forEach(question -> questionMap.put(question.getId(), question));
        //
        insertDeclarations(enoQuestionnaire);
        insertControls(enoQuestionnaire);
        //
        if (sequenceNumbering) addNumberingInSequences(enoQuestionnaire);
        addNumberingInQuestions(enoQuestionnaire, QuestionNumberingMode.SEQUENCE); //TODO: parametrize
        if (arrowCharInQuestions) addArrowCharInQuestion();
    }

    /** Controls are mapped directly in a flat list in the questionnaire object.
     * This processing is intended to insert them into the objects to which they belong.
     * (Controls are placed after the object they belong to in the sequence items lists.)
     * Concerned objects : sequences, subsequences and questions. */
    private void insertControls(EnoQuestionnaire enoQuestionnaire) { // TODO: code is a bit clumsy but works
        //
        Map<String, Control> controlMap = new HashMap<>();
        enoQuestionnaire.getControls().forEach(control -> controlMap.put(control.getId(), control));
        //
        for (Sequence sequence : enoQuestionnaire.getSequences()) {
            List<SequenceItem> sequenceItems = sequence.getSequenceItems();
            if (! sequenceItems.isEmpty()) {
                int bound = sequenceItems.size();
                // Sequence controls
                int i = 0;
                while (i<bound && sequenceItems.get(i).getType() == SequenceItem.SequenceItemType.CONTROL) {
                    sequence.getControls().add(controlMap.get(sequenceItems.get(i).getId()));
                    i ++;
                }
                // Elements (questions, subsequences) in sequence
                while (i < bound) {
                    SequenceItem sequenceItem = sequenceItems.get(i);
                    if (sequenceItem.getType() == SequenceItem.SequenceItemType.QUESTION) {
                        Question question = questionMap.get(sequenceItem.getId());
                        i ++;
                        while (i<bound && sequenceItems.get(i).getType() == SequenceItem.SequenceItemType.CONTROL) {
                            question.getControls().add(controlMap.get(sequenceItems.get(i).getId()));
                            i ++;
                        }
                    }
                    else if (sequenceItem.getType() == SequenceItem.SequenceItemType.SUBSEQUENCE) {
                        Subsequence subsequence = subsequenceMap.get(sequenceItem.getId());
                        List<SequenceItem> subsequenceItems = subsequence.getSequenceItems();
                        if (! subsequenceItems.isEmpty()) {
                            int bound2 = subsequenceItems.size();
                            // Subsequence controls
                            int j = 0;
                            while (j<bound2 && subsequenceItems.get(j).getType() == SequenceItem.SequenceItemType.CONTROL) {
                                subsequence.getControls().add(controlMap.get(subsequenceItems.get(j).getId()));
                                j ++;
                            }
                            // Elements (questions) in subsequence
                            while (j < bound2) {
                                SequenceItem subsequenceItem = subsequenceItems.get(j);
                                if (subsequenceItem.getType() == SequenceItem.SequenceItemType.QUESTION) {
                                    Question question = questionMap.get(subsequenceItem.getId());
                                    j ++;
                                    while (j<bound2 && subsequenceItems.get(j).getType() == SequenceItem.SequenceItemType.CONTROL) {
                                        question.getControls().add(controlMap.get(subsequenceItems.get(j).getId()));
                                        j ++;
                                    }
                                }
                                else { // skip other elements
                                    j ++;
                                }
                            }
                        }
                        i ++;
                    }
                    else { // skip other elements
                        i ++;
                    }
                }
            }
        }
    }

    /** Same idea as in the insertControls function, but for declarations.
     * (Declarations are placed before the object they belong to in the sequence items lists.)
     * Concerned objects : subsequences and questions. */
    private void insertDeclarations(EnoQuestionnaire enoQuestionnaire) { // TODO: code is a bit clumsy but works
        //
        Map<String, Declaration> declarationMap = new HashMap<>();
        enoQuestionnaire.getDeclarations().forEach(declaration -> declarationMap.put(declaration.getId(), declaration));
        //
        for (Sequence sequence : enoQuestionnaire.getSequences()) {
            List<String> declarationIdStack = new ArrayList<>();
            for (SequenceItem sequenceItem : sequence.getSequenceItems()) {
                if (sequenceItem.getType() == SequenceItem.SequenceItemType.DECLARATION) {
                    declarationIdStack.add(sequenceItem.getId());
                }
                if (sequenceItem.getType() == SequenceItem.SequenceItemType.QUESTION) {
                    Question question = questionMap.get(sequenceItem.getId());
                    declarationIdStack.forEach(declarationId ->
                            question.getDeclarations().add(declarationMap.get(declarationId)));
                    declarationIdStack = new ArrayList<>();
                }
                if (sequenceItem.getType() == SequenceItem.SequenceItemType.SUBSEQUENCE) {
                    Subsequence subsequence = subsequenceMap.get(sequenceItem.getId());
                    declarationIdStack.forEach(declarationId ->
                            subsequence.getDeclarations().add(declarationMap.get(declarationId)));
                    declarationIdStack = new ArrayList<>();
                    for (SequenceItem subsequenceItem : subsequence.getSequenceItems()) {
                        if (subsequenceItem.getType() == SequenceItem.SequenceItemType.DECLARATION) {
                            declarationIdStack.add(subsequenceItem.getId());
                        }
                        if (subsequenceItem.getType() == SequenceItem.SequenceItemType.QUESTION) {
                            Question question = questionMap.get(subsequenceItem.getId());
                            declarationIdStack.forEach(declarationId ->
                                    question.getDeclarations().add(declarationMap.get(declarationId)));
                            declarationIdStack = new ArrayList<>();
                        }
                    }
                }
            }
        }
    }

    private void addCommentSection(EnoQuestionnaire enoQuestionnaire) {
        //
        Variable commentVariable = new Variable();
        commentVariable.setName(COMMENT_VARIABLE_NAME);
        commentVariable.setQuestionReference(COMMENT_QUESTION_ID);
        enoQuestionnaire.getVariables().add(commentVariable);
        //
        Sequence sequence = new Sequence();
        sequence.setId(COMMENT_SEQUENCE_ID);
        sequence.setLabel(COMMENT_SEQUENCE_LABEL);
        sequence.getComponentReferences().add(COMMENT_QUESTION_ID);
        enoQuestionnaire.getSequences().add(sequence);
        //
        enoQuestionnaire.getSequenceReferences().add(COMMENT_SEQUENCE_ID);
        //
        TextQuestion commentQuestion = new TextQuestion();
        commentQuestion.setId(COMMENT_QUESTION_ID);
        commentQuestion.setName(COMMENT_VARIABLE_NAME);
        commentQuestion.setLabel(COMMENT_QUESTION_LABEL);
        commentQuestion.setMandatory(false);
        commentQuestion.setMaxLength(BigInteger.valueOf(2000));
        commentQuestion.setResponse(new Response());
        commentQuestion.getResponse().setVariableName(COMMENT_VARIABLE_NAME);
        enoQuestionnaire.getSingleResponseQuestions().add(commentQuestion);
    }

    private void addNumberingInSequences(EnoQuestionnaire enoQuestionnaire) {
        int sequenceNumber = 1;
        for (String sequenceId : enoQuestionnaire.getSequenceReferences()) {
            Sequence sequence = sequenceMap.get(sequenceId);
            String sequenceLabel = sequence.getLabel();
            sequence.setLabel(RomanNumber.toRoman(sequenceNumber) + SEQUENCE_NUMBERING_SEPARATOR + " " + sequenceLabel);
            sequenceNumber ++;
        }
    }

    private void addNumberingInQuestions(EnoQuestionnaire enoQuestionnaire, QuestionNumberingMode mode) {
        if (mode != QuestionNumberingMode.NONE) {
            int questionNumber = 1;
            for (String sequenceId : enoQuestionnaire.getSequenceReferences()) {
                Sequence sequence = sequenceMap.get(sequenceId);
                for (String componentId : sequence.getComponentReferences()) {
                    if (subsequenceMap.containsKey(componentId)) {
                        Subsequence subsequence = subsequenceMap.get(componentId);
                        for (String questionId : subsequence.getComponentReferences()) {
                            Question question = questionMap.get(questionId);
                            String questionLabel = question.getLabel();
                            question.setLabel(questionNumber + QUESTION_NUMBERING_SEPARATOR + " " + questionLabel);
                            questionNumber ++;
                        }
                    } else {
                        Question question = questionMap.get(componentId);
                        String questionLabel = question.getLabel();
                        question.setLabel(questionNumber + QUESTION_NUMBERING_SEPARATOR + " " + questionLabel);
                        questionNumber ++;
                    }
                }
                if (mode == QuestionNumberingMode.SEQUENCE) {
                    questionNumber = 1;
                }
            }
        }
    }

    private void addArrowCharInQuestion() {
        for (Question question : questionMap.values()) {
            String questionLabel = question.getLabel();
            question.setLabel(QUESTION_ARROW_CHAR + " " + questionLabel);
        }
    }

}