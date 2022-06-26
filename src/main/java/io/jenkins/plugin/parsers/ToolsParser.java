package io.jenkins.plugin.parsers;

import io.jenkins.plugin.exceptions.PipelineAsYamlException;
import io.jenkins.plugin.interfaces.ParserInterface;
import io.jenkins.plugin.models.ChildToolModel;
import io.jenkins.plugin.models.ToolsModel;

import java.util.*;

/**
 * Parser for {@link ToolsModel}
 */
public class ToolsParser extends AbstractParser implements ParserInterface<ToolsModel> {

    private LinkedHashMap toolsNode;
    private LinkedHashMap parentNode;

    /**
     * @param parentNode Parent Node which contains model definition as yaml
     */
    public ToolsParser(LinkedHashMap parentNode){
        this.yamlNodeName = ToolsModel.directive;
        this.parentNode = parentNode;
    }

    @Override
    public Optional<ToolsModel> parse() {
        try {
            List<ChildToolModel> childToolModels = new ArrayList<>();
            this.toolsNode = this.getChildNodeAsLinkedHashMap(parentNode);
            if( this.toolsNode == null || this.toolsNode.size() == 0)
                return Optional.empty();
            for (Object childTool : this.toolsNode.entrySet()) {
                Map.Entry childToolEntry = (Map.Entry) childTool;
                String childToolKey = (String) childToolEntry.getKey();
                String childToolValue = (String) childToolEntry.getValue();
                childToolModels.add(new ChildToolModel(childToolKey, childToolValue));
            }
            return Optional.of(new ToolsModel(childToolModels));
        }
        catch (PipelineAsYamlException p) {
            return Optional.empty();
        }
    }
}
