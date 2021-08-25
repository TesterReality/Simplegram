package com.simplegram.payload.response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AttachmentsMessage {
    private String url;
    private String type;
}
