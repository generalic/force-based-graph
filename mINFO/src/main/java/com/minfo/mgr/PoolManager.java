package com.minfo.mgr;

import java.util.List;
import java.util.Random;

import org.apache.log4j.Logger;

import com.minfo.dao.PoolDAO;
import com.minfo.dao.UserDAO;
import com.minfo.model.Answer;
import com.minfo.model.Pool;
import com.minfo.model.User;
import com.minfo.services.MobileService;

public class PoolManager {
	private PoolDAO poolDAO;
	private UserDAO userDAO;
	private static transient Logger log = Logger
	.getLogger(PoolManager.class);

	
	public void setUserDAO(UserDAO userDAO) {
		this.userDAO = userDAO;
	}


	public void setPoolDAO(PoolDAO poolDAO) {
		this.poolDAO = poolDAO;
	}

	
	public void updatePool(Pool pool) {
		poolDAO.updatePool(pool);
	}
	public void addPool(Pool pool) {
		poolDAO.addPool(pool);
	}
	
	public List<Pool> getPool() {
		return poolDAO.getPool();
	}
	

	public Pool getNextPoolForUser(Long userId) {
		log.debug("enter getNextPoolForUser");
		User u = userDAO.getUser(userId);
		List<Pool> pools = poolDAO.getNewPoolsForUser(userId);
		log.debug("Pools size:"+pools.size());
		log.debug("Pools:"+pools);
		if(pools.size()>0) {
			int idx = new Random().nextInt(pools.size());
			log.debug("idx:"+idx);
			Pool p = pools.get(idx);
			u.getUserDisplayedPools().add(p);
			userDAO.updateUser(u);
			log.debug("p.getUsersDisplayedPools()="+p.getUsersDisplayedPools());
			return p;
		} else {
			return null;
		}
	}
	
	public Pool getPool(Long id) {
		Pool p = poolDAO.getPool(id);
		System.out.println(p);
		return poolDAO.getPool(id);
	}
	
	public Answer getAnswer(Long id) {
		return poolDAO.getAnswer(id);
	}
	
	public void removePool(Long id) {
		poolDAO.removePool(id);
	}
	
	public void makeAnswer(User user,Answer answer) {
		user.getUserAnswers().add(answer);
	}
	
	public void makeAnswer(Long userId,Long answerId) {
		User user = userDAO.getUser(userId);
		Answer answer = poolDAO.getAnswer(answerId);
		user.getUserAnswers().add(answer);
		userDAO.updateUser(user);
	}
}
