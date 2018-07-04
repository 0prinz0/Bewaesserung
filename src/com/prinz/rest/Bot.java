package com.prinz.rest;

import javax.annotation.security.RolesAllowed;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.prinz.service.BotService;

@Path("/bot")
public class Bot {

	@GET
	@RolesAllowed("ADMIN")
	@Produces(MediaType.TEXT_HTML)
	//Starts the Botsession, if there is no Session
	public String statusBot() {
		boolean started = BotService.isRunning();
		return "<html> " + "<title>" + "Status des Bots" + "</title>" + "<body><h1>" + "Der Bot ist " + (started ? "gestartet" : "gestoppt") + "</body></h1>" + "</html> ";
	}

	@GET
	@Path("start")
	@RolesAllowed("ADMIN")
	@Produces(MediaType.TEXT_HTML)
	//Starts the Botsession, if there is no Session
	public String startBot() {
		boolean started = BotService.startBot();
		if (started)
			return "<html> " + "<title>" + "Bot wurde gestartet" + "</title>" + "<body><h1>" + "Bot wurde gestartet" + "</body></h1>" + "</html> ";
		else
			return "<html> " + "<title>" + "Fehler" + "</title>" + "<body><h1>" + "Bot wurde bereits gestartet" + "</body></h1>" + "</html> ";
	}

	@GET
	@Path("stop")
	@RolesAllowed("ADMIN")
	@Produces(MediaType.TEXT_HTML)
	//Stops the Botsession, if there is a Session
	public String stopBot() {
		boolean stopped = BotService.stopBot();
		if (stopped)
			return "<html> " + "<title>" + "Bot wurde gestoppt" + "</title>" + "<body><h1>" + "Bot wurde gestartet" + "</body></h1>" + "</html> ";
		else
			return "<html> " + "<title>" + "Fehler" + "</title>" + "<body><h1>" + "Bot wurde bereits gestoppt" + "</body></h1>" + "</html> ";
	}
}
