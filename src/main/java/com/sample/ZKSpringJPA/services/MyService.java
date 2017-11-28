package com.sample.ZKSpringJPA.services;

import com.sample.ZKSpringJPA.entity.Log;
import java.util.List;

public interface MyService {

	Log addLog(Log log);

	List<Log> getLogs();

	void deleteLog(Log log);
}
