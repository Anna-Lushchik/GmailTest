package com.epam.gmail.business;

import java.util.List;

public class Email {

	private String subject;
	private String to;
	private String body;
	private List<String> smiley;

	public Email(String subject, String to, String body) {
		this.subject = subject;
		this.to = to;
		this.body = body;
	}

	public Email() {
		super();
	}

	public String getTo() {
		return to;
	}

	public void setTo(String to) {
		this.to = to;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public String getBody() {
		return body;
	}

	public void setBody(String body) {
		this.body = body;
	}

	public List<String> getSmiley() {
		return smiley;
	}

	public void setSmiley(List<String> smiley) {
		this.smiley = smiley;
	}

	@Override
	public String toString() {
		return "Email [subject=" + subject + ", to=" + to + ", body=" + body
				+ ", smiley=" + smiley + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((body == null) ? 0 : body.hashCode());
		result = prime * result + ((smiley == null) ? 0 : smiley.hashCode());
		result = prime * result + ((subject == null) ? 0 : subject.hashCode());
		result = prime * result + ((to == null) ? 0 : to.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Email other = (Email) obj;
		if (body == null) {
			if (other.body != null)
				return false;
		} else if (!body.equals(other.body))
			return false;
		if (smiley == null) {
			if (other.smiley != null)
				return false;
		} else if (!smiley.equals(other.smiley))
			return false;
		if (subject == null) {
			if (other.subject != null)
				return false;
		} else if (!subject.equals(other.subject))
			return false;
		if (to == null) {
			if (other.to != null)
				return false;
		} else if (!to.equals(other.to))
			return false;
		return true;
	}
}
