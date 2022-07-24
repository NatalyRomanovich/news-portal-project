package by.htp.jd2.dao.impl;

import java.util.ArrayList;
import java.util.List;

import com.google.common.base.Objects;

import by.htp.jd2.bean.NewUserInfo;

public class DataBaseAnalogue {
	private List <NewUserInfo>  dataBase = new ArrayList<>();
	private static final DataBaseAnalogue instance = new DataBaseAnalogue();
	
	
	
	public DataBaseAnalogue () {
		
	}
	
	 public DataBaseAnalogue (List <NewUserInfo>  dataBase) {
		 instance.dataBase = dataBase;
		}
	
	public static DataBaseAnalogue getInstance() {
		return instance;
	}
	  
	
    public List <NewUserInfo> getDataBase(){
    	
    	NewUserInfo userUser = new NewUserInfo ("Ivan", "Ivanov", "aaa", "111","ivanov@mail.ru", "user");
		NewUserInfo userAdmin = new NewUserInfo ("Ann", "Ivanova", "bbb", "222","ivanova@mail.ru", "admin");
		 
		dataBase.add(userUser);
		dataBase.add(userAdmin);
		
		return dataBase;
    }
  	
    @Override
    public String toString() {
        return "DataBaseAnalogue{" +
                "dataBase=" + dataBase +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DataBaseAnalogue that = (DataBaseAnalogue) o;
        return Objects.equal(dataBase, that.dataBase);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(dataBase);
    }

   
}
