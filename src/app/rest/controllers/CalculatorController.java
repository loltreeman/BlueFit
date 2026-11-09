package app.rest.controllers;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import app.components.CalculatorComponent;


@Component
@Path("/calculator")
public class CalculatorController {
	
	@Autowired
	CalculatorComponent cc;
	
	@GET
	@Path("/test")
	public String hello()
	{
		return "Get hello";
	}
	
	@GET
	@Path("/add")
	@Produces(MediaType.TEXT_PLAIN)
	public String add(@QueryParam("a") String a, @QueryParam("b") String b)
	{
		return cc.add(a, b);
	}
	
	@POST
	@Path("/subtract")
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Produces(MediaType.TEXT_PLAIN)
	public String subtract(@FormParam("a") String a, @FormParam("b") String b)
	{
		return cc.subtract(a, b);
	}
	
	@GET
	@Path("/divide")
	@Produces(MediaType.TEXT_PLAIN)
	public String divide(@QueryParam("a") String a, @QueryParam("b") String b)
	{
		return cc.divide(a, b);
	}
	
	@GET
	@Path("/multiply")
	@Produces(MediaType.TEXT_PLAIN)
	public String multiply(@QueryParam("a") String a, @QueryParam("b") String b)
	{
		return cc.multiply(a, b);
	}
	
	@GET
	@Path("/sqrt")
	@Produces(MediaType.TEXT_PLAIN)
	public String sqrt(@QueryParam("n") String n_string)
	{
		return cc.sqrt(n_string);
	}
}
