package com.prinz.bot;

import org.telegram.telegrambots.api.methods.send.SendMessage;
import org.telegram.telegrambots.api.objects.CallbackQuery;
import org.telegram.telegrambots.api.objects.Message;
import org.telegram.telegrambots.api.objects.Update;
import org.telegram.telegrambots.api.objects.User;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.exceptions.TelegramApiException;

public class TelegramBot extends TelegramLongPollingBot {

	private String getUsername(User user) {
		if (user != null)
			return user.getFirstName() + " " + (user.getLastName() != null ? user.getLastName() : "");
		else
			return "";
	}

	@Override
	public void onUpdateReceived(Update update) {
		long chatId = 0l;
		Message message = update.getMessage();
		String command = "";
		String text = "TESTEN";
		String from = "";
		System.out.println("##### 1 ##### " + message);
		if (message != null) {

			// Methode für Message Channel Message
			command = message.getText();
			from = getUsername(message.getFrom());
			chatId = message.getChatId();

			// Methode für das Bearbeiten von normalen Messages
		} else {
			CallbackQuery callbackQuery = update.getCallbackQuery();
			message = callbackQuery != null ? (callbackQuery.getMessage() != null ? callbackQuery.getMessage().getReplyToMessage() : null) : null;
			System.out.println("##### 2 ##### " + message);
			if (message != null) {

				// Methode für das Bearbeiten von Inline Antworten
			} else {
				message = callbackQuery != null ? callbackQuery.getMessage() : null;
				System.out.println("##### 3 ##### " + message);
				if (message != null) {
					from = getUsername(callbackQuery.getFrom());
					String data = callbackQuery.getData();

				} else {
					message = update.getChannelPost();
					System.out.println("##### 4 ##### " + message.getText());
					if (message != null) {
						command = message.getText();
						from = message.getAuthorSignature();
						// Methode für Message Channel Message

					}
				}
			}
		}

		System.out.println("MessageID: " + chatId);
		SendMessage sendMessage = new SendMessage();
		sendMessage.setChatId(chatId);
		sendMessage.setText(text);

		// Menu

		if (text.length() > 0 || (sendMessage.getText() != null && sendMessage.getText().length() > 0))
			sentMessage(sendMessage);
	}

	public void sentMessage(SendMessage sendMessage) {
		try {
			execute(sendMessage);
		} catch (TelegramApiException e) {
			e.printStackTrace();
		}
	}

	@Override
	public String getBotUsername() {
		return "Bot";
	}

	@Override
	public String getBotToken() {
		return "507774528:AAEZucOmo471PJf635o_2aA6i3zzkbiPw6o";
	}

	public long getCreator() {
		return 618171563l;
	}

}
