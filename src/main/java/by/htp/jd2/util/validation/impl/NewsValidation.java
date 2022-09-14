package by.htp.jd2.util.validation.impl;

import java.util.Objects;
import java.util.regex.Pattern;

import by.htp.jd2.bean.News;
import by.htp.jd2.service.ServiceNewsException;
import by.htp.jd2.util.validation.ValidatorBuilder;
import by.htp.jd2.util.validation.impl.NewsValidation.Validator;

public class NewsValidation {

	private final boolean newsParameterIsCorrectForAddNews;
	private final boolean newsParameterIsCorrectForEditNews;

	private NewsValidation(Validator validator) {
		newsParameterIsCorrectForAddNews = validator.newsParameterIsCorrectForAddNews;
		newsParameterIsCorrectForEditNews = validator.newsParameterIsCorrectForEditNews;
	}

	public boolean newsParameterIsCorrectForAddNews() {
		return this.newsParameterIsCorrectForAddNews;
	}

	public boolean newsParameterIsCorrectForEditNews() {
		return this.newsParameterIsCorrectForEditNews;
	}

	public static class Validator implements ValidatorBuilder {

		private final static String DATE_CHECK_PATTERN_FOR_ADD_NEWS = "((?:19|20)[0-9][0-9])-(0?[1-9]|1[012])-(0?[1-9]|[12][0-9]|3[01])";
		private final static String DATE_CHECK_PATTERN_FOR_EDIT_NEWS = "(0?[1-9]|[12][0-9]|3[01])/(0?[1-9]|1[012])/((?:19|20)[0-9][0-9])";
		public boolean newsParameterIsCorrectForAddNews;
		public boolean newsParameterIsCorrectForEditNews;

		public Validator newsParameterIsCorrectForAddNews(News news) throws ServiceNewsException {
			newsParameterIsCorrectForAddNews = isNewsParameterCorrectForAddNews(news);
			return this;
		}

		public Validator newsParameterIsCorrectForEditNews(News news) throws ServiceNewsException {
			newsParameterIsCorrectForEditNews = isNewsParameterCorrectForEditNews(news);
			return this;
		}

		public boolean isNewsParameterCorrectForAddNews(News news) throws ServiceNewsException {

			if (news.getTitle().isEmpty() || news.getContent().isEmpty()
					|| !isDateNewsCorrectForAddNews(news.getNewsDate())) {
				return false;
			}
			return true;
		}

		public boolean isNewsParameterCorrectForEditNews(News news) throws ServiceNewsException {

			if (news.getTitle().isEmpty() || news.getContent().isEmpty()
					|| !isDateNewsCorrectForEditNews(news.getNewsDate())) {
				return false;
			}
			return true;
		}

		private boolean isDateNewsCorrectForAddNews(String date) throws ServiceNewsException {
			if (date.isEmpty() || !Pattern.compile(DATE_CHECK_PATTERN_FOR_ADD_NEWS).matcher(date).matches()) {
				return false;
			}
			return true;
		}

		private boolean isDateNewsCorrectForEditNews(String date) throws ServiceNewsException {
			if (date.isEmpty() || !Pattern.compile(DATE_CHECK_PATTERN_FOR_EDIT_NEWS).matcher(date).matches()) {
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
		return newsParameterIsCorrectForAddNews == that.newsParameterIsCorrectForAddNews
				&& newsParameterIsCorrectForEditNews == that.newsParameterIsCorrectForEditNews;
	}

	@Override
	public int hashCode() {
		return Objects.hash(newsParameterIsCorrectForAddNews, newsParameterIsCorrectForEditNews);
	}

	@Override
	public String toString() {
		return "UserDataValidation{" + "newsParameterIsCorrectForAddNews=" + newsParameterIsCorrectForAddNews
				+ "newsParameterIsCorrectForEditNews" + newsParameterIsCorrectForEditNews + '}';
	}
}