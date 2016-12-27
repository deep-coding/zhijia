package com.jspxcms.core.repository.impl;

import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import com.jspxcms.common.orm.Limitable;
import com.jspxcms.common.orm.QuerydslUtils;
import com.jspxcms.core.domain.VoteMark;
import org.apache.commons.lang3.StringUtils;

import com.jspxcms.core.domaindsl.QVoteMark;
import com.jspxcms.core.repository.VoteMarkDaoPlus;
import com.mysema.query.BooleanBuilder;
import com.mysema.query.jpa.impl.JPAQuery;
import org.hibernate.jpa.QueryHints;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public class VoteMarkDaoImpl implements VoteMarkDaoPlus {
	public int countMark(String ftype, Integer fid, Integer userId, String ip,
			String cookie, Date after) {
		JPAQuery query = new JPAQuery(this.em);
		QVoteMark bean = QVoteMark.voteMark;
		query.from(bean);
		BooleanBuilder exp = new BooleanBuilder();
		exp = exp.and(bean.ftype.eq(ftype));
		exp = exp.and(bean.fid.eq(fid));
		if (userId != null) {
			exp = exp.and(bean.user.id.eq(userId));
		} else if (StringUtils.isNotBlank(ip)) {
			exp = exp.and(bean.ip.eq(ip));
		} else if (StringUtils.isNotBlank(cookie)) {
			exp = exp.and(bean.cookie.eq(cookie));
		} else {
			throw new IllegalArgumentException(
					"userId or ip or cookie is required.");
		}
		if (after != null) {
			exp = exp.and(bean.date.after(after));
		}
		query.where(exp);
		return query.list(bean.count()).iterator().next().intValue();
	}

	public List<VoteMark> findList(String ftype, Integer fid, Integer userId, Limitable limitable) {
		JPAQuery query = new JPAQuery(this.em);
		query.setHint(QueryHints.HINT_CACHEABLE, true);
		QVoteMark voteMark = QVoteMark.voteMark;
		predicate(query, voteMark, ftype, fid, userId);
		return QuerydslUtils.list(query, voteMark, limitable);
	}

	public Page<VoteMark> findPage(String ftype, Integer fid, Integer userId, Pageable pageable) {
		JPAQuery query = new JPAQuery(this.em);
		query.setHint(QueryHints.HINT_CACHEABLE, true);
		QVoteMark voteMark = QVoteMark.voteMark;
		predicate(query, voteMark, ftype, fid, userId);
		return QuerydslUtils.page(query, voteMark, pageable);
	}

	private void predicate(JPAQuery query, QVoteMark voteMark, String ftype, Integer fid, Integer userId) {
		query.from(voteMark);
		BooleanBuilder exp = new BooleanBuilder();
		if (!StringUtils.isBlank(ftype)){
			exp = exp.and(voteMark.ftype.eq(ftype));
		}
		if (fid != null) {
			exp = exp.and(voteMark.fid.eq(fid));
		}
		if (userId != null) {
			exp = exp.and(voteMark.user.id.eq(userId));
		}
		query.where(exp);
	}

	private EntityManager em;

	@PersistenceContext
	public void setEm(EntityManager em) {
		this.em = em;
	}
}
