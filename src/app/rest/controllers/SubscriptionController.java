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

import app.components.SubscriptionComponent;
import app.dto.DeleteDTO;
import app.dto.SubscriptionDTO;
import app.rest.errorhandlers.ServiceException;

@Component
@Path("/subscription")
public class SubscriptionController {

	@Autowired
	private SubscriptionComponent component;

	@POST
	@Path("/new")
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Produces(MediaType.APPLICATION_JSON)
	public SubscriptionDTO newSubscription(@FormParam("memberPk") String memberPk, @FormParam("planPk") String planPk)
			throws ServiceException {
		return component.newSubscription(Params.toLong("memberPk", memberPk), Params.toLong("planPk", planPk));
	}

	@POST
	@Path("/renew")
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Produces(MediaType.APPLICATION_JSON)
	public SubscriptionDTO renew(@FormParam("memberPk") String memberPk, @FormParam("planPk") String planPk)
			throws ServiceException {
		return component.renewSubscription(Params.toLong("memberPk", memberPk), Params.toLong("planPk", planPk));
	}

	@POST
	@Path("/freeze")
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Produces(MediaType.APPLICATION_JSON)
	public SubscriptionDTO freeze(@FormParam("subscriptionPk") String subscriptionPk) throws ServiceException {
		return component.freezeSubscription(Params.toLong("subscriptionPk", subscriptionPk));
	}

	@POST
	@Path("/unfreeze")
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Produces(MediaType.APPLICATION_JSON)
	public SubscriptionDTO unfreeze(@FormParam("subscriptionPk") String subscriptionPk) throws ServiceException {
		return component.unfreezeSubscription(Params.toLong("subscriptionPk", subscriptionPk));
	}

	@POST
	@Path("/cancel")
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Produces(MediaType.APPLICATION_JSON)
	public SubscriptionDTO cancel(@FormParam("subscriptionPk") String subscriptionPk) throws ServiceException {
		return component.cancelSubscription(Params.toLong("subscriptionPk", subscriptionPk));
	}

	@POST
	@Path("/delete")
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Produces(MediaType.APPLICATION_JSON)
	public DeleteDTO delete(@FormParam("subscriptionPk") String subscriptionPk) throws ServiceException {
		return component.deleteSubscription(Params.toLong("subscriptionPk", subscriptionPk));
	}

	@GET
	@Path("/get")
	@Produces(MediaType.APPLICATION_JSON)
	public SubscriptionDTO get(@QueryParam("pk") String pk) throws ServiceException {
		return component.getSubscription(Params.toLong("pk", pk));
	}

	@GET
	@Path("/bymember")
	@Produces(MediaType.APPLICATION_JSON)
	public List<SubscriptionDTO> byMember(@QueryParam("memberPk") String memberPk) throws ServiceException {
		return component.getByMember(Params.toLong("memberPk", memberPk));
	}

	@GET
	@Path("/expiring")
	@Produces(MediaType.APPLICATION_JSON)
	public List<SubscriptionDTO> expiring(@QueryParam("days") String days) throws ServiceException {
		return component.getExpiring(Params.toInt("days", days));
	}

	@GET
	@Path("/unpaid")
	@Produces(MediaType.APPLICATION_JSON)
	public List<SubscriptionDTO> unpaid() {
		return component.getUnpaid();
	}

}
