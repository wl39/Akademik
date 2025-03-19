package uk.ac.standrews.cs.host.cs3099user20.model;

import static uk.ac.standrews.cs.host.cs3099user20.service.InjectionPrevention.injectionPrevention;

public class Submission {

    private String name;
    private MetaData metadata;
    private CodeVersion[] codeVersions;
    private SuperGroupComment[] comments;

    public Submission(String name, MetaData metaData, CodeVersion[] codeVersions, SuperGroupComment[] comments) {
        this.name = injectionPrevention(name);
        this.metadata = metaData;
        this.codeVersions = codeVersions;
        this.comments = comments;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = injectionPrevention(name);
    }

    public MetaData getMetadata() {
        return metadata;
    }

    public void setMetadata(MetaData metadata) {
        this.metadata = metadata;
    }

    public CodeVersion[] getCodeVersions() {
        return codeVersions;
    }

    public void setCodeVersions(CodeVersion[] codeVersions) {
        this.codeVersions = codeVersions;
    }

    public SuperGroupComment[] getComments() {
        return comments;
    }

    public void setComments(SuperGroupComment[] comments) {
        this.comments = comments;
    }
}
