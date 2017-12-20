package com.sample.ZKSpringJPA.services.impl;

import com.sample.ZKSpringJPA.entity.Log;
import com.sample.ZKSpringJPA.services.MyService;
import java.util.List;

import com.sample.ZKSpringJPA.services.authentication.dao.LogDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Service;

@Service("myService")
@Scope(value = "singleton", proxyMode = ScopedProxyMode.TARGET_CLASS)
public class MyServiceImpl implements MyService {

	@Autowired
    LogDao dao;

	public Log addLog(Log log) {
		return dao.save(log);
	}

	public List<Log> getLogs() {
		return dao.queryAll();
	}

	public void deleteLog(Log log) {
		dao.delete(log);
	}

}
