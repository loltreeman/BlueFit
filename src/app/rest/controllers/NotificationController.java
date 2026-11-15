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

import app.components.NotificationComponent;
import app.dto.DeleteDTO;
import app.dto.NotificationDTO;
import app.rest.errorhandlers.ServiceException;

@Component
@Path("/notification")
public class NotificationController {

	@Autowired
	private NotificationComponent component;

	@POST
	@Path("/send")
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Produces(MediaType.APPLICATION_JSON)
	public NotificationDTO send(@FormParam("memberPk") String memberPk, @FormParam("message") String message)
			throws ServiceException {
		return component.sendManual(Params.toLong("memberPk", memberPk), Params.required("message", message));
	}

	@GET
	@Path("/get")
	@Produces(MediaType.APPLICATION_JSON)
	public NotificationDTO get(@QueryParam("pk") String pk) throws ServiceException {
		return component.getNotification(Params.toLong("pk", pk));
	}

	@GET
	@Path("/bymember")
	@Produces(MediaType.APPLICATION_JSON)
	public List<NotificationDTO> byMember(@QueryParam("memberPk") String memberPk) throws ServiceException {
		return component.getByMember(Params.toLong("memberPk", memberPk));
	}

	@POST
	@Path("/delete")
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Produces(MediaType.APPLICATION_JSON)
	public DeleteDTO delete(@FormParam("pk") String pk) throws ServiceException {
		return component.deleteNotification(Params.toLong("pk", pk));
	}

}
