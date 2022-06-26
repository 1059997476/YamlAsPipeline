package io.jenkins.plugin.parsers;

import io.jenkins.plugin.exceptions.PipelineAsYamlException;
import io.jenkins.plugin.interfaces.ParserInterface;
import io.jenkins.plugin.models.OptionsModel;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Optional;

/**
 * Parser for {@link OptionsModel}
 */
public class OptionsParser extends AbstractParser implements ParserInterface<OptionsModel> {

    private List optionsNode;
    private LinkedHashMap parentNode;

    /**
     * @param parentNode Parent Node which contains model definition as yaml
     */
    public OptionsParser(LinkedHashMap parentNode){
        this.yamlNodeName = OptionsModel.directive;
        this.parentNode = parentNode;
    }

    @Override
    public Optional<OptionsModel> parse() {
        try {
            this.optionsNode = this.getChildNodeAsList(parentNode);
            return Optional.of(new OptionsModel(this.optionsNode));
        }
        catch (PipelineAsYamlException p){
            return Optional.empty();
        }
    }
}
