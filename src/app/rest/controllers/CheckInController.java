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

import app.components.CheckInComponent;
import app.dto.CheckInDTO;
import app.dto.DeleteDTO;
import app.rest.errorhandlers.ServiceException;

@Component
@Path("/checkin")
public class CheckInController {

	@Autowired
	private CheckInComponent component;

	@POST
	@Path("/new")
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Produces(MediaType.APPLICATION_JSON)
	public CheckInDTO checkIn(@FormParam("memberPk") String memberPk) throws ServiceException {
		return component.checkIn(Params.toLong("memberPk", memberPk));
	}

	@GET
	@Path("/get")
	@Produces(MediaType.APPLICATION_JSON)
	public CheckInDTO get(@QueryParam("pk") String pk) throws ServiceException {
		return component.getCheckIn(Params.toLong("pk", pk));
	}

	@GET
	@Path("/bymember")
	@Produces(MediaType.APPLICATION_JSON)
	public List<CheckInDTO> byMember(@QueryParam("memberPk") String memberPk) throws ServiceException {
		return component.getByMember(Params.toLong("memberPk", memberPk));
	}

	@POST
	@Path("/delete")
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Produces(MediaType.APPLICATION_JSON)
	public DeleteDTO delete(@FormParam("pk") String pk) throws ServiceException {
		return component.deleteCheckIn(Params.toLong("pk", pk));
	}

}
