package by.htp.jd2.util.validation;

import by.htp.jd2.service.ServiceException;
import by.htp.jd2.service.ServiceNewsException;

public interface ValidatorBuilder <T> {
	T build() throws ServiceException, ServiceNewsException;

}
