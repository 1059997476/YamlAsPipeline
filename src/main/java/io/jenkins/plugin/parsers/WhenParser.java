package io.jenkins.plugin.parsers;

import io.jenkins.plugin.exceptions.PipelineAsYamlException;
import io.jenkins.plugin.exceptions.PipelineAsYamlUnknownTypeException;
import io.jenkins.plugin.interfaces.ParserInterface;
import io.jenkins.plugin.models.WhenModel;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Optional;

/**
 * Parser for {@link WhenModel}
 */
public class WhenParser extends AbstractParser implements ParserInterface<WhenModel> {

    private LinkedHashMap parentNode;

    /**
     * @param parentNode Parent Node which contains model definition as yaml
     */
    public WhenParser(LinkedHashMap parentNode){
        this.yamlNodeName = WhenModel.directive;
        this.parentNode = parentNode;
    }

    @Override
    public Optional<WhenModel> parse() {
        try {
            Object whenObject = this.getValue(this.parentNode, this.yamlNodeName);
            if( whenObject instanceof List) {
                return Optional.of(new WhenModel((List<String>) whenObject));
            }
            else if (whenObject instanceof  LinkedHashMap) {
                return Optional.of(new WhenModel(new WhenConditionalParser((LinkedHashMap) whenObject).parse()));
            }
            else {
                throw new PipelineAsYamlUnknownTypeException(whenObject.getClass().toString());
            }
        }
        catch (PipelineAsYamlException p) {
            return Optional.empty();
        }

    }
}
