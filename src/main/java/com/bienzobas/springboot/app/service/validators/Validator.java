package com.bienzobas.springboot.app.service.validators;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.springframework.stereotype.Component;

import com.bienzobas.springboot.app.models.entity.Metrics;
import com.google.gson.Gson;

@Component
public class Validator {

	private static Properties p = null;

	public enum Fields {

		MESSAGE_TYPE("message_type"),

		TIMESTAMP("timestamp"),

		ORIGIN("origin"),

		DESTINATION("destination");

		private String fieldName;

		Fields(String fieldName) {
			this.fieldName = fieldName;
		}

		public String getFieldName() {
			return this.fieldName;
		}

	}

	public enum CallFields {

		DURATION("duration"),

		STATUS_CODE("status_code"),

		STATUS_DESCRIPTION("status_description");

		private String fieldName;

		CallFields(String fieldName) {
			this.fieldName = fieldName;
		}

		public String getFieldName() {
			return this.fieldName;
		}

	}

	public enum MsgFields {

		MESSAGE_CONTENT("message_content"),

		MESSAGE_STATUS("message_status");

		private String fieldName;

		MsgFields(String fieldName) {
			this.fieldName = fieldName;
		}

		public String getFieldName() {
			return this.fieldName;
		}

	}

	public void validateFields(final String jsonLine, final Metrics metrics) {

		boolean missingFields = false;
		boolean blankFields = false;
		boolean errorFields = false;

		final Properties properties = this.getPropertiesFromJson(jsonLine);

		/**
		 * Recorro los fields comunes
		 */
		for (Fields field : Fields.values()) {

			final String fieldName = field.getFieldName();

			if (!properties.containsKey(fieldName)) {

				missingFields = true;

			} else if (properties.getProperty(fieldName).trim().equalsIgnoreCase("")) {

				blankFields = true;

			} else {
				switch (field) {

				case TIMESTAMP:

					try {
						long timestamp = Long.parseLong(properties.getProperty(field.TIMESTAMP.getFieldName()));

						if (properties.getProperty(fieldName).trim().equalsIgnoreCase("")) {

							blankFields = true;

						}

					} catch (Exception exception) {
						errorFields = true;
					}

					break;

				case ORIGIN:

					try {
						long timestamp = Long.parseLong(properties.getProperty(field.ORIGIN.getFieldName()));

						if (properties.getProperty(fieldName).trim().equalsIgnoreCase("")) {

							blankFields = true;

						}

					} catch (Exception exception) {
						errorFields = true;
					}

					break;

				case DESTINATION:

					try {
						long timestamp = Long.parseLong(properties.getProperty(field.TIMESTAMP.getFieldName()));

						if (properties.getProperty(fieldName).trim().equalsIgnoreCase("")) {

							blankFields = true;

						}

					} catch (Exception exception) {
						errorFields = true;
					}

					break;

				case MESSAGE_TYPE:

					/**
					 * Recorro los fields cuando es una llamada
					 */
					if (properties.getProperty(fieldName).equalsIgnoreCase("CALL")) {
						for (CallFields callField : CallFields.values()) {

							final String callFieldName = callField.getFieldName();

							if (!properties.containsKey(callFieldName)) {

								missingFields = true;

							} else if (properties.getProperty(callFieldName).trim().equalsIgnoreCase("")) {

								blankFields = true;

							} else {
								switch (callField) {

								case DURATION:

									try {
										long duration = Long
												.parseLong(properties.getProperty(callField.DURATION.getFieldName()));

										if (properties.getProperty(callFieldName).trim().equalsIgnoreCase("")) {

											blankFields = true;

										}

									} catch (Exception exception) {
										errorFields = true;
									}

									break;

								case STATUS_CODE:

									try {
										errorFields = !validateStatusCode(
												properties.getProperty(callField.STATUS_CODE.getFieldName()));

										if (properties.getProperty(callFieldName).trim().equalsIgnoreCase("")) {

											blankFields = true;

										}
									} catch (IOException e) {
										e.printStackTrace();
									}

									break;

								case STATUS_DESCRIPTION:

									if (properties.getProperty(callFieldName).trim().equalsIgnoreCase("")) {

										blankFields = true;

									}

									break;

								}
							}

						}
						/**
						 * Recorro los fields cuando es un mensaje
						 */
					} else if (properties.getProperty(fieldName).equalsIgnoreCase("MSG")) {
						for (MsgFields msgField : MsgFields.values()) {

							final String msgFieldName = msgField.getFieldName();

							if (!properties.containsKey(msgFieldName)) {

								missingFields = true;

							} else if (properties.getProperty(msgFieldName).trim().equalsIgnoreCase("")) {

								blankFields = true;

							} else {
								switch (msgField) {

								case MESSAGE_CONTENT:

									break;

								case MESSAGE_STATUS:

									try {
										errorFields = !validateMessageStatus(
												properties.getProperty(msgField.MESSAGE_STATUS.getFieldName()));
									} catch (IOException e) {
										e.printStackTrace();
									}

									break;

								}
							}
						}
					} else {
						errorFields = true;
					}
				}
			}

		}

		if (missingFields) {
			metrics.getRowsWithMissingFields();
		}
		if (blankFields) {
			System.out.println("LINEA CON BLANCO");
			metrics.incrementsMessagesWithBlank();
		}
		if (errorFields) {
			metrics.incrementsRowsWithErrors();
		}

	}

	/**
	 * TODO: Adaptar metodo a la normativa MSISDN
	 */
	public String getCountryCode(String code) {
		return code.substring(0, 3);
	}

	public Properties getPropertiesFromJson(String json) {
		return new Gson().fromJson(json, Properties.class);
	}

	public boolean validateStatusCode(String code) throws IOException {

		p = new Properties();
		InputStream propertiesStream = ClassLoader.getSystemResourceAsStream("validation.properties");
		p.load(propertiesStream);
		propertiesStream.close();

		boolean result = false;

		if (code.equalsIgnoreCase(p.getProperty("value.call.status.code.OK"))
				|| code.equalsIgnoreCase(p.getProperty("value.call.status.code.KO"))) {
			result = true;
		}

		return result;

	}

	public boolean validateMessageStatus(String code) throws IOException {

		p = new Properties();
		InputStream propertiesStream = ClassLoader.getSystemResourceAsStream("validation.properties");
		p.load(propertiesStream);
		propertiesStream.close();

		boolean result = false;

		if (code.equalsIgnoreCase(p.getProperty("value.msg.status.delivered"))
				|| code.equalsIgnoreCase(p.getProperty("value.msg.status.seen"))) {
			result = true;
		}

		return result;

	}
}
