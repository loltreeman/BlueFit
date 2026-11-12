package app.components;

import app.dto.TwilioReply;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Url;

public interface TwilioRequests {

	@POST
	@FormUrlEncoded
	public Call<TwilioReply> sendSMS(@Field("To") String to,
						  @Field("MessagingServiceSid") String messageService,
						  @Field("Body") String body,
						  @Header("Authorization") String creds,
						  @Url String url);
}
