package ar.edu.itba.it.paw.web.converter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import ar.edu.itba.it.paw.domain.Type;
import ar.edu.itba.it.paw.domain.User;
import ar.edu.itba.it.paw.services.UserServices;

@Component
public class UserConverter implements Converter<String, User>{
	
	UserServices service;
	
	@Autowired
	public UserConverter( UserServices service){
		this.service = service;
	}

	@Override
	public User convert(String source) {
		System.out.println("converter de usuario");
		Integer id = Integer.valueOf(source);
		User user;
		if (id == 0){
			//en este caso es una representacion en el formulario que significa 'todos los usuarios'
			user = new User("EVERYBODY","Cualquier","Usuario","Cualquiera",Type.ADMIN,true);
		}else{
			user = service.getUser(id);
		}
		System.out.println(user.getUsername());
		return user;
		
	}
}
