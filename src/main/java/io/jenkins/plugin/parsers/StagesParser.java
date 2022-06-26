package io.jenkins.plugin.parsers;

import io.jenkins.plugin.exceptions.PipelineAsYamlException;
import io.jenkins.plugin.exceptions.PipelineAsYamlUnknownTypeException;
import io.jenkins.plugin.interfaces.ParserInterface;
import io.jenkins.plugin.models.StageModel;
import io.jenkins.plugin.models.StagesModel;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Optional;

/**
 * Parser for {@link StagesModel}
 */
public class StagesParser extends AbstractParser implements ParserInterface<StagesModel> {

    private LinkedHashMap parentNode;

    /**
     * @param parentNode Parent Node which contains model definition as yaml
     */
    public StagesParser(LinkedHashMap parentNode) {
        this.yamlNodeName = StagesModel.directive;
        this.parentNode = parentNode;
    }

    @Override
    public Optional<StagesModel> parse() {
        try {
            List<StageModel> stageModelList = new ArrayList<>();
            Object stagesObject = this.getChildNodeAsObject(parentNode);
            if (stagesObject == null) {
                return Optional.empty();
            }
            if (stagesObject instanceof List) {
                for (LinkedHashMap childStage : (List<LinkedHashMap>) stagesObject) {
                    Optional<StageModel> stageModel = new StageParser(childStage).parse();
                    stageModel.ifPresent(stageModelList::add);
                }
                return Optional.of(new StagesModel(stageModelList));
            } else {
                throw new PipelineAsYamlUnknownTypeException(stagesObject.getClass().toString());
            }
        } catch (PipelineAsYamlException p) {
            return Optional.empty();
        }
    }
}
