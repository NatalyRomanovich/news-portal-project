package by.htp.jd2.service;

import java.util.List;

import by.htp.jd2.bean.News;

public interface NewsService {
  void save();
  void find();
  void update();
  
  List<News> latestList(int count)  throws ServiceException;
  List<News> list()  throws ServiceException;
  News findById(int id) throws ServiceException;
}
