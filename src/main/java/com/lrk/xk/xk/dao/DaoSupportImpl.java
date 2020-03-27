package com.lrk.xk.xk.dao;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManagerFactory;
import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Collections;
import java.util.List;

/**
 * @author lrk
 * @date 2019/4/27 上午9:12
 */
@SuppressWarnings("unchecked")
public class DaoSupportImpl<T> implements DaoSupport<T> {

    @Autowired
    private EntityManagerFactory entityManagerFactory;

    /**
     * 泛型的类型
     */
    private final Class<T> clazz;

    /**
     * 获取泛型的类型
     */
    @SuppressWarnings("rawtypes")
    private static Class getSuperClassGenricType(final Class clazz, final int index) {
        Type genType = clazz.getGenericSuperclass();
        if (!(genType instanceof ParameterizedType)) {

            return Object.class;

        }
        Type[] params = ((ParameterizedType) genType).getActualTypeArguments();
        if (index >= params.length || index < 0) {
            return Object.class;
        }
        if (!(params[index] instanceof Class)) {
            return Object.class;
        }
        return (Class<?>) params[index];
    }

    /**
     * HibernateBaseDao的构造方法
     */
    @SuppressWarnings("unchecked")
    public DaoSupportImpl() {
        this.clazz = getSuperClassGenricType(this.getClass(), 0);
    }

    //	public DaoSupportImpl() {
    //		ParameterizedType pt = (ParameterizedType) this.getClass().getGenericSuperclass();
    //		this.clazz = (Class<T>) pt.getActualTypeArguments()[0];
    //	}

    /**
     * 获取session
     */
    @Transactional
    protected Session getSession() {
        ////这种方式需要手动关闭session
        //		return entityManagerFactory.unwrap(SessionFactory.class).openSession();
        //		 这种方式会自动关闭session，但是要配置current_session_context_class，并且需要使用事务
        return entityManagerFactory.unwrap(SessionFactory.class).getCurrentSession();
    }

    @Override
    public void save(T entity) {
        Session session = getSession();
        Transaction transaction = session.beginTransaction();
        session.save(entity);
        session.flush();
        transaction.commit();
    }

    @Override
    public void update(T entity) {
        Session session = getSession();
        Transaction transaction = session.beginTransaction();
        session.update(entity);
        session.flush();
        transaction.commit();

    }

    @Override
    public void delete(Serializable id) {
        Session session = getSession();
        Transaction transaction = session.beginTransaction();
        Object obj = findById(id);
        if (obj != null) {
            session.delete(obj);
        }
        transaction.commit();

    }

    public void deleteByIds(Integer[] ids) {
        Session session = getSession();
        Transaction transaction = session.beginTransaction();
        session.createQuery("DELETE FROM " + clazz.getSimpleName() + " WHERE id IN (:ids)")
            .setParameterList("ids", ids).executeUpdate();
        transaction.commit();

    }

    @Override
    public T findById(Serializable id) {
        Session session = getSession();
        T entity;
        if (id == null) {
            entity = null;
        } else {
            entity = (T) session.get(clazz, id);
        }
        return entity;
    }

    @Override
    public List<T> findByIds(Integer[] ids) {
        Session session = getSession();
        List<T> list;
        if (ids == null || ids.length == 0) {
            list = Collections.EMPTY_LIST;
        } else {
            list = session.createQuery("FROM " + clazz.getSimpleName() + " WHERE id IN (:ids)")
                .setParameterList("ids", ids).list();
        }
        return list;
    }

    @Override
    public List<T> findAll() {
        Session session = getSession();
        List<T> list = session.createQuery("FROM " + clazz.getSimpleName()).list();
        return list;
    }

    public boolean isFieldExisted(String field, String value) {
        Session session = getSession();
        List<T> list =
            session.createQuery("FROM " + clazz.getSimpleName() + " WHERE " + field + " = (:field)")
                .setParameter("field", value).list();
        return list.size() != 0;
    }

}
