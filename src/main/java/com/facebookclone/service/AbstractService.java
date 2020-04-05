package com.facebookclone.service;

import java.util.List;

public abstract class AbstractService<T> {
public abstract T findOne(T id);
	
	public abstract List<T> findAll();
	
	public abstract T create(T entity);
	
	public abstract T deleteById(T id);
	
	public abstract T updateById(T id);

}
