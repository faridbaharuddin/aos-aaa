package com.projektinnovatif.aosaaa.common;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;

public class EmailSender {

	@Autowired
	private JavaMailSender mailSender;
	
	@Value("${siteurl}")
	private String siteUrl;
	
	
	/**
	 * Sends an activation email with the verification token
	 * @param emailAddress
	 * @param verificationToken
	 * @return 
	 * true if the process succeeded
	 * false if the process failed
	 */
	public boolean sendActivationEmail (String emailAddress, String verificationToken) {
		try {
			// TODO: Send to a queue to process
			String subject = "Registration Confirmation";
			String confirmationUrl = siteUrl + "/#/confirm/" + verificationToken;
			String message = "Please visit the following link to verify your email and proceed with account registration\n\n";
			SimpleMailMessage email = new SimpleMailMessage();
			email.setTo(emailAddress);
			email.setSubject(subject);
			email.setText(message + confirmationUrl);
			mailSender.send(email);
			return true;
		} catch (Exception ex) {
			return false;
		}
	}
	
	
	/**
	 * Sends a password reset email with the reset token
	 * @param emailAddress
	 * @param resetToken
	 * @return 
	 * true if the process succeeded
	 * false if the process failed
	 */
	public boolean sendPasswordResetEmail (String emailAddress, String resetToken) {
		try {
			// TODO: Send to a queue to process
			String subject = "Password Reset Request";
			String passwordResetUrl = siteUrl + "/#/resetpassword/" + resetToken;
			String message = "Please visit the following link to reset your password.\n\n";
			SimpleMailMessage email = new SimpleMailMessage();
			email.setTo(emailAddress);
			email.setSubject(subject);
			email.setText(message + passwordResetUrl);
			mailSender.send(email);
			return true;
		} catch (Exception ex) {
			return false;
		}
	}
}
