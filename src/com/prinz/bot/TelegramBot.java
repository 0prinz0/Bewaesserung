package com.prinz.bot;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import org.telegram.telegrambots.api.methods.send.SendMessage;
import org.telegram.telegrambots.api.objects.CallbackQuery;
import org.telegram.telegrambots.api.objects.Message;
import org.telegram.telegrambots.api.objects.Update;
import org.telegram.telegrambots.api.objects.User;
import org.telegram.telegrambots.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.api.objects.replykeyboard.buttons.KeyboardButton;
import org.telegram.telegrambots.api.objects.replykeyboard.buttons.KeyboardRow;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.exceptions.TelegramApiException;

import com.prinz.rest.InlineKeyboardBuilder;

import entity.Command;
import entity.Sensor;

public class TelegramBot extends TelegramLongPollingBot {

	
	String command = "";
	String text = "TESTEN";
	String from = "";
	String data ="";
	long chatId = 0l;
	
	private String getUsername(User user) {
		if (user != null)
			return user.getFirstName() + " " + (user.getLastName() != null ? user.getLastName() : "");
		else
			return "";
	}

	@Override
	public void onUpdateReceived(Update update) {

		List<Sensor> sensoren = new ArrayList<>();
		Sensor s1 = new Sensor(1, "Basilikum", 1);
		Sensor s2 = new Sensor(2, "Sellerie", 2);
		Sensor s3 = new Sensor(3, "Sonnenblume", 3);
		Sensor s4 = new Sensor(4, "Baum", 4);
		sensoren.add(s1);
		sensoren.add(s2);
		sensoren.add(s3);
		sensoren.add(s4);

		
		SendMessage sendMessage = new SendMessage();
		
		Message message = update.getMessage();
		if (message != null) {
			System.out.println("##### 1 ##### " + message);

			// Methode für Message Channel Message
			setData(message.getText(),"",message.getFrom(),message.getChatId());


			// Methode für das Bearbeiten von normalen Messages
		} else {
			CallbackQuery callbackQuery = update.getCallbackQuery();
			message = callbackQuery != null ? (callbackQuery.getMessage() != null ? callbackQuery.getMessage().getReplyToMessage() : null) : null;
			if (message != null) {
				System.out.println("##### 2 ##### " + message);
				// Methode für das Bearbeiten von Inline Antworten
				setData(message.getText(),"",message.getFrom(),message.getChatId());
			} else {
				message = callbackQuery != null ? callbackQuery.getMessage() : null;
				if (message != null) {
					System.out.println("##### 3 ##### " + message);
					setData(message.getText(),callbackQuery.getData(),message.getFrom(),message.getChatId());
				} else {
					message = update.getChannelPost();
					if (message != null) {
						System.out.println("##### 4 ##### " + message.getText());
						setData(message.getText(),"",message.getFrom(),message.getChatId());
						// Methode für Message Channel Message

					}
				}
			}
		}

		if (command.equals("/status")) {
			InlineKeyboardBuilder builder = InlineKeyboardBuilder.create(chatId);
			builder.setText("Status für welchen Sensor?");
			int i = 0;
			builder.row();
			for (Sensor sensor : sensoren) {
				if (i % 2 == 0 && i > 0) {
					builder.endRow();
					builder.row();
				}
				builder.button(sensor.getDescription(), "SensorID|" + sensor.getId());
				i++;
			}
			builder.endRow();
			sendMessage = builder.build();
			sentMessage(sendMessage);

		}
		
		if(data.contains("SensorID")) {
			for(String string : data.split("\\|")) {
				System.out.println(string);
			}
			long id = Long.parseLong(data.split("\\|")[1]);
			Sensor sensor = new Sensor();
			for(Sensor tmpSensor : sensoren) {
				if(tmpSensor.getId() == id)
					sensor = tmpSensor;
			}
			sendMessage.setChatId(chatId);
			sendMessage.setText("Status für "+sensor.getDescription()+": \nFeutchtigkeit: "+sensor.getLastValue()+"\nErfasst am "+new SimpleDateFormat("dd.MM.yyyy HH:mm").format(sensor.getLastUpdated()));
			sentMessage(sendMessage);
		}
		if(command.equals("/befehle")) {
			ReplyKeyboardMarkup replyKeyboardMarkup = new ReplyKeyboardMarkup();
	        List<KeyboardRow> commands = new ArrayList<>();
	        for (Command command : Command.values()) {
	            KeyboardRow commandRow = new KeyboardRow();
	            commandRow.add(command.getCommand());
	            commands.add(commandRow);
	        }
	        replyKeyboardMarkup.setResizeKeyboard(true);
	        replyKeyboardMarkup.setOneTimeKeyboard(true);
	        replyKeyboardMarkup.setKeyboard(commands);
	        replyKeyboardMarkup.setSelective(true);
	        sendMessage.setChatId(chatId);
	        sendMessage.setReplyMarkup(replyKeyboardMarkup);
	        sendMessage.setText("Wähle deinen Befehl");
	        sentMessage(sendMessage);
			
		}
	}

	private void setData(String command, String data, User user, Long chatId) {
		this.command = command;
		this.from = getUsername(user);
		this.chatId = chatId;
		this.data = data;
		
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
