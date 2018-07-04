package com.prinz.rest;

import javax.annotation.security.RolesAllowed;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.telegram.telegrambots.api.methods.send.SendMessage;

import com.prinz.bot.TelegramBot;
import com.prinz.service.BotService;

@Path("/status")
public class Status {

	// This method is called if HTML is request
	@RolesAllowed({ "USER", "ADMIN" })
	@GET
	@Produces(MediaType.TEXT_HTML)
	public Response getStatus(@QueryParam("id") Integer id, @QueryParam("value") double value) {
		if (BotService.isRunning()) {
			TelegramBot bot = BotService.bot;
			SendMessage message = new SendMessage();
			message.setChatId(618171563l);
			message.setText("Sensor:" + id + " - Value:" + value);
			bot.sentMessage(message);

			return Response.status(200).entity("Nachricht wurde versendet").build();
		} else
			return Response.status(502).entity("Bot ist deaktiviert").build();
	}
}
