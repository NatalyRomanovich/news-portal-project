package by.htp.jd2.util.validation.impl;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.regex.Pattern;

import by.htp.jd2.bean.NewUserInfo;
import by.htp.jd2.bean.News;
import by.htp.jd2.controller.NewsParameter;
import by.htp.jd2.controller.UsersParameter;
import by.htp.jd2.service.ServiceException;
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

		private Map<String, String> validDataForEddNews = new HashMap<String, String>();

		public boolean newsParameterIsCorrect;

		public Validator newsParameterIsCorrect(News news) throws ServiceNewsException {
			newsParameterIsCorrect = validateNewsParameter(news);
			return this;
		}

		public boolean validateNewsParameter(News news) throws ServiceNewsException {

			if (news.getTitle().isEmpty() || news.getBriefNews().isEmpty() || news.getContent().isEmpty()
					|| news.getNewsDate().isEmpty()) {
				return false;
			}
			return true;
		}

		public Validator validateTitle(String title) throws ServiceNewsException {
			if (!(title.isEmpty())) {
				validDataForEddNews.put(NewsParameter.JSP_TITLE, title);
			}
			return this;
		}

		public Validator validateBriefNews(String briefNews) throws ServiceNewsException {
			if (!(briefNews.isEmpty())) {
				validDataForEddNews.put(NewsParameter.JSP_BRIEF_NEWS, briefNews);
			}
			return this;
		}

		public Validator validateContent(String content) throws ServiceNewsException {
			if (!(content.isEmpty())) {
				validDataForEddNews.put(NewsParameter.JSP_CONTENT, content);
			}
			return this;
		}

		public Validator validateDateNews(String date) throws ServiceNewsException {
			if (!(date.isEmpty())) {
				validDataForEddNews.put(NewsParameter.JSP_DATE, date);
			}
			return this;
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
