package io.jenkins.plugin.parsers;


import io.jenkins.plugin.exceptions.PipelineAsYamlException;
import io.jenkins.plugin.exceptions.PipelineAsYamlRuntimeException;
import io.jenkins.plugin.interfaces.ParserInterface;
import io.jenkins.plugin.models.PipelineModel;

import java.util.LinkedHashMap;
import java.util.Optional;

/**
 * Parser for {@link PipelineModel}
 */
public class PipelineParser extends AbstractParser implements ParserInterface<PipelineModel> {

    private String jenkinsFileAsYamlContent;
    private PipelineModel pipelineModel;

    /**
     * @param jenkinsFileAsYamlContent Jenkins File as Yaml
     */
    public PipelineParser(String jenkinsFileAsYamlContent){
        super();
        this.jenkinsFileAsYamlContent = jenkinsFileAsYamlContent;
        this.yamlNodeName = PipelineModel.directive;
    }

    @Override
    public Optional<PipelineModel> parse()  {
        try {
            LinkedHashMap jenkinsFileHashMap = yaml.load(this.jenkinsFileAsYamlContent);
            LinkedHashMap pipelineNode = this.getChildNodeAsLinkedHashMap(jenkinsFileHashMap);
            this.pipelineModel = PipelineModel.builder()
                    .library(new LibraryParser(pipelineNode).parse())
                    .agent(new AgentParser(pipelineNode).parse())
                    .post(new PostParser(pipelineNode).parse())
                    .environment(new EnvironmentParser(pipelineNode).parse())
                    .tools(new ToolsParser(pipelineNode).parse())
                    .options(new OptionsParser(pipelineNode).parse())
                    .parameters(new ParametersParser(pipelineNode).parse())
                    .triggers(new TriggersParser(pipelineNode).parse())
                    .stages(new StagesParser(pipelineNode).parse())
                    .build();
            return Optional.ofNullable(this.pipelineModel);
        }
        catch (PipelineAsYamlException p) {
            return Optional.empty();
        }
        catch (Exception e) {
            throw new PipelineAsYamlRuntimeException(e.getLocalizedMessage(), e);
        }
    }


}
