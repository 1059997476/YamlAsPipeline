package io.jenkins.plugin.exceptions;

/**
 * Exception class for handling key not found in LinkedHashmaps
 *
 * @see PipelineAsYamlException
 */
public class PipelineAsYamlNodeNotFoundException extends PipelineAsYamlException {
    public PipelineAsYamlNodeNotFoundException(String nodeName) {
        super(String.format("%s - yaml definition not found.", nodeName));
    }
}
