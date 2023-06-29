package fr.insee.eno.core.processing.impl;

import fr.insee.eno.core.model.EnoQuestionnaire;
import fr.insee.eno.core.model.navigation.Filter;
import fr.insee.eno.core.model.navigation.Loop;
import fr.insee.eno.core.model.sequence.AbstractSequence;
import fr.insee.eno.core.model.sequence.Sequence;
import fr.insee.eno.core.model.sequence.SequenceItem;
import fr.insee.eno.core.model.sequence.SequenceItem.SequenceItemType;
import fr.insee.eno.core.model.sequence.Subsequence;
import fr.insee.eno.core.processing.InProcessingInterface;
import lombok.extern.slf4j.Slf4j;

/** Sequence objects contain "sequence items" lists, that are designed to keep the structure of the questionnaire.
 * These lists are mapped from the DDI, and contain the reference of components in the right order.
 * Yet, this information does not allow to retrieve questionnaire's structure easily.
 * Thus, this processing class fills the "sequence structure" property of sequence objects.
 * */
@Slf4j
public class DDIResolveSequencesStructure implements InProcessingInterface {

    private EnoQuestionnaire enoQuestionnaire;

    /** Fills the "sequence structure" list in each sequence of the eno questionnaire given,
     * using the "sequence items" list mapped from DDI.
     * @param enoQuestionnaire Eno questionnaire mapped from a DDI.
     * */
    @Override
    public void apply(EnoQuestionnaire enoQuestionnaire) {
        this.enoQuestionnaire = enoQuestionnaire;
        for (Sequence sequence : enoQuestionnaire.getSequences()) {
            resolveSequenceStructure(sequence);
        }
    }

    /** Iterates on sequence items of the given sequence and resolve them.
     * See <code>resolveStructure</code> method.
     * @param sequence A sequence or subsequence object.
     * */
    private void resolveSequenceStructure(AbstractSequence sequence) {
        for (SequenceItem sequenceItem : sequence.getSequenceItems()) {
            resolveStructure(sequence, sequenceItem);
        }
    }

    /** Iterates on sequence items listed in the filter object, and resolve them in the sequence object.
     * See <code>resolveStructure</code> method.
     * @param filter A filter object.
     * @param sequence A sequence or subsequence.
     * */
    private void resolveFilterStructure(Filter filter, AbstractSequence sequence) {
        for (SequenceItem filterItem : filter.getFilterItems()) {
            resolveStructure(sequence, filterItem);
        }
    }

    /** <p>Resolves the sequence item given, recursively if needed. "Resolve" means inserting sequence items in the
     * "sequence structure" of given sequence. It is done by:</p>
     * <ul>
     *   <li>filtering sequence items that are not a part of the questionnaire's structure (e.g. controls,
     *   declarations)</li>
     *   <li>replacing items that encapsulate other items (loops and filters).</li>
     * </ul>
     * <p>Note: (!) Nested loop is not supported here yet. (!)</p>
     * @param sequence A sequence or subsequence.
     * @param sequenceItem A sequence item.
     * */
    private void resolveStructure(AbstractSequence sequence, SequenceItem sequenceItem) {
        switch (sequenceItem.getType()) {
            case SUBSEQUENCE -> {
                sequence.getSequenceStructure().add(sequenceItem);
                Subsequence subsequence = (Subsequence) enoQuestionnaire.get(sequenceItem.getId());
                resolveSequenceStructure(subsequence);
            }
            case QUESTION ->
                    sequence.getSequenceStructure().add(sequenceItem);
            case LOOP -> {
                Loop loop = (Loop) enoQuestionnaire.get(sequenceItem.getId());
                String subsequenceReference = loop.getSequenceReference();
                Subsequence subsequence = (Subsequence) enoQuestionnaire.get(subsequenceReference);
                // Note: embedded loops is not supported here.
                // (With embedded loops the reference might be a loop and not a subsequence.)
                sequence.getSequenceStructure().add(SequenceItem.builder()
                        .id(subsequenceReference)
                        .type(SequenceItemType.SUBSEQUENCE)
                        .build());
                resolveSequenceStructure(subsequence);
            }
            case FILTER -> {
                Filter filter = (Filter) enoQuestionnaire.get(sequenceItem.getId());
                resolveFilterStructure(filter, sequence);
            }
        }
    }

}
