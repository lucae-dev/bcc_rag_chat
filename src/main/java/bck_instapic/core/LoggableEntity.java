package bck_instapic.core;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

public class LoggableEntity {

    @Override
    public String toString() {
        return ReflectionToStringBuilder.toString(this, ToStringStyle.JSON_STYLE);
    }
}
