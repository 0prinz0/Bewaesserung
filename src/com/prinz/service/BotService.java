package com.prinz.service;

import org.telegram.telegrambots.ApiContextInitializer;
import org.telegram.telegrambots.TelegramBotsApi;
import org.telegram.telegrambots.exceptions.TelegramApiException;
import org.telegram.telegrambots.generics.BotSession;

import com.prinz.bot.TelegramBot;

public class BotService {
	public static BotSession session;
	public static TelegramBot bot;

	public static boolean startBot() {
		if (session == null) {
			ApiContextInitializer.init();

			TelegramBotsApi botsApi = new TelegramBotsApi();

			try {
				System.out.println("Server starten");
				bot = new TelegramBot();
				session = botsApi.registerBot(bot);
				return true;

			} catch (TelegramApiException e) {
				e.printStackTrace();
			}
		}
		return false;
	}

	public static boolean stopBot() {
		if (session != null && session.isRunning()) {
			System.out.println("Server stoppen");
			session.stop();
			return true;
		}
		return false;
	}

	public static boolean isRunning() {

		if (session != null && session.isRunning())
			return true;
		else
			return false;

	}
}
