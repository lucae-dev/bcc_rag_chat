package bck_instapic.replicate.client.response;

import bck_instapic.core.LoggableEntity;
import com.fasterxml.jackson.annotation.JsonProperty;

public class ReplicateModel extends LoggableEntity {
    private String url;
    private String owner;
    private String name;
    private String description;
    private String visibility;
    @JsonProperty("cover_image_url")
    private String coverImageUrl;
    @JsonProperty("latest_version")
    private String latestVersion;

    public ReplicateModel() {
    }

    public ReplicateModel(String url, String owner, String name, String description, String visibility, String coverImageUrl, String latestVersion) {
        this.url = url;
        this.owner = owner;
        this.name = name;
        this.description = description;
        this.visibility = visibility;
        this.coverImageUrl = coverImageUrl;
        this.latestVersion = latestVersion;
    }

    public String getUrl() {
        return url;
    }

    public String getOwner() {
        return owner;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public String getVisibility() {
        return visibility;
    }

    public String getCoverImageUrl() {
        return coverImageUrl;
    }

    public String getLatestVersion() {
        return latestVersion;
    }
}
