package io.jenkins.plugin;

import io.jenkins.plugin.exceptions.PipelineAsYamlEmptyInputException;
import io.jenkins.plugin.exceptions.PipelineAsYamlRuntimeException;
import io.jenkins.plugin.models.PipelineModel;
import io.jenkins.plugin.parsers.PipelineParser;

import java.util.Optional;

public class App {
    public static void main(String[] args) {
        String convert = convertToDec("pipeline:\n" +
                "  stages:\n" +
                "    - stage: \"Stage1\"\n" +
                "      steps:\n" +
                "        script |\n" +
                "          echo \"1\"\n" +
                "          echo \"2\"\n" +
                "          echo \"3\"");
        System.out.println(convert);
    }

    private static void checkConverterInput(String converterInput) throws PipelineAsYamlEmptyInputException {
        if (converterInput == null || converterInput.trim().length() == 0)
            throw new PipelineAsYamlEmptyInputException("Input send from Converter Page is empty");
    }

    public static String convertToDec(String pipelinePay) {
        PipelineParser pipelineParser = new PipelineParser(pipelinePay);
        try {
            checkConverterInput(pipelinePay);
            Optional<PipelineModel> pipelineModel = pipelineParser.parse();
            if (pipelineModel.isPresent()) {
                return pipelineModel.get().toPrettyGroovy();
            } else {
                throw new PipelineAsYamlRuntimeException("Exception happened while converting. Please check the logs");
            }
        } catch (PipelineAsYamlEmptyInputException p) {
            return "";
        } catch (PipelineAsYamlRuntimeException p) {
            return "Exception happened while converting. Please check the logs";
        }
    }
}
