package com.jspxcms.core.repository;

import com.jspxcms.common.orm.Limitable;
import com.jspxcms.core.domain.VoteMark;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Date;
import java.util.List;

public interface VoteMarkDaoPlus {
	public int countMark(String ftype, Integer fid, Integer userId, String ip,
			String cookie, Date after);

	public List<VoteMark> findList(String ftype, Integer fid, Integer userId, Limitable limitable);

	public Page<VoteMark> findPage(String ftype, Integer fid, Integer userId, Pageable pageable);
}
