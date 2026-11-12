package app.components;

import java.util.Base64;

import javax.annotation.PostConstruct;

import org.springframework.stereotype.Component;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import app.dto.TwilioReply;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

// the only class that talks to the SMS service; swap this one to change providers
@Component
public class TwilioComponent {

	// values from the FakeTwilio README
	static String msgsid = "MG9ed621bcaf657c21bdf9de98647efc3c";
	static String acctId = "AC7d52d386c10c7c41a3e653d53fcd1b50";
	static String creds = "910931f86fa37a5766293aed1d198fcd";
	static String url = "http://localhost:9997/fake_twilio/send";

	private TwilioRequests request;
	private String authorization;

	@PostConstruct
	public void init() {
		Gson gson = new GsonBuilder().create();

		Retrofit retrofit = new Retrofit.Builder()
				.baseUrl("https://bogus")
				.addConverterFactory(GsonConverterFactory.create(gson))
				.build();

		request = retrofit.create(TwilioRequests.class);

		// Basic Authentication
		byte[] encodedAuth = Base64.getEncoder().encode((acctId + ":" + creds).getBytes());
		authorization = "Basic " + new String(encodedAuth);
	}

	// returns null if the SMS could not be sent (e.g. FakeTwilio is not running)
	public TwilioReply sendSms(String cellNumber, String message) {
		try {
			Call<TwilioReply> call = request.sendSMS(cellNumber, msgsid, message, authorization, url);
			Response<TwilioReply> resp = call.execute();

			// FakeTwilio replies 200 on success (real Twilio uses 201)
			if (resp.code() == 200 || resp.code() == 201) {
				return resp.body();
			}
			System.out.println("SMS send failed (" + resp.code() + "): " + resp.errorBody().string());
		} catch (Exception e) {
			System.out.println("SMS send failed: " + e.getMessage());
		}
		return null;
	}

}
