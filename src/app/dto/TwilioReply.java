package app.dto;

import com.google.gson.annotations.SerializedName;

public class TwilioReply {

	// Retrofit parses with Gson, which ignores Jackson's @JsonProperty
	@SerializedName("messaging_service_sid")
	private String messagingServiceId;

	private String to;
	private String body;
	private String status;

	public String getMessagingServiceId() {
		return messagingServiceId;
	}

	public void setMessagingServiceId(String messagingServiceId) {
		this.messagingServiceId = messagingServiceId;
	}

	public String getTo() {
		return to;
	}

	public void setTo(String to) {
		this.to = to;
	}

	public String getBody() {
		return body;
	}

	public void setBody(String body) {
		this.body = body;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	@Override
	public String toString() {
		return "TwilioReply [messagingServiceId=" + messagingServiceId + ", to=" + to + ", body=" + body
				+ ", status=" + status + "]";
	}

}
