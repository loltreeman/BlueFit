package app.rest.controllers;

import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import app.components.PaymentComponent;
import app.dto.DeleteDTO;
import app.dto.PaymentDTO;
import app.rest.errorhandlers.ServiceException;

@Component
@Path("/payment")
public class PaymentController {

	@Autowired
	private PaymentComponent component;

	@POST
	@Path("/record")
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Produces(MediaType.APPLICATION_JSON)
	public PaymentDTO record(@FormParam("subscriptionPk") String subscriptionPk, @FormParam("amount") String amount,
			@FormParam("method") String method, @FormParam("referenceNo") String referenceNo) throws ServiceException {
		return component.recordPayment(Params.toLong("subscriptionPk", subscriptionPk),
				Params.toMoney("amount", amount), Params.required("method", method), Params.optional(referenceNo));
	}

	@GET
	@Path("/get")
	@Produces(MediaType.APPLICATION_JSON)
	public PaymentDTO get(@QueryParam("pk") String pk) throws ServiceException {
		return component.getPayment(Params.toLong("pk", pk));
	}

	@GET
	@Path("/bysubscription")
	@Produces(MediaType.APPLICATION_JSON)
	public List<PaymentDTO> bySubscription(@QueryParam("subscriptionPk") String subscriptionPk)
			throws ServiceException {
		return component.getBySubscription(Params.toLong("subscriptionPk", subscriptionPk));
	}

	@POST
	@Path("/delete")
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Produces(MediaType.APPLICATION_JSON)
	public DeleteDTO delete(@FormParam("pk") String pk) throws ServiceException {
		return component.deletePayment(Params.toLong("pk", pk));
	}

}
