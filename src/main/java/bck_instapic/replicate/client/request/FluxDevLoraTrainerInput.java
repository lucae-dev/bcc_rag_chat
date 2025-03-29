package bck_instapic.replicate.client.request;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class FluxDevLoraTrainerInput {

    /**
     * Required. A URL (or multiple URLs) pointing to your training data.
     * For example, a zip file or a folder of images.
     */
    private String inputImages;

    /**
     * Required. A unique trigger word so you can "activate" this subject in your prompts.
     */
    private String triggerWord;

    /**
     * Optional. A prefix that is automatically added to each caption if you do auto-captioning.
     */
    private String autocaptionPrefix;

    /**
     * Optional. A suffix automatically added to each caption for auto-captioning.
     */
    private String autocaptionSuffix;

    /**
     * Optional. Number of training steps to run.
     */
    private Integer steps;

    /**
     * Optional. The LoRA rank to use. Higher ranks can capture more detail but might require more memory.
     */
    private Integer loraRank;

    public FluxDevLoraTrainerInput() {}

    // Getters and setters

    public String getInputImages() {
        return inputImages;
    }
    public void setInputImages(String inputImages) {
        this.inputImages = inputImages;
    }

    public String getTriggerWord() {
        return triggerWord;
    }
    public void setTriggerWord(String triggerWord) {
        this.triggerWord = triggerWord;
    }

    public String getAutocaptionPrefix() {
        return autocaptionPrefix;
    }
    public void setAutocaptionPrefix(String autocaptionPrefix) {
        this.autocaptionPrefix = autocaptionPrefix;
    }

    public String getAutocaptionSuffix() {
        return autocaptionSuffix;
    }
    public void setAutocaptionSuffix(String autocaptionSuffix) {
        this.autocaptionSuffix = autocaptionSuffix;
    }

    public Integer getSteps() {
        return steps;
    }
    public void setSteps(Integer steps) {
        this.steps = steps;
    }

    public Integer getLoraRank() {
        return loraRank;
    }
    public void setLoraRank(Integer loraRank) {
        this.loraRank = loraRank;
    }
}