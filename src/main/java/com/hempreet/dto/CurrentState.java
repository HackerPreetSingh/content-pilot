package com.hempreet.dto;

import com.hempreet.utils.AppConstants;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString
public class CurrentState {
    private int topicIndex;
    private String topicName;
    private int formatIndex;
    private String formatName;
    private int angleIndex;
    private String angleName;

    public  CurrentState() {
        this.topicIndex = 0;
        this.topicName = AppConstants.topics.getFirst();
        this.formatIndex = 0;
        this.formatName = AppConstants.formats.getFirst();
        this.angleIndex = 0;
        this.angleName = AppConstants.angles.getFirst();
    }

    public CurrentState(int topicIndex, String topicName, int formatIndex, String formatName, int angleIndex, String angleName) {
        this.topicIndex = topicIndex;
        this.topicName = topicName;
        this.formatIndex = formatIndex;
        this.formatName = formatName;
        this.angleIndex = angleIndex;
        this.angleName = angleName;
    }
}
