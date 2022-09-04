package by.htp.jd2.util.validation.impl;

import java.util.Objects;
import java.util.regex.Pattern;

import by.htp.jd2.bean.News;
import by.htp.jd2.service.ServiceNewsException;
import by.htp.jd2.util.validation.ValidatorBuilder;

public class NewsValidation {

	private final boolean newsParameterIsCorrect;

	private NewsValidation(Validator validator) {
		newsParameterIsCorrect = validator.newsParameterIsCorrect;
	}

	public boolean newsParameterIsCorrect() {
		return this.newsParameterIsCorrect;
	}

	public static class Validator implements ValidatorBuilder {

		private final static String DATE_CHECK_PATTERN = "(0[1-9]|[12][0-9]|3[01])[- /.](0[1-9]|1[012])[- /.](19|20)\\d\\d";
		public boolean newsParameterIsCorrect;

		public Validator newsParameterIsCorrect(News news) throws ServiceNewsException {
			newsParameterIsCorrect = validateNewsParameter(news);
			return this;
		}

		public boolean validateNewsParameter(News news) throws ServiceNewsException {

			if (news.getTitle().isEmpty() || news.getBriefNews().isEmpty() || news.getContent().isEmpty()
					|| validateDateNews(news.getNewsDate())) {
				return false;
			}
			return true;
		}

		private boolean validateDateNews(String date) throws ServiceNewsException {
			if (date.isEmpty() || !Pattern.compile(DATE_CHECK_PATTERN).matcher(date).matches()) {
				return false;
			}
			return true;
		}

		public NewsValidation build() throws ServiceNewsException {
			return new NewsValidation(this);
		}
	}

	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (o == null || getClass() != o.getClass())
			return false;
		NewsValidation that = (NewsValidation) o;
		return newsParameterIsCorrect == that.newsParameterIsCorrect;
	}

	@Override
	public int hashCode() {
		return Objects.hash(newsParameterIsCorrect);
	}

	@Override
	public String toString() {
		return "UserDataValidation{" + "newsParameterIsCorrect=" + newsParameterIsCorrect + '}';
	}
}