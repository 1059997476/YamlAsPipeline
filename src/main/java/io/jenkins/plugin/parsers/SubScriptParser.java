package io.jenkins.plugin.parsers;

import io.jenkins.plugin.exceptions.PipelineAsYamlException;
import io.jenkins.plugin.interfaces.ParserInterface;
import io.jenkins.plugin.models.ScriptModel;
import io.jenkins.plugin.models.SubScriptModel;

import java.util.LinkedHashMap;
import java.util.Optional;

/**
 * Parser for {@link SubScriptModel}
 */
public class SubScriptParser extends AbstractParser implements ParserInterface<SubScriptModel> {

    private LinkedHashMap parentNode;

    /**
     * @param parentNode Parent Node which contains model definition as yaml
     */
    public SubScriptParser(LinkedHashMap parentNode){
        this.parentNode = parentNode;
    }

    @Override
    public Optional<SubScriptModel> parse() {
        try {
            String directive = this.getKey(this.parentNode);
            String value = (String) this.getValue(this.parentNode, directive);
            Optional<ScriptModel> scriptModel = new ScriptParser(this.parentNode).parse();
            return Optional.of(new SubScriptModel(directive, Optional.ofNullable(value), scriptModel.get()));
        }
        catch (PipelineAsYamlException p) {
            return Optional.empty();
        }
    }
}
